package com.bluexin.saoui.screens.window.ui;

import com.bluexin.saoui.screens.buttons.ButtonGUI;
import com.bluexin.saoui.screens.menu.Categories;
import com.bluexin.saoui.util.IconCore;
import com.bluexin.saoui.screens.ParentElement;
import net.minecraft.stats.Achievement;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AchievementList extends ButtonGUI {

    private final Achievement achievement;

    private AchievementList(ParentElement gui, int xPos, int yPos, int w, int h, Achievement ach0) {
        super(gui, Categories.QUEST, xPos, yPos, w, h, ach0.getStatName().getFormattedText(), IconCore.QUEST);
        achievement = ach0;
    }

    public AchievementList(ParentElement gui, int xPos, int yPos, int w, Achievement ach0) {
        this(gui, xPos, yPos, w, 20, ach0);
    }

    public AchievementList(ParentElement gui, int xPos, int yPos, Achievement ach0) {
        this(gui, xPos, yPos, 150, ach0);
    }

    public Achievement getAchievement() {
        return achievement;
    }

}
