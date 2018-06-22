package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.ArrayList;
import java.util.List;

import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.multiblock.validation.ValidationError;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public abstract class SARMultiblockBase extends RectangularMultiblockControllerBase
		implements ISARMultiblock {

	List<Block> requiredBlocks = new ArrayList<Block>();

	protected SARMultiblockBase(World world, Block... requiredBlocks) {
		super(world);
		for(Block required : requiredBlocks) {
			this.requiredBlocks.add(required);
		}
	}

	@Override
	protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
		// TODO
		ArrayList<Block> blocks = new ArrayList<Block>();
		connectedParts.forEach(part -> blocks.add(WORLD.getBlockState(part.getWorldPosition()).getBlock()));

		for(Block required : requiredBlocks) {
			if(!blocks.contains(required)) {
				validatorCallback.setLastError(new ValidationError(
						"steamagerevolution.multiblock.validation.missingrequired", required.getLocalizedName()));
				return false;
			}
		}

		return super.isMachineWhole(validatorCallback);
	}

	@Override
	protected void onMachineAssembled() {
		SteamAgeRevolution.instance.getLogger().devInfo("Machine Assembled");
		SteamAgeRevolution.proxy.spawnMultiblockAssemblyFX(this.getMinimumCoord(), this.getMaximumCoord());
	}

	public ItemStackHandler getInventory(String toWrap) {
		return null;
	}

	protected FluidTank getTank(String toWrap) {
		return null;
	}

	// Modify from protected to public
	@Override
	public int getMinimumXSize() {
		return 0;
	}

	@Override
	public int getMinimumYSize() {
		return 0;
	}

	@Override
	public int getMinimumZSize() {
		return 0;
	}
}
