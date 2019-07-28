package xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler;

import java.awt.Color;

import net.minecraft.util.text.TextComponentTranslation;
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
                String unit = new TextComponentTranslation("info.tempunit").getFormattedText();
                if(unit.contains("F")) {
                    return "Temperature: " + celsiusToFarenheit(boiler.currentTemperature) + unit + "/"
                            + celsiusToFarenheit(ControllerBoiler.operatingTemp) + unit;
                }
                else {
                    return "Temperature: " + boiler.currentTemperature + unit + "/" + ControllerBoiler.operatingTemp
                            + unit;
                }
            }
        }
        return null;
    }

    public double celsiusToFarenheit(int celsius) {
        return (1.8 * celsius) + 32;
    }

}
