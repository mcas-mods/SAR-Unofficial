package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.List;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;

import net.minecraft.block.material.Material;

public class MultiblockBuilder<C extends SARRectangularMultiblockControllerBase> {

	public static boolean[] allButInterior = new boolean[] {true, true, true, true, false};
	public static boolean[] allFaces = new boolean[] {false, true, true, true, false};
	public static boolean[] bottomOnly = new boolean[] {false, false, false, true, false};
	public static boolean[] sidesOnly = new boolean[] {false, true, false, false, false};
	public static boolean[] topOnly = new boolean[] {false, false, true, false, false};

	BlockRegistry registry;
	Class<? extends TileEntityMultiblockBase<C>> tileClass;
	List<BlockMultiblockBase<C>> parts = Lists.newArrayList();
	Material material;

	public MultiblockBuilder(BlockRegistry registry, Class<? extends TileEntityMultiblockBase<C>> tileClass,
			Material material) {
		this.registry = registry;
		this.material = material;
		this.tileClass = tileClass;
	}

	public MultiblockBuilder<C> addNewPart(String name, boolean[] validPositions) {
		parts.add(new BlockMultiblockBase<C>(tileClass, material, name, validPositions, null, null, false, false));
		return this;
	}

	public MultiblockBuilder<C> addNewFluidWrapperPart(String name, boolean[] validPositions, String tankName) {
		parts.add(new BlockMultiblockBase<C>(tileClass, material, name, validPositions, tankName, null, false, false));
		return this;
	}

	public MultiblockBuilder<C> addNewItemWrapperPart(String name, boolean[] validPositions, String handlerName) {
		parts.add(
				new BlockMultiblockBase<C>(tileClass, material, name, validPositions, null, handlerName, false, false));
		return this;
	}

	public MultiblockBuilder<C> addNewTransparentPart(String name, boolean[] validPositions) {
		parts.add(new BlockMultiblockBase<C>(tileClass, material, name, validPositions, null, null, true, false));
		return this;
	}

	public MultiblockBuilder<C> addNewPositionalPart(String name, boolean[] validPositions) {
		parts.add(new BlockMultiblockBase<C>(tileClass, material, name, validPositions, null, null, true, true));
		return this;
	}

	public MultiblockBuilder<C> addNewCustomPartPart(BlockMultiblockBase block) {
		parts.add(block);
		return this;
	}

	public MultiblockBuilder<C> addNewPositionalFluidWrapperPart(String name, boolean[] validPositions,
			String tankName) {
		parts.add(new BlockMultiblockBase<C>(tileClass, material, name, validPositions, tankName, null, true, true));
		return this;
	}

	public void build() {
		parts.forEach(part -> registry.register(part));
	}
}
