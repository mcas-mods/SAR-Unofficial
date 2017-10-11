package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical;

import com.teamacronymcoders.base.materialsystem.MaterialSystem;
import com.teamacronymcoders.base.tileentities.TileEntitySlowTick;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.ISmartTankCallback;

public class TileEntityFumeCollector extends TileEntitySlowTick implements ISmartTankCallback {
	public FluidTank tank;

	public TileEntityFumeCollector() {
		super();
		tank = new FluidTankSmart(Fluid.BUCKET_VOLUME * 16, this);
	}

	// TODO this probably really doesn't need to be ticking. Also jimmy in 'recipe' support.
	@Override
	public void updateTile() {
		if(world.isRemote)
			return;
		BlockPos below = getPos().down();
		TileEntity te = this.getWorld().getTileEntity(below);
		if(te != null && te instanceof TileEntityFurnace) {
			TileEntityFurnace furnace = (TileEntityFurnace) te;
			if(furnace.isBurning()) {
				ItemStack fuel = furnace.getStackInSlot(1);
				if(!fuel.isEmpty() && ItemStackUtils.doItemsMatch(fuel,
						MaterialSystem.getMaterialPart("sulphur:crystal").getItemStack().getItem())) {
					if(getWorld().rand.nextBoolean()) {
						FluidStack fume = FluidRegistry.getFluidStack("sulphur_dioxide", Fluid.BUCKET_VOLUME / 10);
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
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
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

	@Override
	@SideOnly(Side.CLIENT)
	public void updateFluid(PacketFluidUpdate message) {
		this.tank.setFluid(message.fluid);
	}

	@Override
	public void onTankContentsChanged(FluidTank tank) {
		this.markDirty();
		SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(new PacketFluidUpdate(getPos(), tank.getFluid()),
				getPos(), getWorld().provider.getDimension());
	}
}
