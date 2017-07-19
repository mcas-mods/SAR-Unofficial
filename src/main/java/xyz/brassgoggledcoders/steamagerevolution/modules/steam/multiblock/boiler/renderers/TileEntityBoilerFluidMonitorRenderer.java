package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.TileEntityBoilerFluidMonitor;
import xyz.brassgoggledcoders.steamagerevolution.utils.RenderUtil;

public class TileEntityBoilerFluidMonitorRenderer extends TileEntitySpecialRenderer<TileEntityBoilerFluidMonitor> {

	protected static Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void render(TileEntityBoilerFluidMonitor tile, double x, double y, double z, float partialTicks,
			int destroyStage, float alpha) {
		if(!tile.isConnected())
			return;
		FluidTank waterTank = tile.getMultiblockController().waterTank;
		FluidStack water = waterTank.getFluid();

		if(water != null) {

			float height = ((float) water.amount) / (float) waterTank.getCapacity();

			float d = RenderUtil.FLUID_OFFSET;
			RenderUtil.renderFluidCuboid(water, tile.getPos(), x, y, z, d, d, d, 1d - d, height - d, 1d - d - 0.5);
		}

		FluidTank steamTank = tile.getMultiblockController().steamTank;
		FluidStack steam = steamTank.getFluid();

		if(steam != null) {

			float height = ((float) steam.amount) / (float) steamTank.getCapacity();

			float d = RenderUtil.FLUID_OFFSET;
			RenderUtil.renderFluidCuboid(steam, tile.getPos(), x, y, z, d, d, d + 0.5, 1d - d, height - d, 1d - d);
		}
	}
}