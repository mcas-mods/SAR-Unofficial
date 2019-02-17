package xyz.brassgoggledcoders.steamagerevolution.utils;

import com.teamacronymcoders.base.blocks.BlockFluidBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class BlockDamagingFluid extends BlockFluidBase {

	String name;
	DamageSource source;
	int damage;

	public BlockDamagingFluid(String name, Fluid fluid, Material material, DamageSource source, int damage) {
		super(name, fluid, material);
		this.name = name;
		this.source = source;
		this.damage = damage;
	}

	@Override
	public ResourceLocation getResourceLocation(IBlockState blockState) {
		return new ResourceLocation(SteamAgeRevolution.MODID, name);
	}

	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		entityIn.attackEntityFrom(source, damage);
	}
}
