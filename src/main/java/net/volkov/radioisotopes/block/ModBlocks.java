package net.volkov.radioisotopes.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;

import net.volkov.radioisotopes.block.custom.DeuteriumGeneratorBlock;
import net.volkov.radioisotopes.block.custom.ModUraniumCentrifugeBlock;
import net.volkov.radioisotopes.block.custom.ModUraniumGlassBlock;
import net.volkov.radioisotopes.item.ModItemGroup;


public class ModBlocks {

    public static final Block URANIUM_ORE = registerBlock("uranium_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(4.0f).requiresTool().luminance(3),
                    UniformIntProvider.create(2, 6)), ModItemGroup.URANIUM);

    public static final Block DEEPSLATE_URANIUM_ORE = registerBlock("deepslate_uranium_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(4.5f).requiresTool().luminance(3),
                    UniformIntProvider.create(2, 6)), ModItemGroup.URANIUM);

    public static final Block URANIUM_BLOCK = registerBlock("uranium_block",
            new Block(FabricBlockSettings.of(Material.METAL).strength(6f).requiresTool().luminance(8)), ModItemGroup.URANIUM);

    public static final Block ENRICHED_URANIUM_BLOCK = registerBlock("enriched_uranium_block",
            new Block(FabricBlockSettings.of(Material.METAL).strength(8f).requiresTool().luminance(12)), ModItemGroup.URANIUM);

    public static final Block URANIUM_GLASS = registerBlock("uranium_glass",
            new ModUraniumGlassBlock(FabricBlockSettings.of(Material.GLASS).strength(0.3f).luminance(6).nonOpaque().sounds(BlockSoundGroup.GLASS)) {
            }, ModItemGroup.URANIUM);

    public static final Block DEUTERIUM_GENERATOR = registerBlock("deuterium_generator",
            new DeuteriumGeneratorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(4f).nonOpaque()), ModItemGroup.URANIUM);

    public static final Block URANIUM_CENTRIFUGE = registerBlock("uranium_centrifuge",
            new ModUraniumCentrifugeBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(4f).nonOpaque()), ModItemGroup.URANIUM);


    public static final Block LEAD_ORE = registerBlock("lead_ore",
            new Block(FabricBlockSettings.of(Material.STONE).strength(4.0f).requiresTool()), ModItemGroup.URANIUM);

    public static final Block DEEPSLATE_LEAD_ORE = registerBlock("deepslate_lead_ore",
            new Block(FabricBlockSettings.of(Material.STONE).strength(4.5f).requiresTool()), ModItemGroup.URANIUM);

    public static final Block LEAD_BLOCK = registerBlock("lead_block",
            new Block(FabricBlockSettings.of(Material.METAL).strength(4.5f).requiresTool()), ModItemGroup.URANIUM);

    public static final Block LEAD_WALL = registerBlock("lead_wall",
            new WallBlock(FabricBlockSettings.of(Material.METAL).strength(7f).requiresTool()), ModItemGroup.URANIUM);


    private static Block registerBlockRarity(String name, Block block, ItemGroup group, Rarity rarity){
        registerBlockItemRarity(name, block, group, rarity);
        return Registry.register(Registry.BLOCK, new Identifier(net.volkov.radioisotopes.ClientMain.MOD_ID, name), block);
    }
    private static Block registerBlock(String name, Block block, ItemGroup group){
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(net.volkov.radioisotopes.ClientMain.MOD_ID, name), block);
    }

    private static Item registerBlockItemRarity(String name, Block block, ItemGroup group, Rarity rarity) {
        return Registry.register(Registry.ITEM, new Identifier(net.volkov.radioisotopes.ClientMain.MOD_ID, name), new BlockItem(block, new FabricItemSettings().group(group).rarity(rarity)));
    }
    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(net.volkov.radioisotopes.ClientMain.MOD_ID, name), new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static void registerModBlocks () {
        net.volkov.radioisotopes.ClientMain.LOGGER.info("Registering Mod Blocks for" + net.volkov.radioisotopes.ClientMain.MOD_ID);
    }
}
