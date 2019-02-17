package xyz.brassgoggledcoders.steamagerevolution.modules.materials;

import com.teamacronymcoders.base.blocks.BlockBase;

import net.minecraft.block.material.Material;

public class BlockCharcoal extends BlockBase {
	public BlockCharcoal() {
		super(Material.ROCK, "charcoal_block");
		setItemBlock(new ItemBlockCharcoal<>(this));
	}
}
