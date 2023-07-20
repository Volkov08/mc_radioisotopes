package net.volkov.radioisotopes.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.volkov.radioisotopes.ClientMain;
import net.volkov.radioisotopes.effect.custom.RadiationPoisoningEffect;


public class ModEffects {
    public static StatusEffect RAD_POISON;
    public static StatusEffect registerStatusEffect(String name, StatusEffect effect){
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(ClientMain.MOD_ID, name), effect);
    }

    public static void registerEffects() {
        ClientMain.LOGGER.info("Registering Mod Effects for " + ClientMain.MOD_ID);
        RAD_POISON = registerStatusEffect("radiation_poisoning",new RadiationPoisoningEffect(StatusEffectCategory.HARMFUL, 11534112));
    }
}
