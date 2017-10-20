package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.List;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller.ControllerDistiller;

public class MultiblockBuilder<C extends SARRectangularMultiblockControllerBase> {

	BlockRegistry registry;
	Class<C> controller;
	List<BlockMultiblockBase> parts = Lists.newArrayList();
	Material material;

	public MultiblockBuilder(BlockRegistry registry, Class<C> controller, Material material) {
		this.registry = registry;
		this.controller = controller;
		this.material = material;
	}

	public void addNewPart(String name, boolean[] validPositions) {
		parts.add(new BlockMultiblockBase(controller, world -> new TileEntityMultiblockBase<C>(validPositions,
				controller, world2 -> new ControllerDistiller((World) world)), material, name));
	}

	public void build() {
		if(Loader.instance().getLoaderState() != LoaderState.PREINITIALIZATION) {
			throw new IllegalStateException("Multiblock Builder must be used during preinit!");
		}
		parts.forEach(part -> registry.register(part));
	}
}
