package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import net.minecraft.util.text.TextFormatting;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPiece;

public class InventoryPieceRecipeError extends InventoryPiece<InventoryCraftingMachine> {

	public InventoryPieceRecipeError(int xPos, int yPos) {
		super(xPos, yPos, 90, 166, 13, 13, 0);
	}

	@Override
	public boolean shouldRender() {
		return !this.enclosingInv.getRecipeError().equals(RecipeError.NONE);
	}

	@Override
	public String getTooltip() {
		if(this.enclosingInv.getRecipeError() != null) {
			return TextFormatting.RED.toString() + this.enclosingInv.getRecipeError().getLocalized();
		}
		else {
			return super.getTooltip();
		}
	}

}
