package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPiece;

@SideOnly(Side.CLIENT)
public class GuiInventory extends GuiContainer {
	protected final IHasInventory<? extends InventoryBasic> holder;
	public ResourceLocation guiTexture;

	public GuiInventory(EntityPlayer player, IHasInventory<? extends InventoryBasic> holder) {
		this(player, holder, new ContainerInventory(player, holder), "");
	}

	public GuiInventory(EntityPlayer player, IHasInventory<? extends InventoryBasic> holder,
			ContainerBase containerInstance) {
		this(player, holder, containerInstance, "");
	}

	public GuiInventory(EntityPlayer player, IHasInventory<? extends InventoryBasic> holder,
			ContainerBase containerInstance, String textureOverride) {
		super(containerInstance);
		String name = textureOverride;
		if(textureOverride.isEmpty()) {
			name = holder.getName().toLowerCase().replace(' ', '_');
		}
		guiTexture = new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/" + name + ".png");
		this.holder = holder;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
		for(InventoryPiece piece : holder.getInventory().getInventoryPieces()) {
			piece.drawScreenCallback(this, mouseX, mouseY, partialTicks);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.renderEngine.bindTexture(guiTexture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		for(InventoryPiece piece : holder.getInventory().getInventoryPieces()) {
			piece.backgroundLayerCallback(this, partialTicks, mouseX, mouseY);
		}
	}

	// Elevate to public for use in callbacks
	@Override
	public boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY) {
		return super.isPointInRegion(rectX, rectY, rectWidth, rectHeight, pointX, pointY);
	}

}
