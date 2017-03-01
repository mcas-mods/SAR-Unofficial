package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.BlockGUIBase;

public class BlockFurnaceMonitor extends BlockGUIBase<TileEntityFurnaceMonitor> {

	public BlockFurnaceMonitor(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityFurnaceMonitor.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityFurnaceMonitor();
	}

}