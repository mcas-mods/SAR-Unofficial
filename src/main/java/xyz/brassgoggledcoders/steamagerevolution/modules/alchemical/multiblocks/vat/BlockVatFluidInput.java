package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockGUIBase;

public class BlockVatFluidInput extends BlockGUIBase<TileEntityVatFluidInput> {

	public BlockVatFluidInput(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityVatFluidInput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityVatFluidInput();
	}

}