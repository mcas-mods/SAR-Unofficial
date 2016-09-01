package xyz.brassgoggledcoders.steamagerevolution.modules.steam.guis.multiblock.boiler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.boilerplate.client.guis.GuiScreenBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.containers.multiblock.boiler.ContainerSolidFirebox;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.boiler.TileEntitySolidFirebox;

@SideOnly(Side.CLIENT)
public class GuiSolidFirebox extends GuiScreenBase {
	private static ResourceLocation guiTexture =
			new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/multiblock/solid_firebox.png");
	private final TileEntitySolidFirebox firebox;

	public GuiSolidFirebox(EntityPlayer player, TileEntitySolidFirebox firebox) {
		super(new ContainerSolidFirebox(player, firebox));
		this.firebox = firebox;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.mc.renderEngine.bindTexture(guiTexture);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

		if(firebox.getBurnTime() > 0) {
			int k = this.getBurnLeftScaled(13);
			this.drawTexturedModalRect(x + 56, y + 36 + 12 - k, 176, 12 - k, 14, k + 1);
		}

		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}

	private int getBurnLeftScaled(int pixels) {
		int i = this.firebox.getBurnTime();

		if(i == 0) {
			i = 200;
		}

		return this.firebox.getBurnTime() * pixels / i;
	}
}
