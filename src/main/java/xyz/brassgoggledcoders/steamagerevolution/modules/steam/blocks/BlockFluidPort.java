package xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockTEBase;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.TileEntityFluidPort;

public class BlockFluidPort extends BlockTEBase {

	public BlockFluidPort(Material material, String name) {
		super(material, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityFluidPort.class;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFluidPort();
	}

}
