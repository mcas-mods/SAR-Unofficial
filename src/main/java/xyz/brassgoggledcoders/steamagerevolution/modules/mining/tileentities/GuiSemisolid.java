package xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.ISemisolidHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IHasInventory;

@SideOnly(Side.CLIENT)
public class GuiSemisolid extends GuiInventory {

	public GuiSemisolid(EntityPlayer player, IHasInventory<InventorySemisolid> holder, String textureOverride) {
		super(player, holder, textureOverride);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		InventoryPieceSemisolid inventoryPiece = ((InventorySemisolid) holder.getInventory()).ore;
		ISemisolidHandler oreHandler = inventoryPiece.getHandler();
		if (oreHandler.getHolders()[0].getCrushed() != null) {
			if (isPointInRegion(inventoryPiece.getX(0), inventoryPiece.getY(0), 8, 55, mouseX, mouseY)) {
				if (oreHandler.getHolders()[0] != null) {
					List<String> tooltip = Lists.newArrayList();
					int capacity = oreHandler.getHolders()[0].getHolderCapacity();
					if (oreHandler.getHolders()[0].getAmount() > 0) {
						tooltip.add(oreHandler.getHolders()[0].getAmount() + "/" + capacity);
						tooltip.add(oreHandler.getHolders()[0].getCrushed().getMaterial().getTranslationKey());
					} else {
						// TODO Localization
						tooltip.add("Empty");
					}
					this.drawHoveringText(tooltip, mouseX, mouseY, fontRenderer);
				}
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		InventoryPieceSemisolid inventoryPiece = ((InventorySemisolid) holder.getInventory()).ore;
		ISemisolidHandler oreHandler = inventoryPiece.getHandler();
		if (oreHandler.getHolders()[0].getCrushed() != null) {
			mc.renderEngine.bindTexture(guiTexture);
			int rgb = oreHandler.getHolders()[0].getCrushed().getMaterial().getColor();
			int red = (rgb >> 16) & 0xFF;
			int green = (rgb >> 8) & 0xFF;
			int blue = rgb & 0xFF;
			GL11.glColor3f(red, green, blue);
			int amount = oreHandler.getHolders()[0].getAmount();
			int capacity = oreHandler.getHolders()[0].getHolderCapacity();
			int height = 55;
			int renderAmount = Math.max(Math.min(height, amount * height / capacity), 1);
			this.drawTexturedModalRect(guiLeft + inventoryPiece.getX(0),
					guiTop + inventoryPiece.getY(0) + (height - renderAmount), 176, 100, 8, renderAmount);
			GL11.glColor3f(0, 0, 0);
		}
	}
}
