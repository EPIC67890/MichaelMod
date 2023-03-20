package net.kaupenjoe.tutorialmod.player.altardata;

import net.kaupenjoe.tutorialmod.player.PlayerData;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.world.item.Item;

import java.util.Random;

public class PlayerAltarItem {
    public static int playerItem;
    public static void setPlayerItem(int newPlayerItem){
        PlayerData.setPlayerData("altarItem",newPlayerItem);
    }
    public static int getPlayerItem(){
        return PlayerData.getPlayerData("altarItem");
    }

    public static void randomize() {
        PlayerData.setPlayerData("altarItem", Item.getId(PossibleItems.returnRandomItem()));
    }
    public static void makeNotNull(){
        if (PlayerData.getPlayerData("altarItem") == 0){
            PlayerAltarItem.randomize();
        }
    }
}
