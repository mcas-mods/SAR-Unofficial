package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.ArrayList;
import java.util.List;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.multiblock.validation.ValidationError;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public abstract class SARRectangularMultiblockControllerBase extends MultiblockLogicFramework {

	List<Block> requiredBlocks = new ArrayList<Block>();

	protected SARRectangularMultiblockControllerBase(World world, Block... requiredBlocks) {
		super(world);
		for(Block required : requiredBlocks) {
			this.requiredBlocks.add(required);
		}
	}

	protected boolean onMachineFinish() {
		return false;
	}

	protected boolean canWork() {
		return true;
	}

	protected boolean onMachineTick() {
		return false;
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
}
