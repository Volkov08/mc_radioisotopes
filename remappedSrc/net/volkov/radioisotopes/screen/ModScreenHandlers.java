package net.volkov.radioisotopes.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.volkov.radioisotopes.ClientMain;

public class ModScreenHandlers {
    public static ScreenHandlerType<DeuteriumGeneratorScreenHandler> DEUTERIUM_GENERATOR_SCREEN_HANDLER;
    public static ScreenHandlerType<UraniumCentrifugeScreenHandler> URANIUM_CENTRIFUGE_SCREEN_HANDLER;
    public static ScreenHandlerType<AtomicReactorControllerScreenHandler> ATOMIC_REACTOR_CONTROLLER_SCREEN_HANDLER;

    public static void registerAllScreenHandler() {
        DEUTERIUM_GENERATOR_SCREEN_HANDLER =
            ScreenHandlerRegistry.registerSimple(new Identifier(ClientMain.MOD_ID, "deuterium_generator"),
                    DeuteriumGeneratorScreenHandler::new);
        URANIUM_CENTRIFUGE_SCREEN_HANDLER =
            ScreenHandlerRegistry.registerSimple(new Identifier(ClientMain.MOD_ID, "uranium_centrifuge"),
                    UraniumCentrifugeScreenHandler::new);
        ATOMIC_REACTOR_CONTROLLER_SCREEN_HANDLER =
                ScreenHandlerRegistry.registerSimple(new Identifier(ClientMain.MOD_ID, "atomic_reactor_controller"),
                        AtomicReactorControllerScreenHandler::new);
    }

}
