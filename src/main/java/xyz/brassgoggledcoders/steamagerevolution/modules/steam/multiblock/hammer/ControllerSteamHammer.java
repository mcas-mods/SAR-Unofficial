package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.FluidTankSingleType;

public class ControllerSteamHammer extends RectangularMultiblockControllerBase {

	public ItemStackHandler inventory = new ItemStackHandler(2);
	public FluidTank tank = new FluidTankSingleType(Fluid.BUCKET_VOLUME * 4, "steam");
	protected String dieType = "test";
	private int progress = 0;

	protected ControllerSteamHammer(World world) {
		super(world);
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		// TODO Auto-generated method stub

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
	protected int getMaximumXSize() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	protected int getMaximumZSize() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	protected int getMaximumYSize() {
		// TODO Auto-generated method stub
		return 4;
	}

	// @Override
	// protected int getMinimumXSize() {
	// return 3;
	// }
	//
	// @Override
	// protected int getMinimumZSize() {
	// return 3;
	// }
	//
	// @Override
	// protected int getMinimumYSize() {
	// return 4;
	// }

	@Override
	protected void onAssimilate(MultiblockControllerBase assimilated) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean updateServer() {
		if(tank.getFluidAmount() >= Fluid.BUCKET_VOLUME) {
			FMLLog.warning("X");
			if(progress < 10) {
				this.progress++;
				tank.drain(Fluid.BUCKET_VOLUME, true);
				return true;
			}
			else {
				if(ItemStackUtils.isItemNonNull(inventory.getStackInSlot(0))) {
					ItemStack result = SteamHammerRecipe.getResult(inventory.getStackInSlot(0), dieType);
					FMLLog.warning(result.getUnlocalizedName());
					if(ItemStackUtils.isItemNonNull(result) && inventory.insertItem(1, result, true) == null) {
						inventory.extractItem(0, 1, false);
						inventory.insertItem(1, result, false);
						progress = 0;
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	protected void updateClient() {}

	@Override
	protected boolean isBlockGoodForFrame(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	protected boolean isBlockGoodForTop(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	protected boolean isBlockGoodForBottom(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	protected boolean isBlockGoodForSides(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return world.isAirBlock(new BlockPos(x, y, z));
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return world.isAirBlock(new BlockPos(x, y, z));
	}

	@Override
	public void readFromDisk(NBTTagCompound data) {
		data.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		data.setString("dieType", dieType);
		data.setInteger("progress", progress);
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		tank.readFromNBT(data.getCompoundTag("tank"));
		dieType = data.getString("dieType");
		progress = data.getInteger("progress");
	}

	@Override
	public void readFromUpdatePacket(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeToUpdatePacket(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

}
