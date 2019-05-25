package xyz.brassgoggledcoders.steamagerevolution.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ILens {

	public int getColor();

	public String getColorName();

	public static class VanillaLens implements ILens {
		int meta;

		public VanillaLens(int meta) {
			this.meta = meta;
		}

		@Override
		public int getColor() {
			return EnumDyeColor.byMetadata(meta).getColorValue();
		}

		@Override
		public String getColorName() {
			return EnumDyeColor.byMetadata(meta).getTranslationKey();
		}

		@Override
		public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
			// NO-OP
		}

		@Override
		public String getEffect() {
			return "";
		}
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack stack);

	public String getEffect();
}
