package com.bluexin.saoui.social.friends;

import com.bluexin.saoui.events.FriendsHandler;
import com.bluexin.saoui.screens.ParentElement;
import com.bluexin.saoui.screens.buttons.ButtonGUI;
import com.bluexin.saoui.screens.menu.Categories;
import com.bluexin.saoui.social.StaticPlayerHelper;
import com.bluexin.saoui.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class FriendCore extends ButtonGUI {

    private EntityPlayer friend;

    private FriendCore(ParentElement gui, int xPos, int yPos, int w, int h, String name) {
        super(gui, Categories.FRIEND, xPos, yPos, w, h, name, IconCore.NONE);
        enabled = false;
    }

    private FriendCore(ParentElement gui, int xPos, int yPos, int w, String name) {
        this(gui, xPos, yPos, w, 20, name);
    }

    public FriendCore(ParentElement gui, int xPos, int yPos, String name) {
        this(gui, xPos, yPos, 100, name);
    }

    @Override
    public void update(Minecraft mc) {
        final EntityPlayer player = getPlayer(mc);
        enabled = (player != null);

        if (enabled && FriendsHandler.instance().isFriend(player)) {
            highlight = true;
            icon = IconCore.NONE;
        } else {
            highlight = false;
            icon = IconCore.INVITE;
        }

        super.update(mc);
    }

    private EntityPlayer getPlayer(Minecraft mc) {
        if (friend == null || friend.isDead || !friend.isEntityAlive()) friend = findPlayer(mc);

        return friend;
    }

    private EntityPlayer findPlayer(Minecraft mc) {
        final List<EntityPlayer> players = StaticPlayerHelper.listOnlinePlayers(mc);

        for (final EntityPlayer player : players) {
            if (StaticPlayerHelper.getName(player).equals(caption)) {
                return player;
            }
        }

        return null;
    }
}

