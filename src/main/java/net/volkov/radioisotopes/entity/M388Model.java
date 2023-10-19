package net.volkov.radioisotopes.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class M388Model extends EntityModel<Entity> {
    private final ModelPart m388;
    public M388Model(ModelPart root) {
        this.m388 = root.getChild("m388");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData m388 = modelPartData.addChild("m388", ModelPartBuilder.create().uv(0, 0).cuboid(-10.0F, -8.0F, 6.0F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F))
                .uv(4, 14).cuboid(-11.0F, -6.0F, 5.0F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 14).cuboid(-6.0F, -6.0F, 5.0F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F))
                .uv(8, 11).cuboid(-6.0F, -6.0F, 10.0F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F))
                .uv(12, 11).cuboid(-11.0F, -6.0F, 10.0F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 11).cuboid(-9.0F, -9.0F, 7.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, 24.0F, -8.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }
    @Override
    public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        m388.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
