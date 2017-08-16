package xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.ModuleSteam;
import xyz.brassgoggledcoders.steamagerevolution.utils.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.ISmartTankCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.ItemStackHandlerFiltered.ItemStackHandlerFuel;
import xyz.brassgoggledcoders.steamagerevolution.utils.SARRectangularMultiblockControllerBase;

public class ControllerSteamFurnace extends SARRectangularMultiblockControllerBase implements ISmartTankCallback {

	public ItemStackHandler inputInventory = new ItemStackHandlerFuel(1);
	public ItemStackHandler outputInventory = new ItemStackHandler(3);
	public FluidTankSingleSmart steamTank = new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "steam", this);

	int temperature = 0;
	int currentCookTime = 0;
	private boolean isHeating = true;

	private static final int thresholdTemperature = 200;
	// private static final int unstableTemperature = 500; // TODO
	private static final int maxTemperature = 1000;
	private static final int cookTime = 2400;
	private static final int steamUsePerHeat = Fluid.BUCKET_VOLUME;
	private static final int steamUsePerMaintain = steamUsePerHeat / 10;

	public ControllerSteamFurnace(World world) {
		super(world);
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {

	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {

	}

	@Override
	protected boolean updateServer() {
		boolean flag = false;

		// Heating logic
		if(isHeating) {
			if(steamTank.getFluidAmount() >= steamUsePerHeat) {
				temperature++;
				steamTank.drain(steamUsePerHeat, true);
				flag = true;
			}
			else if(temperature > 0) {
				temperature--;
				flag = true;
			}
		}
		else {
			if(temperature > 0) {
				if(steamTank.getFluidAmount() >= steamUsePerMaintain) {
					steamTank.drain(steamUsePerMaintain, true);
					flag = true;
				}
				else {
					temperature--;
					flag = true;
				}
			}
		}
		// If temp exceeds max, start risking melting down
		if(ModuleSteam.enableDestruction && temperature > maxTemperature) {
			if(WORLD.rand.nextInt(10) == 0) {
				for(BlockPos machineBlock : BlockPos.getAllInBox(getMinimumCoord(), getMaximumCoord())) {
					if(WORLD.rand.nextBoolean()) {
						this.WORLD.setBlockState(machineBlock, Blocks.FLOWING_LAVA.getDefaultState());
					}
					else {
						this.WORLD.setBlockToAir(machineBlock);
					}
				}
			}
		}

		// Smelting logic TODO Liquid metal output
		if(temperature >= thresholdTemperature && !inputInventory.getStackInSlot(0).isEmpty()
				&& !SteamFurnaceRecipe.getResult(inputInventory.getStackInSlot(0)).isEmpty()) {

			if(currentCookTime < cookTime) {
				currentCookTime += Math.floor(temperature / 10);
				flag = true;
			}
			else {
				ItemStack resultItem = SteamFurnaceRecipe.getResult(inputInventory.getStackInSlot(0));
				if(ItemHandlerHelper.insertItem(outputInventory, resultItem, true) == null) {
					if(inputInventory.extractItem(0, resultItem.getCount(), true) != null) {
						inputInventory.extractItem(0, resultItem.getCount(), false);
						ItemHandlerHelper.insertItem(outputInventory, resultItem, false);
						currentCookTime = 0;
						flag = true;
					}
				}
			}
		}
		return flag;
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
	public int getMinimumXSize() {
		return 3;
	}

	@Override
	public int getMinimumZSize() {
		return 3;
	}

	@Override
	public int getMinimumYSize() {
		return 3;
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
		isHeating = data.getBoolean("mode");
		temperature = data.getInteger("temperature");
		currentCookTime = data.getInteger("cookTime");
		inputInventory.deserializeNBT(data.getCompoundTag("inputinv"));
		outputInventory.deserializeNBT(data.getCompoundTag("outputinv"));
		steamTank.readFromNBT(data.getCompoundTag("fluidtank"));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setBoolean("mode", isHeating);
		data.setInteger("temperature", temperature);
		data.setInteger("cookTime", currentCookTime);
		data.setTag("inputinv", inputInventory.serializeNBT());
		data.setTag("outputinv", outputInventory.serializeNBT());
		data.setTag("fluidtank", steamTank.writeToNBT(new NBTTagCompound()));
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
	public void onTankContentsChanged(FluidTank tank) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMachineDisassembled() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFromDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	public static void toggleMode(ControllerSteamFurnace controller) {
		SteamAgeRevolution.instance.getLogger().devInfo("Furnace mode toggled");
		controller.isHeating = !controller.isHeating;
	}

	public boolean isHeating() {
		return isHeating;
	}

	@Override
	public void updateFluid(FluidStack fluid) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Steam Furnace";
	}

}
