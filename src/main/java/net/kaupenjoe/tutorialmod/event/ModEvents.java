package net.kaupenjoe.tutorialmod.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.kaupenjoe.tutorialmod.TutorialMod;
import net.kaupenjoe.tutorialmod.item.ModItems;
import net.kaupenjoe.tutorialmod.player.PlayerData;
import net.kaupenjoe.tutorialmod.player.altardata.PlayerAltarItem;
import net.kaupenjoe.tutorialmod.player.altardata.PlayerAltarLevel;
import net.kaupenjoe.tutorialmod.player.altardata.PlayerAltarQty;
import net.kaupenjoe.tutorialmod.player.altardata.ServerToClientStorage;
import net.kaupenjoe.tutorialmod.thirst.PlayerThirst;
import net.kaupenjoe.tutorialmod.thirst.PlayerThirstProvider;
import net.kaupenjoe.tutorialmod.villager.ModVillagers;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if(event.getType() == VillagerProfession.TOOLSMITH) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.EIGHT_BALL.get(), 1);
            int villagerLevel = 1;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    stack,10,8,0.02F));
        }

        if(event.getType() == ModVillagers.JUMP_MASTER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.BLUEBERRY.get(), 15);
            int villagerLevel = 1;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 5),
                    stack,10,8,0.02F));
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerThirstProvider.PLAYER_THIRST).isPresent()) {
                event.addCapability(new ResourceLocation(TutorialMod.MOD_ID, "properties"), new PlayerThirstProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerThirst.class);
    }
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event){
        //Get Server Player
        Player serverPlayer = event.getEntity();

        //Get Server Player Data
        CompoundTag serverPlayerData = serverPlayer.getPersistentData();

        //Save it to other class data, and then we can reference it on client level.
        ServerToClientStorage.getDataFromServer(serverPlayerData);
    }
    @SubscribeEvent
    public static void onClientPlayerLoggedIn(ClientPlayerNetworkEvent.LoggingIn event){
        Player clientPlayer = event.getPlayer();
        CompoundTag clientPlayerData = ServerToClientStorage.sendDataToClient();
        PlayerAltarItem.setPlayerItem(clientPlayerData.getInt("altarItem"));
        PlayerAltarLevel.setPlayerLevel(clientPlayerData.getInt("altarLevel"));
        PlayerAltarQty.setPlayerQty(clientPlayerData.getInt("altarQty"));
        PlayerAltarQty.makeNotNull();

    }
    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event){
        Player player = event.getEntity();
        CompoundTag serverPlayerData = player.getPersistentData();
        serverPlayerData.putInt("altarLevel", PlayerAltarLevel.getPlayerLevel());
        serverPlayerData.putInt("altarItem", PlayerAltarItem.getPlayerItem());
        serverPlayerData.putInt("altarQty", PlayerAltarQty.getPlayerQty());
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.SERVER) {
            event.player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                if(thirst.getThirst() > 0 && event.player.getRandom().nextFloat() < 0.005f) { // Once Every 10 Seconds on Avg
                    thirst.subThirst(1);
                    event.player.sendSystemMessage(Component.literal("Subtracted Thirst"));
                }
            });
        }
    }
}
