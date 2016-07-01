package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockTEBase;

public class BlockGearbox extends BlockTEBase
{
	public BlockGearbox(Material mat, String name)
	{
		super(mat, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileGearbox.class;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileGearbox();
	}

}
