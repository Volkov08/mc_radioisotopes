package net.volkov.radioisotopes.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import net.volkov.radioisotopes.ClientMain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MainMixin {
	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		ClientMain.LOGGER.info("This line is printed by an example mod mixin!");
	}
}
