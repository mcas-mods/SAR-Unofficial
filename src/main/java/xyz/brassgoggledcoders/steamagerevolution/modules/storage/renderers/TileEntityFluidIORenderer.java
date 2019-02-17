package xyz.brassgoggledcoders.steamagerevolution.modules.storage.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities.TileEntityFluidIO;
import xyz.brassgoggledcoders.steamagerevolution.utils.RenderUtil;

public class TileEntityFluidIORenderer extends TileEntitySpecialRenderer<TileEntityFluidIO> {

	protected static Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void render(TileEntityFluidIO tile, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		FluidTank tank = tile.inventory.getInputTank();
		FluidStack liquid = tank.getFluid();

		if(liquid != null) {

			float height = ((float) liquid.amount) / (float) tank.getCapacity();

			float d = RenderUtil.FLUID_OFFSET;
			RenderUtil.renderFluidCuboid(liquid, tile.getPos(), x, y, z, d, d, d, 1d - d, height - d, 1d - d);
		}
	}
}