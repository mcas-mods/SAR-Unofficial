package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryRecipe;

public class InventoryPieceRecipeError extends InventoryPiece {

	public final InventoryRecipe parent;

	public InventoryPieceRecipeError(InventoryRecipe parent, int xPos, int yPos) {
		super("recipeError", xPos, yPos, 90, 166, 13, 13, 0);
		this.parent = parent;
	}

	public boolean shouldRender() {
		return this.parent.getRecipeError() != null;
	}

}
