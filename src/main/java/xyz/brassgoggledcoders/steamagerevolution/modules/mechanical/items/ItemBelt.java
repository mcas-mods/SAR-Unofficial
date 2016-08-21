package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.boilerplate.items.ItemBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.PairingHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.PositionUtils;

public class ItemBelt extends ItemBase {

	int maxLength;

	public ItemBelt(String name, int maxLength) {
		super(name);
		this.setMaxStackSize(1);
		this.maxLength = maxLength;
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
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
		if(worldIn.isRemote)
			return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);

		if(playerIn.isSneaking()) {
			if(!stack.hasTagCompound()) {
				NBTTagCompound nbt = new NBTTagCompound();
				stack.setTagCompound(nbt);
			}

			if(stack.getTagCompound().getLong("pos") != 0) {
				stack.getTagCompound().setLong("pos", 0);

				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
			}
		}

		return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
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
			if(PositionUtils.getDistanceBetweenPositions(clicked_pos, saved_pos) <= maxLength) {
				SteamAgeRevolution.instance.getLogger().devInfo("First paircheck passed (distance)");
				// TODO This is not safe
				if(PairingHandler.pairBlocks(worldIn, stack, clicked_pos, saved_pos)) {
					// If pairing is successful, delete the belt
					stack.stackSize--;
					return EnumActionResult.SUCCESS;
				}
				// TODO
				else {
					// Pairing failed.

					// Remove stored position.
					stack.getTagCompound().setLong("pos", 0);
					// TODO Notify player of failure.
					return EnumActionResult.FAIL;
				}
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
