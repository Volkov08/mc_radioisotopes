package net.volkov.radioisotopes.sound;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.volkov.radioisotopes.ClientMain;

public class ModSounds {
    public static final Identifier NUKE_EXPLOSION_ID = new Identifier("radioisotopes:nuke_explosion");
    public static SoundEvent NUKE_EXPLOSION =  new SoundEvent(NUKE_EXPLOSION_ID);

    public static void registerSounds() {
        ClientMain.LOGGER.info("Registering mod sounds for " + ClientMain.MOD_ID);
        Registry.register(Registry.SOUND_EVENT, ModSounds.NUKE_EXPLOSION_ID, ModSounds.NUKE_EXPLOSION);
    }
}
