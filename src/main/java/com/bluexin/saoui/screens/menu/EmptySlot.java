package com.bluexin.saoui.screens.menu;

import com.bluexin.saoui.util.ColorUtil;
import com.bluexin.saoui.util.IconCore;
import com.bluexin.saoui.screens.ParentElement;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

/**
 * Part of SAOUI
 *
 * @author Bluexin
 */
public final class EmptySlot extends Slots {
    public EmptySlot(ParentElement gui, int xPos, int yPos) {
        super(gui, xPos, yPos, null);
    }

    @Override
    protected String getCaption() {
        return String.format("(%s)", I18n.translateToLocal("gui.empty"));
    }

    @Override
    protected boolean isEmpty() {
        return false;
    }

    @Override
    protected IconCore getIcon() {
        return IconCore.NONE;
    }

    @Override
    public void refreshSlot(Slot slot) {
    }

    @Override
    public int getSlotNumber() {
        return -1;
    }

    @Override
    public ItemStack getStack() {
        return null;
    }

    @Override
    protected int getColor(int hoverState, boolean bg) {
        return bg? ColorUtil.DEFAULT_COLOR.rgba: ColorUtil.DEFAULT_FONT_COLOR.rgba;
    }
}
