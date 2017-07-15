package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.RenderUtil;

public class TileEntityCastingBenchRenderer extends TileEntitySpecialRenderer<TileEntityCastingBench> {

	protected static Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void renderTileEntityFast(@Nonnull TileEntityCastingBench tile, double x, double y, double z,
			float partialTicks, int destroyStage, float partial, net.minecraft.client.renderer.BufferBuilder buffer) {
		FluidTank tank = tile.tank;
		FluidStack liquid = tank.getFluid();

		if(liquid != null) {

			float height = ((float) liquid.amount) / (float) tank.getCapacity();

			float d = RenderUtil.FLUID_OFFSET;
			RenderUtil.renderFluidCuboid(liquid, tile.getPos(), x, y, z, d, d + 0.2F, d, 1d - d, height - d, 1d - d);
		}
	}
}