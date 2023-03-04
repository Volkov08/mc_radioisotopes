package net.volkov.radioisotopes.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ReactorRadEntityRenderer extends EntityRenderer<ReactorRadEntity> {

    public ReactorRadEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(ReactorRadEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        // Do nothing
    }

    @Override
    public Identifier getTexture(ReactorRadEntity entity) {
        return null;
    }
}