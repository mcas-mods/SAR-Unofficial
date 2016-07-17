package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.items.ItemBase;
import xyz.brassgoggledcoders.steamagerevolution.api.SARAPI;

public class ItemSpinReader extends ItemBase {

	public ItemSpinReader(String name) {
		super(name);
		this.setMaxStackSize(1);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(worldIn.getTileEntity(pos) != null
				&& worldIn.getTileEntity(pos).hasCapability(SARAPI.SPIN_HANDLER_CAPABILITY, facing)) {
			playerIn.addChatMessage(new TextComponentString("Current Speed: "
					+ worldIn.getTileEntity(pos).getCapability(SARAPI.SPIN_HANDLER_CAPABILITY, facing).getSpeed()));
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
}
