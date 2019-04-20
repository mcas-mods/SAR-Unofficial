package xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.GenericEvent;

public class CrushedMaterialRegistry {

	private Map<ResourceLocation, ICrushedMaterial> entries;

    public CrushedMaterialRegistry() {
        entries = Maps.newHashMap();
    }

    public void addEntry(ICrushedMaterial entry) {
        this.entries.put(entry.getRegistryName(), entry);
    }

    @SafeVarargs
    public final void addEntries(ICrushedMaterial... entries) {
        for (ICrushedMaterial entry : entries) {
            addEntry(entry);
        }
    }

    @Nullable
    public ICrushedMaterial getEntry(ResourceLocation resourceLocation) {
        return this.entries.get(resourceLocation);
    }

    public Collection<ICrushedMaterial> getEntries() {
        return entries.values();
    }
    
    public static class CrushedMaterialRegistryEvent extends GenericEvent<CrushedMaterialRegistry> {
		CrushedMaterialRegistry reg;
    	
    	public CrushedMaterialRegistryEvent(CrushedMaterialRegistry r, Class<CrushedMaterialRegistry> type) {
			super(type);
			this.reg = r;
		}
		
		public CrushedMaterialRegistry getRegistry() {
			return reg;
		}
    }
}
