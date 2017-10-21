package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.List;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.blocks.BlockTEBase;
import com.teamacronymcoders.base.multiblock.MultiblockTileEntityBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMultiblockBase<C extends SARRectangularMultiblockControllerBase>
		extends BlockTEBase<TileEntityMultiblockBase<C>> {

	private Function<World, TileEntityMultiblockBase<C>> tileEntityCreator;
	private Class<TileEntityMultiblockBase<C>> tileClass;

	public BlockMultiblockBase(Function<World, TileEntityMultiblockBase<C>> tileEntityCreator, Material material,
			String name) {
		this(TileEntityMultiblockBase.class, tileEntityCreator, material, name);
	}

	public BlockMultiblockBase(Class tileClass, Function<World, TileEntityMultiblockBase<C>> tileEntityCreator,
			Material material, String name) {
		super(material, name);
		this.tileEntityCreator = tileEntityCreator;
		this.tileClass = tileClass;
	}

	@Override
	public Class<TileEntityMultiblockBase<C>> getTileEntityClass() {
		return tileClass;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return tileEntityCreator.apply(world);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(playerIn.isSneaking()) {
			MultiblockTileEntityBase<C> tile = getTileEntity(worldIn, pos);
			if(tile.isConnected()) {
				if(tile.getMultiblockController().getLastError() != null) {
					playerIn.sendStatusMessage(tile.getMultiblockController().getLastError().getChatMessage(), true);
				}
				else {
					playerIn.sendStatusMessage(new TextComponentString("Multiblock is assembled"), true);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		IMultiblockTileInfo tile = (IMultiblockTileInfo) this.createTileEntity(player, null);
		IMultiblockControllerInfo controller = tile.getControllerInfo();

		// TODO Localisation
		if(controller != null) {
			tooltip.add("Multiblock: " + controller.getName());
			if(controller.getMinimumYSize() > 1) {
				tooltip.add("Minimum Size (XYZ): " + controller.getMinimumXSize() + "x" + controller.getMinimumYSize()
						+ "x" + controller.getMinimumZSize());
			}
			if(controller.getMaximumXSize() != -1) { // TODO
				tooltip.add("Maximum Size (XYZ): " + controller.getMaximumXSize() + "x" + controller.getMaximumYSize()
						+ "x" + controller.getMaximumZSize());
			}
			if(tile.getPartFunction() != null) {
				tooltip.add("Part function: " + tile.getPartFunction());
			}
			String[] positions = new String[] {"Frame", "Sides", "Top", "Bottom", "Interior"};
			String valid = "Valid part positions: ";
			for(int possiblePositions = 0; possiblePositions < 5; possiblePositions++) {
				if(tile.getValidPositions()[possiblePositions])
					valid += positions[possiblePositions] + ",";
			}
			tooltip.add(valid.substring(0, valid.length() - 1));
		}
		super.addInformation(stack, player, tooltip, advanced);
	}
}
