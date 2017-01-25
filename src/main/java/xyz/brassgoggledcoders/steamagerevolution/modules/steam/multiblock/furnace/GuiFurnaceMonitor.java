package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace;

import org.fusesource.jansi.Ansi.Color;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class GuiFurnaceMonitor extends GuiContainer {

	private static ResourceLocation guiTexture =
			new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/multiblock/controller.png");
	private SteamFurnaceController c;

	public GuiFurnaceMonitor(Container inventorySlotsIn, TileEntityFurnaceMonitor tile) {
		super(inventorySlotsIn);
		c = ((SteamFurnaceController) tile.getMultiblockController());
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.mc.renderEngine.bindTexture(guiTexture);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.drawString(fontRendererObj, "Steam Level: " + c.steamTank.getFluidAmount(), 10, 50, Color.WHITE.value());
		this.drawString(fontRendererObj, "Temperature: " + c.temperature, 10, 70, Color.WHITE.value());
	}

}
