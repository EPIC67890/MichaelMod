package net.kaupenjoe.tutorialmod.player.altardata;

import net.minecraft.nbt.CompoundTag;

public class ServerToClientStorage {
    static CompoundTag tempDataBlob;
    public static void getDataFromServer(CompoundTag dataBlob){
        tempDataBlob = dataBlob;
    }
    public static CompoundTag sendDataToClient(){
        return tempDataBlob;
    }
}
