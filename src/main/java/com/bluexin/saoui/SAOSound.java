package com.bluexin.saoui;

import com.bluexin.saoui.util.SAOOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

public class SAOSound {

    public static final String CONFIRM = "confirm";
    public static final String DIALOG_CLOSE = "dialog_close";
    public static final String MENU_POPUP = "menu_popup";
    public static final String MESSAGE = "message";
    public static final String ORB_DROPDOWN = "orb_dropdown";
    public static final String PARTICLES_DEATH = "particles_death";
    public static final String LOW_HEALTH = "low_health";
    
    private static ResourceLocation getResource(String name) {
        return new ResourceLocation(SAOMod.MODID, name);
    }

    public static void playFromEntity(Entity entity, String name) {
        if (entity != null) {
            playAtEntity(entity, name);
        }
    }

    public static void playAtEntity(Entity entity, String name) {
        final Minecraft mc = Minecraft.getMinecraft();

        if (mc != null && mc.theWorld.isRemote) {
            play(mc.getSoundHandler(), name, (float) entity.posX, (float) entity.posY, (float) entity.posZ);
        }
    }

    public static void play(Minecraft mc, String name) {
        if (mc != null && mc.theWorld.isRemote) {
            play(mc.getSoundHandler(), name);
        }
    }

    public static void play(SoundHandler handler, String name) {
        if ((SAOOption.SOUND_EFFECTS.getValue()) && (handler != null)) {
            handler.playSound(create(getResource(name)));
        }
    }

    private static void play(SoundHandler handler, String name, float x, float y, float z) {
        if ((SAOOption.SOUND_EFFECTS.getValue()) && (handler != null)) {
            handler.playSound(create(getResource(name), x, y, z));
        }
    }

    //Helper functions - 1.8.8 mirror
    public static PositionedSoundRecord create(ResourceLocation soundResource)
    {
        return new PositionedSoundRecord(soundResource, SoundCategory.MASTER, 1.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
    }

    public static PositionedSoundRecord create(ResourceLocation soundResource, float xPosition, float yPosition, float zPosition)
    {
        return new PositionedSoundRecord(soundResource, SoundCategory.MASTER, 4.0F, 1.0F, false, 0, ISound.AttenuationType.LINEAR, xPosition, yPosition, zPosition);
    }

}
