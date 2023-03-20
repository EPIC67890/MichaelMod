package net.kaupenjoe.tutorialmod.player.altardata;

import net.kaupenjoe.tutorialmod.player.PlayerData;

public class PlayerAltarLevel {
    public static int playerLevel;
    public static void setPlayerLevel(int newPlayerLevel){
        PlayerData.setPlayerData("altarLevel",newPlayerLevel);
    }
    public static int getPlayerLevel(){
        return PlayerData.getPlayerData("altarLevel");
    }
}
