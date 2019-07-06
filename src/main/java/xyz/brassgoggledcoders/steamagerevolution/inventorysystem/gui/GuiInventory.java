package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui;

import com.teamacronymcoders.base.containers.ContainerBase;
import com.teamacronymcoders.base.util.GuiHelper;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IMachineHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceProgressBar;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSmart;

@SideOnly(Side.CLIENT)
public class GuiInventory extends GuiContainer {
	protected final IMachineHasInventory holder;
	protected ResourceLocation guiTexture;

	public GuiInventory(EntityPlayer player, IMachineHasInventory holder) {
		this(player, holder, new ContainerInventory(player, holder), "");
	}

	public GuiInventory(EntityPlayer player, IMachineHasInventory holder, ContainerBase containerInstance) {
		this(player, holder, containerInstance, "");
	}

	public GuiInventory(EntityPlayer player, IMachineHasInventory holder, ContainerBase containerInstance,
			String textureOverride) {
		super(containerInstance);
		String name = textureOverride;
		if(textureOverride.isEmpty()) {
			name = holder.getName().toLowerCase().replace(' ', '_');
		}
		guiTexture = new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/" + name + ".png");
		this.holder = holder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
		for(InventoryPieceHandler<? extends FluidTankSmart> fPiece : holder.getInventory().fluidPieces.values()) {
			if(isPointInRegion(fPiece.getX(0), fPiece.getY(0), 20, 55, mouseX, mouseY)) {
				this.drawHoveringText(com.teamacronymcoders.base.util.TextUtils
						.representTankContents(fPiece.getHandler()).getFormattedText(), mouseX, mouseY);
			}
		}
		InventoryPieceProgressBar progressBar = holder.getInventory().progressBar;
		if(progressBar != null) {
			if(isPointInRegion(progressBar.getX(0), progressBar.getY(0), 24, 16, mouseX, mouseY)) {
				if(holder.getCurrentMaxTicks() == 0) {
					this.drawHoveringText(TextFormatting.RED.toString() + "No recipe", mouseX, mouseY); // TODO
					// Localization
				}
				else if(GuiScreen.isShiftKeyDown()) {
					this.drawHoveringText(holder.getCurrentProgress() + "/" + holder.getCurrentMaxTicks() + " ticks",
							mouseX, mouseY);
				}
				else {
					this.drawHoveringText(
							holder.getCurrentProgress() / 20 + "/" + holder.getCurrentMaxTicks() / 20 + " seconds",
							mouseX, mouseY);
				}
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.renderEngine.bindTexture(guiTexture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		for(InventoryPieceHandler<? extends FluidTankSmart> fPiece : holder.getInventory().fluidPieces.values()) {
			addTank(fPiece);
		}

		if(holder.getInventory().progressBar != null && holder.getCurrentMaxTicks() > 0) {
			{
				mc.renderEngine.bindTexture(guiTexture);
				int progress = holder.getCurrentProgress();// TODO this needs packet synced
				int total = holder.getCurrentMaxTicks();
				int progressScaled = progress != 0 && total != 0 ? progress * 24 / total : 0;
				this.drawTexturedModalRect(guiLeft + holder.getInventory().progressBar.getX(0),
						guiTop + holder.getInventory().progressBar.getY(0), 176, 83, progressScaled + 1, 16);
			}
		}
	}

	private void addTank(InventoryPieceHandler<? extends FluidTankSmart> piece) {
		FluidTank tank = piece.getHandler();
		draw(tank.getFluid(), tank.getCapacity(), piece.getX(0), piece.getY(0));
	}

	private void draw(FluidStack stack, int capacity, int xPos, int yPos) {
		if(stack != null && stack.getFluid() != null && stack.amount > 0) {
			GuiHelper.renderGuiTank(stack, capacity, stack.amount, guiLeft + xPos, guiTop + yPos, 20, 60);
			mc.renderEngine.bindTexture(guiTexture);
			this.drawTexturedModalRect(guiLeft + xPos, guiTop + yPos + 6, 176, 14, 20, 49);
		}
	}

}
