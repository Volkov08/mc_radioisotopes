package net.volkov.radioisotopes.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;

import net.minecraft.world.World;
import net.volkov.radioisotopes.ClientMain;
import net.volkov.radioisotopes.ClientOnly;
import net.volkov.radioisotopes.block.custom.ModAtomicReactorControllerBlock;
import net.volkov.radioisotopes.block.custom.ModDeuteriumGeneratorBlock;
import net.volkov.radioisotopes.block.custom.ModUraniumCentrifugeBlock;
import net.volkov.radioisotopes.item.ModItemGroup;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class ModBlocks {

    public static final Block URANIUM_ORE = registerBlock("uranium_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(4.0f).requiresTool().luminance(3),
                    UniformIntProvider.create(3, 8)), ModItemGroup.URANIUM);

    public static final Block DEEPSLATE_URANIUM_ORE = registerBlock("deepslate_uranium_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(4.5f).requiresTool().luminance(3),
                    UniformIntProvider.create(3, 8)), ModItemGroup.URANIUM);

    public static final Block URANIUM_BLOCK = registerBlock("uranium_block",
            new Block(FabricBlockSettings.of(Material.METAL).strength(6f).requiresTool().luminance(8)), ModItemGroup.URANIUM);

    public static final Block ENRICHED_URANIUM_BLOCK = registerBlock("enriched_uranium_block",
            new Block(FabricBlockSettings.of(Material.METAL).strength(8f).requiresTool().luminance(12)), ModItemGroup.URANIUM);

    public static final Block URANIUM_GLASS = registerBlock("uranium_glass",
            new GlassBlock(FabricBlockSettings.of(Material.GLASS).strength(0.3f).luminance(6).nonOpaque().sounds(BlockSoundGroup.GLASS)) {
            }, ModItemGroup.URANIUM);

    public static final Block URANIUM_GLASS_PANE = registerBlock("uranium_glass_pane",
            new PaneBlock(FabricBlockSettings.of(Material.GLASS).strength(0.3f).luminance(6).nonOpaque().sounds(BlockSoundGroup.GLASS)) {
            }, ModItemGroup.URANIUM);


    public static final Block DEUTERIUM_GENERATOR = registerBlock("deuterium_generator",
            new ModDeuteriumGeneratorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(4f).nonOpaque()), ModItemGroup.URANIUM,
            "tooltip.radioisotopes.deuterium_generator_1", "tooltip.radioisotopes.deuterium_generator_2",
            "tooltip.radioisotopes.deuterium_generator_3", "tooltip.radioisotopes.deuterium_generator_4");

    public static final Block URANIUM_CENTRIFUGE = registerBlock("uranium_centrifuge",
            new ModUraniumCentrifugeBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(4f).nonOpaque()), ModItemGroup.URANIUM,
            "tooltip.radioisotopes.uranium_centrifuge_1", "tooltip.radioisotopes.uranium_centrifuge_2",
            "tooltip.radioisotopes.uranium_centrifuge_3", "");

    public static final Block ATOMIC_REACTOR_CONTROLLER = registerBlock("atomic_reactor_controller",
            new ModAtomicReactorControllerBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(4f).nonOpaque()), ModItemGroup.URANIUM,
            "tooltip.radioisotopes.atomic_reactor_controller_1", "tooltip.radioisotopes.atomic_reactor_controller_2",
            "tooltip.radioisotopes.atomic_reactor_controller_3", "");

    public static final Block ATOMIC_REACTOR_CASING = registerBlock("atomic_reactor_casing",
            new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(4f).nonOpaque()), ModItemGroup.URANIUM);


    public static final Block LEAD_ORE = registerBlock("lead_ore",
            new Block(FabricBlockSettings.of(Material.STONE).strength(4.0f).requiresTool()), ModItemGroup.URANIUM);

    public static final Block DEEPSLATE_LEAD_ORE = registerBlock("deepslate_lead_ore",
            new Block(FabricBlockSettings.of(Material.STONE).strength(4.5f).requiresTool()), ModItemGroup.URANIUM);

    public static final Block LEAD_BLOCK = registerBlock("lead_block",
            new Block(FabricBlockSettings.of(Material.METAL).strength(4.5f).requiresTool()), ModItemGroup.URANIUM);

    public static final Block LEAD_WALL = registerBlock("lead_wall",
            new WallBlock(FabricBlockSettings.of(Material.METAL).strength(4.5f).requiresTool()), ModItemGroup.URANIUM);

    public static final Block END_STONE_TENEBRIUM_ORE = registerBlock("end_stone_tenebrium_ore",
            new Block(FabricBlockSettings.of(Material.STONE).strength(12.0f).requiresTool()), ModItemGroup.URANIUM);

    public static final Block LITHIUM_ORE = registerBlock("lithium_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(4.0f).requiresTool(),
                    UniformIntProvider.create(2, 6)), ModItemGroup.URANIUM);


    private static Block registerBlock(String name, Block block, ItemGroup group, String tooltipKey_1, String tooltipKey_2, String tooltipKey_3, String tooltipKey_4) {
        registerBlockItem(name, block, group, tooltipKey_1, tooltipKey_2, tooltipKey_3, tooltipKey_4);
        return Registry.register(Registry.BLOCK, new Identifier(net.volkov.radioisotopes.ClientMain.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group, String tooltipKey_1, String tooltipKey_2, String tooltipKey_3, String tooltipKey_4) {
        return Registry.register(Registry.ITEM, new Identifier(net.volkov.radioisotopes.ClientMain.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)) {
                    @Override
                    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
                        tooltip.add(new TranslatableText(tooltipKey_1));
                        tooltip.add(new TranslatableText(tooltipKey_2));
                        tooltip.add(new TranslatableText(tooltipKey_3));
                        tooltip.add(new TranslatableText(tooltipKey_4));
                    }
                });
    }

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
        net.volkov.radioisotopes.ClientMain.LOGGER.info("Registering Mod Blocks for " + net.volkov.radioisotopes.ClientMain.MOD_ID);
    }
}
