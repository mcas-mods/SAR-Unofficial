package xyz.brassgoggledcoders.steamagerevolution.utils.items;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

public class ItemStackHandlerSmart extends ItemStackHandlerExtractSpecific {
    private MultiblockControllerBase controller;

    public ItemStackHandlerSmart(int size, MultiblockControllerBase controller) {
        super(size);
        this.controller = controller;
    }

    @Override
    protected void onContentsChanged(int slot) {
        if (controller != null) {
            ((ISmartStackCallback) controller).onContentsChanged(slot);
        }
    }
}
