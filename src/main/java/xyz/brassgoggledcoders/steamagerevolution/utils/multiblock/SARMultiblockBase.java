package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.ArrayList;
import java.util.List;

import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.multiblock.validation.ValidationError;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public abstract class SARMultiblockBase extends RectangularMultiblockControllerBase implements ISARMultiblock, IHasGui {

	List<Block> requiredBlocks = new ArrayList<Block>();

	protected SARMultiblockBase(World world, Block... requiredBlocks) {
		super(world);
		for(Block required : requiredBlocks) {
			this.requiredBlocks.add(required);
		}
	}

	@Override
	protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
		// TODO
		ArrayList<Block> blocks = new ArrayList<Block>();
		connectedParts.forEach(part -> blocks.add(WORLD.getBlockState(part.getWorldPosition()).getBlock()));

		for(Block required : requiredBlocks) {
			if(!blocks.contains(required)) {
				validatorCallback.setLastError(new ValidationError(
						"steamagerevolution.multiblock.validation.missingrequired", required.getLocalizedName()));
				return false;
			}
		}

		return super.isMachineWhole(validatorCallback);
	}

	@Override
	protected void onMachineAssembled() {
		SteamAgeRevolution.instance.getLogger().devInfo("Machine Assembled");
		SteamAgeRevolution.proxy.spawnMultiblockAssemblyFX(getMinimumCoord(), getMaximumCoord());
	}

	// Modify from protected to public
	@Override
	public int getMinimumXSize() {
		return 0;
	}

	@Override
	public int getMinimumYSize() {
		return 0;
	}

	@Override
	public int getMinimumZSize() {
		return 0;
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		// NO-OP
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		// NO-OP
	}

	@Override
	protected void onMachineRestored() {
		// NO-OP
	}

	@Override
	protected void onMachinePaused() {
		// NO-OP
	}

	@Override
	protected void onMachineDisassembled() {
		// NO-OP
	}

	@Override
	protected void onAssimilate(MultiblockControllerBase assimilated) {

	}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {

	}

	@Override
	protected void updateClient() {

	}

	@Override
	protected boolean isBlockGoodForFrame(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	protected boolean isBlockGoodForTop(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	protected boolean isBlockGoodForBottom(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	protected boolean isBlockGoodForSides(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return world.isAirBlock(new BlockPos(x, y, z));
	}

	@Override
	public void readFromDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public World getMachineWorld() {
		return WORLD;
	}

	@Override
	public BlockPos getMachinePos() {
		return getReferenceCoord();
	}

}
