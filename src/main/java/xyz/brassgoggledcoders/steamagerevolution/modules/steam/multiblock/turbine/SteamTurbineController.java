package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.turbine;

import java.util.HashSet;
import java.util.Set;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;

import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.RectangularMultiblockController;

public class SteamTurbineController extends RectangularMultiblockController {

	// Values for parts to use
	public static int fluidTransferRate = 20; // mB per tick

	private Set<TileEntityMechanicalOutput> attachedOutputs;
	private Set<TileEntitySteamInput> attachedInputs;

	public SteamTurbineController(World world) {
		super(world);
		attachedOutputs = new HashSet<TileEntityMechanicalOutput>();
		attachedInputs = new HashSet<TileEntitySteamInput>();
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		if(newPart instanceof TileEntitySteamInput) {
			attachedInputs.add((TileEntitySteamInput) newPart);
		}
		else if(newPart instanceof TileEntityMechanicalOutput) {
			attachedOutputs.add((TileEntityMechanicalOutput) newPart);
		}
		super.onBlockAdded(newPart);
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		if(oldPart instanceof TileEntitySteamInput) {
			attachedInputs.remove(oldPart);
		}
		else if(oldPart instanceof TileEntityMechanicalOutput) {
			attachedOutputs.remove(oldPart);
		}
		super.onBlockRemoved(oldPart);
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 3;
	}

	@Override
	protected int getMaximumXSize() {
		return 3;
	}

	@Override
	protected int getMaximumZSize() {
		return 3;
	}

	@Override
	protected int getMaximumYSize() {
		return 3;
	}

	public Set<TileEntityMechanicalOutput> getOutputs() {
		return attachedOutputs;
	}

	public Set<TileEntitySteamInput> getInputs() {
		return attachedInputs;
	}
}
