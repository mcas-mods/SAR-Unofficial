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
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.ItemStackHandlerFiltered.ItemStackHandlerFuel;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.MultiblockCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.multiblocks.boiler.tileentities.*;

public class ControllerBoiler extends MultiblockCraftingMachine<InventoryCraftingMachine> {

	static final String uid = "boiler";
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
		this.setInventory(new InventoryBuilder<>(new InventoryCraftingMachine(this))
				.addPiece("solidFuel",
						new InventoryPieceItemHandler(IOType.POWER, new ItemStackHandlerFuel(1), new int[] { 81 },
								new int[] { 32 }))
				.addPiece("waterTank",
						new InventoryPieceFluidTank(IOType.INPUT,
								new FluidTankSingleSync(Fluid.BUCKET_VOLUME * 16, "water"), 50, 9))
				.addPiece("liquidFuel",
						new InventoryPieceFluidTank(IOType.INPUT, new FluidTankSync(Fluid.BUCKET_VOLUME * 16), 50, 9))
				.addPiece("steamTank",
						new InventoryPieceFluidTank(IOType.INPUT, new FluidTankSync(Fluid.BUCKET_VOLUME * 4), 142, 9))
				.build());
	}

	@Override
	protected boolean updateServer() {

		// Logic must of course run before checking if it should explode...!
		for(BlockPos pos : attachedValves) {
			if(WORLD.isBlockPowered(pos)) {
				this.getInventory().getHandler("waterTank", FluidTankSync.class).drain(Fluid.BUCKET_VOLUME, true);
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
			for(int i = 0; i < getInventory().getItemHandlers().get(0).getSlots(); i++) {
				ItemStack fuel = getInventory().getItemHandlers().get(0).getStackInSlot(i);
				if(!fuel.isEmpty() && TileEntityFurnace.getItemBurnTime(fuel) != 0) {
					currentBurnTime = (TileEntityFurnace.getItemBurnTime(fuel) / fuelDivisor);
					// TODO
					fuel.shrink(1);
					getInventory().getItemHandlers().get(0).setStackInSlot(i, fuel);
					return true;
				}
			}
			if(getInventory().getFluidHandlers().get(1).getFluidAmount() != 0) {
				// TODO
				if(getInventory().getFluidHandlers().get(1).getFluid().getFluid() == FluidRegistry.LAVA) {
					currentBurnTime = 1000;
					return true;
				}
			}
		}
		else {
			if(getInventory().getFluidHandlers().get(0).getFluidAmount() >= fluidConversionPerTick) {
				if(getInventory().getHandler("waterTank", FluidTankSync.class)
						.getFluidAmount() <= (getInventory().getHandler("waterTank", FluidTankSync.class).getCapacity()
								- fluidConversionPerTick)) {
					getInventory().getHandler("waterTank", FluidTankSync.class)
							.fill(new FluidStack(FluidRegistry.getFluid("steam"), fluidConversionPerTick), true);
					getInventory().getFluidHandlers().get(0).drain(fluidConversionPerTick, true);
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
	protected void onMachineAssembled() {
		Pair<BlockPos, BlockPos> interiorPositions = com.teamacronymcoders.base.util.PositionUtils
				.shrinkPositionCubeBy(getMinimumCoord(), getMaximumCoord(), 1);
		minimumInteriorPos = interiorPositions.getLeft();
		maximumInteriorPos = interiorPositions.getRight();
		super.onMachineAssembled();
	}

	@Override
	public String getUID() {
		return uid;
	}

	@Override
	public ItemStack getCatalyst() {
		return new ItemStack(SARObjectHolder.boiler_pressure_monitor);
	}

}
