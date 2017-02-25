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
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.FluidTankSingleType;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks.BlockSteamVent;

public class TileEntitySteamVent extends TileEntitySlowTick {

	public FluidTank tank = new FluidTankSingleType(Fluid.BUCKET_VOLUME * 6, "steam");

	@Override
	// TODO No real need to tick.
	public void updateTile() {
		World w = getWorld();
		BlockPos pos = getPos();
		if(w.isBlockPowered(pos) && this.tank.getFluidAmount() >= Fluid.BUCKET_VOLUME) {
			EnumFacing f = w.getBlockState(pos).getValue(BlockSteamVent.FACING);
			for(EntityLivingBase e : w.getEntitiesWithinAABB(EntityLivingBase.class,
					new AxisAlignedBB(pos.offset(f)))) {
				e.attackEntityFrom(DamageSource.inFire, 3F);
			}
			this.tank.drain(Fluid.BUCKET_VOLUME, true);
			// TODO particles and noise
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
		return super.writeToDisk(tag);
	}

	@Override
	public void readFromDisk(NBTTagCompound tag) {
		tank.readFromNBT(tag.getCompoundTag("tank"));
		super.readFromDisk(tag);
	}

	@Override
	public NBTTagCompound writeToUpdatePacket(NBTTagCompound tag) {
		tank.writeToNBT(tag);
		return super.writeToUpdatePacket(tag);
	}

	@Override
	public void readFromUpdatePacket(NBTTagCompound tag) {
		tank.readFromNBT(tag);
		super.readFromUpdatePacket(tag);
	}

}
