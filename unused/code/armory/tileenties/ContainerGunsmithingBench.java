package xyz.brassgoggledcoders.steamagerevolution.modules.armory.tileenties;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.items.guns.parts.IGunPart;

public class ContainerGunsmithingBench extends ContainerBase {
	public ContainerGunsmithingBench(EntityPlayer player, TileEntityGunsmithingBench tile) {
		createPlayerSlots(player.inventory);
		addSlotToContainer(new SlotItemHandler(tile.inventory, 0, 124, 35) {
			@Override
			public boolean isItemValid(@Nonnull ItemStack stack) {
				return false;
			}
		});

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				addSlotToContainer(new SlotItemHandler(tile.inventory, j + i * 3, 30 + j * 18, 17 + i * 18) {
					@Override
					public boolean isItemValid(@Nonnull ItemStack stack) {
						return stack.getItem() instanceof IGunPart;
					}
				});
			}
		}
	}

	@Override
	public boolean enchantItem(EntityPlayer playerIn, int id) {
		SteamAgeRevolution.instance.getLogger().devInfo("" + id);
		return false;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
