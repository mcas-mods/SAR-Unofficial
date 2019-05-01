package xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailBase.EnumRailDirection;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemMinecart;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidHolder;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.MiningUtils;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.ModuleMining;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities.GuiSemisolid;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities.InventoryPieceSemisolid;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities.InventorySemisolid;

public class EntityMinecartDrilling extends EntityMinecartInventory<InventorySemisolid> {

	public EntityMinecartDrilling(World world) {
		super(world);
		this.setInventory(new InventorySemisolid(
				new InventoryPieceSemisolid(new SemisolidHandlerCart(this, new SemisolidHolder(15)), 83, 16)));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiSemisolid(entityPlayer, this, "crushed_single");
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return null;
	}

	@Override
	public ItemMinecart getItem() {
		return ModuleMining.minecart_drilling;
	}

	@Override
	public void onActivatorRailPass(int blockX, int blockY, int blockZ, boolean receivingPower) {
		if (receivingPower) {
			BlockPos pos = new BlockPos(blockX, blockY, blockZ);
			if (BlockRailBase.isRailBlock(this.world, pos)) {
				BlockRailBase rail = (BlockRailBase) this.getEntityWorld().getBlockState(pos).getBlock();
				EnumRailDirection direction = rail.getRailDirection(getEntityWorld(), pos,
						this.getEntityWorld().getBlockState(pos), this);
				EnumFacing cartFacing = this.getAdjustedHorizontalFacing();
				switch (direction) {
				case NORTH_SOUTH:
					if (EnumFacing.NORTH.equals(cartFacing)) {
						this.doMining(EnumFacing.WEST);
					} else {
						this.doMining(EnumFacing.EAST);
					}
					break;
				case EAST_WEST:
					if (EnumFacing.WEST.equals(cartFacing)) {
						this.doMining(EnumFacing.NORTH);
					} else {
						this.doMining(EnumFacing.SOUTH);
					}
					break;
				default:
					break;
				}
			}
		}
	}

	private void doMining(EnumFacing facingToMine) {
		// for(int i = 0; i < 3; i++) {
		BlockPos target = this.getPosition().offset(facingToMine);
		if (!world.isAirBlock(target)) {
			MiningUtils.doMining(this.getEntityWorld(), target, this.getInventory().getOutputHandler(),
					this.getInventory().ore.getHandler());
		}
		// }
	}

}
