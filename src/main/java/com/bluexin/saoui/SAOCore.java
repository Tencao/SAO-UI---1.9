package com.bluexin.saoui;

import com.bluexin.saoui.events.EventHandler;
import com.bluexin.saoui.events.RenderHandler;
import com.bluexin.saoui.screens.window.WindowView;
import com.bluexin.saoui.screens.window.Window;
import com.bluexin.saoui.events.ConfigHandler;
import com.bluexin.saoui.events.FriendsHandler;
import com.bluexin.saoui.util.OptionCore;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = SAOCore.MODID, name = SAOCore.NAME, version = SAOCore.VERSION)
@SideOnly(Side.CLIENT)
public class SAOCore {
    public static final String MODID = "saoui";
    public static final String NAME = "Sword Art Online UI";
    public static final String VERSION = "1.5.5";
    public static final float UNKNOWN_TIME_DELAY = -1F;
    public static final double MAX_RANGE = 256.0D;
    public static boolean IS_SPRINTING = false; // TODO: move somewhere else, maybe make skills have a activate/deactivate thing
    public static boolean IS_SNEAKING = false;
    public static boolean verChecked = false;
    public static boolean replaceGUI = RenderHandler.replaceGUI;
    // TODO: optimize things, ie remove public and static!

    @Mod.Instance(MODID)
    public static SAOCore instance;

    public static Window getWindow(Minecraft mc) {
        return mc.currentScreen != null && mc.currentScreen instanceof WindowView ? ((WindowView) mc.currentScreen).getWindow() : null;
    }


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        FMLCommonHandler.instance().bus().register(new RenderHandler());
        MinecraftForge.EVENT_BUS.register(new RenderHandler());

        ConfigHandler.preInit(event);
        FriendsHandler.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        replaceGUI = true;
    }

    @SuppressWarnings("unchecked")
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent evt) {
        final Minecraft mc = Minecraft.getMinecraft();

        GLCore.setFont(mc, OptionCore.CUSTOM_FONT.getValue());
    }

}
