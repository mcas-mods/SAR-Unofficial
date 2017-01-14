package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks;

import com.teamacronymcoders.base.blocks.BlockBase;

import net.minecraft.block.material.Material;

public class BlockSawblade extends BlockBase {

	public BlockSawblade() {
		super(Material.IRON, "sawblade");
		this.setBlockUnbreakable();
	}

}
