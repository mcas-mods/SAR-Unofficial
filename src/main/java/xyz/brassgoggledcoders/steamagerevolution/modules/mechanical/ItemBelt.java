package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.items.ItemBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class ItemBelt extends ItemBase {

	public ItemBelt(String name) {
		super(name);
		this.setMaxStackSize(1);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!stack.hasTagCompound()) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setLong("pos", pos.toLong());
			stack.setTagCompound(nbt);
			SteamAgeRevolution.instance.getLogger().devInfo("Compound set");
		}
		else {
			if(worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos) instanceof TileBeltEnd) {
				TileBeltEnd end = (TileBeltEnd) worldIn.getTileEntity(pos);
				if(!(end.isTilePaired())) {
					end.setPairedTileLoc(BlockPos.fromLong(stack.getTagCompound().getLong("pos")));
					SteamAgeRevolution.instance.getLogger().devInfo("First");
					end.setSlave();
					if(!(stack.getTagCompound().getBoolean("second"))) {
						stack.getTagCompound().setBoolean("second", true);
					}
					else
						end.setSlave();
					stack.stackSize--;
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return EnumActionResult.PASS;
	}
}
