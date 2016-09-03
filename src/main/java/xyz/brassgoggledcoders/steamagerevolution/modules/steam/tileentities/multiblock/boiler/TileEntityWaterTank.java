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
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import xyz.brassgoggledcoders.boilerplate.api.IDebuggable;
import xyz.brassgoggledcoders.boilerplate.client.guis.IOpenableGUI;
import xyz.brassgoggledcoders.boilerplate.multiblock.validation.IMultiblockValidator;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.containers.multiblock.boiler.ContainerSingleTank;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.guis.multiblock.boiler.GuiSingleTank;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.boiler.TileEntityWaterInput.WaterLockedTank;

public class TileEntityWaterTank extends TileEntityBasicBoilerPart implements IOpenableGUI, IDebuggable {

	protected FluidTank tank = new WaterLockedTank(Fluid.BUCKET_VOLUME * 16);

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		tank.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag = super.writeToNBT(tag);
		tank.writeToNBT(tag);
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
			return (T) tank;
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

		return true;
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
		debugStrings.put("fluidAmount", "" + tank.getFluidAmount());
		return debugStrings;
	}

	@Override
	public String getPartName() {
		return "Water Tank";
	}

}
