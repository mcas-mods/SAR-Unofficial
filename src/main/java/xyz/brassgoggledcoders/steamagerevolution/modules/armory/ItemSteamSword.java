package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import java.util.List;

import javax.annotation.*;

import com.google.common.collect.*;
import com.teamacronymcoders.base.IBaseMod;
import com.teamacronymcoders.base.IModAware;
import com.teamacronymcoders.base.client.models.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

public class ItemSteamSword extends ItemSword implements IHasModel, IModAware {

	boolean creativeTabSet = false;
	private IBaseMod mod;
	int capacity;
	String name;

	public static final int steamUsePerBlock = 10;

	protected ItemSteamSword(String name, int capacity) {
		super(ModuleArmory.STEAM);
		this.setUnlocalizedName(name);
		this.capacity = capacity;
		this.name = name;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		FluidHandlerItemStack internal =
				(FluidHandlerItemStack) stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		if(internal.getFluid() != null && internal.getFluid().amount >= steamUsePerBlock) {
			internal.drain(steamUsePerBlock, true);
			return true;
		}
		else {
			return false;
		}
	}

	// TODO apply to other tools
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {

		Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier> create();

		FluidHandlerItemStack internal =
				(FluidHandlerItemStack) stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);

		if(slot == EntityEquipmentSlot.MAINHAND && internal.getFluid() != null) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
					new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 3.0F + getAttackDamage(), 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
					new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
		}

		return multimap;
	}

	@Override
	public List<String> getModelNames(List<String> modelNames) {
		modelNames.add(name);
		return modelNames;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new FluidHandlerItemStack(stack, capacity) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return FluidRegistry.getFluidName(fluid).equals("steam");
			}
		};
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		FluidHandlerItemStack internal =
				(FluidHandlerItemStack) stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		return 1.0D - ((double) internal.getFluid().amount / capacity);

	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		FluidHandlerItemStack internal =
				(FluidHandlerItemStack) stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		return internal.getFluid() != null;
	}

	@Override
	@ParametersAreNonnullByDefault
	public void getSubItems(@Nullable CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if(tab != null && tab == this.getCreativeTab() || tab == CreativeTabs.SEARCH) {
			subItems.addAll(this.getAllSubItems(Lists.newArrayList()));
		}
	}

	@Override
	public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
		itemStacks.add(new ItemStack(this, 1));
		return itemStacks;
	}

	@Override
	@Nonnull
	public Item setCreativeTab(@Nonnull CreativeTabs tab) {
		if(!creativeTabSet) {
			super.setCreativeTab(tab);
			this.creativeTabSet = true;
		}
		return this;
	}

	@Override
	public IBaseMod getMod() {
		return mod;
	}

	@Override
	public void setMod(IBaseMod mod) {
		this.mod = mod;
	}

	@Override
	public Item getItem() {
		return this;
	}

}
