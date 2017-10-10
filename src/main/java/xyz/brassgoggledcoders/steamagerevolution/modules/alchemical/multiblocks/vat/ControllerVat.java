package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

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
import xyz.brassgoggledcoders.steamagerevolution.utils.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.ISmartTankCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.SARRectangularMultiblockControllerBase;

public class ControllerVat extends SARRectangularMultiblockControllerBase implements ISmartTankCallback {

	BlockPos minimumInteriorPos;
	BlockPos maximumInteriorPos;
	AxisAlignedBB bounds;

	public FluidTankSmart inputBuffer;
	public FluidTankSmart[] inputs;
	public ItemStackHandler itemInput;
	public FluidTankSmart output;

	protected ControllerVat(World world) {
		super(world);
		inputBuffer = new FluidTankSmart(1000, this, 0);
		inputs = new FluidTankSmart[] {new FluidTankSmart(10000, this, 1), new FluidTankSmart(10000, this, 2),
				new FluidTankSmart(10000, this, 3)};
		itemInput = new ItemStackHandler(3);
		output = new FluidTankSmart(30000, this, 4);
	}

	@Override
	protected boolean updateServer() {

		boolean flag = false;

		if(inputBuffer.getFluidAmount() > 0) {
			FluidStack input = inputBuffer.getFluid();
			int amount = inputBuffer.getFluidAmount();
			for(FluidTank tank : inputs) {
				if(tank.fill(input, false) == amount) {
					tank.fill(input, true);
					inputBuffer.drain(input, true);
					flag = true;
					break;
				}
			}
			// TODO else freeze machine/clear buffer
		}

		// TODO change to a layer of dummy blocks in the top of the machine
		for(EntityItem item : WORLD.getEntitiesWithinAABB(EntityItem.class, bounds)) {
			if(ItemHandlerHelper.insertItem(itemInput, item.getItem(), true).isEmpty()) {
				ItemHandlerHelper.insertItem(itemInput, item.getItem(), false);
				item.setDead();
			}
		}

		for(VatRecipe r : VatRecipe.getRecipeList()) {
			int successfulMatches = 0;
			for(FluidStack recipe_fluid : r.fluidInputs) {
				for(int i = 0; i < inputs.length; i++)
					if(recipe_fluid.isFluidEqual(this.inputs[0].getFluid())
							&& this.inputs[0].getFluidAmount() >= recipe_fluid.amount) {
						successfulMatches++;
					}
			}
			if(successfulMatches == r.fluidInputs.length) {
				for(ItemStack recipe_stack : r.itemInputs) {
					for(int i = 0; i < itemInput.getSlots(); i++)
						if(recipe_stack.isItemEqual(itemInput.getStackInSlot(i))
								&& this.itemInput.getStackInSlot(i).getCount() >= recipe_stack.getCount()) {
							successfulMatches++;
						}
				}

			}
			if(successfulMatches == (r.fluidInputs.length + r.itemInputs.length)) {
				if(this.output.fill(r.output, false) == r.output.amount) {
					this.output.fill(r.output, true);
				}
			}
		}

		return flag;
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		for(int i = 0; i < inputs.length; i++) {
			inputs[i].readFromNBT(data.getCompoundTag("input" + i));
		}
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
		for(int i = 0; i < inputs.length; i++) {
			data.setTag("input" + i, inputs[i].writeToNBT(new NBTTagCompound()));
		}
		data.setTag("items", itemInput.serializeNBT());
		data.setTag("output", output.writeToNBT(new NBTTagCompound()));
	}

	@Override
	public String getName() {
		return "Vat";
	}

	@Override
	public void onTankContentsChanged(FluidTank tank) {
		SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
				new PacketFluidUpdate(this.getReferenceCoord(), tank.getFluid(), ((FluidTankSmart) tank).getId()),
				this.getReferenceCoord(), WORLD.provider.getDimension());
	}

	@Override
	public void updateFluid(PacketFluidUpdate message) {
		if(message.id == output.getId()) {
			output.setFluid(message.fluid);
		}
		else if(message.id == inputBuffer.getId()) {
			inputBuffer.setFluid(message.fluid);
		}
		else {
			for(int i = 0; i < inputs.length; i++) {
				if(message.id == inputs[i].getId()) {
					inputs[i].setFluid(message.fluid);
				}
			}
		}
	}

}
