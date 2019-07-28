package xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler;

import java.awt.Color;

import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPiece;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;

public class InventoryPieceTemperatureGauge extends InventoryPiece<InventoryCraftingMachine> {

    public InventoryPieceTemperatureGauge(int xPos, int yPos) {
        super(xPos, yPos, 88, 166, 4, 44, -2);
    }

    @Override
    public void backgroundLayerCallback(GuiInventory gui, float partialTicks, int mouseX, int mouseY) {
        if(this.enclosingInv.enclosingMachine instanceof ControllerBoiler) {
            ControllerBoiler boiler = (ControllerBoiler) this.enclosingInv.enclosingMachine;
            if(boiler.currentTemperature > 0) {
                int height = (boiler.currentTemperature / ControllerBoiler.operatingTemp) * this.height;
                gui.drawGradientRect(gui.guiLeft + this.getX() + offset, height,
                        gui.guiLeft + this.getX() + this.width + offset, gui.guiTop + this.getY() + this.height,
                        Color.RED.getRGB(), Color.BLUE.getRGB());
            }
        }
        super.backgroundLayerCallback(gui, partialTicks, mouseX, mouseY);
    }

    @Override
    public String getTooltip() {
        if(this.enclosingInv.enclosingMachine instanceof ControllerBoiler) {
            ControllerBoiler boiler = (ControllerBoiler) this.enclosingInv.enclosingMachine;
            if(boiler.currentBurnTime > 0) {
                return "Temperature: " + boiler.currentTemperature + "°C"; // TODO US Localization changes (displayed!)
                                                                           // units to Freedom Units :P
            }
        }
        return null;
    }

}
