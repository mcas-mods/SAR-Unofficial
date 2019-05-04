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
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.MiningUtils;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.ModuleMining;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.ContainerInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.HandlerForceStack;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryRecipeMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceItem;

public class EntityMinecartDrilling extends EntityMinecartInventory<InventoryRecipeMachine> {

	BlockPos miningLocation;
	
	public EntityMinecartDrilling(World world) {
		super(world);
		this.setInventory(new InventoryRecipeMachine(new InventoryPieceItem(new HandlerForceStack(3), new int[] {0,0,0}, new int[] {0,0,0}), null, null, null, null));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiInventory(entityPlayer, this);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new ContainerInventory(entityPlayer, this);
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

	//TODO Blockbreak animation
	private void doMining(EnumFacing facingToMine) {
		BlockPos target = this.getPosition().offset(facingToMine);
		if (!world.isAirBlock(target)) {
			if(getCurrentProgress() >= (world.getBlockState(target).getBlockHardness(world, target) * 100)) {
				MiningUtils.doMining(this.getEntityWorld(), target, this.getInventory().getInputHandler());
				miningLocation = null;
			}
			else {
				miningLocation = target;
				this.setCurrentTicks(this.getCurrentProgress() + 1);
			}
		}
	}

}
