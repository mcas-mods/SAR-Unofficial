package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.boiler;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.FMLLog;
import xyz.brassgoggledcoders.boilerplate.multiblock.IMultiblockPart;
import xyz.brassgoggledcoders.boilerplate.multiblock.MultiblockControllerBase;
import xyz.brassgoggledcoders.boilerplate.multiblock.rectangular.RectangularMultiblockControllerBase;
import xyz.brassgoggledcoders.boilerplate.multiblock.validation.IMultiblockValidator;

public class BasicBoilerController extends RectangularMultiblockControllerBase {

	// Lists of connected parts
	private Set<TileEntityWaterInput> attachedInputs;
	private Set<TileEntityWaterTank> attachedWaterTanks;
	private Set<TileEntitySteamOutput> attachedOutputs;
	private Set<TileEntitySteamTank> attachedSteamTanks;
	private Set<TileEntitySolidFirebox> attachedFireboxes;

	protected BasicBoilerController(World world) {
		super(world);
		attachedInputs = new HashSet<TileEntityWaterInput>();
		attachedWaterTanks = new HashSet<TileEntityWaterTank>();
		attachedOutputs = new HashSet<TileEntitySteamOutput>();
		attachedSteamTanks = new HashSet<TileEntitySteamTank>();
		attachedFireboxes = new HashSet<TileEntitySolidFirebox>();
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		this.readFromDisk(data);
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		// FMLLog.warning("Part added " + newPart.toString());
		if(newPart instanceof TileEntityWaterInput) {
			attachedInputs.add((TileEntityWaterInput) newPart);
		}
		else if(newPart instanceof TileEntityWaterTank) {
			attachedWaterTanks.add((TileEntityWaterTank) newPart);
		}
		else if(newPart instanceof TileEntitySteamOutput) {
			attachedOutputs.add((TileEntitySteamOutput) newPart);
		}
		else if(newPart instanceof TileEntitySteamTank) {
			attachedSteamTanks.add((TileEntitySteamTank) newPart);
		}
		// TODO Not only solid
		else if(newPart instanceof TileEntitySolidFirebox) {
			attachedFireboxes.add((TileEntitySolidFirebox) newPart);
		}
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		if(oldPart instanceof TileEntityWaterInput) {
			attachedInputs.remove((TileEntityWaterInput) oldPart);
		}
		else if(oldPart instanceof TileEntityWaterTank) {
			attachedWaterTanks.remove((TileEntityWaterTank) oldPart);
		}
		else if(oldPart instanceof TileEntitySteamOutput) {
			attachedOutputs.remove((TileEntitySteamOutput) oldPart);
		}
		else if(oldPart instanceof TileEntitySteamTank) {
			attachedSteamTanks.remove((TileEntitySteamTank) oldPart);
		}
		else if(oldPart instanceof TileEntitySolidFirebox) {
			attachedFireboxes.remove((TileEntitySolidFirebox) oldPart);
		}
	}

	@Override
	protected void onMachineAssembled() {}

	@Override
	protected void onMachineRestored() {}

	@Override
	protected void onMachinePaused() {}

	@Override
	protected void onMachineDisassembled() {}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 26;
	}

	@Override
	protected int getMaximumXSize() {
		return 3;
	}

	@Override
	protected int getMaximumZSize() {
		return 3;
	}

	@Override
	protected int getMaximumYSize() {
		return 3;
	}

	@Override
	protected void onAssimilate(MultiblockControllerBase assimilated) {

	}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {
		this.attachedInputs.clear();
		this.attachedWaterTanks.clear();
	}

	@Override
	protected boolean updateServer() {
		boolean flag = false;

		for(TileEntityWaterInput input : attachedInputs) {
			if(input.buffer.getFluidAmount() > 0) {
				for(TileEntityWaterTank tank : attachedWaterTanks) {
					if(tank.tank.fill(input.buffer.getFluid(), false) != 0) {
						tank.tank.fill(input.buffer.getFluid(), true);
						input.buffer.drain(input.buffer.getFluidAmount(), true);
						flag = true;
						FMLLog.warning("Water moved");
						break;
					}
				}
			}
		}

		for(TileEntitySolidFirebox firebox : attachedFireboxes) {
			// TODO Burn time handling
			for(TileEntityWaterTank waterTank : attachedWaterTanks) {
				if(waterTank.tank.getFluidAmount() > Fluid.BUCKET_VOLUME) {
					for(TileEntitySteamTank steamTank : attachedSteamTanks) {
						if(steamTank.tank.fill(new FluidStack(FluidRegistry.getFluid("steam"), Fluid.BUCKET_VOLUME),
								false) == waterTank.tank.drain(Fluid.BUCKET_VOLUME, false).amount) {
							waterTank.tank.drain(Fluid.BUCKET_VOLUME, true);
							steamTank.tank.fill(new FluidStack(FluidRegistry.getFluid("steam"), Fluid.BUCKET_VOLUME),
									true);
							FMLLog.warning("Heating water into steam");
						}

						break;
					}
					break;
				}
			}
		}

		// for(TileEntitySteamOutput output : attachedOutputs) {
		// if(output.buffer.getFluidAmount() == 0) {
		// for(TileEntitySteamTank tank : attachedSteamTanks) {
		// if(tank.tank.drain(Fluid.BUCKET_VOLUME, false).amount != 0
		// && output.buffer.fill(tank.tank.drain(Fluid.BUCKET_VOLUME, false), false) != 0) {
		// output.buffer.fill(tank.tank.drain(Fluid.BUCKET_VOLUME, true), true);
		// FMLLog.warning("Steam moved");
		// break;
		// }
		// }
		// }
		// }

		return flag;
	}

	@Override
	protected void updateClient() {}

	@Override
	protected boolean isBlockGoodForFrame(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean isBlockGoodForTop(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean isBlockGoodForBottom(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean isBlockGoodForSides(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void readFromDisk(NBTTagCompound data) {

	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFromUpdatePacket(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeToUpdatePacket(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

}
