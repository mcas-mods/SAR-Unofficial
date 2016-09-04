package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.boiler;

import java.util.LinkedHashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.boilerplate.api.IDebuggable;
import xyz.brassgoggledcoders.boilerplate.client.guis.IOpenableGUI;
import xyz.brassgoggledcoders.boilerplate.multiblock.MultiblockControllerBase;
import xyz.brassgoggledcoders.boilerplate.multiblock.validation.IMultiblockValidator;
import xyz.brassgoggledcoders.boilerplate.tileentities.IOnSlotChanged;
import xyz.brassgoggledcoders.boilerplate.utils.ItemStackUtils;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.containers.multiblock.boiler.ContainerSolidFirebox;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.guis.multiblock.boiler.GuiSolidFirebox;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.ITickableMultiblockPart;

public class TileEntitySolidFirebox extends TileEntityBasicBoilerPart
		implements ITickableMultiblockPart, IOnSlotChanged, IOpenableGUI, IDebuggable {

	private ItemStackHandler inventory;
	private int burnTime;

	public TileEntitySolidFirebox() {
		super();
		inventory = new ItemStackHandler();
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		inventory.deserializeNBT((NBTTagCompound) tag.getTag("inventory"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(tag);
	}

	@Override
	public boolean tick(MultiblockControllerBase controller) {
		BasicBoilerController boiler = (BasicBoilerController) controller;
		boolean flag = false;
		if(burnTime <= 0) {
			if(ItemStackUtils.isItemNonNull(inventory.getStackInSlot(0))
					&& TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(0)) > 0) {
				// Fuel is worth half as much as it would be in a furnace.
				burnTime = TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(0)) / 2;
				// TODO Handling 0 size stacks
				inventory.getStackInSlot(0).stackSize--;
				flag = true;
			}
		}
		else {
			for(TileEntityWaterTank waterTank : boiler.getAttachedWaterTanks()) {
				if(waterTank.tank.getFluidAmount() > Fluid.BUCKET_VOLUME) {
					for(TileEntitySteamTank steamTank : boiler.getAttachedSteamTanks()) {
						if(steamTank.tank.fill(new FluidStack(FluidRegistry.getFluid("steam"), Fluid.BUCKET_VOLUME),
								false) == waterTank.tank.drain(Fluid.BUCKET_VOLUME, false).amount) {
							waterTank.tank.drain(Fluid.BUCKET_VOLUME, true);
							steamTank.tank.fill(new FluidStack(FluidRegistry.getFluid("steam"), Fluid.BUCKET_VOLUME),
									true);
							FMLLog.warning("Heating water into steam");
							steamTank.sendBlockUpdate();
							steamTank.markDirty();
							waterTank.sendBlockUpdate();
							waterTank.markDirty();
							flag = true;
						}

						break;
					}
					break;
				}
			}
			burnTime--;
			flag = true;
		}

		if(flag) {
			this.sendBlockUpdate();
			this.markDirty();
		}

		return flag;
	}

	@Override
	protected void readFromDisk(NBTTagCompound data) {
		this.setBurnTime(data.getInteger("burnTime"));
		super.readFromDisk(data);
	};

	@Override
	protected NBTTagCompound writeToDisk(NBTTagCompound data) {
		data.setInteger("burnTime", getBurnTime());
		return super.writeToDisk(data);
	};

	@Override
	protected void readFromUpdatePacket(NBTTagCompound data) {
		this.setBurnTime(data.getInteger("burnTime"));
		super.readFromUpdatePacket(data);
	};

	@Override
	protected NBTTagCompound writeToUpdatePacket(NBTTagCompound data) {
		data.setInteger("burnTime", getBurnTime());
		return super.writeToUpdatePacket(data);
	};

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) inventory;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {
		return false;
	}

	public int getBurnTime() {
		return burnTime;
	}

	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}

	@Override
	public Gui getClientGuiElement(int ID, EntityPlayer player, World world, BlockPos blockPos) {
		return new GuiSolidFirebox(player, this);
	}

	@Override
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, BlockPos blockPos) {
		return new ContainerSolidFirebox(player, this);
	}

	@Override
	public void onSlotChanged(Slot slot) {

	}

	@Override
	public LinkedHashMap<String, String> getDebugStrings(LinkedHashMap<String, String> debugStrings) {
		debugStrings.put("burnTime", "" + burnTime);
		return debugStrings;
	}

	@Override
	public String getPartName() {
		return "Solid Firebox";
	}

}
