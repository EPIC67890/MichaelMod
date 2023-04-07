package net.kaupenjoe.tutorialmod.world.dimension;

import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.awt.*;

@EventBusSubscriber(modid = "tutorialmod")
public class DimensionHandler {

    @SubscribeEvent
    public static void onLivingSpawn(LivingSpawnEvent.CheckSpawn event) {
        DimensionType dimType = event.getLevel().dimensionType();


        if (!dimType.bedWorks()) {
            if(dimType.hasSkyLight())
            event.setResult(Event.Result.DENY);
        }
    }
}
