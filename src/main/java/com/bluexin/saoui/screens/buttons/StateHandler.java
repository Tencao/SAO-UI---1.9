package com.bluexin.saoui.screens.buttons;

import com.bluexin.saoui.screens.buttons.ButtonState;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface StateHandler {

    boolean isStateEnabled(Minecraft mc, ButtonState button);

}
