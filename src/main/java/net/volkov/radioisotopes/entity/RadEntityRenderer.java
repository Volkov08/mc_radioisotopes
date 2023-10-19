package net.volkov.radioisotopes.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RadEntityRenderer extends EntityRenderer<RadEntity> {

    public RadEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(RadEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        // Do nothing
    }

    @Override
    public Identifier getTexture(RadEntity entity) {
        return null;
    }
}