package net.volkov.radioisotopes.util;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.volkov.radioisotopes.ClientMain;
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
                            new ItemStack(Items.EMERALD, 42),
                            new ItemStack(ModItems.RAW_URANIUM, 2),
                            8,2,0.05f));
                });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.PHYSICIST, 2,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 20),
                            new ItemStack(ModItems.LEAD_HELMET, 1),
                            3,4,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 24),
                            new ItemStack(ModItems.LEAD_CHESTPLATE, 1),
                            3,4,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 22),
                            new ItemStack(ModItems.LEAD_LEGGINGS, 1),
                            3,4,0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 26),
                            new ItemStack(ModItems.LEAD_BOOTS, 1),
                            3,4,0.05f));
                });
    }

}
