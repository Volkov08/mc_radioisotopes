package net.volkov.radioisotopes.effect.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.volkov.radioisotopes.ClientMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RadiationPoisoningEffect extends StatusEffect {
    private float dose = 0.0f;
    public RadiationPoisoningEffect(StatusEffectCategory statusEffectCategory, int color){
        super(statusEffectCategory,color);
    }
    //public static final Logger LOGGER = LoggerFactory.getLogger(ClientMain.MOD_ID);
    @Override
    public void applyUpdateEffect(LivingEntity pLivingEntity, int pAmplifier){
        if(!pLivingEntity.world.isClient){
            //LOGGER.info(Float.toString(dose));
            pLivingEntity.damage(DamageSource.MAGIC,0.05f*dose);
            StatusEffectInstance nauseaEffect = pLivingEntity.getStatusEffect(StatusEffect.byRawId(9));
            if (dose>5 && (nauseaEffect == null || nauseaEffect.getDuration()<=260)){
                pLivingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA,400,0));
            }
        }
        super.applyUpdateEffect(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int pDuration, int pAmplifier){
        dose = (float)(Math.pow(2,(float)(pDuration)/6000));
        return pDuration%20 == 0;
    }
}
