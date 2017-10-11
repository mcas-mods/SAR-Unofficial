package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;
import com.teamacronymcoders.base.util.OreDictUtils;
import com.teamacronymcoders.base.util.Platform;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat.BlockVatFluidInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat.BlockVatFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat.BlockVatOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat.VatRecipe.VatRecipeBuilder;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleAlchemical extends ModuleBase {

	@Override
	public boolean getActiveDefault() {
		return Platform.isDevEnv();
	}

	@Override
	public String getName() {
		return "Alchemical";
	}

	@Override
	public String getClientProxyPath() {
		return "xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.ClientProxy";
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		new VatRecipeBuilder().setOutput(FluidRegistry.getFluidStack("sulphuric_acid", Fluid.BUCKET_VOLUME))
				.setFluids(FluidRegistry.getFluidStack("water", Fluid.BUCKET_VOLUME))
				.setItems(OreDictUtils.getPreferredItemStack("crystalSulphur")).build();
		// TODO Proper oredict support
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		blockRegistry.register(new BlockVatFrame(Material.IRON, "vat_frame"));
		blockRegistry.register(new BlockVatFluidInput(Material.IRON, "vat_fluid_input"));
		blockRegistry.register(new BlockVatOutput(Material.IRON, "vat_output"));
	}

}
