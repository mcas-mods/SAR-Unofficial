package xyz.brassgoggledcoders.steamagerevolution.utils;

import net.minecraft.item.ItemStack;

import java.util.Comparator;

public class StackComparator implements Comparator<ItemStack> {

    @Override
    public int compare(ItemStack o1, ItemStack o2) {
        return o1.getDisplayName().compareToIgnoreCase(o2.getDisplayName());
    }
}

