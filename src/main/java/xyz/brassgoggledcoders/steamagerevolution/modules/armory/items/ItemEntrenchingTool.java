package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.items.tools.IAreaBreakingTool;
import com.teamacronymcoders.base.items.tools.ItemShovelBase;
import com.teamacronymcoders.base.util.TinkersUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemEntrenchingTool extends ItemShovelBase implements IAreaBreakingTool {
	
	public ItemEntrenchingTool(String name, ToolMaterial materialIn) {
		super(name, materialIn);
		setHarvestLevel("shovel", materialIn.getHarvestLevel());
	}
	
	@Override
	public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
		World world = player.getEntityWorld();
		for(BlockPos extra : TinkersUtils.calcAOEBlocks(stack, world, player, pos, 3, 1, 2)) {
			TinkersUtils.breakExtraBlock(stack, world, player, extra, pos);
		}
		return super.onBlockStartBreak(stack, pos, player);
	}
	
	@Override
	public RayTraceResult rayTrace(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, boolean useLiquids) {
		return super.rayTrace(worldIn, playerIn, useLiquids);
	}

}
