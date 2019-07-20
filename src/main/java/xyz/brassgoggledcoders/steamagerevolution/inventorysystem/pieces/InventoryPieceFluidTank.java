package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import com.teamacronymcoders.base.util.GuiHelper;

import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.FluidTankSync;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;

public class InventoryPieceFluidTank extends InventoryPieceHandler<FluidTankSync> {

	public InventoryPieceFluidTank(IOType type, FluidTankSync handler, int xPosition, int yPosition) {
		this(type, handler, xPosition, yPosition, 2, 168, 18, 44, -1);
		if(IOType.POWER.equals(type)) {
			this.textureX = 0;
			this.textureY = 166;
			this.width = 22;
			this.height = 48;
			this.offset = -3;
		}
	}

	public InventoryPieceFluidTank(FluidTankSync handler, int xPos, int yPos) {
		this(null, handler, xPos, yPos);
	}

	public InventoryPieceFluidTank(IOType input, int capacity, int xPosition, int yPosition) {
		this(null, new FluidTankSync(capacity), xPosition, yPosition);
	}

	protected InventoryPieceFluidTank(IOType type, FluidTankSync handler, int xPosition, int yPosition, int tX, int tY,
			int w, int height, int offset) {
		super(type, handler, xPosition, yPosition, tX, tY, w, height, offset);
		this.getHandler().setEnclosing(this);
	}

	@Override
	public void backgroundLayerCallback(GuiInventory gui, float partialTicks, int mouseX, int mouseY) {
		FluidStack stack = handler.getFluid();
		if(stack != null && stack.getFluid() != null && stack.amount > 0) {
			GuiHelper.renderGuiTank(stack, handler.getCapacity(), stack.amount, gui.guiLeft + this.getX(),
					gui.guiTop + this.getY(), 16, 42);
			gui.mc.renderEngine.bindTexture(GuiInventory.guiTexture);
			gui.drawTexturedModalRect(gui.guiLeft + this.getX(), gui.guiTop + this.getY() - 1, 3, 214, 18, 42);
		}
	}

	@Override
	public String getTooltip() {
		return com.teamacronymcoders.base.util.TextUtils.representTankContents(this.getHandler()).getFormattedText();
	}
}
