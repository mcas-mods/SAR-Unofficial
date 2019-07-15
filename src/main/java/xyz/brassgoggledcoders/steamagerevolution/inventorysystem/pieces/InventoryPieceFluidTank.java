package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import com.teamacronymcoders.base.util.GuiHelper;

import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;

public class InventoryPieceFluidTank extends InventoryPieceHandler<FluidTankSync> {

	public InventoryPieceFluidTank(String name, InventoryBasic parent, IOType type, FluidTankSync handler,
			int xPosition, int yPosition) {
		super(name, parent, type, handler, xPosition, yPosition, 28, 166, 22, 62);
	}

	public InventoryPieceFluidTank(String name, InventoryBasic inventoryBasic, FluidTankSync handler, int xPos,
			int yPos) {
		this(name, inventoryBasic, null, handler, xPos, yPos);
	}

	@Override
	public void drawScreenCallback(GuiInventory gui, int mouseX, int mouseY, float partialTicks) {
		if(gui.isPointInRegion(this.getX(), this.getY(), 20, 55, mouseX, mouseY)) {
			gui.drawHoveringText(com.teamacronymcoders.base.util.TextUtils.representTankContents(this.getHandler())
					.getFormattedText(), mouseX, mouseY);
		}
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
}
