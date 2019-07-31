package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.pieces;

import java.awt.Color;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import xyz.brassgoggledcoders.steamagerevolution.SARCaps;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryHeatable;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryPiece;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GUIInventory;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.ControllerBoiler;

public class InventoryPieceTemperatureGauge extends InventoryPiece<InventoryHeatable> {

    public InventoryPieceTemperatureGauge(int xPos, int yPos) {
        super(xPos, yPos, 88, 166, 6, 44, -1);
    }

    @Override
    public void backgroundLayerCallback(GUIInventory gui, float partialTicks, int mouseX, int mouseY) {
        if(this.enclosingInv.getCapability(SARCaps.HEATABLE, null).getCurrentTemperature() > 0) {
            int scaled = (int) Math.min(this.getGUIElement().height,
                    (this.enclosingInv.getCapability(SARCaps.HEATABLE, null).getCurrentTemperature()
                            / this.enclosingInv.getCapability(SARCaps.HEATABLE, null).getMaximumTemperature())
                            * getGUIElement().height);
            gui.drawGradientRect(gui.guiLeft + this.getX(), gui.guiTop + this.getY() + scaled + getOffset(),
                    gui.guiLeft + this.getX() + this.getGUIElement().width + getOffset(),
                    gui.guiTop + this.getY() + this.getGUIElement().height + getOffset(), Color.RED.getRGB(),
                    Color.BLUE.getRGB());
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
                    tips.add("Temperature: "
                            + celsiusToFarenheit(
                                    this.enclosingInv.getCapability(SARCaps.HEATABLE, null).getCurrentTemperature())
                            + unit + "/"
                            + celsiusToFarenheit(
                                    this.enclosingInv.getCapability(SARCaps.HEATABLE, null).getMaximumTemperature())
                            + unit);
                }
                else {
                    tips.add("Temperature: "
                            + this.enclosingInv.getCapability(SARCaps.HEATABLE, null).getCurrentTemperature() + unit
                            + "/" + this.enclosingInv.getCapability(SARCaps.HEATABLE, null).getMaximumTemperature()
                            + unit);
                }
            }
            else {
                tips.add(TextFormatting.BLUE + "Cold");
            }
        }
        return tips;
    }

    public static double celsiusToFarenheit(double celcius) {
        return (1.8 * celcius) + 32;
    }

}
