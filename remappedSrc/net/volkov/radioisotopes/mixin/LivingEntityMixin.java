package net.volkov.radioisotopes.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;
import net.volkov.radioisotopes.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    private int radiationDur;

    protected LivingEntityMixin(EntityType<? extends LivingEntity> type, World world){
        super(type,world);
    }

    /*
    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci){
        if (!this.world.isClient && this.isPlayer()){
            if (((LivingEntity)(Object)this).isDead() && radiationDur>0){
                radiationDur = 0;
            } else if (radiationDur>0 && ((LivingEntity)(Object)this).getStatusEffect(ModEffects.RAD_POISON)==null){
                ((LivingEntity)(Object)this).addStatusEffect(new StatusEffectInstance(ModEffects.RAD_POISON, radiationDur,0));
            }
        }
    }

    @Inject(method = "onStatusEffectRemoved", at = @At("HEAD"))
    public void onStatusEffectRemoved(StatusEffectInstance effect, CallbackInfo ci){
        if (this.isPlayer() && effect.getEffectType() == ModEffects.RAD_POISON){
            radiationDur = effect.getDuration();
        }
    }
    */
}
