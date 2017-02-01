package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities;

import com.teamacronymcoders.base.tileentities.TileEntityBase;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityCrucible extends TileEntityBase {

	// Fired from the block.
	public void onEntityCollided(Entity entityIn) {
		if(getWorld().getBlockState(getPos().down()).getMaterial() != Material.LAVA)
			return;

		if(entityIn instanceof EntityItem) {
			ItemStack stack = ((EntityItem) entityIn).getEntityItem();
			for(int oreId : OreDictionary.getOreIDs(stack)) {
				String[] splitName = OreDictionary.getOreName(oreId).split("(?=[A-Z])");
				if(FluidRegistry.isFluidRegistered(splitName[1].toLowerCase())) {
					entityIn.setDead();
				}
			}
		}
		else
			entityIn.setFire(10);
	}

}
