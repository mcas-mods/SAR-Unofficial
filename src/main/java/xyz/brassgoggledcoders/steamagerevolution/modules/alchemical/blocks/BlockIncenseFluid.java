package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.blocks;

import java.util.Random;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.blocks.BlockFluidBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.ModuleAlchemical;

public class BlockIncenseFluid extends BlockFluidBase {

	public BlockIncenseFluid(String name, Fluid fluid, Material material) {
		super(name, fluid, material);
	}

	@Override
	public void updateTick(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state,
			@Nonnull Random rand) {
		if(rand.nextBoolean()) {
			world.setBlockState(pos, ModuleAlchemical.incense_block.getDefaultState());
		}
		super.updateTick(world, pos, state, rand);
	}

}
