package xyz.brassgoggledcoders.steamagerevolution.utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import xyz.brassgoggledcoders.boilerplate.utils.ItemStackUtils;
import xyz.brassgoggledcoders.boilerplate.utils.PositionUtils;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.api.events.BeltLinkedEvent;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.ModuleMechanical;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockBeltDummy;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockBeltEnd;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.TileEntityBeltEnd;

public class PairingHandler {

	public static void unpair(TileEntityBeltEnd tile) {
		// Remove the dummy blocks.
		PositionUtils.removeBlocksInArea(tile.getWorld(), tile.getPos(), tile.getPairedTile().getPos());
		// Unpair tiles
		tile.getPairedTile().setPairedTileLoc(null);
		tile.getPairedTile().setSlave();
		tile.setPairedTileLoc(null);
		tile.setSlave();
	}

	public static boolean pairBlocks(World worldIn, ItemStack stack, BlockPos clicked_pos, BlockPos saved_pos) {
		// Check that both ends are actually pairable.
		if(worldIn.getTileEntity(clicked_pos) instanceof TileEntityBeltEnd
				&& worldIn.getChunkFromBlockCoords(saved_pos).isLoaded()
				&& worldIn.getTileEntity(saved_pos) instanceof TileEntityBeltEnd) {
			SteamAgeRevolution.instance.getLogger().devInfo("Second paircheck passed (instanceof)");
			TileEntityBeltEnd start = (TileEntityBeltEnd) worldIn.getTileEntity(saved_pos);
			TileEntityBeltEnd end = (TileEntityBeltEnd) worldIn.getTileEntity(clicked_pos);

			// Don't allow pairing if either end is already paired or if you're trying to pair something with
			// itself.
			if(!end.isTilePaired() && !start.isTilePaired() && saved_pos != clicked_pos) {
				SteamAgeRevolution.instance.getLogger().devInfo("Third paircheck passed (not already paired)");
				// Ensure pairs are aligned on axes
				if(PositionUtils.arePositionsAlignedOnTwoAxes(clicked_pos, saved_pos)) {
					SteamAgeRevolution.instance.getLogger().devInfo("Fourth paircheck passed (alignment)");
					if(PositionUtils.isLOSClear(worldIn, saved_pos, clicked_pos)) {
						SteamAgeRevolution.instance.getLogger().devInfo("Fifth paircheck passed (clear LOS)");
						// // Add the dummy blocks.
						IBlockState dummy;
						if(ItemStackUtils.doItemsMatch(stack, ModuleMechanical.leather_belt)) {
							dummy = ModuleMechanical.leather_belt_dummy.getDefaultState().withProperty(
									BlockBeltDummy.FACING,
									PositionUtils.getFacingFromPositions(clicked_pos, saved_pos));
						}
						else {
							dummy = ModuleMechanical.rubber_belt_dummy.getDefaultState().withProperty(
									BlockBeltDummy.FACING,
									PositionUtils.getFacingFromPositions(clicked_pos, saved_pos));
						}
						PositionUtils.replaceBlocksIn(worldIn, clicked_pos, saved_pos, dummy);
						// TODO For some reason this breaks everything...
						// // Set facings of ends
						worldIn.setBlockState(start.getPos(), worldIn.getBlockState(start.getPos()).withProperty(
								BlockBeltEnd.FACING, PositionUtils.getFacingFromPositions(clicked_pos, saved_pos)));
						worldIn.setBlockState(end.getPos(), worldIn.getBlockState(end.getPos()).withProperty(
								BlockBeltEnd.FACING, PositionUtils.getFacingFromPositions(saved_pos, clicked_pos)));
						// Set start's pair, and make it a master.
						start.setPairedTileLoc(clicked_pos);
						start.setMaster();
						start.markDirty();
						// Set end's pair, and make it a slave.
						end.setPairedTileLoc(saved_pos);
						end.setSlave();
						end.markDirty();
						// Post event
						MinecraftForge.EVENT_BUS.post(new BeltLinkedEvent(start, end));
						return true;
					}
				}
			}
		}
		return false;
	}

}
