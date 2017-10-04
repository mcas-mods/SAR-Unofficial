package xyz.brassgoggledcoders.steamagerevolution.modules.armory.expandablearmor;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.containers.ContainerBase;
import com.teamacronymcoders.base.containers.slots.SlotArmor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

// TODO Shift-click crashes.
public class ContainerExpansionEditor extends ContainerBase {
	private TileEntityExpansionEditor expansionEditor;

	public ContainerExpansionEditor(EntityPlayer player, TileEntityExpansionEditor expansionEditor) {
		this.expansionEditor = expansionEditor;
		IItemHandler inventory =
				expansionEditor.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
		for(int i = 0; i < 4; ++i) {
			this.addSlotToContainer(new SlotArmor(player, EntityEquipmentSlot.values()[i], 62, 8 + i * 18));
		}

		this.addSlotToContainer(new SlotExpansionContainer(inventory, expansionEditor, 0, 89, 26));

		for(int v = 0; v < 4; v++) {
			for(int h = 0; h < 3; h++) {
				this.addSlotToContainer(
						new SlotExpansion(inventory, expansionEditor, h + (v * 3), 116 + h * 18, 8 + (v * 18)));
			}
		}

		this.createPlayerSlots(player.inventory);
	}

	@Override
	public boolean canInteractWith(@Nonnull EntityPlayer player) {
		return true;
	}
}
