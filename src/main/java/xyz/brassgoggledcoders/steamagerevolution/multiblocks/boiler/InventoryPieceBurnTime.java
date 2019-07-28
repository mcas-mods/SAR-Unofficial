package xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPiece;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;

public class InventoryPieceBurnTime extends InventoryPiece<InventoryCraftingMachine> {

    public InventoryPieceBurnTime(int xPos, int yPos) {
        super(xPos, yPos, 92, 166, 13, 13, 0);
    }

    @Override
    public void backgroundLayerCallback(GuiInventory gui, float partialTicks, int mouseX, int mouseY) {
        if(this.enclosingInv.enclosingMachine instanceof ControllerBoiler) {
            ControllerBoiler boiler = (ControllerBoiler) this.enclosingInv.enclosingMachine;
            if(boiler.currentBurnTime > 0) {
                int height = boiler.currentBurnTime / this.height;
                gui.drawTexturedModalRect(gui.guiLeft + this.getX(), gui.guiTop + this.getY(), 92, 179, 14, height);
            }
        }
        super.backgroundLayerCallback(gui, partialTicks, mouseX, mouseY);
    }

    @Override
    public String getTooltip() {
        if(this.enclosingInv.enclosingMachine instanceof ControllerBoiler) {
            ControllerBoiler boiler = (ControllerBoiler) this.enclosingInv.enclosingMachine;
            if(boiler.currentBurnTime > 0) {
                return "Burn Time: " + boiler.currentBurnTime + " ticks";
            }
        }
        return null;
    }

}
