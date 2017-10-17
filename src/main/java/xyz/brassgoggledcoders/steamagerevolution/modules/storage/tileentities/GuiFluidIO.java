package xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.GuiUtils;

@SideOnly(Side.CLIENT)
public class GuiFluidIO extends GuiContainer {
	private static ResourceLocation guiTexture =
			new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/fluid_io.png");
	private final TileEntityFluidIO tile;

	public GuiFluidIO(EntityPlayer player, TileEntityFluidIO tile) {
		super(new ContainerFluidIO(player, tile));
		this.tile = tile;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.mc.renderEngine.bindTexture(guiTexture);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

		FluidStack containedFluid = tile.buffer.getFluid();
		int capacity = tile.buffer.getCapacity();

		if(containedFluid != null && containedFluid.getFluid() != null && containedFluid.amount > 0) {
			GuiUtils.renderGuiTank(containedFluid, capacity, containedFluid.amount, this.guiLeft + 78, this.guiTop + 11,
					20, 60);
		}

		this.mc.renderEngine.bindTexture(guiTexture);
		this.drawTexturedModalRect(this.guiLeft + 78, this.guiTop + 17, 176, 14, 20, 49);
	}

}
