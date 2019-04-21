package xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.CrushedHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.CrushedHolder;
import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.CrushedStack;
import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.ICrushedHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.InventoryCrushed;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.InventoryPieceCrushed;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks.BlockHeavyOre;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceFluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceItem;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerDrill extends SARMultiblockInventory<InventoryCrushed> {

	private static final String name = "[" +
			SteamAgeRevolution.MODNAME + "]";
	private static final GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(name.getBytes()), name);

	ArrayList<BlockPos> positions = Lists.newArrayList();
	int currentPosition = 0;
	
	protected ControllerDrill(World world) {
		super(world);
		// TODO Util methods for positioning x/y grids of slots would be handy
		int xOffset = 49;
		int yOffset = 1;
		int slotGap = 2;
		this.setInventory(new InventoryCrushed(new InventoryPieceItem(new ItemStackHandlerSmart(1, this), 40, 32),
				new InventoryPieceItem(new ItemStackHandlerSmart(9, this),
						new int[] { xOffset + 16, xOffset + 32 + slotGap, xOffset + 48 + slotGap * 2,
								xOffset + 16, xOffset + 32 + slotGap, xOffset + 48 + slotGap * 2,
								xOffset + 16, xOffset + 32 + slotGap, xOffset + 48 + slotGap * 2},
						new int[] { yOffset + 16, yOffset + 16, yOffset + 16,
								yOffset + 32 + slotGap, yOffset + 32 + slotGap, yOffset + 32 + slotGap,
								yOffset + 48 + slotGap * 2, yOffset + 48 + slotGap * 2, yOffset + 48 + slotGap * 2}),
				new InventoryPieceCrushed(new CrushedHandler(new CrushedHolder(null, 30)), 126, 15),
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
		WeakReference<FakePlayer> fakePlayer = new
		WeakReference<FakePlayer>(FakePlayerFactory.get((WorldServer) WORLD, profile));
		if (this.getCurrentProgress() >= 20) {
			if (currentPosition < positions.size()) {
				BlockPos pos = positions.get(currentPosition);
				//Skip air, skip unbreakable blocks, skip tile entities and skip blocks that are otherwise unharvestable
				IBlockState state = WORLD.getBlockState(pos);
				if (!WORLD.isAirBlock(pos) && state.getBlockHardness(WORLD, pos) >= 0 && WORLD.getTileEntity(pos) == null && allowedToBreak(state, WORLD, pos, fakePlayer.get())) {
					if(state.getBlock() instanceof BlockHeavyOre) {
						BlockHeavyOre ore = (BlockHeavyOre) state.getBlock();
						ICrushedHandler oreHolder = this.getInventory().ore.getHandler();
						oreHolder.getHolders()[0].fill(new CrushedStack(SteamAgeRevolution.materialRegistry.getEntry(new ResourceLocation(ore.getRegistryName().getNamespace(), ore.type)), 1));
						this.markReferenceCoordForUpdate();
						int chunks = state.getValue(BlockHeavyOre.CHUNKS).intValue();
						if(chunks > 1) {
							WORLD.setBlockState(pos, state.withProperty(BlockHeavyOre.CHUNKS, chunks - 1), 2);
							
						}
						else {
							WORLD.destroyBlock(pos, false);
						}
					}
					else { 
						NonNullList<ItemStack> drops = NonNullList.create();
						state.getBlock().getDrops(drops, WORLD, pos, state, 0);
						for(ItemStack drop : drops) {
							ItemHandlerHelper.insertItemStacked(this.getInventory().getOutputHandler(), drop, false);
						}
						WORLD.destroyBlock(pos, false);
						ForgeEventFactory.fireBlockHarvesting(drops, WORLD, pos, state, 0, 0, false, fakePlayer.get());
					}
					SteamAgeRevolution.instance.getLogger().devInfo("Mining " + pos.toString());
				}
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
	
	public static boolean allowedToBreak(IBlockState state, World world, BlockPos pos, EntityPlayer entityPlayer) {
        if (!state.getBlock().canEntityDestroy(state, world, pos, entityPlayer)) {
            return false;
        }
        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos, state, entityPlayer);
        MinecraftForge.EVENT_BUS.post(event);
        return !event.isCanceled();
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
	

	@SideOnly(Side.CLIENT)
	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiDrill(entityPlayer, this);
	}

}
