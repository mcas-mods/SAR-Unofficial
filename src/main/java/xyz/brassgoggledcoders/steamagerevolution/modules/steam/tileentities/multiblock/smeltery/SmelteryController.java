package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.smeltery;

import java.util.Set;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.RectangularMultiblockController;

public class SmelteryController extends RectangularMultiblockController {

	private Set<TileEntitySteamInjector> attachedInjectors;

	public SmelteryController(World world) {
		super(world);
	}

	@Override
	protected boolean updateServer() {
		super.updateServer();
		boolean flag = false;
		FMLLog.warning("Ticking");
		return flag;
	}

	// @Override
	// protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
	// // if(connectedParts.)
	// return super.isMachineWhole(validatorCallback);
	// // else
	// // return false;
	// }

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		// TODO
		return 1;
	}

	@Override
	protected boolean isBlockGoodForSides(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		if(world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.NETHER_BRICK)
			return true;
		else
			return false;
	}

	@Override
	protected boolean isBlockGoodForTop(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		if(world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.NETHER_BRICK)
			return true;
		else
			return false;
	}

	@Override
	protected boolean isBlockGoodForBottom(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		if(world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.NETHER_BRICK)
			return true;
		else
			return false;
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		if(world.isAirBlock(new BlockPos(x, y, z)))
			return true;
		else
			return false;
	}

	// May allow larger smelteries in future.

	@Override
	protected int getMinimumXSize() {
		return 5;
	}

	@Override
	protected int getMinimumZSize() {
		return 5;
	}

	@Override
	protected int getMinimumYSize() {
		return 9;
	}

	@Override
	protected int getMaximumXSize() {
		return 5;
	}

	@Override
	protected int getMaximumZSize() {
		return 5;
	}

	@Override
	protected int getMaximumYSize() {
		return 9;
	}

}
