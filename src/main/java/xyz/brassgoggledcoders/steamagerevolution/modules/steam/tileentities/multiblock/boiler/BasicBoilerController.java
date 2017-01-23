package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.boiler;

import java.util.HashSet;
import java.util.Set;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.RectangularMultiblockController;

public class BasicBoilerController extends RectangularMultiblockController {

	// Values for parts to use
	public static int fluidTransferRate = 10; // mB per tick

	// Lists of connected parts
	private Set<TileEntityWaterInput> attachedInputs;
	private Set<TileEntityWaterTank> attachedWaterTanks;
	private Set<TileEntitySteamOutput> attachedOutputs;
	private Set<TileEntitySteamTank> attachedSteamTanks;
	private Set<TileEntitySolidFirebox> attachedFireboxes;

	protected BasicBoilerController(World world) {
		super(world);
		attachedInputs = new HashSet<TileEntityWaterInput>();
		attachedWaterTanks = new HashSet<TileEntityWaterTank>();
		attachedOutputs = new HashSet<TileEntitySteamOutput>();
		attachedSteamTanks = new HashSet<TileEntitySteamTank>();
		attachedFireboxes = new HashSet<TileEntitySolidFirebox>();
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		// FMLLog.warning("Part added " + newPart.toString());
		if(newPart instanceof TileEntityWaterInput) {
			attachedInputs.add((TileEntityWaterInput) newPart);
		}
		else if(newPart instanceof TileEntityWaterTank) {
			attachedWaterTanks.add((TileEntityWaterTank) newPart);
		}
		else if(newPart instanceof TileEntitySteamOutput) {
			attachedOutputs.add((TileEntitySteamOutput) newPart);
		}
		else if(newPart instanceof TileEntitySteamTank) {
			attachedSteamTanks.add((TileEntitySteamTank) newPart);
		}
		// TODO Not only solid
		else if(newPart instanceof TileEntitySolidFirebox) {
			attachedFireboxes.add((TileEntitySolidFirebox) newPart);
		}
		super.onBlockAdded(newPart);
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		if(oldPart instanceof TileEntityWaterInput) {
			attachedInputs.remove(oldPart);
		}
		else if(oldPart instanceof TileEntityWaterTank) {
			attachedWaterTanks.remove(oldPart);
		}
		else if(oldPart instanceof TileEntitySteamOutput) {
			attachedOutputs.remove(oldPart);
		}
		else if(oldPart instanceof TileEntitySteamTank) {
			attachedSteamTanks.remove(oldPart);
		}
		else if(oldPart instanceof TileEntitySolidFirebox) {
			attachedFireboxes.remove(oldPart);
		}
		super.onBlockRemoved(oldPart);
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 26;
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

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {
		this.attachedInputs.clear();
		this.attachedWaterTanks.clear();
		this.attachedFireboxes.clear();
		this.attachedOutputs.clear();
		this.attachedSteamTanks.clear();
		super.onAssimilated(assimilator);
	}

	public Set<TileEntityWaterTank> getAttachedWaterTanks() {
		return attachedWaterTanks;
	}

	public Set<TileEntitySteamTank> getAttachedSteamTanks() {
		return attachedSteamTanks;
	}

	@Override
	protected boolean isBlockGoodForFrame(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		FMLLog.warning("Checking for frame at" + new BlockPos(x, y, z).toString());
		if(world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.NETHER_BRICK)
			return true;
		else
			return false;
	}

	@Override
	protected boolean isBlockGoodForSides(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		if(world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.NETHER_BRICK)
			return true;
		else
			return false;
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		if(world.isAirBlock(new BlockPos(x, y, z)))
			return true;
		else
			return false;
	}

}
