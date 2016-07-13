package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.boilerplate.items.ItemBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.TileEntityPaired;

public class ItemBelt extends ItemBase {

	public ItemBelt(String name) {
		super(name);
		this.setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if(stack.hasTagCompound() && stack.getTagCompound().getLong("pos") != 0)
			tooltip.add(
					"Storing start position: " + BlockPos.fromLong(stack.getTagCompound().getLong("pos")).toString());
		else
			tooltip.add("No stored position");
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos clicked_pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(worldIn.isRemote)
			return EnumActionResult.PASS;

		if(!stack.hasTagCompound()) {
			NBTTagCompound nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		// Store position of first block
		if(stack.getTagCompound().getLong("pos") == 0) {
			stack.getTagCompound().setLong("pos", clicked_pos.toLong());
			SteamAgeRevolution.instance.getLogger().devInfo(clicked_pos.toString());
			return EnumActionResult.SUCCESS;
		}
		else {
			SteamAgeRevolution.instance.getLogger().devInfo("Attempted pairing");
			// Get saved block position
			BlockPos saved_pos = BlockPos.fromLong(stack.getTagCompound().getLong("pos"));
			if(TileEntityPaired.pairBlocks(worldIn, clicked_pos, saved_pos)) {
				// If pairing is successful, delete the belt
				stack.stackSize--;
				return EnumActionResult.SUCCESS;
			}
			else {
				// Pairing failed.

				// Remove stored position.
				stack.getTagCompound().setLong("pos", 0);
				// TODO Notify player of failure.
				return EnumActionResult.FAIL;
			}
		}
	}
}
