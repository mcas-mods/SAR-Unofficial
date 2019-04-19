package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import java.awt.Color;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SARCapabilities;

@SideOnly(Side.CLIENT)
public class GuiOreMinecart extends GuiInventory {
	
	EntityMinecartOreCarrier cart;
	
	public GuiOreMinecart(EntityPlayer player, EntityMinecartOreCarrier cart) {
		super(player);
		this.cart = cart;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.drawCenteredString(this.fontRenderer, "Amount: "+ this.cart.getCapability(SARCapabilities.HEAVYORE_HOLDER, null).getOreAmount("Gold"), 50, 50, Color.BLACK.getRGB()); 
	}
}
