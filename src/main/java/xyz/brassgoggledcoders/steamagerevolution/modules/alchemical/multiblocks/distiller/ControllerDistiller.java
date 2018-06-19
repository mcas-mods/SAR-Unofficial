package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
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
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARRectangularMultiblockControllerBase;

public class ControllerDistiller extends SARRectangularMultiblockControllerBase implements ISmartTankCallback, IHasGui {

	public static int tankCapacity = Fluid.BUCKET_VOLUME * 8;

	public FluidTankSmart fluidInput;
	public FluidTankSmart fluidOutput;
	public ItemStackHandlerExtractSpecific itemOutput;
	public FluidTankSingleSmart steamTank;

	protected ControllerDistiller(World world) {
		super(world);
		fluidInput = new FluidTankSmart(tankCapacity, this, 0);
		fluidOutput = new FluidTankSmart(tankCapacity, this, 1);
		itemOutput = new ItemStackHandlerExtractSpecific(1);
		steamTank = new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "steam", this);
	}

	@Override
	public String getName() {
		return "Distiller";
	}

	@Override
	protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
		BlockPos first = this.getMinimumCoord();
		BlockPos second = new BlockPos(this.getMaximumCoord().getX(), this.getMinimumCoord().getY(),
				this.getMaximumCoord().getZ());

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
		steamTank.readFromNBT(data.getCompoundTag("steam"));
		fluidInput.readFromNBT(data.getCompoundTag("input"));
		fluidOutput.readFromNBT(data.getCompoundTag("output"));
		itemOutput.deserializeNBT(data.getCompoundTag("itemOutput"));
		// temperature = data.getInteger("temperature");
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
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
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return WORLD.isAirBlock(new BlockPos(x, y, z));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setTag("steam", steamTank.writeToNBT(new NBTTagCompound()));
		data.setTag("input", fluidInput.writeToNBT(new NBTTagCompound()));
		data.setTag("output", fluidOutput.writeToNBT(new NBTTagCompound()));
		data.setTag("itemOutput", itemOutput.serializeNBT());
	}

	@Override
	public void readFromDisk(NBTTagCompound data) {}

	@Override
	public void onTankContentsChanged(FluidTank tank) {
		SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
				new PacketFluidUpdate(this.getReferenceCoord(), tank.getFluid(), ((FluidTankSmart) tank).getId()),
				this.getReferenceCoord(), WORLD.provider.getDimension());
	}

	@Override
	public void updateFluid(PacketFluidUpdate message) {
		if(message.id == fluidInput.getId()) {
			fluidInput.setFluid(message.fluid);
		}
		else if(message.id == fluidOutput.getId()) {
			fluidOutput.setFluid(message.fluid);
		}
		else {
			steamTank.setFluid(message.fluid);
		}
	}

	@Override
	protected FluidTank getTank(String toWrap) {
		if(toWrap.equals("input")) {
			return fluidInput;
		}
		else if(toWrap.equals("output")) {
			return fluidOutput;
		}
		return steamTank;
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
	public ItemStackHandler getItemInput() {
		return null;
	}

	@Override
	public MultiFluidTank getFluidInputs() {
		MultiFluidTank dummy = new MultiFluidTank(Integer.MAX_VALUE, this);
		dummy.fluids.add(this.fluidInput.getFluid());
		return dummy;
	}

	@Override
	public ItemStackHandlerExtractSpecific getItemOutput() {
		return this.itemOutput;
	}

	@Override
	public MultiFluidTank getFluidOutputs() {
		MultiFluidTank dummy = new MultiFluidTank(Integer.MAX_VALUE, this);
		dummy.fluids.add(this.fluidOutput.getFluid());
		return dummy;
	}

}
