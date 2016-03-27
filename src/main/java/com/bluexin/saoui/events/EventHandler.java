package com.bluexin.saoui.events;

import com.bluexin.saoui.SAOCore;
import com.bluexin.saoui.SoundCore;
import com.bluexin.saoui.commands.Command;
import com.bluexin.saoui.colorstates.ColorStateHandler;
import com.bluexin.saoui.colorstates.ColorState;
import com.bluexin.saoui.util.OptionCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EventHandler {

    private final Minecraft mc = Minecraft.getMinecraft();
    private boolean isPlaying = false;

    public void stateChanger(EntityLivingBase entity, boolean major, boolean aggro){
        if (entity instanceof EntityPlayer) {
            ColorState state = ColorStateHandler.getInstance().getSavedState(entity);
            if (major){
                if (state == ColorState.VIOLENT) {
                    ColorStateHandler.getInstance().set(entity, ColorState.KILLER, true);
                }else if (state == ColorState.INNOCENT || state == null)
                    ColorStateHandler.getInstance().set(entity, ColorState.VIOLENT, true);
            } else if (state == ColorState.INNOCENT || state == null)
                ColorStateHandler.getInstance().set(entity, ColorState.VIOLENT, true);
        }
        else {
            ColorState defaultState =ColorStateHandler.getInstance().getDefault(entity);
            ColorState state = ColorStateHandler.getInstance().getSavedState(entity);
            if (aggro && defaultState == ColorState.VIOLENT)
                ColorStateHandler.getInstance().set(entity, ColorState.KILLER, true);
            else if (major) {
                if (state == ColorState.INNOCENT)
                    ColorStateHandler.getInstance().set(entity, ColorState.VIOLENT, true);
                else if (state == ColorState.VIOLENT)
                    ColorStateHandler.getInstance().set(entity, ColorState.KILLER, true);
            } else {
                if (defaultState == ColorState.INNOCENT && state != ColorState.VIOLENT)
                    ColorStateHandler.getInstance().set(entity, ColorState.VIOLENT, true);
                else if (defaultState == ColorState.VIOLENT && state != ColorState.KILLER)
                    ColorStateHandler.getInstance().set(entity, ColorState.KILLER, true);
            }
        }
    }

    public static void getColor(EntityLivingBase entity){
        ColorStateHandler.getInstance().stateColor(entity);
    }

    @SubscribeEvent
    public void checkAggro(LivingSetAttackTargetEvent e) {
        if (OptionCore.AGGRO_SYSTEM.getValue() && ColorStateHandler.getInstance().getSavedState(e.getEntityLiving()) != ColorState.KILLER  && e.getPhase().equals(TickEvent.Phase.END))
            if (e.getTarget() instanceof EntityPlayer) {
                stateChanger(e.getEntityLiving(), false, true);
                if (OptionCore.DEBUG_MODE.getValue()) System.out.print(e.getEntityLiving().getName() + " sent to State Changer from checkAggro" + "\n");
            }
    }

    @SubscribeEvent
    public void checkAttack(LivingAttackEvent e) {
        if (OptionCore.AGGRO_SYSTEM.getValue() && e.getPhase().equals(TickEvent.Phase.END))
            if (e.getSource().getEntity() instanceof IAnimals)
                if (e.getEntityLiving() instanceof EntityPlayer) {
                    if (e.getEntityLiving().getHealth() <= 0)
                        stateChanger((EntityLivingBase) e.getSource().getEntity(), true, false);
                    else stateChanger((EntityLivingBase) e.getSource().getEntity(), false, false);
                    if (OptionCore.DEBUG_MODE.getValue())
                        System.out.print(e.getSource().getEntity().getName() + " sent to State Changer from checkAttack" + "\n");
                }
    }

    @SubscribeEvent
    public void checkPlayerAttack(AttackEntityEvent e) {
        if (OptionCore.AGGRO_SYSTEM.getValue() && e.getPhase().equals(TickEvent.Phase.END))
            if (e.getTarget() instanceof EntityPlayer && e.getTarget().getUniqueID() != e.getEntityPlayer().getUniqueID()) {
                if (((EntityPlayer) e.getTarget()).getHealth() <= 0) stateChanger(e.getEntityPlayer(), true, false);
                else stateChanger(e.getEntityPlayer(), false, false);
                if (OptionCore.DEBUG_MODE.getValue())
                    System.out.print(e.getEntityPlayer().getName() + " sent to State Changer from checkPlayerAttack" + "\n");
            }
    }

    @SubscribeEvent
    public void checkKill(LivingDeathEvent e){
        if (OptionCore.AGGRO_SYSTEM.getValue() && e.getPhase().equals(TickEvent.Phase.END)) {
            if (e.getSource().getEntity() instanceof EntityLivingBase)
                if (e.getEntityLiving() instanceof EntityPlayer) {
                    stateChanger((EntityLivingBase) e.getSource().getEntity(), true, false);
                    if (OptionCore.DEBUG_MODE.getValue())
                        System.out.print(e.getSource().getEntity().getName() + " sent to State Changer from checkKill" + "\n");
                }
            if (!(e.getEntityLiving() instanceof EntityPlayer)) ColorStateHandler.getInstance().remove(e.getEntityLiving());
        }
        if (OptionCore.PARTICLES.getValue() && e.getEntity().worldObj.isRemote) RenderHandler.deadHandlers.add(e.getEntityLiving());
    }

    @SubscribeEvent
    public void resetState(TickEvent.RenderTickEvent e){
        if (OptionCore.AGGRO_SYSTEM.getValue())ColorStateHandler.getInstance().updateKeeper();
        if (!OptionCore.AGGRO_SYSTEM.getValue()){
            if (!ColorStateHandler.getInstance().isEmpty())ColorStateHandler.getInstance().clean();
        }
    }

    @SubscribeEvent
    public void cleanStateMaps(FMLNetworkEvent.ClientDisconnectionFromServerEvent e){
        ColorStateHandler.getInstance().clean();
    }

    @SubscribeEvent
    public void disableAggroOnServer(FMLNetworkEvent.ClientConnectedToServerEvent e){
        if (!e.isLocal() && OptionCore.AGGRO_SYSTEM.getValue()) OptionCore.AGGRO_SYSTEM.flip();
    }

    @SubscribeEvent
    public void nameNotification(ClientChatReceivedEvent e){
        if (e.getMessage().getUnformattedTextForChat().contains(mc.thePlayer.getDisplayNameString())) SoundCore.play(mc, SoundCore.MESSAGE);
    }

    @SubscribeEvent
    public void genStateMaps(EntityEvent.EntityConstructing e){
        if (e.getEntity() instanceof EntityLivingBase)
            if (ColorStateHandler.getInstance().getDefault((EntityLivingBase)e.getEntity()) == null && !(e.getEntity() instanceof EntityPlayer))
                ColorStateHandler.getInstance().genDefaultState((EntityLivingBase)e.getEntity());
    }

    @SubscribeEvent
    public void abilityCheck(TickEvent.ClientTickEvent e) {
        if (mc.thePlayer == null) {
            SAOCore.IS_SPRINTING = false;
            SAOCore.IS_SNEAKING = false;
        } else if (mc.inGameHasFocus) {
            if (SAOCore.IS_SPRINTING) KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
            if (SAOCore.IS_SNEAKING) KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void chatEvent(ClientChatReceivedEvent evt) {
        if (Command.processCommand(evt.getMessage().getUnformattedText())) evt.setCanceled(true);// TODO: add pm feature and PT chat
    }

}

