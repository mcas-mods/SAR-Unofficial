package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities;

import com.teamacronymcoders.base.tileentities.TileEntityBase;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.FluidTankSingleType;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.ModuleSteam;

public class TileEntityDropHammer extends TileEntityBase implements ITickable {

	public TileEntityDropHammer() {
		super();
	}

	private int progress = 0;
	public FluidTank tank = new FluidTankSingleType(Fluid.BUCKET_VOLUME * 4, "steam");
	public String dieType = "";

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
		return super.getCapability(capability, facing);
	}

	public int getProgress() {
		return progress;
	}

	@Override
	public void readFromDisk(NBTTagCompound compound) {
		tank.readFromNBT(compound.getCompoundTag("tank"));
		this.progress = compound.getInteger("progress");
		this.dieType = compound.getString("die");
		super.readFromDisk(compound);
	}

	@Override
	public NBTTagCompound writeToDisk(NBTTagCompound compound) {
		compound.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		compound.setInteger("progress", progress);
		compound.setString("die", dieType);
		return super.writeToDisk(compound);
	}

	@Override
	public void update() {
		if (tank.getFluidAmount() >= Fluid.BUCKET_VOLUME) {
			if (progress < 10) {
				this.progress++;
				tank.drain(Fluid.BUCKET_VOLUME, true);
			} else {
				BlockPos target = getPos().down(2);

				// TODO Metadata and ore dictionary handling.
				if (!getWorld().isAirBlock(target)) {
					Block targetBlock = getWorld().getBlockState(target).getBlock();
					if (targetBlock == ModuleSteam.dropHammerAnvil) {
						TileEntityDropHammerAnvil anvil = (TileEntityDropHammerAnvil) getWorld().getTileEntity(target);
						if (anvil.handler.getStackInSlot(0) != null) {
							ItemStack result = DropHammerRecipes.instance().getResult(anvil.handler.getStackInSlot(0));
							//TODO Use this for recipe registration, no need to check on tick
							for (int oreId : OreDictionary.getOreIDs(anvil.handler.getStackInSlot(0))) {
								String[] splitName = OreDictionary.getOreName(oreId).split("(?=[A-Z])");
								if (splitName.length != 2)
									continue;
								String name = dieType + splitName[1];
								FMLLog.fine("Searching for " + name);
								if (OreDictionary.doesOreNameExist(name)) {
									result = OreDictionary.getOres(name).get(0);
								}
							}
							if (ItemStackUtils.isItemNonNull(result)
									&& anvil.handler.insertItem(1, result, true) == null) {
								anvil.handler.extractItem(0, 1, false);
								anvil.handler.insertItem(1, result, false);
								progress = 0;
								return;
							}
						}
					} else {
						ItemStack result = DropHammerRecipes.instance().getResult(new ItemStack(targetBlock));
						if (result != null) {
							getWorld().setBlockState(target,
									Block.getBlockFromItem(result.getItem()).getDefaultState());
							progress = 0;
							return;
						}
					}
				}
				progress = 0;
			}
		}
	}
}
