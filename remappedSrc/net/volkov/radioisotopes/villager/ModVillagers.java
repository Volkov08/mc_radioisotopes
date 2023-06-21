package net.volkov.radioisotopes.villager;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.minecraft.block.Block;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import net.volkov.radioisotopes.ClientMain;
import net.volkov.radioisotopes.block.ModBlocks;

public class ModVillagers {
    public static final PointOfInterestType CENTRIFUGE_POI = registerPOI("centrifugepoi", ModBlocks.URANIUM_CENTRIFUGE);
    public static final VillagerProfession PHYSICIST = registerProfession("physicist",
            RegistryKey.of(Registry.POINT_OF_INTEREST_TYPE_KEY, new Identifier(ClientMain.MOD_ID, "centrifugepoi")));

    public static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(Registry.VILLAGER_PROFESSION, new Identifier(ClientMain.MOD_ID, name),
                VillagerProfessionBuilder.create().id(new Identifier(ClientMain.MOD_ID, name)).workstation(type)
                        .workSound(SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN).build());
    }

    public static PointOfInterestType registerPOI(String name, Block block) {
        return Registry.register(Registry.POINT_OF_INTEREST_TYPE, new Identifier(ClientMain.MOD_ID, name),
                new PointOfInterestType(ImmutableSet.copyOf(block.getStateManager().getStates()), 1, 1));
    }

}
