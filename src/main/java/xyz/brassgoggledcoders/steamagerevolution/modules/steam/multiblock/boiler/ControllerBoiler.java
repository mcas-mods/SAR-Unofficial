package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.FluidTankSingleType;

// TODO NBT
public class ControllerBoiler extends RectangularMultiblockControllerBase {

	public static final int fuelDivisor = 3;
	public static final int fluidConversionPerTick = 5;

	public ItemStackHandler solidFuelInventory = new ItemStackHandler(3);
	public FluidTank liquidFuelInventory = new FluidTank(Fluid.BUCKET_VOLUME * 16);
	public FluidTankSingleType waterTank = new FluidTankSingleType(Fluid.BUCKET_VOLUME * 16, "water");
	public FluidTankSingleType steamTank = new FluidTankSingleType(Fluid.BUCKET_VOLUME * 4, "steam");

	float pressure = 1.0F;
	int currentBurnTime = 0;

	protected ControllerBoiler(World world) {
		super(world);
	}

	@Override
	protected boolean updateServer() {

		if(pressure > 2.0F) {
			// Whoopsyboom
			this.WORLD.createExplosion(null, this.getReferenceCoord().getX(), getReferenceCoord().getY(),
					getReferenceCoord().getZ(), 10, true);
			return true;
		}

		if(currentBurnTime == 0) {
			for(int i = 0; i < solidFuelInventory.getSlots(); i++) {
				ItemStack fuel = solidFuelInventory.getStackInSlot(i);
				if(ItemStackUtils.isItemNonNull(fuel) && TileEntityFurnace.getItemBurnTime(fuel) != 0) {
					currentBurnTime = (TileEntityFurnace.getItemBurnTime(fuel) / fuelDivisor);
					// TODO
					fuel.stackSize--;
					solidFuelInventory.setStackInSlot(i, fuel);
					return true;
				}
			}
			if(liquidFuelInventory.getFluidAmount() != 0) {
				// TODO
				if(liquidFuelInventory.getFluid().getFluid() == FluidRegistry.LAVA) {
					currentBurnTime = 1000;
					return true;
				}
			}
		}
		else {
			if(waterTank.getFluidAmount() >= fluidConversionPerTick) {
				if(steamTank.getFluidAmount() <= (steamTank.getCapacity() - fluidConversionPerTick)) {
					steamTank.fill(new FluidStack(FluidRegistry.getFluid("steam"), fluidConversionPerTick), true);
					waterTank.drain(fluidConversionPerTick, true);
				}
				else {
					pressure++;
				}
				currentBurnTime--;
				return true;
			}
		}

		return false;
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 26;
	}

	@Override
	protected int getMaximumXSize() {
		return 3;
	}

	@Override
	protected int getMaximumZSize() {
		return 3;
	}

	@Override
	protected int getMaximumYSize() {
		return 3;
	}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {

	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		if(world.isAirBlock(new BlockPos(x, y, z)))
			return true;
		else
			return false;
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
	protected void onMachineDisassembled() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onAssimilate(MultiblockControllerBase assimilated) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateClient() {
		// TODO Auto-generated method stub

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
	public void readFromDisk(NBTTagCompound data) {
		pressure = data.getFloat("pressure");
		currentBurnTime = data.getInteger("burntime");
		solidFuelInventory.deserializeNBT(data.getCompoundTag("fuelinv"));
		waterTank.readFromNBT(data.getCompoundTag("wtank"));
		steamTank.readFromNBT(data.getCompoundTag("stank"));
		liquidFuelInventory.readFromNBT(data.getCompoundTag("liquidfuelinv"));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setFloat("pressure", pressure);
		data.setInteger("burntime", currentBurnTime);
		data.setTag("fuelinv", solidFuelInventory.serializeNBT());
		data.setTag("wtank", waterTank.writeToNBT(new NBTTagCompound()));
		data.setTag("stank", steamTank.writeToNBT(new NBTTagCompound()));
		data.setTag("liquidfuelinv", liquidFuelInventory.writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void readFromUpdatePacket(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeToUpdatePacket(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isBlockGoodForFrame(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isBlockGoodForSides(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		// TODO Auto-generated method stub

	}

}
