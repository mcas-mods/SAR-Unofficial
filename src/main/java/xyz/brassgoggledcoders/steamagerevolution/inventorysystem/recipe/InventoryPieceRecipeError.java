package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import java.util.List;

import net.minecraft.util.text.TextFormatting;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryPiece;

public class InventoryPieceRecipeError extends InventoryPiece<InventoryCraftingMachine> {

    public InventoryPieceRecipeError(int xPos, int yPos) {
        super(xPos, yPos, 90, 166, 13, 13, 0);
    }

    @Override
    public boolean shouldRender() {
        return !this.enclosingInv.getRecipeError().equals(RecipeError.NONE);
    }

    @Override
    public List<String> getTooltip(List<String> tips) {
        if(this.enclosingInv.getRecipeError() != null) {
            tips.add(TextFormatting.RED.toString() + this.enclosingInv.getRecipeError().getLocalized());
        }
        return tips;
    }

}
