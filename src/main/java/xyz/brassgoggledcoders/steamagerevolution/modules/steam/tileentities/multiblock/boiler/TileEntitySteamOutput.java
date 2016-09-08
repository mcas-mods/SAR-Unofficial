package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.boiler;

import java.util.LinkedHashMap;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.FMLLog;
import xyz.brassgoggledcoders.boilerplate.api.IDebuggable;
import xyz.brassgoggledcoders.boilerplate.client.guis.IOpenableGUI;
import xyz.brassgoggledcoders.boilerplate.multiblock.MultiblockControllerBase;
import xyz.brassgoggledcoders.boilerplate.multiblock.validation.IMultiblockValidator;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.containers.multiblock.boiler.ContainerSingleTank;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.guis.multiblock.boiler.GuiSingleTank;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.ITickableMultiblockPart;

public class TileEntitySteamOutput extends TileEntityBasicBoilerPart
		implements IOpenableGUI, IDebuggable, ITickableMultiblockPart {

	public FluidTank buffer = new FluidTank(Fluid.BUCKET_VOLUME);

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		buffer.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag = super.writeToNBT(tag);
		buffer.writeToNBT(tag);
		return tag;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return (T) buffer;
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return true;
	}

	@Override
	public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public Gui getClientGuiElement(int ID, EntityPlayer player, World world, BlockPos blockPos) {
		return new GuiSingleTank(player, this);
	}

	@Override
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, BlockPos blockPos) {
		return new ContainerSingleTank(player, this);
	}

	@Override
	public LinkedHashMap<String, String> getDebugStrings(LinkedHashMap<String, String> debugStrings) {
		debugStrings.put("fluidAmount", "" + buffer.getFluidAmount());
		return debugStrings;
	}

	@Override
	public String getPartName() {
		return "Steam Output";
	}

	@Override
	public boolean tick(MultiblockControllerBase controller) {
		BasicBoilerController boiler = (BasicBoilerController) controller;
		boolean flag = false;

		if(buffer.getFluidAmount() == 0) {
			for(TileEntitySteamTank tank : boiler.getAttachedSteamTanks()) {
				if(tank.tank.getFluid() != null && tank.tank.getFluidAmount() != 0
						&& tank.tank.drain(Fluid.BUCKET_VOLUME, false).amount != 0
						&& buffer.fill(tank.tank.drain(Fluid.BUCKET_VOLUME, false), false) != 0) {
					buffer.fill(tank.tank.drain(Fluid.BUCKET_VOLUME, true), true);
					tank.markDirty();
					tank.sendBlockUpdate();
					this.markDirty();
					tank.sendBlockUpdate();
					FMLLog.warning("Moving from tank to output");
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	@Override
	protected void readFromUpdatePacket(NBTTagCompound data) {
		this.buffer.setFluid(new FluidStack(FluidRegistry.getFluid("steam"), data.getInteger("level")));
		super.readFromUpdatePacket(data);
	};

	@Override
	protected NBTTagCompound writeToUpdatePacket(NBTTagCompound data) {
		data.setInteger("level", this.buffer.getFluidAmount());
		return super.writeToUpdatePacket(data);
	};
}
