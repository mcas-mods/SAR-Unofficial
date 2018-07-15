package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.tileentities;

import com.teamacronymcoders.base.tileentities.TileEntitySlowTick;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.SARCapabilities;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.api.IFumeProducer;

public class TileEntityFumeCollector extends TileEntitySlowTick {
	public static int outputCapacity = Fluid.BUCKET_VOLUME * 16;
	public FluidTank tank;

	public TileEntityFumeCollector() {
		super();
		// tank = new FluidTankSmart(outputCapacity, this);
	}

	// TODO this probably really doesn't need to be ticking.
	@Override
	public void updateTile() {
		if(world.isRemote) {
			return;
		}
		BlockPos below = getPos().down();
		TileEntity te = getWorld().getTileEntity(below);
		if(te != null && te.hasCapability(SARCapabilities.FUME_PRODUCER, EnumFacing.DOWN)) {
			IFumeProducer producer = te.getCapability(SARCapabilities.FUME_PRODUCER, EnumFacing.DOWN);
			if(producer.isBurning()) {
				SteamAgeRevolution.instance.getLogger().devInfo("Fume collector has burning producer");
				ItemStack fuel = producer.getCurrentFuel();
				if(!fuel.isEmpty()) {
					FumeCollectorRecipe r = FumeCollectorRecipe.getRecipe(fuel);
					if(r != null && getWorld().rand.nextFloat() < r.chance) {
						FluidStack fume = r.output;
						if(tank.fill(fume, false) == fume.amount) {
							tank.fill(fume, true);
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
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void readFromDisk(NBTTagCompound tag) {
		tank.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToDisk(NBTTagCompound tag) {
		return tank.writeToNBT(tag);
	}
}
