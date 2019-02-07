package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns;

import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.entities.EntityBullet;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns.IAmmo.AmmoType;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns.IGunPart.GunPartType;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.guns.IMechanism.ActionType;

public class ItemGun extends ItemBase {

	@ObjectHolder(value = SteamAgeRevolution.MODID + ":bullet")
	public static final Item bullet = null;

	public ItemGun() {
		super("gun");
	}

	@Override
	@SideOnly(Side.CLIENT)
	@ParametersAreNonnullByDefault
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(getOrCreateTagCompound(stack).getString("BARREL"));
		tooltip.add(getOrCreateTagCompound(stack).getString("MECHANISM"));
		tooltip.add(getOrCreateTagCompound(stack).getString("CHAMBER"));
		tooltip.add(getOrCreateTagCompound(stack).getString("STOCK"));
		if(getOrCreateTagCompound(stack).getBoolean("isLoaded")) {
			tooltip.add("Loaded");
		}
		else {
			tooltip.add("Unloaded");
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		ItemGun.getOrCreateTagCompound(stack);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);

		if(ActionType.AUTO
				.equals(((IMechanism) GunPartRegistry.getPart(getOrCreateTagCompound(stack).getString("MECHANISM")))
						.getActionType())
				|| getOrCreateTagCompound(stack).getBoolean("isLoaded")) {
			playerIn.setActiveHand(handIn);
		}
		else {
			ItemStack ammo = this.findAmmo(playerIn, stack);
			if(!ammo.isEmpty()) {
				getOrCreateTagCompound(stack).setTag("loaded", ammo.writeToNBT(new NBTTagCompound()));
				ammo.shrink(1);
				getOrCreateTagCompound(stack).setBoolean("isLoaded", true);
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
			}
		}

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase entityLiving, int count) {
		if(((IMechanism) GunPartRegistry.getPart(getOrCreateTagCompound(stack).getString("MECHANISM")))
				.getActionType() == ActionType.AUTO) {
			ItemStack ammo = this.findAmmo((EntityPlayer) entityLiving, stack);
			if(!ammo.isEmpty()) {
				ammo.shrink(1);
				shoot(entityLiving.getEntityWorld(), entityLiving);
			}
		}
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		int i = this.getMaxItemUseDuration(stack) - timeLeft;
		if(i < 60) {
			return;
		}
		if(getOrCreateTagCompound(stack).getBoolean("isLoaded")) {
			if(entityLiving instanceof EntityPlayer) {
				shoot(worldIn, entityLiving);
				if(ActionType.BOLT.equals(
						((IMechanism) GunPartRegistry.getPart(getOrCreateTagCompound(stack).getString("MECHANISM")))
								.getActionType())) {
					stack.getTagCompound().setBoolean("isLoaded", false);
				}
				else {
					ItemStack ammo = this.findAmmo((EntityPlayer) entityLiving, stack);
					if(!ammo.isEmpty()) {
						ammo.shrink(1);
						getOrCreateTagCompound(stack).setBoolean("isLoaded", true);
					}
				}
			}
		}
	}

	private void shoot(World worldIn, EntityLivingBase entityLiving) {
		EntityPlayer playerIn = (EntityPlayer) entityLiving;

		EntityBullet bullet = new EntityBullet(worldIn, playerIn);
		bullet.shoot(playerIn, playerIn.getPitchYaw().x, playerIn.getRotationYawHead(), playerIn.getEyeHeight(), 3f, 0);
		worldIn.spawnEntity(bullet);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		if(ActionType.AUTO
				.equals(((IMechanism) GunPartRegistry.getPart(getOrCreateTagCompound(stack).getString("MECHANISM")))
						.getActionType())
				|| getOrCreateTagCompound(stack).getBoolean("isLoaded")) {
			return 72000;
		}
		return super.getMaxItemUseDuration(stack);
	}

	private ItemStack findAmmo(EntityPlayer player, ItemStack gunStack) {
		IChamber part = (IChamber) GunPartRegistry
				.getPart(ItemGun.getOrCreateTagCompound(gunStack).getString(GunPartType.CHAMBER.toString()));
		SteamAgeRevolution.instance.getLogger().devInfo("Accepted: " + part.getAcceptedType().toString());
		if(this.isValidAmmo(player.getHeldItem(EnumHand.OFF_HAND), part.getAcceptedType())) {
			return player.getHeldItem(EnumHand.OFF_HAND);
		}
		else if(this.isValidAmmo(player.getHeldItem(EnumHand.MAIN_HAND), part.getAcceptedType())) {
			return player.getHeldItem(EnumHand.MAIN_HAND);
		}
		else {
			for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if(this.isValidAmmo(itemstack, part.getAcceptedType())) {
					return itemstack;
				}
			}
			return ItemStack.EMPTY;
		}
	}

	protected boolean isValidAmmo(ItemStack stack, AmmoType type) {
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

}
