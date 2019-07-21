package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui;

import java.io.IOException;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.network.PacketRequestStatusUpdate;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPiece;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;

@SideOnly(Side.CLIENT)
public class GuiInventory extends GuiContainer {
	protected final IHasInventory<? extends InventoryBasic> holder;
	protected final InventoryPlayer playerInventory;
	public static final ResourceLocation guiTexture = new ResourceLocation(SteamAgeRevolution.MODID,
			"textures/gui/inventory.png");

	public GuiInventory(EntityPlayer player, IHasInventory<? extends InventoryBasic> holder) {
		this(player, holder, new ContainerInventory(player, holder));
	}

	public GuiInventory(EntityPlayer player, IHasInventory<? extends InventoryBasic> holder,
			ContainerBase containerInstance) {
		super(containerInstance);
		this.holder = holder;
		this.playerInventory = player.inventory;
	}

	@Override
	public void initGui() {
		super.initGui();
		SteamAgeRevolution.instance.getPacketHandler()
				.sendToServer(new PacketRequestStatusUpdate(holder.getMachinePos()));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
		for(InventoryPiece<?> piece : holder.getInventory().getInventoryPieces()) {
			if(piece.getTooltip() != null
					&& this.isPointInRegion(piece.getX(), piece.getY(), piece.width, piece.height, mouseX, mouseY)) {
				// TODO wrapping
				this.drawHoveringText(piece.getTooltip(), mouseX, mouseY);
			}
			piece.drawScreenCallback(this, mouseX, mouseY, partialTicks);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRenderer.drawString(this.holder.getLocalizedName(), 8, 6, 4210752);
		this.fontRenderer.drawString(playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2,
				4210752);
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.renderEngine.bindTexture(guiTexture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		for(InventoryPiece<?> piece : holder.getInventory().getInventoryPieces()) {
			if(piece.shouldRender()) {
				if(piece instanceof InventoryPieceItemHandler) {
					InventoryPieceItemHandler pieceH = (InventoryPieceItemHandler) piece;
					for(int slot = 0; slot < pieceH.getHandler().getSlots(); slot++) {
						this.drawTexturedModalRect(this.getGuiLeft() + pieceH.getSlotPositionX(slot) + pieceH.offset,
								this.getGuiTop() + pieceH.getSlotPositionY(slot) + pieceH.offset, piece.textureX,
								piece.textureY, piece.width, piece.height);
					}
				}
				else {
					this.drawTexturedModalRect(this.getGuiLeft() + piece.getX() + piece.offset,
							this.getGuiTop() + piece.getY() + piece.offset, piece.textureX, piece.textureY, piece.width,
							piece.height);
				}
			}
			piece.backgroundLayerCallback(this, partialTicks, mouseX, mouseY);
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		for(InventoryPiece<?> piece : holder.getInventory().getInventoryPieces()) {
			if(this.isPointInRegion(piece.getX(), piece.getY(), piece.width, piece.height, mouseX, mouseY)) {
				piece.mouseClickedCallback(this, mouseButton);
			}
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	// Elevate to public for use in callbacks
	@Override
	public boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY) {
		return super.isPointInRegion(rectX, rectY, rectWidth, rectHeight, pointX, pointY);
	}

}
