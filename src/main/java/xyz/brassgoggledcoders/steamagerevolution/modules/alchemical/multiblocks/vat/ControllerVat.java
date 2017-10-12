package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketMultiFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.*;

public class ControllerVat extends SARRectangularMultiblockControllerBase implements ISmartTankCallback {

	BlockPos minimumInteriorPos;
	BlockPos maximumInteriorPos;
	AxisAlignedBB bounds;

	public MultiFluidTank fluidInput;
	public ItemStackHandler itemInput;
	public FluidTankSmart output;

	protected ControllerVat(World world) {
		super(world);
		fluidInput = new MultiFluidTank(30000, this);
		itemInput = new ItemStackHandler(3);
		output = new FluidTankSmart(30000, this, 4);
	}

	@Override
	protected boolean updateServer() {

		boolean flag = false;

		for(Entity entity : WORLD.getEntitiesWithinAABB(Entity.class, bounds)) {
			if(entity instanceof EntityItem) {
				EntityItem item = (EntityItem) entity;
				if(ItemHandlerHelper.insertItem(itemInput, item.getItem(), true).isEmpty()) {
					ItemHandlerHelper.insertItem(itemInput, item.getItem(), false);
					item.setDead();
				}
			}
			// Simulate contact with fluid in vat when an entity falls in. TODO change bounds based on fluid fill level
			if(this.output.getFluid() != null) {
				Block fluidBlock = this.output.getFluid().getFluid().getBlock();
				fluidBlock.onEntityCollidedWithBlock(WORLD, getReferenceCoord(), fluidBlock.getDefaultState(), entity);
			}
		}

		Optional<VatRecipe> r = VatRecipe.getRecipeList().parallelStream().filter(this::hasRequiredFluids)
				.filter(this::hasRequiredItems).findFirst();

		if(r.isPresent()) {
			FluidStack result = r.get().output;
			if(this.output.fill(result, false) == result.amount) {
				this.output.fill(result, true);
			}
			flag = true;
		}

		return flag;
	}

	private boolean hasRequiredFluids(VatRecipe recipe) {
		return Arrays.stream(recipe.fluidInputs).map(this::tanksHaveFluid).reduce((a, b) -> a && b).orElse(false);
	}

	private boolean tanksHaveFluid(FluidStack stack) {
		return IntStream.range(0, fluidInput.getFluidTypes()).mapToObj(num -> fluidInput.fluids.get(num))
				.filter(fluid -> fluid.containsFluid(stack)).findAny().isPresent();
	}

	private boolean hasRequiredItems(VatRecipe recipe) {
		return Arrays.stream(recipe.itemInputs).map(this::handlerHasItems).reduce((a, b) -> a && b).orElse(false);
	}

	private boolean handlerHasItems(ItemStack stack) {
		return IntStream.range(0, itemInput.getSlots()).mapToObj(slotNum -> itemInput.getStackInSlot(slotNum))
				.filter(inputStack -> ItemStackUtils.containsItemStack(stack, inputStack)).findAny().isPresent();
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		fluidInput.readFromNBT(data.getCompoundTag("fluids"));
		itemInput.deserializeNBT(data.getCompoundTag("items"));
		output.readFromNBT(data.getCompoundTag("output"));
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
		return 0;
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
	}

	@Override
	public String getName() {
		return "Vat";
	}

	@Override
	public void onTankContentsChanged(FluidTank tank) {
		if(tank instanceof MultiFluidTank) {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketMultiFluidUpdate(this.getReferenceCoord(), ((MultiFluidTank) tank)),
					this.getReferenceCoord(), WORLD.provider.getDimension());
		}
		else {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketFluidUpdate(this.getReferenceCoord(), tank.getFluid()), this.getReferenceCoord(),
					WORLD.provider.getDimension());
		}
	}

	@Override
	public void updateFluid(PacketFluidUpdate message) {
		output.setFluid(message.fluid);
	}

	@Override
	public void updateFluid(PacketMultiFluidUpdate message) {
		fluidInput.fluids.clear();
		fluidInput.fluids.addAll(message.tank.fluids);
	}
}
