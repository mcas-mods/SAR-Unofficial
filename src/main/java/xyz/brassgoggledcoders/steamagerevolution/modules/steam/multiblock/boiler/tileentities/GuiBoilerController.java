package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities;

import org.fusesource.jansi.Ansi.Color;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.ControllerBoiler;

@SideOnly(Side.CLIENT)
public class GuiBoilerController extends GuiContainer {
	private static ResourceLocation guiTexture =
			new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/multiblock/controller.png");
	private final ControllerBoiler c;

	public GuiBoilerController(EntityPlayer player, TileEntityBoilerController tile) {
		super(new ContainerBoilerController(player, tile));
		this.c = tile.getMultiblockController();
	}

	@Override
	public void initGui() {
		super.initGui();
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
		// this.drawString(fontRendererObj, "Temperature: " + c.temperature, 10, 70, Color.WHITE.value());
		this.drawString(fontRendererObj, "Pressure: " + c.pressure, 10, 90, Color.WHITE.value());
		this.drawString(fontRendererObj, "Burn Time: " + c.currentBurnTime, 10, 110, Color.WHITE.value());
	}

}