package net.kaupenjoe.tutorialmod.block.custom;

import net.kaupenjoe.tutorialmod.player.PlayerData;
import net.kaupenjoe.tutorialmod.player.PlayerStatMessages;
import net.kaupenjoe.tutorialmod.player.altardata.PlayerAltarItem;
import net.kaupenjoe.tutorialmod.player.altardata.PlayerAltarLevel;
import net.kaupenjoe.tutorialmod.player.altardata.PlayerAltarQty;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class EpicAltarBlock extends Block {
    public EpicAltarBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player,
                                 InteractionHand hand, BlockHitResult blockHitResult) {
        if(!level.isClientSide && hand == InteractionHand.MAIN_HAND){
            PlayerAltarItem.makeNotNull();

            Item requiredItem = Item.byId(PlayerData.getPlayerData("altarItem"));


            AABB minmax = new AABB(blockPos.getX()-2,blockPos.getY()-2,blockPos.getZ()-2,blockPos.getX()+2,blockPos.getY()+2,blockPos.getZ()+2);
            List<Entity> entities = level.getEntities(null,minmax);

            for (Entity ent : entities){
                if (ent instanceof ItemEntity){
                    ItemEntity itemEntity = (ItemEntity) ent;
                    ItemStack itemStack = itemEntity.getItem();
                    if (!itemStack.isEmpty() && itemStack.getItem() instanceof Item && itemStack.getItem().equals(requiredItem)) {
                        //Get amt on floor
                        int qtyFound = itemStack.getCount();

                        itemStack.setCount(qtyFound - Math.min(qtyFound,PlayerData.getPlayerData("altarQty")));
                        PlayerAltarQty.subQty(qtyFound);

                        //Check if we finished
                        if(PlayerData.getPlayerData("altarQty")<=0){
                            //Finished Quest
                            PlayerAltarLevel.setPlayerLevel(PlayerData.getPlayerData("altarLevel") + 1);
                            PlayerStatMessages.showPlayerLevel();
                            PlayerAltarItem.randomize();
                            PlayerStatMessages.showPlayerItem();
                            PlayerAltarQty.randomQty();
                            PlayerStatMessages.showPlayerQty();
                            return InteractionResult.SUCCESS;
                        }

                    }
                }
            }
            if(PlayerData.getPlayerData("altarQty")>0){
                PlayerStatMessages.showPlayerQty();
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, level, blockPos, player, hand, blockHitResult);
    }
}
