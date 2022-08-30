package net.volkov.elismod.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;


public class ModBlocks {

    public static final Block ENRICHED_URANIUM_BLOCK = registerBlock("enriched_uranium_block",
            new Block(FabricBlockSettings.of(Material.METAL).strength(8f).requiresTool().luminance(12)), ItemGroup.BUILDING_BLOCKS);


    private static Block registerBlockRarity(String name, Block block, ItemGroup group, Rarity rarity){
        registerBlockItemRarity(name, block, group, rarity);
        return Registry.register(Registry.BLOCK, new Identifier(net.volkov.elismod.ClientMain.MOD_ID, name), block);
    }
    private static Block registerBlock(String name, Block block, ItemGroup group){
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(net.volkov.elismod.ClientMain.MOD_ID, name), block);
    }

    private static Item registerBlockItemRarity(String name, Block block, ItemGroup group, Rarity rarity) {
        return Registry.register(Registry.ITEM, new Identifier(net.volkov.elismod.ClientMain.MOD_ID, name), new BlockItem(block, new FabricItemSettings().group(group).rarity(rarity)));
    }
    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(net.volkov.elismod.ClientMain.MOD_ID, name), new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static void registerModBlocks () {
        net.volkov.elismod.ClientMain.LOGGER.info("Registering Mod Blocks for" + net.volkov.elismod.ClientMain.MOD_ID);
    }
}
