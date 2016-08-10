package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLLog;

public class TileEntityDropHammer extends TileEntitySpinConsumer {
	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		// TODO Wind up

		BlockPos target = getPos().down(2);

		if(!getWorld().isAirBlock(target)) {
			FMLLog.warning("" + getWorld().getBlockState(target).getBlock());
			ItemStack result =
					DropHammerRecipes.instance().getResult(new ItemStack(getWorld().getBlockState(target).getBlock()));
			// TODO Metadata
			if(result != null) {
				getWorld().setBlockState(target, Block.getBlockFromItem(result.getItem()).getDefaultState());
			}
		}

		// TODO Anvil and dies
	}
}
