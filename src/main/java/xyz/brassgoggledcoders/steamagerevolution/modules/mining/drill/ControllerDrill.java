package xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine.InventoryPieceFluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine.InventoryPieceItem;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerDrill extends SARMultiblockInventory {

	// private static final GameProfile profile = new GameProfile(null, "[" +
	// SteamAgeRevolution.MODNAME + "]");

	ArrayList<BlockPos> positions = Lists.newArrayList();
	int currentPosition = 0;

	protected ControllerDrill(World world) {
		super(world);
		// TODO Util methods for positioning x/y grids of slots would be handy
		int xOffset = 84;
		int yOffset = -1;
		int slotGap = 2;
		this.setInventory(new InventoryMachine(new InventoryPieceItem(new ItemStackHandlerSmart(1, this), 40, 32), null,
				new InventoryPieceItem(new ItemStackHandlerForceStack(9, this),
						new int[] { xOffset + 16, xOffset + 32 + slotGap, xOffset + 48 + slotGap * 2,
								xOffset + 16, xOffset + 32 + slotGap, xOffset + 48 + slotGap * 2,
								xOffset + 16, xOffset + 32 + slotGap, xOffset + 48 + slotGap * 2},
						new int[] { yOffset + 16, yOffset + 16, yOffset + 16,
								yOffset + 32 + slotGap, yOffset + 32 + slotGap, yOffset + 32 + slotGap,
								yOffset + 48 + slotGap * 2, yOffset + 48 + slotGap * 2, yOffset + 48 + slotGap * 2}),
				null,
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
		// WeakReference<FakePlayer> fakePlayer = new
		// WeakReference<FakePlayer>(FakePlayerFactory.get((WorldServer) WORLD,
		// profile));
		if (this.getCurrentProgress() >= 20) {
			if (currentPosition < positions.size()) {
				BlockPos position = positions.get(currentPosition);
				// FMLLog.warning(WORLD.getBlockState(position).getBlock().getLocalizedName());
				if (!WORLD.isAirBlock(position)) {
					NonNullList<ItemStack> drops = NonNullList.create();
					WORLD.getBlockState(position).getBlock().getDrops(drops, WORLD, position, WORLD.getBlockState(position), 0);
					for(ItemStack drop : drops) {
						ItemHandlerHelper.insertItemStacked(this.getInventory().getOutputHandler(), drop, false);
					}
					//WORLD.getBlockState(position).getBlock().onPlayerDestroy(WORLD, position, WORLD.getBlockState(position));
					WORLD.destroyBlock(position, false);
					SteamAgeRevolution.instance.getLogger().devInfo("Mining " + position.toString());
				}
				currentPosition++;
			} else {
				currentPosition = 0;
			}
			this.currentTicks = 0;
		} else {
			this.currentTicks++;
		}
		return false;
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
