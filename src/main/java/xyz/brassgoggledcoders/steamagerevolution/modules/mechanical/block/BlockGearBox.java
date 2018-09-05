package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.block;

import com.teamacronymcoders.base.blocks.BlockSidedBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentity.TileEntityGearBox;

import javax.annotation.Nonnull;

public class BlockGearBox extends BlockSidedBase<TileEntityGearBox> {
    protected BlockGearBox() {
        super(Material.IRON, "gearbox");
    }

    @Nonnull
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState blockState) {
        return new TileEntityGearBox();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityGearBox.class;
    }
}
