package net.kaupenjoe.tutorialmod.player.altardata;

import net.kaupenjoe.tutorialmod.player.PlayerData;

import java.util.Random;

public class PlayerAltarQty {
    public static int playerQty;
    public static void setPlayerQty(int newPlayerQty){
        PlayerData.setPlayerData("altarQty",newPlayerQty);
    }
    public static int getPlayerQty(){
        return PlayerData.getPlayerData("altarQty");
    }

    public static void randomQty() {
        Random random = new Random();
        int currentLevel = PlayerData.getPlayerData("altarLevel");
        if (currentLevel==0){
            currentLevel = 1;
        }
        int randomItemQty = (int) (currentLevel * 10 * (0.9 + (random.nextDouble() * 0.2)));
        PlayerData.setPlayerData("altarQty",randomItemQty);
    }

    public static void makeNotNull(){
        if (PlayerData.getPlayerData("altarQty") == 0){
            randomQty();
        }
    }
    public static void subQty(int sub){
        PlayerData.setPlayerData("altarQty",PlayerData.getPlayerData("altarQty")-sub);
    }
}
