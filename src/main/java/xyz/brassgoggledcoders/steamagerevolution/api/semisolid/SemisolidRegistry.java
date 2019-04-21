package xyz.brassgoggledcoders.steamagerevolution.api.semisolid;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.GenericEvent;

public class SemisolidRegistry {

	private Map<ResourceLocation, ISemisolid> entries;

    public SemisolidRegistry() {
        entries = Maps.newHashMap();
    }

    public void addEntry(ISemisolid entry) {
        this.entries.put(entry.getRegistryName(), entry);
    }

    @SafeVarargs
    public final void addEntries(ISemisolid... entries) {
        for (ISemisolid entry : entries) {
            addEntry(entry);
        }
    }

    @Nullable
    public ISemisolid getEntry(ResourceLocation resourceLocation) {
        return this.entries.get(resourceLocation);
    }

    public Collection<ISemisolid> getEntries() {
        return entries.values();
    }
    
    public static class SemisolidRegistryEvent extends GenericEvent<SemisolidRegistry> {
		SemisolidRegistry reg;
    	
    	public SemisolidRegistryEvent(SemisolidRegistry r, Class<SemisolidRegistry> type) {
			super(type);
			this.reg = r;
		}
		
		public SemisolidRegistry getRegistry() {
			return reg;
		}
    }
}
