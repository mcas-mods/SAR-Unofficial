package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.ControllerBoiler;
import xyz.brassgoggledcoders.steamagerevolution.utils.RenderUtil;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.TileEntityMultiblockBase;

public class TileEntityBoilerSteamGaugeRenderer
		extends TileEntitySpecialRenderer<TileEntityMultiblockBase<ControllerBoiler>> {

	protected static Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void render(TileEntityMultiblockBase<ControllerBoiler> tile, double x, double y, double z,
			float partialTicks, int destroyStage, float alpha) {
		if(!tile.isConnected())
			return;
		FluidTank steamTank = tile.getMultiblockController().steamTank;
		FluidStack steam = steamTank.getFluid();

		if(steam != null) {

			float height = ((float) steam.amount) / (float) steamTank.getCapacity();

			float d = RenderUtil.FLUID_OFFSET;
			RenderUtil.renderFluidCuboid(steam, tile.getPos(), x, y, z, d, d, d, 1d - d, height - d, 1d - d);
		}
	}
}