package net.volkov.radioisotopes.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.volkov.radioisotopes.ClientMain;


public class ModItems {
    public static final Item ENRICHED_URANIUM_INGOT = registerItem("enriched_uranium_ingot",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item ENRICHED_URANIUM_NUGGET = registerItem("enriched_uranium_nugget",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(ClientMain.MOD_ID, name), item);
    }
    public static void registerModItems() {
        ClientMain.LOGGER.info("Registering Mod Items for" + ClientMain.MOD_ID);
    }
}
