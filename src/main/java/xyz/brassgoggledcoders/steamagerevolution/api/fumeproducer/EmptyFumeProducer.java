package xyz.brassgoggledcoders.steamagerevolution.api.fumeproducer;

import net.minecraft.item.ItemStack;

public class EmptyFumeProducer implements IFumeProducer {
    @Override
    public boolean isBurning() {
        return false;
    }

    @Override
    public ItemStack getCurrentFuel() {
        return ItemStack.EMPTY;
    }
}
