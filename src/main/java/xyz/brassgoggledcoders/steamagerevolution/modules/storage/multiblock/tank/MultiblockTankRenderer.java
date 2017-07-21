package xyz.brassgoggledcoders.steamagerevolution.modules.storage.multiblock.tank;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.FMLLog;
import xyz.brassgoggledcoders.steamagerevolution.utils.RenderUtil;

public class MultiblockTankRenderer extends TileEntitySpecialRenderer<TileEntityTankCasing> {

	protected static Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void render(TileEntityTankCasing tile, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		// RE last check - ensures we only render once. Save delegate should be at reference coord - lowest x,y,z etc -
		// so should always be casing.
		if(tile.isConnected() && tile.getMultiblockController().isAssembled() && tile.isMultiblockSaveDelegate()) {
			ControllerTank t = tile.getMultiblockController();
			FluidTank tank = t.tank;
			FluidStack fluid = tank.getFluid();

			if(fluid != null) {
				// FMLLog.warning("Fluid is not null");
				float d = RenderUtil.FLUID_OFFSET;
				int yd = 1 + Math.max(0, t.maximumInteriorPos.getY() - t.maximumInteriorPos.getY());
				int height = yd * 1000 - (int) (d * 2000d);
				FMLLog.warning("Trying render" + height);
				RenderUtil.renderStackedFluidCuboid(fluid, x, y, z, tile.getPos(), t.minimumInteriorPos,
						t.maximumInteriorPos, d, height);
				// RenderUtil.renderFluidCuboid(liquid, tile.getPos(), x, y, z, d, d, d, 1d - d, height - d, 1d - d);
			}
		}
	}
}