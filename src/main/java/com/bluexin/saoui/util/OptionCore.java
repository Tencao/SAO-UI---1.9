package com.bluexin.saoui.util;

import com.bluexin.saoui.GLCore;
import com.bluexin.saoui.events.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.stream.Stream;

@SideOnly(Side.CLIENT)
public enum OptionCore {

    //Main Categories
    VANILLA_OPTIONS(I18n.translateToLocal("guiOptions"), false, false, null),
    UI(I18n.translateToLocal("optCatUI"), false, true, null),
    HEALTH_OPTIONS(I18n.translateToLocal("optCatHealth"), false, true, null),
    HOTBAR_OPTIONS(I18n.translateToLocal("optCatHotBar"), false, true, null),
    EFFECTS(I18n.translateToLocal("optCatEffects"), false, true, null),
    MISC(I18n.translateToLocal("optCatMisc"), false, true, null),
    //General UI Settings
    DEFAULT_UI(I18n.translateToLocal("optionDefaultUI"), false, false, UI),
    DEFAULT_INVENTORY(I18n.translateToLocal("optionDefaultInv"), true, false, UI),
    DEFAULT_DEATH_SCREEN(I18n.translateToLocal("optionDefaultDeath"), false, false, UI),
    DEFAULT_DEBUG(I18n.translateToLocal("optionDefaultDebug"), false, false, UI),
    ORIGINAL_UI(I18n.translateToLocal("optionOrigUI"), true, false, UI),
    FORCE_HUD(I18n.translateToLocal("optionForceHud"), false, false, UI),
    LOGOUT(I18n.translateToLocal("optionLogout"), false, false, UI),
    GUI_PAUSE(I18n.translateToLocal("optionGuiPause"), true, false, UI),
    // Health Options
    SMOOTH_HEALTH(I18n.translateToLocal("optionSmoothHealth"), true, false, HEALTH_OPTIONS),
    HEALTH_BARS(I18n.translateToLocal("optionHealthBars"), true, false, HEALTH_OPTIONS),
    REMOVE_HPXP(I18n.translateToLocal("optionLightHud"), false, false, HEALTH_OPTIONS),
    //DEFAULT_HEALTH(I18n.translateToLocal("optionDefaultHealth"), false, false, HEALTH_OPTIONS),
    ALT_ABSORB_POS(I18n.translateToLocal("optionAltAbsorbPos"), false, false, HEALTH_OPTIONS),
    //Hotbar
    DEFAULT_HOTBAR(I18n.translateToLocal("optionDefaultHotbar"), false, false, HOTBAR_OPTIONS),
    ALT_HOTBAR(I18n.translateToLocal("optionAltHotbar"), false, false, HOTBAR_OPTIONS),
    //Effects
    COLOR_CURSOR(I18n.translateToLocal("optionColorCursor"), true, false, EFFECTS),
    CURSOR_MOVEMENT(I18n.translateToLocal("optionCursorMov"), true, false, EFFECTS),
    SPINNING_CRYSTALS(I18n.translateToLocal("optionSpinning"), true, false, EFFECTS),
    PARTICLES(I18n.translateToLocal("optionParticles"), true, false, EFFECTS),
    LESS_VISUALS(I18n.translateToLocal("optionLessVis"), false, false, EFFECTS),
    SOUND_EFFECTS(I18n.translateToLocal("optionSounds"), true, false, EFFECTS),
    //Misc
    CROSS_HAIR(I18n.translateToLocal("optionCrossHair"), false, false, MISC),
    AGGRO_SYSTEM(I18n.translateToLocal("optionAggro"), false, false, MISC),
    CLIENT_CHAT_PACKETS(I18n.translateToLocal("optionCliChatPacks"), true, false, MISC),
    MOUNT_STAT_VIEW(I18n.translateToLocal("optionMountStatView"), true, false, MISC),
    CUSTOM_FONT(I18n.translateToLocal("optionCustomFont"), false, false, MISC),
    DEBUG_MODE(I18n.translateToLocal("optionDebugMode"), false, false, MISC),
    COMPACT_INVENTORY(I18n.translateToLocal("optionCompatInv"), false, false, MISC);

    public final String name;
    public final boolean isCategory;
    public final OptionCore category;
    private boolean value;

    OptionCore(String optionName, boolean defaultValue, boolean isCat, OptionCore category) {
        name = optionName;
        value = defaultValue;
        isCategory = isCat;
        this.category= category;
    }

    public static OptionCore fromString(String str) {
        return Stream.of(values()).filter(option -> option.toString().equals(str)).findAny().orElse(null);
    }

    @Override
    public final String toString() {
        return name;
    }

    public boolean flip() {
        this.value = !this.getValue();
        ConfigHandler.setOption(this);
        if (this == CUSTOM_FONT) GLCore.setFont(Minecraft.getMinecraft(), this.value);
        return this.value;
    }

    public boolean getValue() {
        return this.value;
    }

    public void disable() {
        if (this.value) this.flip();
    }

    public void enable() {
        if (!this.value) this.flip();
    }
}
