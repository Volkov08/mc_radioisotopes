package net.volkov.radioisotopes.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.volkov.radioisotopes.ClientMain;

import net.volkov.radioisotopes.item.custom.ModAxeItem;
import net.volkov.radioisotopes.item.custom.ModDangerousSwordItem;
import net.volkov.radioisotopes.item.custom.ModHoeItem;
import net.volkov.radioisotopes.item.custom.ModPickaxeItem;
import net.volkov.radioisotopes.item.custom.ModShovelItem;


public class ModItems {
    public static final Item URANIUM_INGOT = registerItem("uranium_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item URANIUM_NUGGET = registerItem("uranium_nugget",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item RAW_URANIUM = registerItem("raw_uranium",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item NUCLEAR_FUEL_ROD = registerItem("nuclear_fuel_rod",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item URANIUM_SWORD = registerItem("uranium_sword",
            new ModDangerousSwordItem(ModToolMaterials.URANIUM, 3, -2.4f,
                    new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item URANIUM_PICKAXE = registerItem("uranium_pickaxe",
            new ModPickaxeItem(ModToolMaterials.URANIUM, 1, -2.8f,
                    new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item URANIUM_AXE = registerItem("uranium_axe",
            new ModAxeItem(ModToolMaterials.URANIUM, 5, -3.2f,
                    new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item URANIUM_HOE = registerItem("uranium_hoe",
            new ModHoeItem(ModToolMaterials.URANIUM, -5, -2.0f,
                    new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item URANIUM_SHOVEL = registerItem("uranium_shovel",
            new ModShovelItem(ModToolMaterials.URANIUM, 2, -3.0f,
                    new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item ENRICHED_URANIUM_INGOT = registerItem("enriched_uranium_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item ENRICHED_URANIUM_NUGGET = registerItem("enriched_uranium_nugget",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item HEAVY_WATER_BUCKET = registerItem("heavy_water_bucket",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM).maxCount(1)));

    public static final Item RAW_LEAD = registerItem("raw_lead",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item LEAD_INGOT = registerItem("lead_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item LEAD_PLATE = registerItem("lead_plate",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(ClientMain.MOD_ID, name), item);
    }
    public static void registerModItems() {
        ClientMain.LOGGER.info("Registering Mod Items for" + ClientMain.MOD_ID);
    }
}
