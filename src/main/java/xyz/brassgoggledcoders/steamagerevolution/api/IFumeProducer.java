package xyz.brassgoggledcoders.steamagerevolution.api;

import net.minecraft.item.ItemStack;

public interface IFumeProducer {
    boolean isBurning();

    ItemStack getCurrentFuel();
}
