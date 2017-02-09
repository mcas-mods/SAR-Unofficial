package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.FluidTankSingleType;

public class ControllerSteamFurnace extends RectangularMultiblockControllerBase {

	private int numAttachedTempLimiters = 0;

	public ItemStackHandler inputInventory = new ItemStackHandler(1);
	public ItemStackHandler outputInventory = new ItemStackHandler(3);
	public FluidTankSingleType steamTank = new FluidTankSingleType(Fluid.BUCKET_VOLUME * 16, "steam");

	int temperature = 0;
	float pressure = 1.0F;
	int currentCookTime = 0;

	private static final int temperatureMax = 100;
	private static final float pressureMax = 2.0F;
	private static final int cookTime = 2400;
	private static final int fluidUseOnHeat = 1000;
	private static final int fluidUseOnUpkeep = 10;

	public ControllerSteamFurnace(World world) {
		super(world);
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		if(newPart instanceof TileEntityFurnaceTemperatureLimiter) {
			numAttachedTempLimiters++;
		}
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		if(oldPart instanceof TileEntityFurnaceTemperatureLimiter) {
			numAttachedTempLimiters--;
		}
	}

	@Override
	protected boolean updateServer() {
		boolean flag = false;
		// Check it isn't boom time.
		if(pressure >= pressureMax) {
			// Uh oh.
			this.WORLD.createExplosion(null, this.getReferenceCoord().getX(), getReferenceCoord().getY(),
					getReferenceCoord().getZ(), 10, true);
			return true;
		}

		// Heat from cold to maximum.
		if(temperature < temperatureMax) {
			if(steamTank.getFluid() != null && steamTank.drain(fluidUseOnHeat, false).amount == fluidUseOnHeat) {
				steamTank.drain(fluidUseOnHeat, true);
				temperature++;
				return true;
			}
		}
		// When at maximum heat, switch to upkeep mode.
		else {
			if(steamTank.getFluid() != null && steamTank.drain(fluidUseOnUpkeep, false).amount == fluidUseOnUpkeep) {
				steamTank.drain(fluidUseOnUpkeep, true);
				flag = true;
			}
			else if(temperature > 0) {
				temperature--;
				flag = true;
			}
		}

		// Smelting logic TODO Liquid metal output
		if(temperature > 30) {
			if(ItemStackUtils.isItemNonNull(inputInventory.getStackInSlot(0)) && ItemStackUtils.isItemNonNull(
					SteamFurnaceRecipes.instance().getResult(inputInventory.getStackInSlot(0), temperature))) {
				if(currentCookTime < cookTime) {
					currentCookTime += Math.round((temperature * pressure) / 10); // TODO
				}
				else {
					ItemStack r =
							SteamFurnaceRecipes.instance().getResult(inputInventory.getStackInSlot(0), temperature);
					ItemStack resultItem = new ItemStack(r.getItem(), 1, r.getItemDamage());
					if(ItemHandlerHelper.insertItem(outputInventory, resultItem, true) == null) {
						if(inputInventory.extractItem(0, resultItem.stackSize, true) != null) {
							inputInventory.extractItem(0, resultItem.stackSize, false);
							ItemHandlerHelper.insertItem(outputInventory, resultItem, false);
							currentCookTime = 0;
							pressure -= 0.01F;
							flag = true;
						}
					}
				}
			}
			// If the furnace has nothing to do, build pressure.
			else {
				pressure += 0.001F;
			}
		}

		return flag;
	}

	@Override
	protected void onMachineDisassembled() {
		temperature = 0;
		pressure = 1.0F;
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		if(world.isAirBlock(new BlockPos(x, y, z)))
			return true;
		else
			return false;
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 26;
	}

	@Override
	protected int getMinimumXSize() {
		return 3;
	}

	@Override
	protected int getMinimumZSize() {
		return 3;
	}

	@Override
	protected int getMinimumYSize() {
		return 3;
	}

	@Override
	protected int getMaximumXSize() {
		return 6;
	}

	@Override
	protected int getMaximumZSize() {
		return 6;
	}

	@Override
	protected int getMaximumYSize() {
		return 6;
	}

	@Override
	public void readFromDisk(NBTTagCompound data) {
		temperature = data.getInteger("temperature");
		pressure = data.getFloat("pressure");
		currentCookTime = data.getInteger("cookTime");
		inputInventory.deserializeNBT(data.getCompoundTag("inputinv"));
		outputInventory.deserializeNBT(data.getCompoundTag("outputinv"));
		steamTank.readFromNBT(data.getCompoundTag("fluidtank"));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setInteger("temperature", temperature);
		data.setFloat("pressure", pressure);
		data.setInteger("cookTime", currentCookTime);
		data.setTag("inputinv", inputInventory.serializeNBT());
		data.setTag("outputinv", outputInventory.serializeNBT());
		NBTTagCompound tankTag = new NBTTagCompound();
		steamTank.writeToNBT(tankTag);
		data.setTag("fluidtank", tankTag);
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMachineAssembled() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMachineRestored() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMachinePaused() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onAssimilate(MultiblockControllerBase assimilated) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateClient() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isBlockGoodForFrame(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isBlockGoodForTop(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isBlockGoodForBottom(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isBlockGoodForSides(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void readFromUpdatePacket(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeToUpdatePacket(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

}
