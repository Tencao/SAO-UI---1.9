package com.bluexin.saoui.screens;

import com.bluexin.saoui.GLCore;
import com.bluexin.saoui.resources.StringNames;
import com.bluexin.saoui.screens.menu.Categories;
import com.bluexin.saoui.util.*;
import net.minecraft.client.Minecraft;

public class Alert extends Elements {

    @SuppressWarnings("unused")
    private String caption;
    private ColorUtil alertColor;

    private Alert(ParentElement gui, int xPos, int yPos, int w, String string, ColorUtil color) {
        super(gui, xPos, yPos, w, 32);
        caption = string;
        alertColor = color;
    }

    public Alert(ParentElement gui, int xPos, int yPos, String string, ColorUtil color) {
        this(gui, xPos, yPos, autoWidth(string), string, color);
    }

    private static int autoWidth(String string) {
        final int defValue = GLCore.glStringWidth(string);

        return Math.max(0, defValue - 20);
    }

    @Override
	public void draw(Minecraft mc, int cursorX, int cursorY) {
        super.draw(mc, cursorX, cursorY);

        if (visibility > 0) {
            GLCore.glStart();
            GLCore.glBindTexture(OptionCore.ORIGINAL_UI.getValue() ? StringNames.gui : StringNames.guiCustom);

            final int color = mouseOver(cursorX, cursorY) ? alertColor.mediumColor(ColorUtil.DEFAULT_FONT_COLOR) : alertColor.rgba;

            GLCore.glColorRGBA(ColorUtil.multiplyAlpha(color, visibility));

            final int left = getX(false);
            final int top = getY(false);

            GLCore.glTexturedRect(left - 20, top, 0, 155, 20, height);
            GLCore.glTexturedRect(left, top, width, height, 20, 155, 40, height);
            GLCore.glTexturedRect(left + width, top, 60, 155, 20, height);

            // Handled texture-wise now... Yeah lazy ^-^
            //SAOGL.glString(caption, left + (width - SAOGL.glStringWidth(caption)) / 2, top + (height - SAOGL.glStringHeight()) / 2, alertColor);

            //GlStateManager.scale(0.5F, 0.5F, 0.5F);
            //SAOGL.glString(caption, (int) (left + (width - SAOGL.glStringWidth(caption) / 4) / 16) * 2, (int) (top + (height - SAOGL.glStringHeight()) / 1.5F) * 2, alertColor);
            //GlStateManager.scale(1.0F, 1.0F, 1.0F); // There might be a better way of doing this. Whatever.
            GLCore.glEnd();
        }
    }

    @Override
	public boolean mouseReleased(Minecraft mc, int cursorX, int cursorY, int button) {
        return (button == 0);
    }

    @Override
	public int getX(boolean relative) {
        return super.getX(relative) - width / 2;
    }

    @Override
	public int getY(boolean relative) {
        return super.getY(relative) - height / 2;
    }

    @Override
	public Categories ID() {
        return Categories.ALERT;
    }

}
