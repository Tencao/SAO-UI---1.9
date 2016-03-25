package com.bluexin.saoui.screens;

import com.bluexin.saoui.GLCore;
import com.bluexin.saoui.resources.StringNames;
import com.bluexin.saoui.util.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class VLine extends Elements {

    private int lineWidth;

    public VLine(ParentElement gui, int xPos, int yPos, int size) {
        super(gui, xPos, yPos, size, 2);
        lineWidth = size;
    }

    @Override
    public void draw(Minecraft mc, int cursorX, int cursorY) {
        super.draw(mc, cursorX, cursorY);

        if (visibility > 0) {
            GLCore.glBindTexture(OptionCore.ORIGINAL_UI.getValue() ? StringNames.gui : StringNames.guiCustom);
            GLCore.glColorRGBA(ColorUtil.DEFAULT_FONT_COLOR.multiplyAlpha(visibility));

            final int left = getX(false) + (width - lineWidth) / 2;
            final int top = getY(false);

            GLCore.glTexturedRect(left, top, lineWidth, 2, 42, 42, 4, 2);
        }
    }

}
