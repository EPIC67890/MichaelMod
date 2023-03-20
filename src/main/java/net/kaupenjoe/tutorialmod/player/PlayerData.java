package net.kaupenjoe.tutorialmod.player;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class PlayerData {
    public static Player getPlayerInstance(){
        return Minecraft.getInstance().player;
    }
    public static void setPlayerData(String dataKey, Integer dataValue){
        //Store Int in playerdata
        getPlayerInstance().getPersistentData().putInt(dataKey,dataValue);

    }
    public static int getPlayerData(String dataKey){
        //Get Int from playerdata
        return getPlayerInstance().getPersistentData().getInt(dataKey);

    }
}
