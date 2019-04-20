package xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill;

import java.awt.Color;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.ICrushedHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.InventoryOreHolder;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.InventoryPieceOre;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IHasInventory;

@SideOnly(Side.CLIENT)
public class GuiDrill extends GuiInventory {
	
	public GuiDrill(EntityPlayer player, IHasInventory<InventoryOreHolder> holder) {
		super(player, holder);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		InventoryPieceOre inventoryPiece = ((InventoryOreHolder) holder.getInventory()).ore;
		ICrushedHandler oreHandler = inventoryPiece.getHandler();
		if(oreHandler.getHolders() != null && oreHandler.getHolders().length > 0 && oreHandler.getHolders()[0].getCrushed() != null) {
			this.drawCenteredString(fontRenderer, oreHandler.getHolders()[0].getCrushed().getMaterial().getTranslationKey(), inventoryPiece.getX(0), inventoryPiece.getY(0), Color.BLACK.getRGB());
		}
	}
}
