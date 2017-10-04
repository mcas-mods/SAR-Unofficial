package xyz.brassgoggledcoders.steamagerevolution.modules.armory.expandablearmor;

import net.minecraft.util.ResourceLocation;

public interface IExpansionContainer {
	ResourceLocation getIdentifier();

	boolean canAcceptExpansion(IExpansion extension);
}
