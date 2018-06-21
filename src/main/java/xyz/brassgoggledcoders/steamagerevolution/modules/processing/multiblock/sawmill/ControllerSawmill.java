package xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.sawmill;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketItemUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.IMultiblockControllerInfo;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockBase;

public class ControllerSawmill extends SARMultiblockBase
		implements ISmartTankCallback, IMultiblockControllerInfo, ISmartStackCallback {

	public ItemStackHandler inputInventory = new ItemStackHandlerSmart(1, this);
	public ItemStackHandler outputInventory = new ItemStackHandler(3);
	public FluidTankSingleSmart steamTank = new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "steam", this);

	int speed = 0;

	private static final int threshholdSpeed = 50;
	private static final int steamUsePerSpeedup = Fluid.BUCKET_VOLUME;
	private static final int steamUsePerTick = Fluid.BUCKET_VOLUME / 10;

	public ControllerSawmill(World world) {
		super(world);
	}

	@Override
	protected FluidTank getTank(String toWrap) {
		return steamTank;
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {

	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {

	}

	@Override
	protected boolean updateServer() {
		for(BlockPos pos : BlockPos.getAllInBox(getMinimumCoord(), getMaximumCoord())) {
			IBlockState current = WORLD.getBlockState(pos);
			Block block = current.getBlock();
			if(!WORLD.isAirBlock(pos) && !(WORLD.getTileEntity(pos) instanceof TileEntitySawmillPart)) {
				ItemStack stackToAdd = new ItemStack(Item.getItemFromBlock(block));
				ItemStack inInventory = inputInventory.getStackInSlot(0);
				if(inInventory.isEmpty() || OreDictionary.itemMatches(stackToAdd, inInventory, false)) {
					WORLD.setBlockToAir(pos);
					inputInventory.insertItem(0, stackToAdd, false);
				}
			}
		}
		if(speed >= threshholdSpeed) {
			// if(steamTank.getFluidAmount() >= steamUsePerTick) {
			// steamTank.drain(steamUsePerTick, true);
			ItemStack resultItem = SawmillRecipe.getResult(inputInventory.getStackInSlot(0));
			if(ItemHandlerHelper.insertItem(outputInventory, resultItem, true).isEmpty()) {
				if(!inputInventory.extractItem(0, resultItem.getCount(), true).isEmpty()) {
					inputInventory.extractItem(0, resultItem.getCount(), false);
					ItemHandlerHelper.insertItem(outputInventory, resultItem, false);
				}
			}
			return true;
			// }
			// else {
			// speed = 0;
			// return true;
			// }
		}
		else {
			if(steamTank.getFluidAmount() >= steamUsePerSpeedup) {
				speed++;
				steamTank.drain(steamUsePerSpeedup, true);
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return world.isAirBlock(new BlockPos(x, y, z));
	}

	@Override
	protected boolean isBlockGoodForSides(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 37;
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
		return 5;
	}

	@Override
	public int getMaximumXSize() {
		return 5;
	}

	@Override
	public int getMaximumZSize() {
		return 5;
	}

	@Override
	public int getMaximumYSize() {
		return 5;
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		speed = data.getInteger("speed");
		steamTank.readFromNBT(data.getCompoundTag("fluidtank"));
		inputInventory.deserializeNBT(data.getCompoundTag("input"));
		outputInventory.deserializeNBT(data.getCompoundTag("output"));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setInteger("speed", speed);
		data.setTag("fluidtank", steamTank.writeToNBT(new NBTTagCompound()));
		data.setTag("input", inputInventory.serializeNBT());
		data.setTag("output", outputInventory.serializeNBT());
	}

	@Override
	protected void onMachineAssembled() {
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
	protected void onMachineDisassembled() {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFromDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFluid(PacketFluidUpdate message) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Sawmill";
	}

	@Override
	public void onContentsChanged(int slot) {
		if(WORLD.isRemote)
			return;
		SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
				new PacketItemUpdate(this.getReferenceCoord(), inputInventory.getStackInSlot(slot), slot),
				this.getReferenceCoord(), WORLD.provider.getDimension());
	}

	@Override
	public void updateStack(PacketItemUpdate message) {
		this.inputInventory.setStackInSlot(message.slot, message.item);
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
