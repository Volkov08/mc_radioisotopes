package net.volkov.radioisotopes.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.volkov.radioisotopes.ClientMain;

public class UraniumCentrifugeScreen extends HandledScreen<UraniumCentrifugeScreenHandler> {
    private static final Identifier TEXTURE =
            new Identifier(ClientMain.MOD_ID, "textures/gui/uranium_centrifuge_gui.png");

    public UraniumCentrifugeScreen(UraniumCentrifugeScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        if(handler.isCrafting()) {
            int progress = handler.getScaledProgress();
            if (progress<=12){
                drawTexture(matrices, x + 65, y + 24, 177, 62, progress, 4);
            } else if (progress<=55) {
                if (progress==55){
                    drawTexture(matrices, x + 65, y + 24, 177, 14, 12, progress-8);
                }
                drawTexture(matrices, x + 65, y + 24, 177, 14, 12, progress-9);
            } else {
                drawTexture(matrices, x + 65, y + 24, 177, 14, 12, 47);
                if (progress<=61){
                    drawTexture(matrices, x + 77, y + 67, 189, 57, progress-55, 4);
                } else if (progress<=87){
                    drawTexture(matrices, x + 77, y + 67-(progress-61), 189, 57-(progress-61), 6, 4+progress-61);
                } else if (progress<=106){
                    drawTexture(matrices, x + 77, y + 35, 189, 25, 6+progress-87, 36);
                }
            }
            //drawTexture(matrices, x + 65, y + 24, 177, 14, progress, 47);
        }

        if(handler.hasFuel()) {
            drawTexture(matrices, x + 34, y + 37 + 14 - handler.getScaledFuelProgress(), 176,
                    14 - handler.getScaledFuelProgress(), 14, handler.getScaledFuelProgress());
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
