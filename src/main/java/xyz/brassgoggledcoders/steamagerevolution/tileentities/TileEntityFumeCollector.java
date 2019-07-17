package xyz.brassgoggledcoders.steamagerevolution.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.SARCapabilities;
import xyz.brassgoggledcoders.steamagerevolution.api.IFumeProducer;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IOType;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.TileEntityCraftingMachine;
import xyz.brassgoggledcoders.steamagerevolution.machines.IMachine;
import xyz.brassgoggledcoders.steamagerevolution.recipes.FumeCollectorRecipe;

//TODO add ability output to item placed in gui, and to item right clicked on block
public class TileEntityFumeCollector extends TileEntityCraftingMachine<InventoryCraftingMachine> {
	static final String uid = "fume_collector";
	public static int outputCapacity = Fluid.BUCKET_VOLUME * 16;

	static {
		IMachine.referenceMachinesList.put(uid, new TileEntityFumeCollector());
	}

	public TileEntityFumeCollector() {
		super();
		this.setInventory(
				new InventoryCraftingMachine(this).addFluidHandler("tank", IOType.OUTPUT, 105, 11, outputCapacity));
	}

	@Override
	public void update() {
		if(getWorld().isRemote) {
			return;
		}
		BlockPos below = getPos().down();
		TileEntity te = getWorld().getTileEntity(below);
		if(te != null && te.hasCapability(SARCapabilities.FUME_PRODUCER, EnumFacing.DOWN)) {
			IFumeProducer producer = te.getCapability(SARCapabilities.FUME_PRODUCER, EnumFacing.DOWN);
			if(producer.isBurning()) {
				// SteamAgeRevolution.instance.getLogger().devInfo("Fume collector has burning
				// producer");
				ItemStack fuel = producer.getCurrentFuel();
				if(!fuel.isEmpty()) {
					FumeCollectorRecipe r = FumeCollectorRecipe.getRecipe(fuel);
					if(r != null && getWorld().rand.nextFloat() < r.chance) {
						FluidStack fume = r.output;
						IFluidHandler tank = this.getInventory().getFluidPiece("tank").getHandler();
						if(tank.fill(fume, false) == fume.amount) {
							tank.fill(fume, true);
							this.markMachineDirty();
							this.sendBlockUpdate();
						}
					}
				}
			}
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
					.cast(this.getInventory().getFluidPiece("tank").getHandler());
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public String getUID() {
		return uid;
	}
}
