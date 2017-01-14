package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.items;

import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.CapabilityHandler;

public class ItemSpinReader extends ItemBase {

	public ItemSpinReader(String name) {
		super(name);
		this.setMaxStackSize(1);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(worldIn.getTileEntity(pos) != null
				&& worldIn.getTileEntity(pos).hasCapability(CapabilityHandler.SPIN_HANDLER_CAPABILITY, facing)) {
			if(worldIn.isRemote) {
				playerIn.addChatMessage(new TextComponentString("Current Speed: " + worldIn.getTileEntity(pos)
						.getCapability(CapabilityHandler.SPIN_HANDLER_CAPABILITY, facing).getSpeed()));
				return EnumActionResult.SUCCESS;
			}
			else {
				if(playerIn.isSneaking()) {
					playerIn.addChatMessage(
							new TextComponentString("Current Speed (Server):" + worldIn.getTileEntity(pos)
									.getCapability(CapabilityHandler.SPIN_HANDLER_CAPABILITY, facing).getSpeed()));
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return EnumActionResult.PASS;
	}
}
