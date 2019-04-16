package xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill;

import java.lang.ref.WeakReference;

import org.apache.commons.lang3.tuple.Pair;

import com.mojang.authlib.GameProfile;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine.InventoryPieceFluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine.InventoryPieceItem;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerDrill extends SARMultiblockInventory {

	private static final GameProfile profile = new GameProfile(null, "[" + SteamAgeRevolution.MODNAME + "]");
	
	public BlockPos minimumInteriorPos;
	public BlockPos maximumInteriorPos;
	
	protected ControllerDrill(World world) {
		super(world);
		this.setInventory(new InventoryMachine(new InventoryPieceItem(new ItemStackHandlerSmart(1, this), 0, 0), null, new InventoryPieceItem(new ItemStackHandlerSmart(9, this), new int[] {0}, new int[] {0}), null, new InventoryPieceFluid(new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 16, "steam", this), 13, 9)));
		
	}
	
	@Override
	protected void onMachineAssembled() {
		Pair<BlockPos, BlockPos> interiorPositions = com.teamacronymcoders.base.util.PositionUtils
				.shrinkPositionCubeBy(getMinimumCoord(), getMaximumCoord(), 1);
		minimumInteriorPos = interiorPositions.getLeft();
		maximumInteriorPos = interiorPositions.getRight();
		super.onMachineAssembled();
	}
	
	@Override
	protected boolean updateServer() {
		WeakReference<FakePlayer> fakePlayer = new WeakReference<FakePlayer>(FakePlayerFactory.get((WorldServer) WORLD, profile));
		for(BlockPos pos : BlockPos.getAllInBoxMutable(minimumInteriorPos, maximumInteriorPos)) {
			WORLD.getBlockState(pos).getBlock().removedByPlayer(WORLD.getBlockState(pos), WORLD, pos, fakePlayer.get(), fakePlayer.get().canHarvestBlock(WORLD.getBlockState(pos)));
		}
		return false;
	}

	@Override
	public String getName() {
		return "Drill";
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		//TODO
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
		return 16;
	}
	
	@Override
	public int getMinimumYSize() {
		return 4;
	}
	
	@Override
	public int getMinimumXSize() {
		return 4;
	}
	
	@Override
	public int getMinimumZSize() {
		return 4;
	}
	
	@Override
	protected boolean isBlockGoodForSides(World world, int x, int y, int z,
			IMultiblockValidator validatorCallback) {
		Material mat = world.getBlockState(new BlockPos(x,y,z)).getMaterial();
		return mat == Material.ROCK || mat == Material.GROUND || mat == Material.GRASS || mat == Material.SAND || world.isAirBlock(new BlockPos(x,y,z));
	}
	
	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z,
			IMultiblockValidator validatorCallback) {
		return this.isBlockGoodForSides(world, x, y, z, validatorCallback);
	}
	
	@Override
	protected boolean isBlockGoodForTop(World world, int x, int y, int z,
			IMultiblockValidator validatorCallback) {
		return this.isBlockGoodForSides(world, x, y, z, validatorCallback);
	}
	
	@Override
	protected boolean isBlockGoodForBottom(World world, int x, int y, int z,
			IMultiblockValidator validatorCallback) {
		return this.isBlockGoodForSides(world, x, y, z, validatorCallback);
	}

}
