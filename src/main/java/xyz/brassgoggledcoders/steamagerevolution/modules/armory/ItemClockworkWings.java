package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import java.util.List;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.client.ClientHelper;
import com.teamacronymcoders.base.items.ItemArmorBase;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class ItemClockworkWings extends ItemArmorBase {

	private static final float hungerPerTick = 0.5F;

	public ItemClockworkWings() {
		super(ArmorMaterial.LEATHER, EntityEquipmentSlot.CHEST);
		this.setMaxDamage(0);
		this.setUnlocalizedName("clockwork_wings");
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if(!player.capabilities.allowFlying && (player.getFoodStats().getFoodLevel() != 0)) {
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());

			NBTTagCompound tag = stack.getTagCompound();
			boolean shouldBoost = ClientHelper.settings().keyBindJump.isKeyDown();
			boolean wasJumping = tag.getBoolean("isJumping");

			if(shouldBoost)
				if(wasJumping)
					shouldBoost = false;
				else
					tag.setBoolean("isJumping", true);
			else if(wasJumping)
				tag.setBoolean("isJumping", false);

			if(/* (SteamAgeRevolution.proxy.isScreenEmpty()) && */ (player.posY < 160) && shouldBoost) {
				player.addExhaustion(hungerPerTick);

				if(player.motionY > 0.0D)
					player.motionY += 0.3D;
				else
					player.motionY += 0.4D;
			}

			if((player.motionY < 0.0D) && player.isSneaking() && !player.onGround) {
				player.addExhaustion(hungerPerTick / 6);
				player.motionY /= 1.4D;

				player.motionX *= 1.05D;
				player.motionZ *= 1.05D;
			}

			if(!player.onGround) {
				player.motionX *= 1.04D;
				player.motionZ *= 1.04D;
			}

			if(player.fallDistance > 0) {
				player.addExhaustion(hungerPerTick / 4);
				player.fallDistance = 0;
			}
		}
	}

	@Override
	@Nonnull
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack is, Entity entity, EntityEquipmentSlot slot, String type) {
		return SteamAgeRevolution.MODID + ":textures/models/armor/wings.png";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public net.minecraft.client.model.ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack,
			EntityEquipmentSlot armorSlot, net.minecraft.client.model.ModelBiped _default) {
		return new ModelClockworkWings(1F);
	}

	@Override
	public Item getItem() {
		return this;
	}

	@Override
	public List<String> getModelNames(List<String> modelNames) {
		modelNames.add("clockwork_wings");
		return modelNames;
	}
}
