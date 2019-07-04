package xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryRecipeMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.invpieces.InventoryPieceFluid;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.tileentities.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerFiltered.ItemStackHandlerFuel;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerBoiler extends SARMultiblockInventory<InventoryRecipeMachine> {

	public static final int fuelDivisor = 3;
	public static final int fluidConversionPerTick = 5;
	public static final float maxPressure = 3.0F;

	public float pressure = 1.0F;
	public int currentBurnTime = 0;
	public BlockPos minimumInteriorPos;
	public BlockPos maximumInteriorPos;
	public boolean hasWindow = false;
	Set<BlockPos> attachedMonitors;
	Set<BlockPos> attachedValves;

	public ControllerBoiler(World world) {
		super(world);
		attachedMonitors = new HashSet<BlockPos>();
		attachedValves = new HashSet<BlockPos>();
		/* TODO: having water tank as output is...hacky */ 
		setInventory(new InventoryRecipeMachine().setItemInput(81, 32, new ItemStackHandlerFuel(1, this)).setFluidInput(0,0,new FluidTankSmart(Fluid.BUCKET_VOLUME * 16, this)) new MultiFluidHandler(Fluid.BUCKET_VOLUME * 16, this, 1), 50, 9),
				new InventoryPieceFluid(new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 4, "steam", this), 142, 9)));
	}

	@Override
	protected boolean updateServer() {

		// Logic must of course run before checking if it should explode...!
		for(BlockPos pos : attachedValves) {
			if(WORLD.isBlockPowered(pos)) {
				inventory.getSteamTank().drain(Fluid.BUCKET_VOLUME, true);
				pressure = 1.0F;
				updateRedstoneOutputLevels();
				return true;
			}
		}
		// FIXME
		// if (ModuleSteam.enableDestruction && pressure > maxPressure) {
		// // Whoopsyboom
		// WORLD.createExplosion(null, getReferenceCoord().getX(),
		// getReferenceCoord().getY(),
		// getReferenceCoord().getZ(), 10 * pressure, true);
		// return true;
		// }

		if(currentBurnTime == 0) {
			for(int i = 0; i < inventory.getInputHandler().getSlots(); i++) {
				ItemStack fuel = inventory.getInputHandler().getStackInSlot(i);
				if(!fuel.isEmpty() && TileEntityFurnace.getItemBurnTime(fuel) != 0) {
					currentBurnTime = (TileEntityFurnace.getItemBurnTime(fuel) / fuelDivisor);
					// TODO
					fuel.shrink(1);
					inventory.getInputHandler().setStackInSlot(i, fuel);
					return true;
				}
			}
			if(inventory.getInputTank().getFluidAmount() != 0) {
				// TODO
				if(inventory.getInputTank().getFluid().getFluid() == FluidRegistry.LAVA) {
					currentBurnTime = 1000;
					return true;
				}
			}
		}
		else {
			if(inventory.getOutputTank().getFluidAmount() >= fluidConversionPerTick) {
				if(inventory.getSteamTank()
						.getFluidAmount() <= (inventory.getSteamTank().getCapacity() - fluidConversionPerTick)) {
					inventory.getSteamTank()
							.fill(new FluidStack(FluidRegistry.getFluid("steam"), fluidConversionPerTick), true);
					inventory.getOutputTank().drain(fluidConversionPerTick, true);
				}
				else {
					pressure += 0.01F;
					updateRedstoneOutputLevels();
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
		return 6;
	}

	@Override
	public int getMaximumZSize() {
		return 6;
	}

	@Override
	public int getMaximumYSize() {
		return 6;
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		pressure = data.getFloat("pressure");
		currentBurnTime = data.getInteger("burntime");
		super.onAttachedPartWithMultiblockData(part, data);
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setFloat("pressure", pressure);
		data.setInteger("burntime", currentBurnTime);
		super.writeToDisk(data);
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		if(newPart instanceof TileEntityBoilerPressureMonitor) {
			attachedMonitors.add(newPart.getWorldPosition());
		}
		else if(newPart instanceof TileEntityBoilerPressureValve) {
			attachedValves.add(newPart.getWorldPosition());
		}
		else if(newPart instanceof TileEntityBoilerGauge) {
			hasWindow = true;
		}
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {

		if(oldPart instanceof TileEntityBoilerGauge && hasWindow == true) {
			hasWindow = connectedParts.stream().noneMatch(part -> part instanceof TileEntityBoilerGauge);
		}

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
			WORLD.updateComparatorOutputLevel(pos, SARObjectHolder.boiler_pressure_monitor);
		}
	}

	@Override
	public String getName() {
		return "Boiler";
	}

	@Override
	protected void onMachineAssembled() {
		Pair<BlockPos, BlockPos> interiorPositions = com.teamacronymcoders.base.util.PositionUtils
				.shrinkPositionCubeBy(getMinimumCoord(), getMaximumCoord(), 1);
		minimumInteriorPos = interiorPositions.getLeft();
		maximumInteriorPos = interiorPositions.getRight();
		super.onMachineAssembled();
	}

}
