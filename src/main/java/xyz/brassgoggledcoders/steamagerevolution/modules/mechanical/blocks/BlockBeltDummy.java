package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class BlockBeltDummy extends BlockBase {

	public BlockBeltDummy(Material mat, String name) {
		super(mat, name);
		this.setBlockUnbreakable();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		entityIn.attackEntityFrom(SteamAgeRevolution.belt, 3.0F + worldIn.rand.nextInt(3));
		entityIn.addVelocity(worldIn.rand.nextDouble() * 2, worldIn.rand.nextDouble() * 2,
				worldIn.rand.nextDouble() * 2);
	}

}
