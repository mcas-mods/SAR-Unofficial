package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import com.teamacronymcoders.base.util.GuiHelper;

import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;

public class InventoryPieceFluidTank extends InventoryPieceHandler<FluidTankSync> {

	public InventoryPieceFluidTank(IOType type, FluidTankSync handler, int xPosition, int yPosition) {
		super(type, handler, xPosition, yPosition, 28, 166, 22, 41);
		this.getHandler().setEnclosing(this);
	}

	public InventoryPieceFluidTank(FluidTankSync handler, int xPos, int yPos) {
		this(null, handler, xPos, yPos);
	}

	public InventoryPieceFluidTank(IOType input, int capacity, int xPosition, int yPosition) {
		this(null, new FluidTankSync(capacity), xPosition, yPosition);
	}

	@Override
	public void backgroundLayerCallback(GuiInventory gui, float partialTicks, int mouseX, int mouseY) {
		FluidStack stack = handler.getFluid();
		if(stack != null && stack.getFluid() != null && stack.amount > 0) {
			GuiHelper.renderGuiTank(stack, handler.getCapacity(), stack.amount, gui.guiLeft + this.getX(),
					gui.guiTop + this.getY(), 20, 39);
			gui.mc.renderEngine.bindTexture(GuiInventory.guiTexture);
			gui.drawTexturedModalRect(gui.guiLeft + this.getX(), gui.guiTop + this.getY() + 6, 21, 207, 22, 41);
		}
	}

	@Override
	public String getTooltip() {
		return com.teamacronymcoders.base.util.TextUtils.representTankContents(this.getHandler()).getFormattedText();
	}
}
