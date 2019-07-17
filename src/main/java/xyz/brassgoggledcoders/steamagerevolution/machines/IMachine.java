package xyz.brassgoggledcoders.steamagerevolution.machines;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public interface IMachine {

	// A single instance of each class extending this interface should be added to
	// this list (ex. in static init on the extending class) for Reasons.
	static HashMap<String, IMachine> referenceMachinesList = new HashMap<String, IMachine>() {
		private static final long serialVersionUID = -4864024057406246752L;

		@Override
		public IMachine put(String key, IMachine value) {
			return super.put(key.toLowerCase().replace(" ", "_"), value);
		}
	};

	public String getUID();

	default String getLocalizedName() {
		return new TextComponentTranslation(SteamAgeRevolution.MODID + ".machine." + getUID()).getFormattedText();
	}

	public World getMachineWorld();

	public BlockPos getMachinePos();

	// FOR JEI
	default ItemStack getCatalyst() {
		return null;
	}
}
