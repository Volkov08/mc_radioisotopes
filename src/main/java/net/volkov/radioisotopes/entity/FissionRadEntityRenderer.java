package net.volkov.radioisotopes.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class FissionRadEntityRenderer extends EntityRenderer<FissionRadEntity> {

    public FissionRadEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(FissionRadEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        // Do nothing
    }

    @Override
    public Identifier getTexture(FissionRadEntity entity) {
        return null;
    }
}