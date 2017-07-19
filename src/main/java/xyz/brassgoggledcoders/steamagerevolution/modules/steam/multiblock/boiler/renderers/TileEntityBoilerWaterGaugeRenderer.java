package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.TileEntityBoilerWaterGauge;
import xyz.brassgoggledcoders.steamagerevolution.utils.RenderUtil;

public class TileEntityBoilerWaterGaugeRenderer extends TileEntitySpecialRenderer<TileEntityBoilerWaterGauge> {

	protected static Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void render(TileEntityBoilerWaterGauge tile, double x, double y, double z, float partialTicks,
			int destroyStage, float alpha) {
		if(!tile.isConnected())
			return;
		FluidTank waterTank = tile.getMultiblockController().waterTank;
		FluidStack water = waterTank.getFluid();

		if(water != null) {

			float height = ((float) water.amount) / (float) waterTank.getCapacity();

			float d = RenderUtil.FLUID_OFFSET;
			RenderUtil.renderFluidCuboid(water, tile.getPos(), x, y, z, d, d, d, 1d - d, height - d, 1d - d);
		}
	}
}