package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler;

import java.util.HashSet;
import java.util.Set;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.ModuleSteam;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.TileEntityBoilerPressureMonitor;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.TileEntityBoilerPressureValve;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerFiltered.ItemStackHandlerFuel;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockBase;

public class ControllerBoiler extends SARMultiblockBase implements ISmartTankCallback {

	public static final int fuelDivisor = 3;
	public static final int fluidConversionPerTick = 5;
	public static final float maxPressure = 3.0F;

	public ItemStackHandler solidFuelInventory = new ItemStackHandlerFuel(3);
	public FluidTank liquidFuelTank = new FluidTank(Fluid.BUCKET_VOLUME * 16);
	public FluidTankSingleSmart waterTank = new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "water", this);
	public FluidTankSingleSmart steamTank = new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 4, "steam", this);

	public float pressure = 1.0F;
	public int currentBurnTime = 0;

	Set<BlockPos> attachedMonitors;
	Set<BlockPos> attachedValves;

	public ControllerBoiler(World world) {
		super(world, ModuleSteam.boilerWaterInput, ModuleSteam.boilerSteamOutput);
		attachedMonitors = new HashSet<BlockPos>();
		attachedValves = new HashSet<BlockPos>();
	}

	@Override
	protected FluidTank getTank(String toWrap) {
		if(toWrap.equals("water")) {
			return waterTank;
		}
		else if(toWrap.equals("liquid")) {
			return liquidFuelTank;
		}
		return steamTank;
	}

	@Override
	public ItemStackHandler getInventory(String toWrap) {
		return solidFuelInventory;
	}

	@Override
	protected boolean updateServer() {

		// Logic must of course run before checking if it should explode...!
		for(BlockPos pos : attachedValves) {
			if(WORLD.isBlockPowered(pos)) {
				this.steamTank.drain(Fluid.BUCKET_VOLUME, true);
				pressure = 1.0F;
				this.updateRedstoneOutputLevels();
				return true;
			}
		}

		if(ModuleSteam.enableDestruction && pressure > maxPressure) {
			// Whoopsyboom
			this.WORLD.createExplosion(null, this.getReferenceCoord().getX(), getReferenceCoord().getY(),
					getReferenceCoord().getZ(), 10 * pressure, true);
			return true;
		}

		if(currentBurnTime == 0) {
			for(int i = 0; i < solidFuelInventory.getSlots(); i++) {
				ItemStack fuel = solidFuelInventory.getStackInSlot(i);
				if(!fuel.isEmpty() && TileEntityFurnace.getItemBurnTime(fuel) != 0) {
					currentBurnTime = (TileEntityFurnace.getItemBurnTime(fuel) / fuelDivisor);
					// TODO
					fuel.shrink(1);
					solidFuelInventory.setStackInSlot(i, fuel);
					return true;
				}
			}
			if(liquidFuelTank.getFluidAmount() != 0) {
				// TODO
				if(liquidFuelTank.getFluid().getFluid() == FluidRegistry.LAVA) {
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
					pressure += 0.01F;
					this.updateRedstoneOutputLevels();
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
	public int getMaximumXSize() {
		return 3;
	}

	@Override
	public int getMaximumZSize() {
		return 3;
	}

	@Override
	public int getMaximumYSize() {
		return 3;
	}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {

	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return world.isAirBlock(new BlockPos(x, y, z));
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
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		pressure = data.getFloat("pressure");
		currentBurnTime = data.getInteger("burntime");
		solidFuelInventory.deserializeNBT(data.getCompoundTag("fuelinv"));
		waterTank.readFromNBT(data.getCompoundTag("wtank"));
		steamTank.readFromNBT(data.getCompoundTag("stank"));
		liquidFuelTank.readFromNBT(data.getCompoundTag("liquidfuelinv"));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setFloat("pressure", pressure);
		data.setInteger("burntime", currentBurnTime);
		data.setTag("fuelinv", solidFuelInventory.serializeNBT());
		data.setTag("wtank", waterTank.writeToNBT(new NBTTagCompound()));
		data.setTag("stank", steamTank.writeToNBT(new NBTTagCompound()));
		data.setTag("liquidfuelinv", liquidFuelTank.writeToNBT(new NBTTagCompound()));
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
		if(newPart instanceof TileEntityBoilerPressureMonitor) {
			attachedMonitors.add(newPart.getWorldPosition());
		}
		else if(newPart instanceof TileEntityBoilerPressureValve) {
			attachedValves.add(newPart.getWorldPosition());
		}
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		if(oldPart instanceof TileEntityBoilerPressureMonitor) {
			attachedMonitors.remove(oldPart.getWorldPosition());
		}
		else if(oldPart instanceof TileEntityBoilerPressureValve) {
			attachedValves.remove(oldPart.getWorldPosition());
		}
	}

	private void updateRedstoneOutputLevels() {
		for(BlockPos pos : attachedMonitors) {
			// FMLLog.warning(pos.toString());
			WORLD.updateComparatorOutputLevel(pos, ModuleSteam.boilerPressureMonitor);
		}
	}

	@Override
	public void updateFluid(PacketFluidUpdate message) {
		if(message.fluid.getFluid().equals(FluidRegistry.WATER))
			waterTank.setFluid(message.fluid);
		else
			steamTank.setFluid(message.fluid);
	}

	@Override
	public void readFromDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Boiler";
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
	public ItemStackHandler getItemInput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiFluidTank getFluidInputs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStackHandlerExtractSpecific getItemOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiFluidTank getFluidOutputs() {
		// TODO Auto-generated method stub
		return null;
	}
}
