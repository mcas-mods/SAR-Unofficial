package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.entities.EntityBullet;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns.IAmmo.AmmoType;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns.IGunPart.GunPartType;

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

	public static ItemStack findAmmo(EntityPlayer player, ItemStack gunStack) {
		IChamber part = (IChamber) GunPartRegistry
				.getPart(GunUtils.getOrCreateTagCompound(gunStack).getString(GunPartType.CHAMBER.toString()));
		SteamAgeRevolution.instance.getLogger().devInfo("Accepted: " + part.getAcceptedType().toString());
		if(GunUtils.isValidAmmo(player.getHeldItem(EnumHand.OFF_HAND), part.getAcceptedType())) {
			return player.getHeldItem(EnumHand.OFF_HAND);
		}
		else if(GunUtils.isValidAmmo(player.getHeldItem(EnumHand.MAIN_HAND), part.getAcceptedType())) {
			return player.getHeldItem(EnumHand.MAIN_HAND);
		}
		else {
			for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if(GunUtils.isValidAmmo(itemstack, part.getAcceptedType())) {
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

	public static NBTTagCompound getOrCreateTagCompound(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setBoolean("isLoaded", false);
			stack.setTagCompound(tag);
		}

		return stack.getTagCompound();
	}

	public static IGunPart getPartFromGun(ItemStack stack, IGunPart.GunPartType type) {
		return GunPartRegistry.getPart(getOrCreateTagCompound(stack).getString(type.toString()));
	}

	public static void shoot(World worldIn, EntityLivingBase entityLiving) {
		EntityPlayer playerIn = (EntityPlayer) entityLiving;
	
		EntityBullet bullet = new EntityBullet(worldIn, playerIn);
		bullet.shoot(playerIn, playerIn.getPitchYaw().x, playerIn.getRotationYawHead(), playerIn.getEyeHeight(), 3f, 0);
		worldIn.spawnEntity(bullet);
	}
}
