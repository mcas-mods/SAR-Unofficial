package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.ArrayList;
import java.util.List;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.multiblock.validation.ValidationError;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;

public abstract class SARRectangularMultiblockControllerBase extends RectangularMultiblockControllerBase
		implements IMultiblockControllerInfo {

	List<Block> requiredBlocks = new ArrayList<Block>();
	public FluidTankSingleSmart steamTank;
	protected int steamPerTick = 0;
	protected int steamPerOperation = 0;
	protected int ticksPerOperation = 0;

	private int currentProgress = 0;

	protected SARRectangularMultiblockControllerBase(World world, Block... requiredBlocks) {
		super(world);
		for(Block required : requiredBlocks) {
			this.requiredBlocks.add(required);
		}
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		steamTank.readFromNBT(data.getCompoundTag("steamTank"));
		currentProgress = data.getInteger("progress");
	}

	protected FluidTank getTank(String toWrap) {
		return steamTank;
	}

	@Override
	protected boolean updateServer() {
		boolean flag = false;
		if(steamPerTick > 0 && steamTank.getFluidAmount() >= steamPerTick) {
			if(canWork()) {
				flag = onMachineTick();
				steamTank.drain(steamPerTick, true);
				if(currentProgress == ticksPerOperation
						&& (steamPerOperation == 0 || steamTank.getFluidAmount() >= steamPerOperation)) {
					if(onMachineFinish()) {
						flag = true;
					}
					steamTank.drain(steamPerOperation, true);
					currentProgress = 0;
				}
				else {
					currentProgress++;
				}
			}
		}
		return flag;
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

	public ItemStackHandler getInventory(String toWrap) {
		return null;
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setTag("steamTank", steamTank.writeToNBT(new NBTTagCompound()));
		data.setInteger("progress", currentProgress);
	}
}
