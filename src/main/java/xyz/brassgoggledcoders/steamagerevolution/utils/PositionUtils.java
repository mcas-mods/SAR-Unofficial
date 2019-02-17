package xyz.brassgoggledcoders.steamagerevolution.utils;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.util.math.BlockPos;

public class PositionUtils {
	public static Pair<BlockPos, BlockPos> shrinkPositionCubeBy(BlockPos minimumCoord, BlockPos maximumCoord, int by) {
		int minX = minimumCoord.getX();
		int minY = minimumCoord.getY();
		int minZ = minimumCoord.getZ();
		int maxX = maximumCoord.getX();
		int maxY = maximumCoord.getY();
		int maxZ = maximumCoord.getZ();

		// TODO Can this be simplified. I can't quite visualise what I'm doing here.
		int interiorMinX = (int) Math.copySign(Math.abs(minX) + (Math.signum(minX) * by), minX);
		int interiorMinY = minY + by;// Y cannot be negative
		int interiorMinZ = (int) Math.copySign(Math.abs(minZ) + (Math.signum(minZ) * by), minZ);

		int interiorMaxX = (int) Math.copySign(Math.abs(maxX) - (Math.signum(maxX) * by), maxX);
		int interiorMaxY = maxY - by;
		int interiorMaxZ = (int) Math.copySign(Math.abs(maxZ) - (Math.signum(maxZ) * by), maxZ);

		BlockPos minimumInteriorPos = new BlockPos(interiorMinX, interiorMinY, interiorMinZ);
		BlockPos maximumInteriorPos = new BlockPos(interiorMaxX, interiorMaxY, interiorMaxZ);

		return Pair.of(minimumInteriorPos, maximumInteriorPos);
	}
}
