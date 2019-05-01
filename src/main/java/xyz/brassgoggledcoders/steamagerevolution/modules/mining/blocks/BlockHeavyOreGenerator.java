package xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.blocks.IHasTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities.TileEntityHeavyOreGenerator;

public class BlockHeavyOreGenerator extends Block implements IHasTileEntity {

	String type;
	
	public BlockHeavyOreGenerator(String type) {
		super(Material.ROCK);
		this.setTranslationKey("heavy_ore_generator_" + type);
		this.type = type;
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityHeavyOreGenerator.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityHeavyOreGenerator();
	}
	
	public String getType() {
		return type;
	}
	
	@Override
	public String getTileName(ResourceLocation blockName) {
        return "heavy_ore_generator";
	}
	

    @Override
    public boolean hasTileEntity(IBlockState blockState) {
        return true;
    }
    
    @Override
    public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        super.breakBlock(world, pos, state);
        world.removeTileEntity(pos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
        super.eventReceived(state, world, pos, id, param);
        TileEntity tileentity = world.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }


}
