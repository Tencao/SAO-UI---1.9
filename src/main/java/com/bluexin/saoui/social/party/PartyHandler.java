package com.bluexin.saoui.social.party;

import com.bluexin.saoui.screens.ParentElement;
import com.bluexin.saoui.screens.buttons.ButtonState;
import com.bluexin.saoui.screens.buttons.StateHandler;
import com.bluexin.saoui.screens.menu.Categories;
import com.bluexin.saoui.util.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PartyHandler extends ButtonState {

    private PartyHandler(ParentElement gui, Categories saoID, int xPos, int yPos, int w, int h, String string, IconCore iconCore) {
        super(gui, saoID, xPos, yPos, w, h, string, iconCore, new SAOPartyStateHandler(saoID));
    }

    private PartyHandler(ParentElement gui, Categories saoID, int xPos, int yPos, int w, String string, IconCore iconCore) {
        this(gui, saoID, xPos, yPos, w, 20, string, iconCore);
    }

    public PartyHandler(ParentElement gui, Categories saoID, int xPos, int yPos, String string, IconCore iconCore) {
        this(gui, saoID, xPos, yPos, 100, string, iconCore);
    }

    private static final class SAOPartyStateHandler implements StateHandler {

        private final Categories id;

        private SAOPartyStateHandler(Categories id) {

            this.id = id;
        }

        @Override
        public boolean isStateEnabled(Minecraft mc, ButtonState button) {
            return PartyHelper.instance().shouldHighlight(id);
        }

    }

}
