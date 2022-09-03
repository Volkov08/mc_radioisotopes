package net.volkov.radioisotopes.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.volkov.radioisotopes.ClientMain;

public class ModScreenHandlers {
    public static ScreenHandlerType<DeuteriumGeneratorScreenHandler> DEUTERIUM_GENERATOR_SCREEN_HANDLER =
            ScreenHandlerRegistry.registerSimple(new Identifier(ClientMain.MOD_ID, "deuterium_generator"),
                    DeuteriumGeneratorScreenHandler::new);

}
