package net.volkov.radioisotopes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;
import net.volkov.radioisotopes.block.ModBlocks;
import net.volkov.radioisotopes.entity.*;
import net.volkov.radioisotopes.screen.AtomicReactorControllerScreen;
import net.volkov.radioisotopes.screen.DeuteriumGeneratorScreen;
import net.volkov.radioisotopes.screen.ModScreenHandlers;
import net.volkov.radioisotopes.screen.UraniumCentrifugeScreen;

public class ClientOnly implements ClientModInitializer {
    @Override
    public void onInitializeClient()  {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.URANIUM_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.URANIUM_GLASS_PANE, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.URANIUM_CENTRIFUGE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DEUTERIUM_GENERATOR, RenderLayer.getCutout());

        ScreenRegistry.register(ModScreenHandlers.DEUTERIUM_GENERATOR_SCREEN_HANDLER, DeuteriumGeneratorScreen::new);
        ScreenRegistry.register(ModScreenHandlers.URANIUM_CENTRIFUGE_SCREEN_HANDLER, UraniumCentrifugeScreen::new);
        ScreenRegistry.register(ModScreenHandlers.ATOMIC_REACTOR_CONTROLLER_SCREEN_HANDLER, AtomicReactorControllerScreen::new);

        EntityRendererRegistry.register(ModEntities.FISSION_RAD_ENTITY, FissionRadEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.REACTOR_RAD_ENTITY, ReactorRadEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.FISSION_EXPLOSION_ENTITY, FissionExplosionEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.FUSION_EXPLOSION_ENTITY, FusionExplosionEntityRenderer::new);
    }
}
