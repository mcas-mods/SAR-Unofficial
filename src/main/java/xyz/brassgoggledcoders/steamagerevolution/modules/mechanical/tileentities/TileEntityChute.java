package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.brassgoggledcoders.boilerplate.utils.ItemStackUtils;

public class TileEntityChute extends TileEntitySpinConsumer {

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;
		int rate = handler.getSpeed() / 4;

		// TODO Make Directional
		TileEntity up = this.getWorld().getTileEntity(this.getPos().up());
		TileEntity down = this.getWorld().getTileEntity(this.getPos().down());
		if(up != null && up.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)
				&& down != null && down.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP)) {
			IItemHandler top_inventory =
					up.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
			IItemHandler bottom_inventory =
					down.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
			for(int i = 0; i < top_inventory.getSlots(); i++) {
				ItemStack stack = top_inventory.getStackInSlot(i);

				if(ItemStackUtils.isItemNonNull(stack) && top_inventory.extractItem(i, 1 * rate, true) != null
						&& ItemHandlerHelper.insertItemStacked(bottom_inventory,
								top_inventory.extractItem(i, 1 * rate, true), true) == null) {
					ItemHandlerHelper.insertItemStacked(bottom_inventory, top_inventory.extractItem(i, 1 * rate, false),
							false);
				}
				break;
			}
		}
		super.updateTile();
	}
}
