package net.volkov.radioisotopes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;
import net.volkov.radioisotopes.block.ModBlocks;
import net.volkov.radioisotopes.screen.DeuteriumGeneratorScreen;
import net.volkov.radioisotopes.screen.ModScreenHandlers;

public class ClientOnly implements ClientModInitializer {
    @Override
    public void onInitializeClient()  {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.URANIUM_GLASS, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DEUTERIUM_GENERATOR, RenderLayer.getCutout());


        ScreenRegistry.register(ModScreenHandlers.DEUTERIUM_GENERATOR_SCREEN_HANDLER, DeuteriumGeneratorScreen::new);


    }
}
