package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@SideOnly(Side.CLIENT)
public class GuiBoilerController extends GuiContainer {
	private static ResourceLocation guiTexture =
			new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/multiblock/controller.png");
	private final TileEntityBoilerController tile;

	public GuiBoilerController(EntityPlayer player, TileEntityBoilerController tile) {
		super(new ContainerBoilerController(player, tile));
		this.tile = tile;
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

}