package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.function.Function;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class TileEntityMultiblockBase<T extends SARRectangularMultiblockControllerBase>
		extends RectangularMultiblockTileEntityBase<T> implements IMultiblockTileInfo {

	private Class<T> controllerClass;
	private Function<World, SARRectangularMultiblockControllerBase> controllerCreator;
	private boolean[] validPositions;

	public TileEntityMultiblockBase() {}

	public TileEntityMultiblockBase(boolean[] validPositions, Class<T> controllerClass,
			Function<World, SARRectangularMultiblockControllerBase> controllerCreator) {
		this.validPositions = validPositions;
		this.controllerClass = controllerClass;
		this.controllerCreator = controllerCreator;
	}

	// FIXME Hackity hack...
	@Override
	public void onLoad() {
		if(((TileEntityMultiblockBase) getWorld().getTileEntity(getPos())).controllerClass == null) {
			getWorld().setTileEntity(getPos(),
					((BlockMultiblockBase) this.getWorld().getBlockState(getPos()).getBlock())
							.createTileEntity(getWorld(), this.getWorld().getBlockState(getPos())));
		}
	}

	@Override
	public Class<T> getMultiblockControllerType() {
		return controllerClass;
	}

	@Override
	public IMultiblockControllerInfo getControllerInfo() {
		return (IMultiblockControllerInfo) controllerCreator.apply(Minecraft.getMinecraft().world);
	}

	@Override
	public boolean[] getValidPositions() {
		return validPositions;
	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return validPositions[0];
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return validPositions[1];
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		return validPositions[2];
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return validPositions[3];
	}

	@Override
	public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {
		return validPositions[4];
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return controllerCreator.apply(getWorld());
	}

	@Override
	public void onMachineActivated() {}

	@Override
	public void onMachineDeactivated() {}

}
