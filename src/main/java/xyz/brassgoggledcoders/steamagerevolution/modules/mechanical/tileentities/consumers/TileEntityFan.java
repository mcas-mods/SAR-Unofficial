package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.consumers;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.FMLLog;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.consumers.BlockFan;

public class TileEntityFan extends TileEntitySpinConsumer {

	public TileEntityFan() {
		super(5, 50);
	}

	@Override
	public void tickAtWorkSpeed() {
		int velocityAddition = this.handler.getSpeed() / 4;
		EnumFacing facing = this.getWorld().getBlockState(getPos()).getValue(BlockFan.FACING);
		List<EntityItem> items = this.getWorld().getEntitiesWithinAABB(EntityItem.class, BlockFan.FULL_BLOCK_AABB
				.expand(facing.getFrontOffsetX() * 3, facing.getFrontOffsetY() * 3, facing.getFrontOffsetZ() * 3));
		for(EntityItem item : items) {
			item.addVelocity(Math.abs(facing.getFrontOffsetX()) * velocityAddition,
					Math.abs(facing.getFrontOffsetY()) * velocityAddition,
					Math.abs(facing.getFrontOffsetZ()) * velocityAddition);
		}
	}

	@Override
	public void tickAtDangerSpeed() {
		if(this.handler.getSpeed() > 100) {
			this.getWorld().createExplosion(null, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(),
					1.0F, false);
		}
		else {
			int velocityAddition = this.handler.getSpeed() / 4;
			EnumFacing facing = this.getWorld().getBlockState(getPos()).getValue(BlockFan.FACING);
			AxisAlignedBB box = new AxisAlignedBB(this.getPos());
			List<Entity> entities = this.getWorld().getEntitiesWithinAABB(Entity.class, box
					.expand(facing.getFrontOffsetX() * 3, facing.getFrontOffsetY() * 3, facing.getFrontOffsetZ() * 3));
			FMLLog.warning(entities.toString());
			for(Entity entity : entities) {
				entity.addVelocity(Math.abs(facing.getFrontOffsetX()) * velocityAddition,
						Math.abs(facing.getFrontOffsetY()) * velocityAddition,
						Math.abs(facing.getFrontOffsetZ()) * velocityAddition);
			}
		}
	}

}
