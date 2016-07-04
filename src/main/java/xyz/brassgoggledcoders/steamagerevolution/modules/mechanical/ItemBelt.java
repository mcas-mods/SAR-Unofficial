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

	// TODO Only allow linking along axes. Disallow linking if already linked.
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
			SteamAgeRevolution.instance.getLogger().devInfo("Pos " + clicked_pos.toString());
			return EnumActionResult.SUCCESS;
		}
		else {
			// Get saved block position
			BlockPos saved_pos = BlockPos.fromLong(stack.getTagCompound().getLong("pos"));
			// Check that both ends are actually belts.
			if(worldIn.getTileEntity(clicked_pos) instanceof TileEntityBeltEnd
					&& (worldIn.getChunkFromBlockCoords(saved_pos).isLoaded()
							&& worldIn.getTileEntity(saved_pos) instanceof TileEntityBeltEnd)) {
				TileEntityBeltEnd start = (TileEntityBeltEnd) worldIn.getTileEntity(saved_pos);
				TileEntityBeltEnd end = (TileEntityBeltEnd) worldIn.getTileEntity(clicked_pos);

				// Don't allow pairing if either end is already paired or if you're trying to pair something with
				// itself.
				if(!(end.isTilePaired()) && !(start.isTilePaired()) && saved_pos != clicked_pos) {
					// Set start's pair
					start.setPairedTileLoc(clicked_pos);
					start.setMaster();
					// Set end's pair
					end.setPairedTileLoc(saved_pos);
					end.setSlave();
				}
				// Delete the belt
				stack.stackSize--;
				return EnumActionResult.SUCCESS;
			}
		}
		return EnumActionResult.PASS;
	}
}
