package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import java.lang.ref.WeakReference;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
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
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.ISemisolidHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidStack;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.blocks.BlockHeavyOre;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill.ControllerDrill;

public class MiningUtils {

	public static boolean allowedToBreak(IBlockState state, World world, BlockPos pos, EntityPlayer entityPlayer) {
		if (!state.getBlock().canEntityDestroy(state, world, pos, entityPlayer)) {
			return false;
		}
		BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos, state, entityPlayer);
		MinecraftForge.EVENT_BUS.post(event);
		return !event.isCanceled();
	}

	public static void doMining(World world, BlockPos pos, IItemHandler itemHandler, @Nullable ISemisolidHandler semisolidHandler) {
		WeakReference<FakePlayer> fakePlayer = new WeakReference<FakePlayer>(
				FakePlayerFactory.get((WorldServer) world, ControllerDrill.profile));
		// Skip air, skip unbreakable blocks, skip tile entities and skip blocks that
		// are otherwise unharvestable
		IBlockState state = world.getBlockState(pos);
		if (!world.isAirBlock(pos) && state.getBlockHardness(world, pos) >= 0
				&& world.getTileEntity(pos) == null && allowedToBreak(state, world, pos, fakePlayer.get())) {
			if (semisolidHandler != null && state.getBlock() instanceof BlockHeavyOre) {
				BlockHeavyOre ore = (BlockHeavyOre) state.getBlock();
				semisolidHandler.fill(new SemisolidStack(SteamAgeRevolution.semisolidRegistry
						.getEntry(new ResourceLocation(ore.getRegistryName().getNamespace(), ore.type)), 1));
				//markReferenceCoordForUpdate();
				int chunks = state.getValue(BlockHeavyOre.CHUNKS).intValue();
				if (chunks > 1) {
					world.setBlockState(pos, state.withProperty(BlockHeavyOre.CHUNKS, chunks - 1), 2);
	
				} else {
					world.destroyBlock(pos, false);
				}
			} else {
				NonNullList<ItemStack> drops = NonNullList.create();
				state.getBlock().getDrops(drops, world, pos, state, 0);
				for (ItemStack drop : drops) {
					ItemHandlerHelper.insertItemStacked(itemHandler, drop, false);
				}
				world.destroyBlock(pos, false);
				ForgeEventFactory.fireBlockHarvesting(drops, world, pos, state, 0, 0, false, fakePlayer.get());
			}
			SteamAgeRevolution.instance.getLogger().devInfo("Mining " + pos.toString());
		}
	}

}
