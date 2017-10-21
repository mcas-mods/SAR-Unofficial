package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.List;
import java.util.function.Function;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class MultiblockBuilder<C extends SARRectangularMultiblockControllerBase> {

	public static boolean[] allButInterior = new boolean[] {true, true, true, true, false};
	public static boolean[] allFaces = new boolean[] {false, true, true, true, false};
	public static boolean[] bottomOnly = new boolean[] {false, false, false, true, false};
	public static boolean[] sidesOnly = new boolean[] {false, true, false, false, false};
	public static boolean[] topOnly = new boolean[] {false, false, true, false, false};

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

	public MultiblockBuilder<C> addNewPart(String name, boolean[] validPositions) {
		parts.add(new BlockMultiblockBase<C>(
				world -> new TileEntityMultiblockBase<C>(validPositions, controller, controllerCreator), material,
				name));
		return this;
	}

	public MultiblockBuilder<C> addNewFluidWrapperPart(String name, boolean[] validPositions, String tankName) {
		parts.add(new BlockMultiblockBase<C>(TileEntityMultiblockFluidWrapper.class,
				world -> new TileEntityMultiblockFluidWrapper<C>(tankName, validPositions, controller,
						controllerCreator),
				material, name));
		return this;
	}

	public MultiblockBuilder<C> addNewItemWrapperPart(String name, boolean[] validPositions, String handlerName) {
		parts.add(new BlockMultiblockBase<C>(TileEntityMultiblockItemWrapper.class,
				world -> new TileEntityMultiblockItemWrapper<C>(handlerName, validPositions, controller,
						controllerCreator),
				material, name));
		return this;
	}

	public MultiblockBuilder<C> addNewTransparentPart(String name, boolean[] validPositions) {
		parts.add(new BlockMultiblockTransparent<C>(
				world -> new TileEntityMultiblockBase<C>(validPositions, controller, controllerCreator), material,
				name));
		return this;
	}

	public MultiblockBuilder<C> addNewPositionalPart(String name, boolean[] validPositions) {
		parts.add(new BlockMultiblockPositional<C>(
				world -> new TileEntityMultiblockBase<C>(validPositions, controller, controllerCreator), material,
				name));
		return this;
	}

	public MultiblockBuilder<C> addNewPositionalFluidWrapperPart(String name, boolean[] validPositions,
			String tankName) {
		parts.add(new BlockMultiblockPositional<C>(world -> new TileEntityMultiblockFluidWrapper<C>(tankName,
				validPositions, controller, controllerCreator), material, name));
		return this;
	}

	public void build() {
		parts.forEach(part -> registry.register(part));
	}
}
