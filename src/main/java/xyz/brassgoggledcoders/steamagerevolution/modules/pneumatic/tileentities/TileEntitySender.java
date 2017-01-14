package xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.tileentities;

import com.teamacronymcoders.base.tileentities.TileEntitySlowTick;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.ModulePneumatic;
import xyz.brassgoggledcoders.steamagerevolution.modules.pneumatic.blocks.BlockSender;

public class TileEntitySender extends TileEntitySlowTick {

	private int maxDistance = 16;

	@Override
	public void updateTile() {
		EnumFacing facing = this.getWorld().getBlockState(getPos()).getValue(BlockSender.FACING);
		TileEntity behind = this.getWorld().getTileEntity(getPos().offset(facing.getOpposite()));
		if(behind instanceof TileEntityRouter) {
			IItemHandler sendInventory =
					behind.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite());
			if(!ItemStackUtils.isItemNonNull(sendInventory.getStackInSlot(0))) {
				return;
			}
			for(int i = 1; i < maxDistance; i++) {
				Block block = this.getWorld().getBlockState(getPos().offset(facing, i)).getBlock();
				if(block == ModulePneumatic.router) {
					IItemHandler recieveInventory = this.getWorld().getTileEntity(getPos().offset(facing, i))
							.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
					if(recieveInventory.insertItem(0, sendInventory.getStackInSlot(0), true) == null) {
						recieveInventory.insertItem(0, sendInventory.getStackInSlot(0), false);
						sendInventory.extractItem(0, sendInventory.getStackInSlot(0).stackSize, false);
						this.getWorld().playSound(null, this.getPos(), SoundEvents.ENTITY_CAT_HISS,
								SoundCategory.BLOCKS, 1, 1);
					}
					return;
				}
				else if(block == ModulePneumatic.pneumaticTube) {
					continue;
				}
				else
					return;
			}
		}
	}
}
