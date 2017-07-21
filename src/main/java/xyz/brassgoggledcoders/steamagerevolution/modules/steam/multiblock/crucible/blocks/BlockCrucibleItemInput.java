package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.crucible.blocks;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.crucible.tileentities.TileEntityCrucibleCasing;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.crucible.tileentities.TileEntityCrucibleItemInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.crucible.tileentities.TileEntityCrucibleItemInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.crucible.tileentities.TileEntityCrucibleItemInput;

public class BlockCrucibleItemInput extends BlockTEBase<TileEntityCrucibleItemInput> {

	public BlockCrucibleItemInput(Material material, String name) {
		super(material, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityCrucibleItemInput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityCrucibleItemInput();
	}

}
