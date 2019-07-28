package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Loader;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.compat.jei.SARJEIPlugin;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPiece;

public class InventoryPieceProgressBar extends InventoryPiece<InventoryCraftingMachine> {

    public InventoryPieceProgressBar(int xPos, int yPos) {
        super(xPos, yPos, 22, 166, 22, 15, 0);
        // TODO Formal subpiece system
        // FIXME Broken by the change to builders
        // new InventoryPieceRecipeError(xPos + 10, yPos - 10);
    }

    @Override
    public void backgroundLayerCallback(GuiInventory gui, float partialTicks, int mouseX, int mouseY) {
        if(enclosingInv.getCurrentRecipe() != null) {
            gui.mc.renderEngine.bindTexture(GuiInventory.guiTexture);
            int progress = enclosingInv.getCurrentTicks();
            int total = enclosingInv.getCurrentRecipe().getTicksPerOperation();
            int progressScaled = progress != 0 && total != 0 ? progress * this.width / total : 0;
            gui.drawTexturedModalRect(gui.guiLeft + this.getX(), gui.guiTop + this.getY(), 22, 181, progressScaled, 16);
        }
    }

    @Override
    public String getTooltip() {
        if(enclosingInv.getCurrentRecipe() == null) {
            // TODO Newline support
            String show = Loader.isModLoaded("jei")
                    ? TextFormatting.RESET + new TextComponentTranslation("jei.tooltip.show.recipes").getFormattedText()
                    : "";
            return TextFormatting.RED.toString()
                    + new TextComponentTranslation("sar.recipeerror.norecipe").getFormattedText() + show;
        }
        else if(GuiScreen.isShiftKeyDown()) {
            return enclosingInv.getCurrentTicks() + "/" + enclosingInv.getCurrentRecipe().getTicksPerOperation() + " "
                    + new TextComponentTranslation("info.ticks").getFormattedText();
        }
        else {
            return ((double) enclosingInv.getCurrentTicks() / 20) + "/"
                    + ((double) enclosingInv.getCurrentRecipe().getTicksPerOperation() / 20) + " "
                    + new TextComponentTranslation("info.seconds").getFormattedText();
        }
    }

    @Override
    public void mouseClickedCallback(GuiInventory inventory, int mouseButton) {
        if(Loader.isModLoaded("jei") && SARJEIPlugin.recipesGui != null) {
            SARJEIPlugin.recipesGui.showCategories(
                    Lists.newArrayList(SteamAgeRevolution.MODID + ":" + this.enclosingInv.enclosingMachine.getUID()));
        }
    }
}