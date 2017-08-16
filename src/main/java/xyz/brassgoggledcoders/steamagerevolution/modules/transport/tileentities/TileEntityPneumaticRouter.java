package xyz.brassgoggledcoders.steamagerevolution.modules.transport.tileentities;

import com.teamacronymcoders.base.tileentities.TileEntityInventoryBase;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.brassgoggledcoders.steamagerevolution.modules.transport.blocks.BlockPneumaticRouter;

public class TileEntityPneumaticRouter extends TileEntityInventoryBase implements ITickable {

	boolean hasCache = false;
	private IItemHandler otherHandler;

	private int tickRate = 20; // Default to once a second
	private int ticks = 0;

	public TileEntityPneumaticRouter() {
		super(1);
	}

	@Override
	protected void readFromDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	protected NBTTagCompound writeToDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub
		return data;
	}

	@Override
	public void update() {
		if(ticks == tickRate) {
			updateTile();
			ticks = 0;
		}
		else
			ticks++;
	}

	public void updateTile() {
		if(world.isRemote)
			return;
		if(!hasCache)
			recalculateCache(getWorld(), getPos(), getWorld().getBlockState(getPos()), null);

		IItemHandler handler = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for(int i = 0; i < handler.getSlots(); i++) {
			if(ItemHandlerHelper.insertItem(otherHandler, handler.getStackInSlot(i).copy().splitStack(1), true)
					.isEmpty()) {
				ItemHandlerHelper.insertItem(otherHandler, handler.getStackInSlot(i).splitStack(1), false);
			}
		}
	}

	public void recalculateCache(World worldIn, BlockPos pos, IBlockState state, BlockPos fromPos) {
		if(fromPos == null) {
			fromPos = pos.offset(state.getValue(BlockPneumaticRouter.FACING));
		}
		TileEntity other = getWorld().getTileEntity(fromPos);
		if(other != null && other.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
				state.getValue(BlockPneumaticRouter.FACING).getOpposite())) {
			otherHandler = other.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
					state.getValue(BlockPneumaticRouter.FACING).getOpposite());
		}
	}

}
