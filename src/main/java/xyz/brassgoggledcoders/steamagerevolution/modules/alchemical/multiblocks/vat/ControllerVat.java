package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat;

import java.util.*;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketMultiFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.*;

public class ControllerVat extends SARRectangularMultiblockControllerBase implements ISmartTankCallback {

	public static int outputCapacity = Fluid.BUCKET_VOLUME * 8;
	public static int inputCapacity = outputCapacity * 3;
	BlockPos minimumInteriorPos;
	BlockPos maximumInteriorPos;
	AxisAlignedBB bounds;

	public MultiFluidTank fluidInput;
	public ItemStackHandlerExtractSpecific itemInput;
	public FluidTankSmart output;

	protected ControllerVat(World world) {
		super(world);
		fluidInput = new MultiFluidTank(inputCapacity, this);
		itemInput = new ItemStackHandlerExtractSpecific(3);
		output = new FluidTankSmart(outputCapacity, this);
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
			FluidStack fluid = null;
			if(this.output.getFluid() != null) {
				fluid = this.output.getFluid();
			}
			else if(this.fluidInput.fluids.get(0) != null) {
				fluid = this.fluidInput.fluids.get(0);
			}
			if(fluid != null && fluid.getFluid() != null && fluid.getFluid().getBlock() != null) {
				if(fluid.getFluid() == FluidRegistry.getFluid("potion") && entity instanceof EntityLiving) {
					EntityLiving living = (EntityLiving) entity;
					PotionType.getPotionTypeForName(fluid.tag.getString("Potion")).getEffects()
							.forEach(effect -> living.addPotionEffect(effect));
				}
				if(fluid.getFluid().getTemperature() >= FluidRegistry.LAVA.getTemperature()) {
					entity.setFire(5);
				}
				Block fluidBlock = fluid.getFluid().getBlock();
				fluidBlock.onEntityCollidedWithBlock(WORLD, getReferenceCoord(), fluidBlock.getDefaultState(), entity);

			}
		}

		Optional<VatRecipe> r = VatRecipe.getRecipeList().parallelStream().filter(this::hasRequiredFluids)
				.filter(this::hasRequiredItems).findFirst();

		if(r.isPresent()) {
			VatRecipe recipe = r.get();
			FluidStack result = recipe.output;
			if(this.output.fill(result, false) == result.amount) {
				this.output.fill(result, true);
				for(FluidStack stack : recipe.fluidInputs) {
					this.fluidInput.drain(stack, true);
				}
				if(recipe.itemInputs != null) {
					for(ItemStack stack : recipe.itemInputs) {
						this.itemInput.extractStack(stack);
					}
				}
			}
			flag = true;
		}

		return flag;
	}

	private boolean hasRequiredFluids(VatRecipe recipe) {
		// Stream the fluid stacks
		return Arrays.stream(recipe.fluidInputs)
				// Apply tanksHaveFluid to each element and output result to stream
				.map(this::tanksHaveFluid)
				// Reduce list of booleans into one - so will only evaluate true if every boolean is true
				.reduce((a, b) -> a && b).orElse(false);
	}

	private boolean tanksHaveFluid(FluidStack stack) {
		return fluidInput.fluids.stream().filter(Objects::nonNull).filter(fluid -> fluid.containsFluid(stack)).findAny()
				.isPresent();
	}

	private boolean hasRequiredItems(VatRecipe recipe) {
		// No doubt Sky will slap me with a way to integrate this into the stream shortly
		if(recipe.itemInputs != null) {
			return Arrays.stream(recipe.itemInputs).map(this::handlerHasItems).reduce((a, b) -> a && b).orElse(false);
		}
		else {
			return true;
		}
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
