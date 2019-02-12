package xyz.brassgoggledcoders.steamagerevolution.modules.storage;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.blocks.*;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.items.ItemCanister;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.multiblock.tank.blocks.*;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleStorage extends ModuleBase {

    public static Block trunk, fluidIO, basicFluidTank, fluidHopper;
    public static Block tankCasing, tankValve, tankWindow;
    public static Item canister;

    @Override
    public String getName() {
        return "Storage";
    }

    @Override
    public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
        trunk = new BlockTrunk(Material.WOOD, "trunk");
        blockRegistry.register(trunk);
        fluidIO = new BlockFluidIO();
        blockRegistry.register(fluidIO);
        fluidHopper = new BlockFluidHopper(Material.IRON, "fluid_hopper");
        blockRegistry.register(fluidHopper);

        tankCasing = new BlockTankCasing(Material.ROCK, "tank_casing");
        blockRegistry.register(tankCasing);
        tankValve = new BlockTankValve(Material.IRON, "tank_valve");
        blockRegistry.register(tankValve);
        tankWindow = new BlockTankWindow(Material.GLASS, "tank_window");
        blockRegistry.register(tankWindow);
    }

    @Override
    public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
        canister = new ItemCanister("canister", Fluid.BUCKET_VOLUME * 8);
        itemRegistry.register(canister);
    }

    @Override
    public String getClientProxyPath() {
        return "xyz.brassgoggledcoders.steamagerevolution.modules.storage.ClientProxy";
    }
}
