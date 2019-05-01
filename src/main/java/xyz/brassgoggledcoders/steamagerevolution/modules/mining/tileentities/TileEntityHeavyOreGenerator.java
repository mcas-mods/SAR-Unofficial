package xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities;

import com.google.common.base.Predicate;
import com.teamacronymcoders.base.util.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks.BlockHeavyOreGenerator;

public class TileEntityHeavyOreGenerator extends TileEntity implements ITickable {

	EnumFacing facing;
	int maxL, maxH, maxW;
	
	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}
		if (world.isAreaLoaded(this.getPos(), 16 * 6, false)
				&& world.getChunkProvider().isChunkGeneratedAt(Utils.getChunkXFromBlock(getPos()),
						Utils.getChunkZFromBlock(getPos()))
				&& world.getChunkProvider().isChunkGeneratedAt(Utils.getChunkXFromBlock(getPos()) + 3,
						Utils.getChunkZFromBlock(getPos()) + 3) && world.getChunkProvider().isChunkGeneratedAt(Utils.getChunkXFromBlock(getPos()) - 3, Utils.getChunkZFromBlock(getPos()) - 3)) {
			if (facing == null) {
				facing = EnumFacing.byHorizontalIndex(world.rand.nextInt(3));
				maxL = 10 + world.rand.nextInt(10);
				maxH = 4 + world.rand.nextInt(2);
				maxW = 3 + world.rand.nextInt(3);
			}
			BlockHeavyOreGenerator sourceBlock = (BlockHeavyOreGenerator) this.getWorld().getBlockState(getPos())
					.getBlock();
			String type = sourceBlock.getType();
			Block block = Block.getBlockFromName(SteamAgeRevolution.MODID + ":heavy_ore_" + type);
			if (block != null) {
				for (int length = 1; length < maxL; length++) {
					for (int height = 0; height < maxH; height++) {
						for (int width = 0; width < maxW; width++) {
							if (length == 1 || length == maxL - 1) {
								// Cut out horizontal corners
								if (height == maxH - 1 || height == 0) {
									continue;
								}
								// Make ends 'pointy'
								if (width == 0 || width == maxW - 1) {
									continue;
								}
							}
							BlockPos pos = getPos().up(height).offset(facing.rotateY(), width).offset(facing, length);
							if (world.rand.nextInt(5) == 0) {
								pos = pos.add(world.rand.nextInt(3), world.rand.nextInt(3), world.rand.nextInt(3));
							}
							if (new StonePredicate().apply(world.getBlockState(pos)) && pos.getY() > 0) {
								world.setBlockState(pos, block.getDefaultState());
							}
						}
					}
				}
				world.setBlockState(getPos(), Blocks.STONE.getDefaultState());
			} else {
				world.setBlockState(getPos(), Blocks.STONE.getDefaultState());
			}
		}
	}

	static class StonePredicate implements Predicate<IBlockState> {
		private StonePredicate() {
		}

		public boolean apply(IBlockState toTest) {
			if (toTest != null) {
				Block block = toTest.getBlock();
				if (block == Blocks.BEDROCK) {
					return true;
				} else if (block == Blocks.STONE) {
					BlockStone.EnumType blockstone$enumtype = (BlockStone.EnumType) toTest.getValue(BlockStone.VARIANT);
					return blockstone$enumtype.isNatural();
				}
			}
			return false;
		}
	}

}
