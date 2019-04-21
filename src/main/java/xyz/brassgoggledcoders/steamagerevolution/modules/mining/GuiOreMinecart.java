package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import java.awt.Color;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SARCapabilities;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities.EntityMinecartOreCarrier;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.GuiInventory;

@SideOnly(Side.CLIENT)
public class GuiOreMinecart extends GuiInventory {
	
	EntityMinecartOreCarrier cart;
	
	public GuiOreMinecart(EntityPlayer player, EntityMinecartOreCarrier cart) {
		super(player, new TileEntityCrushedLoader());//TODO
		this.cart = cart;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.drawCenteredString(this.fontRenderer, "Amount: "+ this.cart.getCapability(SARCapabilities.CRUSHED_HANDLER, null).getHolders()[0].getAmount(), this.guiLeft + 50, this.guiTop + 50, Color.RED.getRGB()); 
	}
}
