package net.volkov.radioisotopes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;
import net.volkov.radioisotopes.block.ModBlocks;
import net.volkov.radioisotopes.entity.*;
import net.volkov.radioisotopes.screen.*;

public class ClientOnly implements ClientModInitializer {
    @Override
    public void onInitializeClient()  {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.URANIUM_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.URANIUM_GLASS_PANE, RenderLayer.getTranslucent());

        ScreenRegistry.register(ModScreenHandlers.DEUTERIUM_GENERATOR_SCREEN_HANDLER, DeuteriumGeneratorScreen::new);
        ScreenRegistry.register(ModScreenHandlers.URANIUM_CENTRIFUGE_SCREEN_HANDLER, UraniumCentrifugeScreen::new);
        ScreenRegistry.register(ModScreenHandlers.ATOMIC_REACTOR_CONTROLLER_SCREEN_HANDLER, AtomicReactorControllerScreen::new);
        ScreenRegistry.register(ModScreenHandlers.PLUTONIUM_REPROCESSING_PLANT_SCREEN_HANDLER, PlutoniumReprocessingPlantScreen::new);

        EntityRendererRegistry.register(ModEntities.RAD_ENTITY, RadEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.NUCLEAR_EXPLOSION_ENTITY, NuclearExplosionEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.M388_ENTITY, M388EntityRenderer::new);
    }
}
