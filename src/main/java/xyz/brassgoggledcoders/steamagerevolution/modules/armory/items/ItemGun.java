package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items;

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
		if(stack.hasTagCompound()) {
			if(stack.getTagCompound().getBoolean("isLoaded")) {
				tooltip.add("Loaded");
			}
			else {
				tooltip.add("Unloaded");
			}
		}
		else {
			this.setupNBT(stack);
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		this.setupNBT(stack);
	}

	void setupNBT(ItemStack stack) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setBoolean("isLoaded", false);
		stack.setTagCompound(tag);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if(stack.hasTagCompound()) {
			if(stack.getTagCompound().getBoolean("isLoaded")) {
				playerIn.setActiveHand(handIn);
			}
			else {
				ItemStack ammo = this.findAmmo(playerIn);
				if(!ammo.isEmpty()) {
					ammo.shrink(1);
					stack.getTagCompound().setBoolean("isLoaded", true);
				}
			}
		}
		else {
			this.setupNBT(stack);
		}

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		int i = this.getMaxItemUseDuration(stack) - timeLeft;
		if(i < 60)
			return;
		if(stack.hasTagCompound()) {
			if(entityLiving instanceof EntityPlayer) {
				EntityPlayer playerIn = (EntityPlayer) entityLiving;
				if(stack.getTagCompound().getBoolean("isLoaded")) {
					EntityBullet bullet = new EntityBullet(worldIn, playerIn);
					bullet.shoot(playerIn, playerIn.getPitchYaw().x, playerIn.getRotationYawHead(),
							playerIn.getEyeHeight(), 3f, 0);
					worldIn.spawnEntity(bullet);
					stack.getTagCompound().setBoolean("isLoaded", false);
				}
			}
		}
		else {
			this.setupNBT(stack);
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		if(stack.hasTagCompound()) {
			if(stack.getTagCompound().getBoolean("isLoaded")) {
				return 72000;
			}
		}
		else {
			this.setupNBT(stack);
		}
		return super.getMaxItemUseDuration(stack);
	}

	private ItemStack findAmmo(EntityPlayer player) {
		if(this.isBullet(player.getHeldItem(EnumHand.OFF_HAND))) {
			return player.getHeldItem(EnumHand.OFF_HAND);
		}
		else if(this.isBullet(player.getHeldItem(EnumHand.MAIN_HAND))) {
			return player.getHeldItem(EnumHand.MAIN_HAND);
		}
		else {
			for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if(this.isBullet(itemstack)) {
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}

	protected boolean isBullet(ItemStack stack) {
		return stack.getItem() == bullet;
	}

}
