package net.volkov.radioisotopes.util;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.volkov.radioisotopes.ClientMain;
import net.volkov.radioisotopes.block.ModBlocks;
import net.volkov.radioisotopes.item.ModItems;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.volkov.radioisotopes.villager.ModVillagers;

public class ModRegistries {
    public static void registerModStuffs() {
        registerFuels();
        registerCustomTrades();
    }

    private static void registerFuels() {
        ClientMain.LOGGER.info("Registering fuels for " + ClientMain.MOD_ID);
        FuelRegistry registry = FuelRegistry.INSTANCE;

        //Ähem also das sollten wir wohl besser rausnehmen,
        //wenn wir den Reaktor hinzufügen
        registry.add(ModItems.NUCLEAR_FUEL_ROD, 36000);
        //registry.add(Items.POTATO, 36000);
    }

    private static void registerCustomTrades() {
        TradeOfferHelper.registerVillagerOffers(ModVillagers.PHYSICIST, 1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.RAW_URANIUM, 2),
                            new ItemStack(Items.EMERALD, 16),
                            8,2,0.05f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.RAW_LEAD, 8),
                            new ItemStack(Items.EMERALD, 2),
                            8,2,0.05f));
                });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.PHYSICIST, 2,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 16),
                            new ItemStack(ModItems.LEAD_HELMET, 1),
                            3,8,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 24),
                            new ItemStack(ModItems.LEAD_CHESTPLATE, 1),
                            3,8,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 20),
                            new ItemStack(ModItems.LEAD_LEGGINGS, 1),
                            3,8,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 12),
                            new ItemStack(ModItems.LEAD_BOOTS, 1),
                            3,8,0.05f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 2),
                            new ItemStack(ModBlocks.URANIUM_GLASS.asItem(), 4),
                            12,4,0.05f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 2),
                            new ItemStack(ModItems.HEAVY_LEAD_PLATE, 1),
                            12,8,0.05f));
                });
        TradeOfferHelper.registerVillagerOffers(ModVillagers.PHYSICIST, 3,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 30),
                            new ItemStack(ModItems.HEAVY_LEAD_HELMET, 1),
                            3,10,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 48),
                            new ItemStack(ModItems.HEAVY_LEAD_CHESTPLATE, 1),
                            3,10,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 42),
                            new ItemStack(ModItems.HEAVY_LEAD_LEGGINGS, 1),
                            3,10,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 24),
                            new ItemStack(ModItems.HEAVY_LEAD_BOOTS, 1),
                            3,10,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 16),
                            new ItemStack(ModItems.HEAVY_WATER_BUCKET, 1),
                            12,4,0.05f));
                });
                TradeOfferHelper.registerVillagerOffers(ModVillagers.PHYSICIST, 4,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 20),
                            new ItemStack(ModBlocks.DEUTERIUM_GENERATOR.asItem(), 1),
                            3,12,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 24),
                            new ItemStack(ModBlocks.URANIUM_CENTRIFUGE.asItem(), 1),
                            3,10,0.05f));
                    /*
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
                            new ItemStack(Items.EMERALD, 32),
                            new ItemStack(ModItems.ENRICHED_URANIUM_INGOT, 1),
                            2,16,0.05f));
                });
    }


}
