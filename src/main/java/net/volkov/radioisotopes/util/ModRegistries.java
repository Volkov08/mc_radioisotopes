package net.volkov.radioisotopes.util;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.volkov.radioisotopes.ClientMain;
import net.volkov.radioisotopes.block.ModBlocks;
import net.volkov.radioisotopes.item.ModItems;
import net.volkov.radioisotopes.villager.ModVillagers;

public class ModRegistries {
    public static void registerModStuffs() {
        registerFuels();
        registerCustomTrades();
    }

    private static void registerFuels() {
        ClientMain.LOGGER.info("Registering fuels for " + ClientMain.MOD_ID);
        FuelRegistry registry = FuelRegistry.INSTANCE;
        registry.add(ModItems.FULL_LEAD_BATTERY, 30000);
    }

    private static void registerCustomTrades() {
        TradeOfferHelper.registerVillagerOffers(ModVillagers.PHYSICIST, 1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.RAW_URANIUM, 2),
                            new ItemStack(Items.EMERALD, 8),
                            8,5,0.05f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.RAW_LEAD, 7),
                            new ItemStack(Items.EMERALD, 2),
                            10,3,0.05f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.COAL_BLOCK, 2),
                            new ItemStack(Items.EMERALD, 5),
                            10,3,0.05f));
                });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.PHYSICIST, 2,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 16),
                            new ItemStack(ModItems.LEAD_HELMET, 1),
                            3,12,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 24),
                            new ItemStack(ModItems.LEAD_CHESTPLATE, 1),
                            3,12,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 20),
                            new ItemStack(ModItems.LEAD_LEGGINGS, 1),
                            3,12,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 12),
                            new ItemStack(ModItems.LEAD_BOOTS, 1),
                            3,12,0.05f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 2),
                            new ItemStack(ModBlocks.URANIUM_GLASS.asItem(), 4),
                            12,6,0.05f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 4),
                            new ItemStack(ModItems.HEAVY_LEAD_PLATE, 1),
                            12,8,0.05f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.FLUORINE, 2),
                            new ItemStack(Items.EMERALD, 8),
                            12,12,0.05f));
                });
        TradeOfferHelper.registerVillagerOffers(ModVillagers.PHYSICIST, 3,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 22),
                            new ItemStack(ModItems.HEAVY_LEAD_HELMET, 1),
                            3,16,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 29),
                            new ItemStack(ModItems.HEAVY_LEAD_CHESTPLATE, 1),
                            3,16,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 24),
                            new ItemStack(ModItems.HEAVY_LEAD_LEGGINGS, 1),
                            3,16,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 18),
                            new ItemStack(ModItems.HEAVY_LEAD_BOOTS, 1),
                            3,16,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.HEAVY_WATER_BUCKET, 1),
                            new ItemStack(Items.EMERALD, 12),
                            8,24,0.05f));
                });
                TradeOfferHelper.registerVillagerOffers(ModVillagers.PHYSICIST, 4,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.ELECTRIC_ENGINE, 4),
                            new ItemStack(Items.EMERALD, 24),
                            new ItemStack(ModBlocks.URANIUM_CENTRIFUGE.asItem(), 1),
                            2,30,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.ATOMIC_WASTE, 3),
                            new ItemStack(Items.EMERALD, 26),
                            new ItemStack(ModItems.PLUTONIUM, 1),
                            5,26,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 38),
                            new ItemStack(ModItems.LITHIUM_INGOT, 1),
                            3,26,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.RAD_DETECTOR, 1),
                            new ItemStack(Items.EMERALD, 16),
                            6,20,0.05f));

                    /*
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 24),
                            new ItemStack(ModBlocks.URANIUM_CENTRIFUGE.asItem(), 1),
                            3,10,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 3),
                            new ItemStack(ModItems.CONTACT_FUZE, 1),
                            12,4,0.05f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 3),
                            new ItemStack(ModItems.TIMED_FUZE, 1),
                            12,4,0.05f));
                    */
                });
                TradeOfferHelper.registerVillagerOffers(ModVillagers.PHYSICIST, 5,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 42),
                            new ItemStack(ModItems.ENRICHED_URANIUM_INGOT, 1),
                            2,32,0.05f));
                });
    }


}
