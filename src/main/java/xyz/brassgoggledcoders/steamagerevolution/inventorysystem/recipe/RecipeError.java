package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import net.minecraft.util.text.TextComponentTranslation;

public enum RecipeError {
	OUTPUT_BLOCKED("sar.recipeerror.outputspace"), INSUFFICIENT_STEAM("sar.recipeerror.insufficientsteam");

	String translationKey;

	RecipeError(String translationKey) {
		this.translationKey = translationKey;
	}

	public String getTranslationKey() {
		return translationKey;
	}

	public String getLocalized() {
		return new TextComponentTranslation(this.getTranslationKey()).getFormattedText();
	}

}
