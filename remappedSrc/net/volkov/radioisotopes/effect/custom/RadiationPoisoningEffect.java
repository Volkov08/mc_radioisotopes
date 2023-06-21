package net.volkov.radioisotopes.effect.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class RadiationPoisoningEffect extends StatusEffect {
    private float dose = 0.0f;
    public RadiationPoisoningEffect(StatusEffectCategory statusEffectCategory, int color){
        super(statusEffectCategory,color);
    }
    @Override
    public void applyUpdateEffect(LivingEntity pLivingEntity, int pAmplifier){
        if(!pLivingEntity.world.isClient){
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
        dose = calRadiationDose(pDuration);
        return pDuration%20 == 0;
    }

    public static float calRadiationDose(int dur){
        return (float)(Math.pow(2,(float)(dur)/6000));
    }
}