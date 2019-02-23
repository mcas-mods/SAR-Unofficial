package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items;

import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.text.WordUtils;

import com.teamacronymcoders.base.items.IHasSubItems;
import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.ILens;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.ModuleArmory;

public class ItemLens extends ItemBase implements IHasSubItems {

	public ItemLens() {
		super("lens");
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public List<String> getModelNames(List<String> modelNames) {
		modelNames.add(name);
		return modelNames;
	}

	@Override
	public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
		// TODO This is likely a fragile way of storing, but it feels unnecessary to use
		// a hashmap
		int i = 0;
		for(ILens lens : ModuleArmory.lenseTypes) {
			itemStacks.add(new ItemStack(this, 1, i++));
		}
		return itemStacks;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("Effect:");
		tooltip.add(ModuleArmory.lenseTypes.get(stack.getMetadata()).getEffect());
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public String getItemStackDisplayName(ItemStack stack)
    {
        return WordUtils.capitalize(I18n.translateToLocal(ModuleArmory.lenseTypes.get(stack.getMetadata()).getColorName())) + " " + super.getItemStackDisplayName(stack);
    }

	public static class LensTintHandler implements IItemColor {

		@Override
		public int colorMultiplier(ItemStack stack, int tintIndex) {
			if(tintIndex == 1) {
				return ModuleArmory.lenseTypes.get(stack.getMetadata()).getColor();
			}
			return -1;
		}
	}
}
