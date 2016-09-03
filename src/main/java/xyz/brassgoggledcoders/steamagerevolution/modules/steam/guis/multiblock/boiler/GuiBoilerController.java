package xyz.brassgoggledcoders.steamagerevolution.modules.steam.guis.multiblock.boiler;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.boilerplate.client.guis.GuiScreenBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.containers.multiblock.boiler.ContainerBoilerController;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.boiler.BasicBoilerController;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.boiler.TileEntityBoilerController;

@SideOnly(Side.CLIENT)
public class GuiBoilerController extends GuiScreenBase {
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
		if(tile.isConnected()) {
			BasicBoilerController controller = (BasicBoilerController) tile.getMultiblockController();
			for(int i = 0; i < controller.getAttachedGUIs().size(); i++) {
				GuiButton button = new GuiButton(i, this.guiLeft + 5, this.guiTop + (30 * i),
						controller.getAttachedGUIs().get(i).toString());
				this.buttonList.add(button);
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(tile.isConnected()) {
			BasicBoilerController controller = (BasicBoilerController) tile.getMultiblockController();
			TileEntity otherTile = controller.getAttachedGUIs().get(button.id);
			Minecraft.getMinecraft().thePlayer.openGui(SteamAgeRevolution.instance, 0, otherTile.getWorld(),
					otherTile.getPos().getX(), otherTile.getPos().getY(), otherTile.getPos().getZ());
		}
		super.actionPerformed(button);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.mc.renderEngine.bindTexture(guiTexture);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}

}