package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.ModuleMetalworking;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerFiltered.ItemStackHandlerFuel;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerSteelworks extends SARMultiblockInventory implements ISmartTankCallback {

	public FluidTank ironTank = new FluidTankSmart(Fluid.BUCKET_VOLUME * 16, this);
	public ItemStackHandler inputSolid = new ItemStackHandlerFuel(1);
	public FluidTank outputTank = new FluidTankSmart(Fluid.BUCKET_VOLUME * 16, this);

	// public static final int workingPoolLevel = ModuleMetalworking.VALUE_BLOCK *
	// 9;
	public static final int conversionPerOperation = ModuleMetalworking.VALUE_NUGGET;
	public static final int steamUsePerOperation = Fluid.BUCKET_VOLUME / 10;
	public static final int carbonPerOperation = TileEntityFurnace.getItemBurnTime(new ItemStack(Items.COAL)) / 2;

	public ControllerSteelworks(World world) {
		super(world);
	}

	@Override
	protected FluidTank getTank(String toWrap) {
		if(toWrap.equals("iron")) {
			return ironTank;
		}
		else if(toWrap.equals("steel")) {
			return outputTank;
		}
		return steamTank;
	}

	@Override
	public ItemStackHandler getInventory(String toWrap) {
		return inputSolid;
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		// TODO
		return 1;
	}

	@Override
	public int getMinimumXSize() {
		return 5;
	}

	@Override
	public int getMinimumZSize() {
		return 5;
	}

	@Override
	public int getMinimumYSize() {
		return 9;
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		inputSolid.deserializeNBT(data.getCompoundTag("solidInput"));
		ironTank.readFromNBT(data.getCompoundTag("fluidInput"));
		outputTank.readFromNBT(data.getCompoundTag("output"));
		super.onAttachedPartWithMultiblockData(part, data);
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setTag("solidInput", inputSolid.serializeNBT());
		data.setTag("fluidInput", ironTank.writeToNBT(new NBTTagCompound()));
		data.setTag("output", outputTank.writeToNBT(new NBTTagCompound()));
		super.writeToDisk(data);
	}

	@Override
	public String getName() {
		return "Steelworks";
	}

	@Override
	public int getMaximumXSize() {
		return getMinimumXSize();
	}

	@Override
	public int getMaximumZSize() {
		return getMinimumZSize();
	}

	@Override
	public int getMaximumYSize() {
		return getMinimumYSize();
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onTankContentsChanged(FluidTankSmart tank) {
		// TODO Auto-generated method stub

	}

	@Override
	public ItemStackHandlerExtractSpecific getItemInput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiFluidTank getFluidInputs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStackHandler getItemOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiFluidTank getFluidOutputs() {
		// TODO Auto-generated method stub
		return null;
	}
}
