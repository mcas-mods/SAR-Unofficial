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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.ModuleSteam;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.TileEntityBoilerPressureMonitor;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities.TileEntityBoilerPressureValve;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.ISmartTankCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.SARRectangularMultiblockControllerBase;

public class ControllerBoiler extends SARRectangularMultiblockControllerBase implements ISmartTankCallback {

	public static final int fuelDivisor = 3;
	public static final int fluidConversionPerTick = 5;
	public static final float maxPressure = 3.0F;

	public ItemStackHandler solidFuelInventory = new ItemStackHandler(3);
	public FluidTank liquidFuelTank = new FluidTank(Fluid.BUCKET_VOLUME * 16);
	public FluidTankSingleSmart waterTank = new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "water", this);
	public FluidTankSingleSmart steamTank = new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 4, "steam", this);

	public float pressure = 1.0F;
	public int currentBurnTime = 0;

	Set<BlockPos> attachedMonitors;
	Set<BlockPos> attachedValves;

	public ControllerBoiler(World world) {
		super(world);
		attachedMonitors = new HashSet<BlockPos>();
		attachedValves = new HashSet<BlockPos>();
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
		if(world.isAirBlock(new BlockPos(x, y, z)))
			return true;
		else
			return false;
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
	public void onTankContentsChanged(FluidTank tank) {
		SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
				new PacketFluidUpdate(this.getReferenceCoord(), tank.getFluid()), this.getReferenceCoord(),
				WORLD.provider.getDimension());
	}

	@Override
	public void updateFluid(FluidStack fluid) {
		if(fluid.getFluid().equals(FluidRegistry.WATER))
			waterTank.setFluid(fluid);
		else
			steamTank.setFluid(fluid);
	}

	@Override
	public void readFromDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Boiler";
	}
}
