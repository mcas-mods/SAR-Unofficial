package xyz.brassgoggledcoders.steamagerevolution.modules.mining.items;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.items.IHasItemColor;
import com.teamacronymcoders.base.items.itemblocks.ItemBlockModel;

import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks.BlockHeavyOre;

public class ItemBlockHeavyOre extends ItemBlockModel<BlockHeavyOre> implements IHasItemColor {

	public ItemBlockHeavyOre(BlockHeavyOre block) {
		super(block);
	}

	@Override
	public int getColorFromItemstack(@Nonnull ItemStack stack, int tintIndex) {
		return tintIndex == 0 ? this.getActualBlock().colorMultiplier(null, null, null, tintIndex) : -1;
	}

}
