package com.bluexin.saoui.screens.buttons;

import com.bluexin.saoui.screens.Elements;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface ActionHandler {

    void actionPerformed(Elements element, Actions action, int data);

}
