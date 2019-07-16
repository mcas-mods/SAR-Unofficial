package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import com.teamacronymcoders.base.util.GuiHelper;

import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryRecipe;

public class InventoryPieceFluidTank extends InventoryPieceHandler<FluidTankSync> {

	public InventoryPieceFluidTank(String name, InventoryBasic parent, IOType type, FluidTankSync handler,
			int xPosition, int yPosition) {
		super(name, parent, type, handler, xPosition, yPosition, 28, 166, 22, 62);
		parent.fluidPieces.put(name, this);
		// TODO
		if(parent instanceof InventoryRecipe) {
			if(type.equals(IOType.INPUT)) {
				((InventoryRecipe) parent).fluidInputPieces.add(this);
			}
			else if(type.equals(IOType.OUTPUT)) {
				((InventoryRecipe) parent).fluidInputPieces.add(this);
			}
		}
	}

	public InventoryPieceFluidTank(String name, InventoryBasic inventoryBasic, FluidTankSync handler, int xPos,
			int yPos) {
		this(name, inventoryBasic, null, handler, xPos, yPos);
	}

	@Override
	public void backgroundLayerCallback(GuiInventory gui, float partialTicks, int mouseX, int mouseY) {
		FluidStack stack = handler.getFluid();
		if(stack != null && stack.getFluid() != null && stack.amount > 0) {
			GuiHelper.renderGuiTank(stack, handler.getCapacity(), stack.amount, gui.guiLeft + this.getX(),
					gui.guiTop + this.getY(), 20, 60);
			gui.mc.renderEngine.bindTexture(GuiInventory.guiTexture);
			gui.drawTexturedModalRect(gui.guiLeft + this.getX(), gui.guiTop + this.getY() + 6, 176, 14, 20, 49);
		}
	}

	@Override
	public String getTooltip() {
		return com.teamacronymcoders.base.util.TextUtils.representTankContents(this.getHandler()).getFormattedText();
	}
}
