package net.volkov.radioisotopes.item;

import net.volkov.radioisotopes.ClientMain;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup URANIUM = FabricItemGroupBuilder.build(new Identifier(ClientMain.MOD_ID, "uranium"),
            ()-> new ItemStack(ModItems.URANIUM_INGOT));
}

