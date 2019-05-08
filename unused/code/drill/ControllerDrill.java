package xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill;

import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.MiningUtils;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceFluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceItem;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryRecipeMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerDrill extends SARMultiblockInventory<InventoryRecipeMachine> {

	private static final String name = "[" + SteamAgeRevolution.MODNAME + "]";
	public static final GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(name.getBytes()), name);

	ArrayList<BlockPos> positions = Lists.newArrayList();
	int currentPosition = 0;

	protected ControllerDrill(World world) {
		super(world);
		Pair<int[], int[]> pos = MiningUtils.getGUIPositionGrid(49, 10, 3, 3);
		this.setInventory(new InventoryRecipeMachine(new InventoryPieceItem(new ItemStackHandlerSmart(1, this), 40, 32), null,
				new InventoryPieceItem(new ItemStackHandlerSmart(9, this), pos.getLeft(), pos.getRight()), null,
				new InventoryPieceFluid(new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "steam", this), 13, 9)));
	}

	@Override
	protected void onMachineAssembled() {
		Pair<BlockPos, BlockPos> interiorPositions = com.teamacronymcoders.base.util.PositionUtils.shrinkPositionCubeBy(
				getMinimumCoord(),
				new BlockPos(getMaximumCoord().getX(), getMinimumCoord().getY(), getMaximumCoord().getZ()), 1);
		BlockPos minimumInteriorPosXZ = interiorPositions.getLeft();
		BlockPos maximumInteriorPosXZ = interiorPositions.getRight();
		BlockPos.getAllInBox(minimumInteriorPosXZ.down(2), maximumInteriorPosXZ.down(8))
				.forEach(pos -> positions.add(pos));
		super.onMachineAssembled();
	}

	@Override
	protected boolean updateServer() {
		if (this.getCurrentProgress() >= 20) {
			if (currentPosition < positions.size()) {
				BlockPos pos = positions.get(currentPosition);
				MiningUtils.doMining(WORLD, pos, this.getInventory().getOutputHandler());
				currentPosition++;
			} else {
				currentPosition = 0;
			}
			this.currentTicks = 0;
		} else {
			this.currentTicks++;
		}
		return true;
	}

	@Override
	public String getName() {
		return "Drill";
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		// TODO
		return 0;
	}

	@Override
	public int getMaximumXSize() {
		return 16;
	}

	@Override
	public int getMaximumZSize() {
		return 16;
	}

	@Override
	public int getMaximumYSize() {
		return 1;
	}

	@Override
	public int getMinimumYSize() {
		return 1;
	}

	@Override
	public int getMinimumXSize() {
		return 4;
	}

	@Override
	public int getMinimumZSize() {
		return 4;
	}

}
