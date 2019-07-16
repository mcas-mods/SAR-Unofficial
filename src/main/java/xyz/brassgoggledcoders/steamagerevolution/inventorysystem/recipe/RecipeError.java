package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import net.minecraft.util.text.TextComponentTranslation;

public enum RecipeError {
	OUTPUT_BLOCKED(0, "sar.recipeerror.outputspace"), INSUFFICIENT_STEAM(1, "sar.recipeerror.insufficientsteam");

	String translationKey;
	Short networkID;

	RecipeError(int networkID, String translationKey) {
		this.networkID = Integer.valueOf(networkID).shortValue();
		this.translationKey = translationKey;
	}

	public Short getNetworkID() {
		return networkID;
	}

	public String getTranslationKey() {
		return translationKey;
	}

	public String getLocalized() {
		return new TextComponentTranslation(this.getTranslationKey()).getFormattedText();
	}

	// TODO
	public static RecipeError fromNetworkID(short errorID) {
		switch(errorID) {
			case 0:
				return OUTPUT_BLOCKED;
			case 1:
				return INSUFFICIENT_STEAM;
		}
		return null;
	}

}
