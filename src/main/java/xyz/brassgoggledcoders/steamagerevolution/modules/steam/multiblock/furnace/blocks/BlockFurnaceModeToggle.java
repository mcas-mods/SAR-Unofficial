package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.blocks;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.ControllerSteamFurnace;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.furnace.tileentities.TileEntityFurnaceModeToggle;
import xyz.brassgoggledcoders.steamagerevolution.utils.BlockMultiblockBase;

public class BlockFurnaceModeToggle extends BlockMultiblockBase<TileEntityFurnaceModeToggle> {

	public BlockFurnaceModeToggle(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityFurnaceModeToggle.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityFurnaceModeToggle();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityFurnaceModeToggle te = getTileEntity(worldIn, pos);
		if(te != null && te.isConnected() && te.getMultiblockController().isAssembled()) {
			ControllerSteamFurnace.toggleMode(te.getMultiblockController());
			playerIn.sendStatusMessage(new TextComponentString("Changing heating mode to: "
					+ StringUtils.capitalize(String.valueOf(te.getMultiblockController().isHeating()))), true);
			return true;
		}
		return false;
	}
}