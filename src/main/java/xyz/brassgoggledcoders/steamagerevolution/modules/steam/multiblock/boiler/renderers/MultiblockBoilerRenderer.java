package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.renderers;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.ControllerBoiler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.TileEntityBoilerCasing;
import xyz.brassgoggledcoders.steamagerevolution.utils.RenderUtil;

public class MultiblockBoilerRenderer extends TileEntitySpecialRenderer<TileEntityBoilerCasing> {

    protected static Minecraft mc = Minecraft.getMinecraft();

    public static int[] calcLiquidHeights(List<FluidStack> liquids, int capacity, int height, int min) {
        int fluidHeights[] = new int[liquids.size()];

        if (liquids.size() > 0) {

            for (int i = 0; i < liquids.size(); i++) {
                FluidStack liquid = liquids.get(i);

                float h = (float) liquid.amount / (float) capacity;
                fluidHeights[i] = Math.max(min, (int) Math.ceil(h * (float) height));
            }

            // check if we have enough height to render everything, if not remove pixels
            // from the tallest liquid
            int sum = 0;
            do {
                sum = 0;
                int biggest = -1;
                int m = 0;
                for (int i = 0; i < fluidHeights.length; i++) {
                    sum += fluidHeights[i];
                    if (fluidHeights[i] > biggest) {
                        biggest = fluidHeights[i];
                        m = i;
                    }
                }

                // we can't get a result without going negative
                if (fluidHeights[m] == 0) {
                    break;
                }

                // remove a pixel from the biggest one
                if (sum > height) {
                    fluidHeights[m]--;
                }
            }
            while (sum > height);
        }

        return fluidHeights;
    }

    @Override
    public void render(TileEntityBoilerCasing tile, double x, double y, double z, float partialTicks, int destroyStage,
                       float alpha) {
        if (tile.isConnected() && tile.getMultiblockController().isAssembled()
                && tile.getMultiblockController().hasWindow && tile.isMultiblockSaveDelegate()) {
            ControllerBoiler boiler = tile.getMultiblockController();
            double x1 = boiler.minimumInteriorPos.getX() - tile.getPos().getX();
            double y1 = boiler.minimumInteriorPos.getY() - tile.getPos().getY();
            double z1 = boiler.minimumInteriorPos.getZ() - tile.getPos().getZ();

            double x2 = boiler.maximumInteriorPos.getX() - tile.getPos().getX();
            double y2 = boiler.maximumInteriorPos.getY() - tile.getPos().getY();
            double z2 = boiler.maximumInteriorPos.getZ() - tile.getPos().getZ();

            ArrayList<FluidStack> fluids = Lists.newArrayList();
            FluidTank waterTank = boiler.inventory.getOutputTank();
            FluidStack water = waterTank.getFluid();

            if (water != null) {
                fluids.add(water);
            }

            FluidTank steamTank = boiler.inventory.getSteamTank();
            FluidStack steam = steamTank.getFluid();

            if (steam != null) {
                fluids.add(steam);
            }

            BlockPos minPos = new BlockPos(x1, y1, z1);
            BlockPos maxPos = new BlockPos(x2, y1, z2);

            int yd = 1 + Math.max(0, boiler.maximumInteriorPos.getY() - boiler.minimumInteriorPos.getY());
            // one block height = 1000 mb
            int[] heights = calcLiquidHeights(fluids, waterTank.getCapacity() + steamTank.getCapacity(),
                    yd * 1000 - (int) (RenderUtil.FLUID_OFFSET * 2000d), 100);

            double curY = RenderUtil.FLUID_OFFSET;
            // rendering time
            if (water != null) {
                double h = (double) heights[0] / 1000d;
                RenderUtil.renderStackedFluidCuboid(fluids.get(0), x, y, z, boiler.minimumInteriorPos, minPos, maxPos,
                        curY, curY + h, RenderUtil.FLUID_OFFSET);
                curY += h;
            }
            if (steam != null) {
                double h = (double) heights[1] / 1000d;
                RenderUtil.renderStackedFluidCuboid(steam, x, y, z, boiler.minimumInteriorPos, minPos, maxPos, curY,
                        yd - RenderUtil.FLUID_OFFSET, RenderUtil.FLUID_OFFSET);
            }
        }
    }
}