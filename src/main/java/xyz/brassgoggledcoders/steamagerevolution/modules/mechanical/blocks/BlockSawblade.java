package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks;

import net.minecraft.block.material.Material;
import xyz.brassgoggledcoders.boilerplate.blocks.BlockBase;

public class BlockSawblade extends BlockBase {

	public BlockSawblade() {
		super(Material.IRON, "sawblade");
		this.setBlockUnbreakable();
	}

}
