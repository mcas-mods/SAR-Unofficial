package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import net.minecraft.util.text.TextFormatting;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPiece;

public class InventoryPieceRecipeError extends InventoryPiece<InventoryRecipe> {

	public InventoryPieceRecipeError(InventoryRecipe parent, int xPos, int yPos) {
		super("recipeError", parent, xPos, yPos, 90, 166, 13, 13, 0);
	}

	@Override
	public boolean shouldRender() {
		return !this.parent.getRecipeError().equals(RecipeError.NONE);
	}

	@Override
	public String getTooltip() {
		if(this.parent.getRecipeError() != null) {
			return TextFormatting.RED.toString() + this.parent.getRecipeError().getLocalized();
		}
		else {
			return super.getTooltip();
		}
	}

}
