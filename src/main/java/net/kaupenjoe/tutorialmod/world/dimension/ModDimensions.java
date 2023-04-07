package net.kaupenjoe.tutorialmod.world.dimension;

import net.kaupenjoe.tutorialmod.TutorialMod;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.dimension.DimensionDefaults;
import net.minecraft.world.level.dimension.DimensionType;

public class ModDimensions {
    public static final ResourceKey<Level> EPICDIM_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY,
        new ResourceLocation(TutorialMod.MOD_ID, "epicdim"));
    public static final ResourceKey<DimensionType> EPICDIM_TYPE =
            ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY,new ResourceLocation(TutorialMod.MOD_ID, "epicdim"));
    public static void register(){
        System.out.println("Registering ModDimensions for " + TutorialMod.MOD_ID);
    }
    // Get the World object for your custom dimension

}