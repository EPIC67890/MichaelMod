package net.kaupenjoe.tutorialmod.player;

import net.kaupenjoe.tutorialmod.player.altardata.PlayerAltarLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class PlayerStatMessages {
    public static void showPlayerLevel(){
        PlayerData.getPlayerInstance().sendSystemMessage(Component.literal("Current altar level: " + PlayerData.getPlayerInstance().getPersistentData().getInt("altarLevel")));
    }

    public static void showPlayerItem() {
        PlayerData.getPlayerInstance().sendSystemMessage(Component.literal("Current altar item: " + Item.byId(PlayerData.getPlayerInstance().getPersistentData().getInt("altarItem"))));

    }

    public static void showPlayerQty() {
        PlayerData.getPlayerInstance().sendSystemMessage(Component.literal("Current altar Qty: " + PlayerData.getPlayerInstance().getPersistentData().getInt("altarQty")));
    }
}
