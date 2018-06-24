package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.multiblock.validation.ValidationError;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.ModuleAlchemical;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.ISmartTankCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerDistiller extends SARMultiblockInventory implements ISmartTankCallback, IHasGui {

	public static int tankCapacity = Fluid.BUCKET_VOLUME * 8;

	public MultiFluidTank fluidInput;
	public MultiFluidTank fluidOutput;
	public ItemStackHandlerExtractSpecific itemOutput;

	public ControllerDistiller(World world) {
		super(world);
		fluidInput = new MultiFluidTank(tankCapacity, this, 0);
		fluidOutput = new MultiFluidTank(tankCapacity, this, 1);
		itemOutput = new ItemStackHandlerExtractSpecific(1);
	}

	@Override
	public String getName() {
		return "Distiller";
	}

	@Override
	protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
		BlockPos first = getMinimumCoord();
		BlockPos second = new BlockPos(getMaximumCoord().getX(), getMinimumCoord().getY(), getMaximumCoord().getZ());

		// Get all blocks in bottom layer of machine & check they're radiators
		for(BlockPos pos : BlockPos.getAllInBox(first, second)) {
			if(WORLD.getBlockState(pos).getBlock() != ModuleAlchemical.distiller_radiator) {
				validatorCallback.setLastError(
						new ValidationError("steamagerevolution.multiblock.validation.distiller_radiator"));
				return false;
			}
		}
		// Same for second layer, check they're hotplates
		for(BlockPos pos : BlockPos.getAllInBox(first.up(), second.up())) {
			if(WORLD.getBlockState(pos).getBlock() != ModuleAlchemical.distiller_hotplate) {
				validatorCallback.setLastError(
						new ValidationError("steamagerevolution.multiblock.validation.distiller_hotplate"));
				return false;
			}
		}

		return super.isMachineWhole(validatorCallback);
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		fluidInput.readFromNBT(data.getCompoundTag("input"));
		fluidOutput.readFromNBT(data.getCompoundTag("output"));
		itemOutput.deserializeNBT(data.getCompoundTag("itemOutput"));
		super.onAttachedPartWithMultiblockData(part, data);
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 34;
	}

	@Override
	public int getMinimumXSize() {
		return 3;
	}

	@Override
	public int getMinimumYSize() {
		return 5;
	}

	@Override
	public int getMinimumZSize() {
		return 3;
	}

	@Override
	public int getMaximumXSize() {
		return 10;
	}

	@Override
	public int getMaximumZSize() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getMaximumYSize() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setTag("input", fluidInput.writeToNBT(new NBTTagCompound()));
		data.setTag("output", fluidOutput.writeToNBT(new NBTTagCompound()));
		data.setTag("itemOutput", itemOutput.serializeNBT());
		super.writeToDisk(data);
	}

	@Override
	public void updateFluid(PacketFluidUpdate message) {
		SteamAgeRevolution.instance.getLogger().devInfo("Packet ID: " + message.id);
		if(message.id == fluidInput.getId()) {
			fluidInput.setFluid(message.fluid);
		}
		else if(message.id == fluidOutput.getId()) {
			fluidOutput.setFluid(message.fluid);
		}
		else {
			super.updateFluid(message);
		}
	}

	@Override
	public FluidTank getTank(String toWrap) {
		if(toWrap.equals("input")) {
			return fluidInput;
		}
		else if(toWrap.equals("output")) {
			return fluidOutput;
		}
		return super.getTank(toWrap);
	}

	@Override
	public ItemStackHandler getInventory(String toWrap) {
		return itemOutput;
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		// TODO Auto-generated method stub
		return new GuiDistiller(entityPlayer, this);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		// TODO Auto-generated method stub
		return new ContainerDistiller(entityPlayer, this);
	}

	@Override
	public ItemStackHandlerExtractSpecific getItemInput() {
		return null;
	}

	@Override
	public MultiFluidTank getFluidInputs() {
		return fluidInput;
	}

	@Override
	public ItemStackHandlerExtractSpecific getItemOutput() {
		return itemOutput;
	}

	@Override
	public MultiFluidTank getFluidOutputs() {
		return fluidOutput;
	}

}
