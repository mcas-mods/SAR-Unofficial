package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.ModuleSteam;
import xyz.brassgoggledcoders.steamagerevolution.utils.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.ISmartTankCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.SARRectangularMultiblockControllerBase;

public class ControllerSteamHammer extends SARRectangularMultiblockControllerBase implements ISmartTankCallback {

	public ItemStackHandler inventory = new ItemStackHandler(2);
	public FluidTank tank = new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 4, "steam", this);
	public String dieType = "";
	private int progress = 0;
	BlockPos center = null;

	public ControllerSteamHammer(World world) {
		super(world);
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		tank.readFromNBT(data.getCompoundTag("tank"));
		dieType = data.getString("dieType");
		progress = data.getInteger("progress");
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
		center = this.getReferenceCoord().up().east().south();
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
		// TODO Auto-genera@Nullableted method stub

	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 26;// 3*3*4-10 Shielding is optional, always hollow.
	}

	@Override
	public int getMaximumXSize() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getMaximumZSize() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getMaximumYSize() {
		// TODO Auto-generated method stub
		return 4;
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
		return 4;
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
	protected boolean updateServer() {
		if(tank.getFluidAmount() >= Fluid.BUCKET_VOLUME) {
			if(progress < 10) {
				this.progress++;
				tank.drain(Fluid.BUCKET_VOLUME, true);
				return true;
			}
			else {
				if(!inventory.getStackInSlot(0).isEmpty()) {
					ItemStack result = SteamHammerRecipe.getResult(inventory.getStackInSlot(0), dieType);
					if(!result.isEmpty() && inventory.insertItem(1, result, true) == ItemStack.EMPTY) {
						inventory.extractItem(0, 1, false);
						inventory.insertItem(1, result, false);
						progress = 0;

						WORLD.playSound(null, center.getX() + .5F, center.getY(), center.getZ() + .5F,
								SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 1F, 1F);
						SteamAgeRevolution.proxy.spawnFX(EnumParticleTypes.FLAME, center);
						for(EntityLivingBase entity : WORLD.getEntitiesWithinAABB(EntityLivingBase.class,
								new AxisAlignedBB(center).expand(1, 2, 1))) {
							entity.attackEntityFrom(ModuleSteam.hammer, entity.getMaxHealth());
						}

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

	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		data.setString("dieType", dieType);
		data.setInteger("progress", progress);
	}

	@Override
	public void onTankContentsChanged(FluidTank tank) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFluid(FluidStack fluid) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Steam Hammer";
	}
}
