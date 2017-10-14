package xyz.brassgoggledcoders.steamagerevolution.utils;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;

import net.minecraftforge.items.ItemStackHandler;

public class SmartItemStackHandler extends ItemStackHandler {
	private MultiblockControllerBase controller;

	public SmartItemStackHandler(int size, MultiblockControllerBase controller) {
		super(size);
		this.controller = controller;
	}

	@Override
	protected void onContentsChanged(int slot) {
		if(controller != null) {
			((ISmartStackCallback) controller).onContentsChanged(slot);
		}
	}
}
