package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace;

import java.util.HashSet;
import java.util.Set;

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

public class SteamFurnaceController extends RectangularMultiblockControllerBase {

	private Set<TileEntityFurnaceItemInput> attachedItemInputs;
	private Set<TileEntityFurnaceItemOutput> attachedItemOutputs;
	private Set<TileEntityFurnaceSteamInput> attachedSteamInputs;

	public ItemStackHandler inputInventory;
	public ItemStackHandler outputInventory;
	public FluidTankSingleType steamTank;

	int temperature = 0;
	private float pressure = 0;
	private static final int fluidUseOnHeat = 1000;
	private static final int fluidUseOnUpkeep = 10;

	public SteamFurnaceController(World world) {
		super(world);
		attachedItemInputs = new HashSet<TileEntityFurnaceItemInput>();
		attachedItemOutputs = new HashSet<TileEntityFurnaceItemOutput>();
		attachedSteamInputs = new HashSet<TileEntityFurnaceSteamInput>();
		inputInventory = new ItemStackHandler(3);
		outputInventory = new ItemStackHandler(3);
		steamTank = new FluidTankSingleType(0, "steam");
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		if(newPart instanceof TileEntityFurnaceItemInput) {
			attachedItemInputs.add((TileEntityFurnaceItemInput) newPart);
		}
		else if(newPart instanceof TileEntityFurnaceItemOutput) {
			attachedItemOutputs.add((TileEntityFurnaceItemOutput) newPart);
		}
		else if(newPart instanceof TileEntityFurnaceSteamInput) {
			attachedSteamInputs.add((TileEntityFurnaceSteamInput) newPart);
			steamTank = new FluidTankSingleType(steamTank.getFluid(),
					Fluid.BUCKET_VOLUME * 16 * attachedSteamInputs.size(), "steam");
		}
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		if(oldPart instanceof TileEntityFurnaceItemInput) {
			attachedItemInputs.remove(oldPart);
		}
		else if(oldPart instanceof TileEntityFurnaceItemOutput) {
			attachedItemOutputs.remove(oldPart);
		}
		else if(oldPart instanceof TileEntityFurnaceSteamInput) {
			attachedSteamInputs.add((TileEntityFurnaceSteamInput) oldPart);
			steamTank = new FluidTankSingleType(steamTank.getFluid(),
					(Fluid.BUCKET_VOLUME * 16) * attachedSteamInputs.size(), "steam");
		}
	}

	@Override
	protected boolean updateServer() {
		boolean flag = false;
		if(temperature < 200) {
			if(steamTank.getFluid() != null && steamTank.drain(fluidUseOnHeat, false).amount == fluidUseOnHeat) {
				steamTank.drain(fluidUseOnHeat, true);
				temperature++;
				return true;
			}
		}

		if(steamTank.getFluid() != null && steamTank.drain(fluidUseOnUpkeep, false).amount == fluidUseOnUpkeep) {
			steamTank.drain(fluidUseOnUpkeep, true);
			flag = true;
		}
		else if(temperature > 0) {
			temperature--;
			flag = true;
		}

		if(temperature >= 70) {
			for(int i = 0; i < inputInventory.getSlots(); i++) {
				if(ItemStackUtils.isItemNonNull(inputInventory.getStackInSlot(i)) && ItemStackUtils.isItemNonNull(
						SteamFurnaceRecipes.instance().getResult(inputInventory.getStackInSlot(i), temperature))) {
					ItemStack r =
							SteamFurnaceRecipes.instance().getResult(inputInventory.getStackInSlot(i), temperature);
					ItemStack resultItem = new ItemStack(r.getItem(), 1, r.getItemDamage());
					if(ItemHandlerHelper.insertItem(outputInventory, resultItem, true) == null) {
						if(inputInventory.extractItem(i, resultItem.stackSize, true) != null) {
							inputInventory.extractItem(i, resultItem.stackSize, false);
							ItemHandlerHelper.insertItem(outputInventory, resultItem, false);
							flag = true;
						}
					}
				}
			}
		}
		return flag;
	}

	@Override
	protected void onMachineDisassembled() {
		temperature = 0;
		pressure = 0;
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
		inputInventory.deserializeNBT(data.getCompoundTag("inputinv"));
		outputInventory.deserializeNBT(data.getCompoundTag("outputinv"));
		steamTank.readFromNBT(data.getCompoundTag("fluidtank"));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setInteger("temperature", temperature);
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
