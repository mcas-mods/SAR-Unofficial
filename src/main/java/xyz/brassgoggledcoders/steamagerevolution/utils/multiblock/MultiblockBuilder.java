package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.List;
import java.util.function.Function;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class MultiblockBuilder<C extends SARRectangularMultiblockControllerBase> {

	BlockRegistry registry;
	Class<C> controller;
	Function<World, SARRectangularMultiblockControllerBase> controllerCreator;
	List<BlockMultiblockBase<C>> parts = Lists.newArrayList();
	Material material;

	public MultiblockBuilder(BlockRegistry registry, Class<C> controller,
			Function<World, SARRectangularMultiblockControllerBase> controllerCreator, Material material) {
		this.registry = registry;
		this.controller = controller;
		this.material = material;
		this.controllerCreator = controllerCreator;
	}

	public void addNewPart(String name, boolean[] validPositions) {
		parts.add(new BlockMultiblockBase(controller,
				world -> new TileEntityMultiblockBase<C>(validPositions, controller, controllerCreator), material,
				name));
	}

	public void build() {
		parts.forEach(part -> registry.register(part));
	}
}
