package xyz.brassgoggledcoders.steamagerevolution.utils;

public interface ISARMachineRecipe {

	default int getRecipeProcessingTicks() {
		return 20;
	}

}
