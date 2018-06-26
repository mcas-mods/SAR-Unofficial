package xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.ISmartTankCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerFiltered.ItemStackHandlerFuel;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerSteamFurnace extends SARMultiblockInventory implements ISmartTankCallback {

	public ItemStackHandler inputInventory = new ItemStackHandlerFuel(1);
	public ItemStackHandler outputInventory = new ItemStackHandler(3);

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
	public ItemStackHandler getInventory(String toWrap) {
		if(toWrap.equals("input")) {
			return inputInventory;
		}
		return outputInventory;
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return world.isAirBlock(new BlockPos(x, y, z));
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
		super.onAttachedPartWithMultiblockData(part, data);
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setBoolean("mode", isHeating);
		data.setInteger("temperature", temperature);
		data.setInteger("cookTime", currentCookTime);
		data.setTag("inputinv", inputInventory.serializeNBT());
		data.setTag("outputinv", outputInventory.serializeNBT());
		super.writeToDisk(data);
	}

	public static void toggleMode(ControllerSteamFurnace controller) {
		SteamAgeRevolution.instance.getLogger().devInfo("Furnace mode toggled");
		controller.isHeating = !controller.isHeating;
	}

	public boolean isHeating() {
		return isHeating;
	}

	@Override
	public String getName() {
		return "Steam Furnace";
	}
}
