package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat;

import org.apache.commons.lang3.tuple.Pair;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.block.Block;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketMultiFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.PositionUtils;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.ISmartTankCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerVat extends SARMultiblockInventory implements ISmartTankCallback, IHasGui {

	public static int outputCapacity = Fluid.BUCKET_VOLUME * 8;
	public static int inputCapacity = outputCapacity * 3;
	BlockPos minimumInteriorPos;
	BlockPos maximumInteriorPos;
	AxisAlignedBB bounds;

	public MultiFluidTank fluidInput;
	public ItemStackHandlerExtractSpecific itemInput;
	public MultiFluidTank output;

	protected ControllerVat(World world) {
		super(world);
		fluidInput = new MultiFluidTank(inputCapacity, this, 0);
		itemInput = new ItemStackHandlerExtractSpecific(3);
		output = new MultiFluidTank(outputCapacity, this, 1);
	}

	@Override
	protected void onTick() {
		for(Entity entity : WORLD.getEntitiesWithinAABB(Entity.class, bounds)) {
			if(entity instanceof EntityItem) {
				EntityItem item = (EntityItem) entity;
				if(ItemHandlerHelper.insertItem(itemInput, item.getItem(), true).isEmpty()) {
					ItemHandlerHelper.insertItem(itemInput, item.getItem(), false);
					item.setDead();
				}
			}
			// Simulate contact with fluid in vat when an entity falls in. TODO change bounds based on fluid fill level
			FluidStack fluid = null;
			if(this.output.getFluid() != null) {
				fluid = this.output.getFluid();
			}
			else if(!this.fluidInput.fluids.isEmpty() && this.fluidInput.fluids.get(0) != null) {
				fluid = this.fluidInput.fluids.get(0);
			}
			if(fluid != null && fluid.getFluid() != null && fluid.getFluid().getBlock() != null) {
				if(fluid.getFluid().getTemperature() >= FluidRegistry.LAVA.getTemperature()) {
					entity.setFire(5);
				}
				Block fluidBlock = fluid.getFluid().getBlock();
				fluidBlock.onEntityCollidedWithBlock(WORLD, getReferenceCoord(), fluidBlock.getDefaultState(), entity);

			}
		}
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		fluidInput.readFromNBT(data.getCompoundTag("fluids"));
		itemInput.deserializeNBT(data.getCompoundTag("items"));
		output.readFromNBT(data.getCompoundTag("output"));
		super.onAttachedPartWithMultiblockData(part, data);
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
	protected void onMachineAssembled() {
		Pair<BlockPos, BlockPos> interiorPositions =
				PositionUtils.shrinkPositionCubeBy(this.getMinimumCoord(), this.getMaximumCoord(), 1);
		this.minimumInteriorPos = interiorPositions.getLeft();
		this.maximumInteriorPos = interiorPositions.getRight();
		this.bounds = new AxisAlignedBB(this.getMinimumCoord(), this.getMaximumCoord());
		super.onMachineAssembled();
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
		return 40;
	}

	@Override
	public int getMinimumXSize() {
		return 4;
	}

	@Override
	public int getMinimumYSize() {
		return 3;
	}

	@Override
	public int getMinimumZSize() {
		return 4;
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
		return 10;
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
		// FMLLog.warning(this.fluidInput.fluids.toString());
	}

	@Override
	protected boolean isBlockGoodForFrame(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isBlockGoodForTop(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return WORLD.isAirBlock(new BlockPos(x, y, z));
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
	public void readFromDisk(NBTTagCompound data) {

	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setTag("fluids", fluidInput.writeToNBT(new NBTTagCompound()));
		data.setTag("items", itemInput.serializeNBT());
		data.setTag("output", output.writeToNBT(new NBTTagCompound()));
		super.writeToDisk(data);
	}

	@Override
	public String getName() {
		return "Vat";
	}

	@Override
	public void updateFluid(PacketMultiFluidUpdate message) {
		if(message.id == fluidInput.getId()) {
			fluidInput.fluids.clear();
			fluidInput.fluids.addAll(message.tank.fluids);
		}
		else if(message.id == output.getId()) {
			output.fluids.clear();
			output.fluids.addAll(message.tank.fluids);
		}
		super.updateFluid(message);
	}

	@Override
	protected FluidTank getTank(String toWrap) {
		if(toWrap.equals("input")) {
			return fluidInput;
		}
		else {
			return output;
		}
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		// TODO Auto-generated method stub
		return new GuiVat(entityPlayer, this);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		// TODO Auto-generated method stub
		return new ContainerVat(entityPlayer, this);
	}

	@Override
	public ItemStackHandlerExtractSpecific getItemInput() {
		return this.itemInput;
	}

	@Override
	public MultiFluidTank getFluidInputs() {
		return this.fluidInput;
	}

	@Override
	public ItemStackHandlerExtractSpecific getItemOutput() {
		return null;
	}

	@Override
	public MultiFluidTank getFluidOutputs() {
		return output;
	}
}
