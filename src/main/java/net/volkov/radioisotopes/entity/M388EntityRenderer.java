package net.volkov.radioisotopes.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.volkov.radioisotopes.ClientMain;

public class M388EntityRenderer extends EntityRenderer<M388Entity> {
    private static final Identifier TEXTURE = new Identifier(ClientMain.MOD_ID, "textures/entity/davy_chrockett_texture.png");
    private final M388Model model = new M388Model(M388Model.getTexturedModelData().createModel());

    public M388EntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(M388Entity persistentProjectileEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        float yaw = persistentProjectileEntity.getYaw(g);
        float pitch = persistentProjectileEntity.getPitch(g);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-yaw));
        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(pitch - 90.0f));
        this.model.render(matrixStack, vertexConsumerProvider.getBuffer(this.model.getLayer(this.getTexture(persistentProjectileEntity))), i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
        super.render(persistentProjectileEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(M388Entity entity) {
        return TEXTURE;
    }
}
