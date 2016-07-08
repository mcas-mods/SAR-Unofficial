package xyz.brassgoggledcoders.steamagerevolution.utils;

import java.util.ArrayList;

import com.google.common.primitives.Ints;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.api.SARAPI;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;

public class SpinUtils {
	public static int getHighestSpeedNearby(World world, BlockPos pos) {
		int[] speeds = new int[EnumFacing.VALUES.length];

		ArrayList<ISpinHandler> handlers = getHandlersNearby(world, pos);

		for(int i = 0; i < EnumFacing.VALUES.length; i++) {
			if(handlers.get(i) == null) {
				speeds[i] = 0;
			}
			else {
				speeds[i] = handlers.get(i).getSpeed();
			}
		}

		return Ints.max(speeds);
	}

	public static ArrayList<ISpinHandler> getHandlersNearby(World world, BlockPos pos) {
		ArrayList<ISpinHandler> handlers = new ArrayList<ISpinHandler>();
		for(int i = 0; i < EnumFacing.VALUES.length; i++) {
			BlockPos off = pos.offset(EnumFacing.VALUES[i]);

			if(world.getTileEntity(off) != null) {
				TileEntity te = world.getTileEntity(off);

				if(te.hasCapability(SARAPI.SPIN_HANDLER_CAPABILITY, null)) {
					ISpinHandler handler = te.getCapability(SARAPI.SPIN_HANDLER_CAPABILITY, null);
					handlers.add(handler);
				}
			}
		}
		return handlers;
	}
}
