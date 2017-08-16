package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import net.minecraft.util.ResourceLocation;

public interface IExpansionContainer {
	ResourceLocation getIdentifier();

	boolean canAcceptExpansion(IExpansion extension);
}
