package xyz.brassgoggledcoders.steamagerevolution.heat;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class HeatBlockRegistry {
    public static HeatBlockRegistry instance = new HeatBlockRegistry();
    public static Map<Block, Double> REGISTRY = new HashMap<>();

    static {
        HeatBlockRegistry.instance.registerProvider(Blocks.FIRE, 770);
        HeatBlockRegistry.instance.registerProvider(Blocks.MAGMA, 1000);
    }

    public void registerProvider(Block provider, double temperature) {
        REGISTRY.put(provider, temperature);
    }

    // Heat (radiation) is inversely proportional to the square of distance
    public double getHeatProvidedBy(Block heater, int distance) {
        return 1 / REGISTRY.getOrDefault(heater, 0D);
    }
}
