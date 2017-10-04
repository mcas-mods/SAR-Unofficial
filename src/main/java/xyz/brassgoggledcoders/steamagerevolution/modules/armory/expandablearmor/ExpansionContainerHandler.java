package xyz.brassgoggledcoders.steamagerevolution.modules.armory.expandablearmor;

import net.minecraft.util.ResourceLocation;

public class ExpansionContainerHandler implements IExpansionContainer {
	@Override
	public ResourceLocation getIdentifier() {
		return new ResourceLocation("");
	}

	@Override
	public boolean canAcceptExpansion(IExpansion extension) {
		return true;
	}
}
