package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;

import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public abstract class SARRectangularMultiblockControllerBase extends RectangularMultiblockControllerBase
		implements IMultiblockControllerInfo {

	protected SARRectangularMultiblockControllerBase(World world) {
		super(world);
	}

	protected FluidTank getTank(String toWrap) {
		return null;
	}

	// @Override
	// protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
	// // TODO
	// ArrayList<Block> blocks = new ArrayList<Block>();
	// connectedParts.forEach(part -> blocks.add(WORLD.getBlockState(part.getWorldPosition()).getBlock()));
	//
	// for(Block required : requiredBlocks) {
	// if(!blocks.contains(required)) {
	// validatorCallback.setLastError(new ValidationError(
	// "steamagerevolution.multiblock.validation.missingrequired", required.getLocalizedName()));
	// return false;
	// }
	// }
	//
	// return super.isMachineWhole(validatorCallback);
	// }

	// Modify from protected to public
	@Override
	public int getMinimumXSize() {
		return 1;
	}

	@Override
	public int getMinimumYSize() {
		return 1;
	}

	@Override
	public int getMinimumZSize() {
		return 1;
	}

	@Override
	protected void onMachineAssembled() {
		SteamAgeRevolution.instance.getLogger().devInfo("Machine Assembled");
		SteamAgeRevolution.proxy.spawnMultiblockAssemblyFX(this.getMinimumCoord(), this.getMaximumCoord());
	}
}
