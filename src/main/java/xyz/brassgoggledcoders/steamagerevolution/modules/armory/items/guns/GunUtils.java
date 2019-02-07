package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns.IAmmo.AmmoType;

public class GunUtils {
	public static ItemStack findAmmo(EntityPlayer player, ItemStack gunStack, AmmoType type) {
		if(isValidAmmo(player.getHeldItem(EnumHand.OFF_HAND), type)) {
			return player.getHeldItem(EnumHand.OFF_HAND);
		}
		else if(isValidAmmo(player.getHeldItem(EnumHand.MAIN_HAND), type)) {
			return player.getHeldItem(EnumHand.MAIN_HAND);
		}
		else {
			for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if(isValidAmmo(itemstack, type)) {
					return itemstack;
				}
			}
			return ItemStack.EMPTY;
		}
	}

	public static boolean isValidAmmo(ItemStack stack, AmmoType type) {
		Item item = stack.getItem();
		if(item instanceof IAmmo) {
			return ((IAmmo) item).getAmmoType().equals(type);
		}
		return false;
	}
}
