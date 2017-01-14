package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.consumers;

import java.util.Iterator;

import net.minecraft.util.math.BlockPos;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.BlockBeltDummy;

public class TileEntitySaw extends TileEntitySpinConsumer {

	public TileEntitySaw() {
		super(50, 101);
	}

	@Override
	public void onSpeedChanged(int lastSpeed, int newSpeed) {
		boolean flag = newSpeed > 0;
		BlockPos from = this.getPos();
		BlockPos to = this.getPos();
		Iterator<BlockPos> positions = BlockPos.getAllInBox(from, to).iterator();
		while(positions.hasNext()) {
			BlockPos pos = positions.next();
			if(pos.equals(from) || pos.equals(to))
				continue;
			getWorld().setBlockState(pos, getWorld().getBlockState(pos).getActualState(getWorld(), getPos())
					.withProperty(BlockBeltDummy.SPINNING, flag), 3);
		}
		super.onSpeedChanged(lastSpeed, newSpeed);
	}

	@Override
	public void tickAtWorkSpeed() {}

	@Override
	public void tickAtDangerSpeed() {
		// TODO Shrapnel
		this.getWorld().createExplosion(null, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), 3.0F,
				false);
	}

}
