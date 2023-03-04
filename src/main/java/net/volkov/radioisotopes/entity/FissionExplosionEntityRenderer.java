package net.volkov.radioisotopes.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class FissionExplosionEntityRenderer extends EntityRenderer<FissionExplosionEntity> {

    public FissionExplosionEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(FissionExplosionEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        // Do nothing
    }

    @Override
    public Identifier getTexture(FissionExplosionEntity entity) {
        return null;
    }
}