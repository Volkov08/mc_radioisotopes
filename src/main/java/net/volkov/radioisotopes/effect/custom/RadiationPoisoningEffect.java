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
    public RadiationPoisoningEffect(StatusEffectCategory statusEffectCategory, int color){
        super(statusEffectCategory,color);
    }
    @Override
    public void applyUpdateEffect(LivingEntity pLivingEntity, int pAmplifier){
        if(!pLivingEntity.world.isClient){
            pLivingEntity.damage(DamageSource.MAGIC,0.05f*(int)Math.pow(1+pAmplifier,2));
            StatusEffectInstance nauseaEffect = pLivingEntity.getStatusEffect(StatusEffect.byRawId(9));
            if (pAmplifier>0 && (nauseaEffect == null || nauseaEffect.getDuration()<=260)){
                pLivingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA,400,pAmplifier-1));
            }
        }
        super.applyUpdateEffect(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int pDuration, int pAmplifier){
        return pDuration%30 == 0;
    }
}
