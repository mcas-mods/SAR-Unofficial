package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import java.lang.ref.WeakReference;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill.ControllerDrill;

@Deprecated //Move me somewhere sane on feature end
public class MiningUtils {

	public static boolean allowedToBreak(IBlockState state, World world, BlockPos pos, EntityPlayer entityPlayer) {
		if (!state.getBlock().canEntityDestroy(state, world, pos, entityPlayer)) {
			return false;
		}
		BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos, state, entityPlayer);
		MinecraftForge.EVENT_BUS.post(event);
		return !event.isCanceled();
	}

	public static void doMining(World world, BlockPos pos, IItemHandler itemHandler) {
		//TODO Player tool
		WeakReference<FakePlayer> fakePlayer = new WeakReference<FakePlayer>(
				FakePlayerFactory.get((WorldServer) world, ControllerDrill.profile));
		// Skip air, skip unbreakable blocks, skip tile entities and skip blocks that
		// are otherwise unharvestable
		IBlockState state = world.getBlockState(pos);
		if (!world.isAirBlock(pos) && state.getBlockHardness(world, pos) >= 0 && world.getTileEntity(pos) == null
				&& allowedToBreak(state, world, pos, fakePlayer.get())) {
				state.getBlock().onBlockHarvested(world, pos, state, fakePlayer.get());
				state.getBlock().harvestBlock(world, fakePlayer.get(), pos, state, world.getTileEntity(pos), new ItemStack(Items.IRON_PICKAXE));
				NonNullList<ItemStack> drops = NonNullList.create();
				for(EntityItem item : world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos).grow(1))) {
					ItemStack stack = item.getItem().copy();
					if(ItemHandlerHelper.insertItemStacked(itemHandler, stack, true).isEmpty()) {
						ItemHandlerHelper.insertItemStacked(itemHandler, stack, false);
						item.setDead();
						drops.add(stack);
					}
				}
				world.destroyBlock(pos, false);
				ForgeEventFactory.fireBlockHarvesting(drops, world, pos, state, 0, 0, false, fakePlayer.get());
		}
	}
	
	//This took me a surprisngly long time. To be fair, I did get a U in Math. 
	//And apparently English too...
	public static Pair<int[], int[]> getGUIPositionGrid(int xStart, int yStart, int xSize, int ySize) {
		int[] xPositions = new int[xSize * ySize];
		int[] yPositions = new int[xSize * ySize];
		for (int vertical = 0; vertical < ySize; ++vertical) {
            for (int horizontal = 0; horizontal < xSize; ++horizontal) {
                xPositions[horizontal + (vertical * ySize)] = xStart + (horizontal * 18);
                yPositions[horizontal + (vertical * ySize)] = yStart + (vertical * 18);
            }
        }
		return Pair.of(xPositions, yPositions);
	}

}
