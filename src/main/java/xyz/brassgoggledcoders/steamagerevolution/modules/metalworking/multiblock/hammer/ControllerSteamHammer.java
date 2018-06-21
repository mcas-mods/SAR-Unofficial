package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.ModuleMetalworking;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketItemUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerSteamHammer extends SARMultiblockInventory implements ISmartStackCallback {

	public ItemStackHandlerSmart inventory;
	public String dieType = "";
	private int progress = 0;
	BlockPos center = null;
	AxisAlignedBB interior = null;

	public ControllerSteamHammer(World world) {
		super(world);
		inventory = new ItemStackHandlerSmart(2, this);
	}

	@Override
	public ItemStackHandler getInventory(String toWrap) {
		return inventory;
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		dieType = data.getString("dieType");
		progress = data.getInteger("progress");
		super.onAttachedPartWithMultiblockData(part, data);
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMachineAssembled() {
		center = this.getReferenceCoord().up().east().south();
		interior = new AxisAlignedBB(center).expand(1, 2, 1);
		WORLD.markBlockRangeForRenderUpdate(getMinimumCoord(), getMaximumCoord());
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
	protected void onTick() {
		for(EntityItem item : WORLD.getEntitiesWithinAABB(EntityItem.class, interior)) {
			if(ItemHandlerHelper.insertItem(inventory, item.getItem(), true).isEmpty()) {
				ItemHandlerHelper.insertItem(inventory, item.getItem(), false);
				item.setDead();
			}
		}
	}

	@Override
	public void onFinish() {
		super.onFinish();
		WORLD.playSound(null, center.getX() + .5F, center.getY(), center.getZ() + .5F, SoundEvents.BLOCK_ANVIL_LAND,
				SoundCategory.BLOCKS, 1F, 1F);
		SteamAgeRevolution.proxy.spawnFX(EnumParticleTypes.FLAME, center);
		WORLD.getEntitiesWithinAABB(EntityLivingBase.class, interior).forEach(
				entity -> entity.attackEntityFrom(ModuleMetalworking.damageSourceHammer, entity.getMaxHealth()));
	}

	@Override
	protected void updateClient() {
		// FMLLog.warning(TextUtils.representInventoryContents(inventory).getUnformattedComponentText());
	}

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
		data.setString("dieType", dieType);
		data.setInteger("progress", progress);
		super.writeToDisk(data);
	}

	@Override
	public String getName() {
		return "Steam Hammer";
	}

	@Override
	public void updateStack(PacketItemUpdate message) {
		this.inventory.setStackInSlot(message.slot, message.item);
	}

	@Override
	public ItemStackHandlerExtractSpecific getItemInput() {
		return this.inventory;
	}

	@Override
	public MultiFluidTank getFluidInputs() {
		return null;
	}

	@Override
	public ItemStackHandlerExtractSpecific getItemOutput() {
		return this.inventory;
	}

	@Override
	public MultiFluidTank getFluidOutputs() {
		return null;
	}

	@Override
	public void onContentsChanged(int slot) {
		if(WORLD.isRemote)
			return;
		SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
				new PacketItemUpdate(this.getReferenceCoord(), inventory.getStackInSlot(slot), slot),
				this.getReferenceCoord(), WORLD.provider.getDimension());
	}
}
