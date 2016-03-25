package com.bluexin.saoui.screens;

import com.bluexin.saoui.GLCore;
import com.bluexin.saoui.SoundCore;
import com.bluexin.saoui.resources.StringNames;
import com.bluexin.saoui.screens.menu.Categories;
import com.bluexin.saoui.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class IconGUI extends Elements {

    private final Categories id;
    public boolean highlight;
    public ColorUtil bgColor, disabledMask;
    private IconCore icon;

    public IconGUI(ParentElement gui, Categories saoID, int xPos, int yPos, IconCore iconCore) {
        super(gui, xPos, yPos, 20, 20);
        id = saoID;
        icon = iconCore;
        highlight = false;
        bgColor = ColorUtil.DEFAULT_COLOR;
        disabledMask = ColorUtil.DISABLED_MASK;
    }

    @Override
    public void draw(Minecraft mc, int cursorX, int cursorY) {
        super.draw(mc, cursorX, cursorY);

        if (visibility > 0) {
            GLCore.glBindTexture(OptionCore.ORIGINAL_UI.getValue() ? StringNames.gui : StringNames.guiCustom);

            final int hoverState = hoverState(cursorX, cursorY);

            final int color0 = getColor(hoverState, true);
            final int color1 = getColor(hoverState, false);

            GLCore.glColorRGBA(ColorUtil.multiplyAlpha(color0, visibility));

            final int left = getX(false);
            final int top = getY(false);

            GLCore.glTexturedRect(left, top, 0, 25, 20, 20);

            final int iconOffset = 2;

            GLCore.glColorRGBA(ColorUtil.multiplyAlpha(color1, visibility));
            icon.glDraw(left + iconOffset, top + iconOffset);
        }
    }

    private int getColor(int hoverState, boolean bg) {
        if (icon == IconCore.CONFIRM)
            return bg ? hoverState == 1 ? ColorUtil.CONFIRM_COLOR.rgba : hoverState == 2 ? ColorUtil.CONFIRM_COLOR_LIGHT.rgba : ColorUtil.CONFIRM_COLOR.rgba & disabledMask.rgba : hoverState > 0 ? ColorUtil.HOVER_FONT_COLOR.rgba : disabledMask.rgba;
        else if (icon == IconCore.CANCEL)
            return bg ? hoverState == 1 ? ColorUtil.CANCEL_COLOR.rgba : hoverState == 2 ? ColorUtil.CANCEL_COLOR_LIGHT.rgba : ColorUtil.CANCEL_COLOR.rgba & disabledMask.rgba : hoverState > 0 ? ColorUtil.HOVER_FONT_COLOR.rgba : disabledMask.rgba;
        else
            return bg ? hoverState == 1 ? bgColor.rgba : hoverState == 2 ? ColorUtil.HOVER_COLOR.rgba : bgColor.rgba & disabledMask.rgba : hoverState == 1 ? ColorUtil.DEFAULT_FONT_COLOR.rgba : hoverState == 2 ? ColorUtil.HOVER_FONT_COLOR.rgba : ColorUtil.DEFAULT_FONT_COLOR.rgba & disabledMask.rgba;


    }

    @Override
    public boolean mouseReleased(Minecraft mc, int cursorX, int cursorY, int button) {
        return (button == 0);
    }

    @Override
    public void click(SoundHandler handler, boolean flag) {
        if (icon == IconCore.CONFIRM) SoundCore.play(handler, SoundCore.CONFIRM);
        else super.click(handler, flag);
    }

    private int hoverState(int cursorX, int cursorY) {
        return highlight || mouseOver(cursorX, cursorY) ? 2 : enabled ? 1 : 0;
    }

    @Override
    public Categories ID() {
        return id;
    }

}
