package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities;

import com.teamacronymcoders.base.tileentities.TileEntitySlowTick;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.BlockSteamVent;
import xyz.brassgoggledcoders.steamagerevolution.utils.FluidTankSingleSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.ISmartTankCallback;

public class TileEntitySteamVent extends TileEntitySlowTick implements ISmartTankCallback {

	public FluidTank tank = new FluidTankSingleSmart(Fluid.BUCKET_VOLUME * 6, "steam", this);

	@Override
	// TODO No real need to tick.
	public void updateTile() {
		World w = getWorld();
		BlockPos pos = getPos();
		if(w.isBlockPowered(pos) && this.tank.getFluidAmount() >= Fluid.BUCKET_VOLUME) {
			EnumFacing f = w.getBlockState(pos).getValue(BlockSteamVent.FACING);
			for(EntityLivingBase e : w.getEntitiesWithinAABB(EntityLivingBase.class,
					new AxisAlignedBB(pos.offset(f)))) {
				e.attackEntityFrom(DamageSource.IN_FIRE, 3F);
			}
			this.tank.drain(Fluid.BUCKET_VOLUME, true);
			SteamAgeRevolution.proxy.spawnSteamJet(pos, f);
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
				|| super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
		return super.getCapability(capability, facing);
	}

	@Override
	public NBTTagCompound writeToDisk(NBTTagCompound tag) {
		tag.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		return tag;
	}

	@Override
	public void readFromDisk(NBTTagCompound tag) {
		tank.readFromNBT(tag.getCompoundTag("tank"));
	}

	@Override
	public void onTankContentsChanged(FluidTank tank) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFluid(FluidStack fluid) {
		// TODO Auto-generated method stub

	}

}
