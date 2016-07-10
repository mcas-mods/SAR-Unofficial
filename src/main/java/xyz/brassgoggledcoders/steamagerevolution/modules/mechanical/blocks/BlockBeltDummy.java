package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks;

import net.minecraft.block.material.Material;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockBase;

public class BlockBeltDummy extends BlockBase {

	public BlockBeltDummy(Material mat, String name) {
		super(mat, name);
		this.setBlockUnbreakable();
	}

}
