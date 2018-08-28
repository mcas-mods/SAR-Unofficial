package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.ControllerBoiler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.TileEntityBoilerGauge;
import xyz.brassgoggledcoders.steamagerevolution.utils.RenderUtil;

public class TileEntityBoilerGaugeRenderer extends TileEntitySpecialRenderer<TileEntityBoilerGauge> {

	protected static Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void render(TileEntityBoilerGauge tile, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		if(!tile.isConnected() || !tile.getMultiblockController().isAssembled()) {
			return;
		}
		ControllerBoiler boiler = tile.getMultiblockController();
		FluidTank steamTank = boiler.inventory.getSteamTank();
		FluidStack steam = steamTank.getFluid();

		if(steam != null) {
			double x1 = boiler.minimumInteriorPos.getX() - boiler.getReferenceCoord().getX();
			double y1 = boiler.minimumInteriorPos.getY() - boiler.getReferenceCoord().getY();
			double z1 = boiler.minimumInteriorPos.getZ() - boiler.getReferenceCoord().getZ();

			double x2 = boiler.maximumInteriorPos.getX() - boiler.getReferenceCoord().getX();
			double z2 = boiler.maximumInteriorPos.getZ() - boiler.getReferenceCoord().getZ();

			BlockPos minPos = new BlockPos(x1, y1, z1);
			BlockPos maxPos = new BlockPos(x2, y1, z2);
			float d = RenderUtil.FLUID_OFFSET;
			int yd = 1 + Math.max(0, boiler.maximumInteriorPos.getY() - boiler.minimumInteriorPos.getY());
			double height = (((float) steam.amount / (float) steamTank.getCapacity())) / yd;
			RenderUtil.renderStackedFluidCuboid(steam, x, y, z, tile.getPos(), minPos, maxPos, d, height - d);
		}
	}
}