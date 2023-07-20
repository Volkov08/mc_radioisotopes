package net.volkov.radioisotopes.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import net.volkov.radioisotopes.ClientMain;
import net.volkov.radioisotopes.item.ModItems;

public class ModLootTableModifiers {
    private static final Identifier CREEPER_ID
            = new Identifier("minecraft", "entities/creeper");

    public static void modifyLootTables() {
        ClientMain.LOGGER.info("Modifying loot tables for " + ClientMain.MOD_ID);
        LootTableEvents.MODIFY.register(((resourceManager, manager, id, supplier, setter) -> {

            if(CREEPER_ID.equals(id)) {
                // Adds RAW_URANIUM to Creepers.
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.15f)) // Drops 15% of the time
                        .with(ItemEntry.builder(ModItems.RAW_URANIUM))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                supplier.pool(poolBuilder.build());
            }
        }));
    }
}
