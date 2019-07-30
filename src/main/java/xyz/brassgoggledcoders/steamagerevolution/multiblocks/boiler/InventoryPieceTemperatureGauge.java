package xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler;

import java.awt.Color;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryPiece;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GUIInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;

public class InventoryPieceTemperatureGauge extends InventoryPiece<InventoryCraftingMachine> {

    public InventoryPieceTemperatureGauge(int xPos, int yPos) {
        super(xPos, yPos, 88, 166, 4, 44, -1);
    }

    @Override
    public void backgroundLayerCallback(GUIInventory gui, float partialTicks, int mouseX, int mouseY) {
        if(this.enclosingInv.enclosingMachine instanceof ControllerBoiler) {
            ControllerBoiler boiler = (ControllerBoiler) this.enclosingInv.enclosingMachine;
            if(boiler.currentTemperature > 0) {
                int scaled = Math.min(this.getGUIElement().height,
                        (boiler.currentTemperature / ControllerBoiler.operatingTemp) * getGUIElement().height);
                gui.drawGradientRect(gui.guiLeft + this.getX(), gui.guiTop + this.getY() + scaled + getOffset(),
                        gui.guiLeft + this.getX() + this.getGUIElement().width + getOffset() * 2,
                        gui.guiTop + this.getY() + this.getGUIElement().height + getOffset() * 2, Color.RED.getRGB(),
                        Color.BLUE.getRGB());
            }
        }
        super.backgroundLayerCallback(gui, partialTicks, mouseX, mouseY);
    }

    @Override
    public List<String> getTooltip(List<String> tips) {
        if(this.enclosingInv.enclosingMachine instanceof ControllerBoiler) {
            ControllerBoiler boiler = (ControllerBoiler) this.enclosingInv.enclosingMachine;
            if(boiler.currentBurnTime > 0) {
                String unit = new TextComponentTranslation("info.tempunit").getFormattedText();
                if(unit.contains("F") && !GuiScreen.isShiftKeyDown()) {
                    tips.add("Temperature: " + celsiusToFarenheit(boiler.currentTemperature) + unit + "/"
                            + celsiusToFarenheit(ControllerBoiler.operatingTemp) + unit);
                }
                else {
                    tips.add("Temperature: " + boiler.currentTemperature + unit + "/" + ControllerBoiler.operatingTemp
                            + unit);
                }
            }
            else {
                tips.add(TextFormatting.BLUE + "Cold");
            }
        }
        return tips;
    }

    public double celsiusToFarenheit(int celsius) {
        return (1.8 * celsius) + 32;
    }

}
