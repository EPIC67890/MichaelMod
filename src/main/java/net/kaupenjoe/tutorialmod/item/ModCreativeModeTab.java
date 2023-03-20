package net.kaupenjoe.tutorialmod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab extends CreativeModeTab {

    public ModCreativeModeTab() {
        super("tutorialmod");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModItems.ZIRCON.get());
    }

    public static final ModCreativeModeTab TUTORIAL_TAB = new ModCreativeModeTab();

}
