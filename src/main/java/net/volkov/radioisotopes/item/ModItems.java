package net.volkov.radioisotopes.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.volkov.radioisotopes.ClientMain;
import net.volkov.radioisotopes.item.custom.*;


public class ModItems {
    public static final Item URANIUM_INGOT = registerItem("uranium_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item URANIUM_NUGGET = registerItem("uranium_nugget",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item RAW_URANIUM = registerItem("raw_uranium",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item NUCLEAR_FUEL_ROD = registerItem("nuclear_fuel_rod",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item POBE_ROD = registerItem("pobe_rod",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item NUCLEAR_FUEL_STACK = registerItem("nuclear_fuel_stack",
            new Item(new FabricItemSettings().maxCount(1).group(ModItemGroup.URANIUM)));

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

    public static final Item FLUORINE = registerItem("fluorine",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item ENRICHED_URANIUM_INGOT = registerItem("enriched_uranium_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item ENRICHED_URANIUM_NUGGET = registerItem("enriched_uranium_nugget",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item DEUTERIUM = registerItem("deuterium",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item TRITIUM = registerItem("tritium",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item RAW_LEAD = registerItem("raw_lead",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item LEAD_INGOT = registerItem("lead_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item LEAD_PLATE = registerItem("lead_plate",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item LEAD_HELMET = registerItem("lead_helmet",
            new ArmorItem(ModArmorMaterials.LEAD, EquipmentSlot.HEAD,
                    new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item LEAD_CHESTPLATE = registerItem("lead_chestplate",
            new ArmorItem(ModArmorMaterials.LEAD, EquipmentSlot.CHEST,
                    new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item LEAD_LEGGINGS = registerItem("lead_leggings",
            new ArmorItem(ModArmorMaterials.LEAD, EquipmentSlot.LEGS,
                    new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item LEAD_BOOTS = registerItem("lead_boots",
            new ArmorItem(ModArmorMaterials.LEAD, EquipmentSlot.FEET,
                    new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item HEAVY_LEAD_PLATE = registerItem("heavy_lead_plate",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item HEAVY_LEAD_HELMET = registerItem("heavy_lead_helmet",
            new ModArmorItem(ModArmorMaterials.HEAVY_LEAD, EquipmentSlot.HEAD,
                    new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item HEAVY_LEAD_CHESTPLATE = registerItem("heavy_lead_chestplate",
            new ArmorItem(ModArmorMaterials.HEAVY_LEAD, EquipmentSlot.CHEST,
                    new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item HEAVY_LEAD_LEGGINGS = registerItem("heavy_lead_leggings",
            new ArmorItem(ModArmorMaterials.HEAVY_LEAD, EquipmentSlot.LEGS,
                    new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item HEAVY_LEAD_BOOTS = registerItem("heavy_lead_boots",
            new ArmorItem(ModArmorMaterials.HEAVY_LEAD, EquipmentSlot.FEET,
                    new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item RAW_LITHIUM = registerItem("raw_lithium",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item LITHIUM_INGOT = registerItem("lithium_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item LITHIUM_DEUTERIDE = registerItem("lithium_deuteride",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item COPPER_WIRE = registerItem("copper_wire",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item ELECTRIC_ENGINE = registerItem("electric_engine",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item EXPLOSIVE_LENSES = registerItem("explosive_lenses",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item SMALL_EXPLOSIVE_LENSES = registerItem("small_explosive_lenses",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    /*
    public static final Item TIMED_FUZE = registerItem("timed_fuze",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
     */
    public static final Item CONTACT_FUZE = registerItem("contact_fuze",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item CASING_ALLOY = registerItem("casing_alloy",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item FUSION_BOMB_CASING = registerItem("fusion_bomb_casing",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item LEAD_BATTERY = registerItem("lead_battery",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item FULL_LEAD_BATTERY = registerItem("full_lead_battery",
            new ModLeadBatteryItem(new FabricItemSettings().recipeRemainder(LEAD_BATTERY).maxCount(1).group(ModItemGroup.URANIUM)));

    public static final Item ATOMIC_WASTE = registerItem("atomic_waste",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item RAW_BISMUTH = registerItem("raw_bismuth",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item BISMUTH_INGOT = registerItem("bismuth_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item POLONIUM_NUGGET = registerItem("polonium_nugget",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item PLUTONIUM_INGOT = registerItem("plutonium_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item NEUTRON_INITIATOR = registerItem("neutron_initiator",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item PLUTONIUM_PIT = registerItem("plutonium_pit",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item COMPOSITE_PIT = registerItem("composite_pit",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item BOOST_CAPSULE = registerItem("boost_capsule",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item NEUTRON_GENERATOR = registerItem("neutron_generator",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item BOOSTED_PLUTONIUM_PIT = registerItem("boosted_plutonium_pit",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item URANIUM_SPARK_PLUG = registerItem("uranium_spark_plug",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item BERYLLIUM_NUGGET = registerItem("beryllium_nugget",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item BERYLLIUM_INGOT = registerItem("beryllium_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item TUNGSTEN_CARBIDE_NUGGET = registerItem("tungsten_carbide_nugget",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item TUNGSTEN_CARBIDE_INGOT = registerItem("tungsten_carbide_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item URANIUM_PUSHER = registerItem("uranium_pusher",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item URANIUM_TARGET = registerItem("uranium_target",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));


    public static final Item GUN_FISSION_STAGE = registerItem("gun_fission_stage",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item IMPLOSION_FISSION_STAGE = registerItem("implosion_fission_stage",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item LOW_YIELD_IMPLOSION_FISSION_STAGE = registerItem("low_yield_implosion_fission_stage",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item BOOSTED_IMPLOSION_FISSION_STAGE = registerItem("boosted_implosion_fission_stage",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));
    public static final Item FUSION_STAGE = registerItem("fusion_stage",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item DAVY_CHROCKETT = registerItem("davy_chrockett",
            new ModDavyChrockettItem(new FabricItemSettings().maxCount(1).group(ModItemGroup.URANIUM)));
    public static final Item M388_NUCLEAR_ROUND = registerItem("m388_nuclear_round",
            new Item(new FabricItemSettings().group(ModItemGroup.URANIUM)));

    public static final Item RAD_DETECTOR = registerItem("rad_detector",
            new ModRadDetectorItem(new FabricItemSettings().maxCount(1).group(ModItemGroup.URANIUM)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(ClientMain.MOD_ID, name), item);
    }
    public static void registerModItems() {
        ClientMain.LOGGER.info("Registering mod items for " + ClientMain.MOD_ID);
    }
}
