package xyz.brassgoggledcoders.steamagerevolution.modules.transport.pipenetwork;

import com.teamacronymcoders.base.client.ClientHelper;

import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class PipeNetworkEventHandler {

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onChunkLoad(final ChunkEvent.Load loadEvent) {

		Chunk chunk = loadEvent.getChunk();

		PipeNetworkRegistry.INSTANCE.onChunkLoaded(loadEvent.getWorld(), chunk.x, chunk.z);
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onWorldUnload(final WorldEvent.Unload unloadWorldEvent) {
		PipeNetworkRegistry.INSTANCE.onWorldUnloaded(unloadWorldEvent.getWorld());
	}

	@SubscribeEvent
	public void onWorldTick(final TickEvent.WorldTickEvent event) {

		if(TickEvent.Phase.START == event.phase)
			PipeNetworkRegistry.INSTANCE.tickStart(event.world);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onClientTick(final TickEvent.ClientTickEvent event) {

		if(TickEvent.Phase.START == event.phase)
			PipeNetworkRegistry.INSTANCE.tickStart(ClientHelper.world());
	}
}