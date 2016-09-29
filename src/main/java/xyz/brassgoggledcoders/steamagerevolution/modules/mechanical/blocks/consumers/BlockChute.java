package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.consumers;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockMechanicalTEBase;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.consumers.TileEntityChute;

public class BlockChute extends BlockMechanicalTEBase<TileEntityChute> {

	public BlockChute(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityChute.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityChute();
	}

}
