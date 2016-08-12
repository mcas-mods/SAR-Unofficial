package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.ModuleMechanical;

public class TileEntityDropHammer extends TileEntitySpinConsumer {

	private int progress = 0;

	public int getProgress() {
		return progress;
	}

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		if(this.handler.getSpeed() > 50) {
			if(progress < 10) {
				this.progress++;
			}
			else {
				progress = 0;
				drop();
			}
		}

		// TODO Anvil and dies

		super.updateTile();
	}

	private void drop() {
		SteamAgeRevolution.instance.getLogger().devInfo("Dropping");
		BlockPos target = getPos().down(2);

		// TODO Metadata and ore dictionary handling
		if(!getWorld().isAirBlock(target)) {
			Block target_block = getWorld().getBlockState(target).getBlock();
			if(target_block == ModuleMechanical.drop_hammer_anvil) {
				TileEntityDropHammerAnvil anvil = (TileEntityDropHammerAnvil) getWorld().getTileEntity(target);
				if(anvil.handler.getStackInSlot(0) != null) {
					ItemStack result = DropHammerRecipes.instance().getResult(anvil.handler.getStackInSlot(0));
					if(anvil.handler.insertItem(1, result, true) == null) {
						anvil.handler.extractItem(0, 1, false);
						anvil.handler.insertItem(1, result, false);
					}
				}
			}
			else {
				ItemStack result = DropHammerRecipes.instance().getResult(new ItemStack(target_block));
				if(result != null) {
					getWorld().setBlockState(target, Block.getBlockFromItem(result.getItem()).getDefaultState());
				}
			}
		}
	}

	@Override
	public void readFromNBTCustom(NBTTagCompound compound) {
		this.progress = compound.getInteger("progress");
	}

	@Override
	public NBTTagCompound writeToNBTCustom(NBTTagCompound compound) {
		compound.setInteger("progress", progress);
		return compound;
	}
}
