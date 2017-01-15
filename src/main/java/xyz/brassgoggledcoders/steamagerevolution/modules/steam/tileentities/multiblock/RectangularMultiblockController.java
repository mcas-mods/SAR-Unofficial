package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class RectangularMultiblockController extends RectangularMultiblockControllerBase {

	protected Set<ITickableMultiblockPart> attachedTickables;
	protected ArrayList<TileEntity> attachedGUIs;

	protected RectangularMultiblockController(World world) {
		super(world);
		attachedTickables = new HashSet<ITickableMultiblockPart>();
		attachedGUIs = new ArrayList<TileEntity>();
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		this.readFromDisk(data);
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		if(newPart instanceof ITickableMultiblockPart) {
			attachedTickables.add((ITickableMultiblockPart) newPart);
		}

		if(newPart instanceof IHasGui) {
			attachedGUIs.add((TileEntity) newPart);
		}
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		if(oldPart instanceof ITickableMultiblockPart) {
			attachedTickables.remove(oldPart);
		}

		if(oldPart instanceof IHasGui) {
			attachedGUIs.remove(oldPart);
		}
	}

	@Override
	protected void onMachineAssembled() {}

	@Override
	protected void onMachineRestored() {}

	@Override
	protected void onMachinePaused() {}

	@Override
	protected void onMachineDisassembled() {}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {
		this.attachedTickables.clear();
		this.attachedGUIs.clear();
	}

	@Override
	protected boolean updateServer() {
		boolean flag = false;

		int i = 0;

		for(ITickableMultiblockPart tickable : attachedTickables) {
			if(tickable.tick(this))
				i++;
		}

		// If any tickable has changed, the whole multiblock has changed, thus flag for update.
		if(i > 0) {
			flag = true;
		}

		return flag;
	}

	@Override
	protected void updateClient() {}

	@Override
	protected void onAssimilate(MultiblockControllerBase assimilated) {}

	@Override
	protected boolean isBlockGoodForFrame(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	protected boolean isBlockGoodForTop(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	protected boolean isBlockGoodForBottom(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	protected boolean isBlockGoodForSides(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public void readFromDisk(NBTTagCompound data) {}

	@Override
	public void writeToDisk(NBTTagCompound data) {}

	@Override
	public void readFromUpdatePacket(NBTTagCompound data) {}

	@Override
	public void writeToUpdatePacket(NBTTagCompound data) {}

	public ArrayList<TileEntity> getAttachedGUIs() {
		return attachedGUIs;
	}
}
