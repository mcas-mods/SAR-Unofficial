package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemExpandableArmor extends ItemArmorBase {

	@CapabilityInject(IExpansionContainer.class)
	static Capability<IExpansionContainer> EXPANSION_CONTAINER_CAP = null;

	public ItemExpandableArmor(ArmorMaterial mat, EntityEquipmentSlot slot, String name) {
		super(mat, slot, name, "expandable");
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new CapabilityProvider();
	}

	public static class CapabilityProvider implements ICapabilityProvider {
		private IExpansionContainer container;

		public CapabilityProvider() {
			this.container = new ExpansionContainerHandler();
		}

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			if(capability == EXPANSION_CONTAINER_CAP)
				return true;
			return false;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if(capability == EXPANSION_CONTAINER_CAP)
				return EXPANSION_CONTAINER_CAP.cast(container);
			return null;
		}
	}

}
