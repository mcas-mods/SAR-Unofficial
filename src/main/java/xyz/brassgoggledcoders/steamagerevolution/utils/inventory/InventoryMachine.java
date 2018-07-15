package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketMultiFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

public class InventoryMachine implements ISARMachineInventory, INBTSerializable<NBTTagCompound>, ISmartTankCallback {

	public InventoryPieceItem itemInput;
	public InventoryPieceFluid fluidInput;
	public InventoryPieceItem itemOutput;
	public InventoryPieceFluid fluidOutput;
	public InventoryPieceFluid steamTank;
	public InventoryPieceProgressBar progressBar;

	// @Deprecated
	public InventoryMachine(InventoryPieceFluid fluidInput, InventoryPieceFluid fluidOutput,
			InventoryPieceFluid steamTank) {
		this(null, fluidInput, null, fluidOutput, steamTank);
	}

	// @Deprecated
	public InventoryMachine(InventoryPieceItem itemInput, InventoryPieceFluid fluidInput, InventoryPieceItem itemOutput,
			InventoryPieceFluid fluidOutput, InventoryPieceFluid steamTank) {
		this.itemInput = itemInput;
		this.fluidInput = fluidInput;
		if(fluidInput != null) {
			this.fluidInput.setTankType(TankType.INPUT);
		}
		this.itemOutput = itemOutput;
		this.fluidOutput = fluidOutput;
		if(fluidOutput != null) {
			this.fluidOutput.setTankType(TankType.OUTPUT);
		}
		this.steamTank = steamTank;
		if(steamTank != null) {
			this.steamTank.setTankType(TankType.STEAM);
		}
	}

	public InventoryMachine setProgressBar(InventoryPieceProgressBar bar) {
		this.progressBar = bar;
		return this;
	}

	public static class InventoryPieceProgressBar extends InventoryPiece {

		public InventoryPieceProgressBar(int xPos, int yPos) {
			super(new int[] { xPos }, new int[] { yPos });
		}

	}

	public static class InventoryPieceFluid extends InventoryPiece {
		private FluidTankSmart handler;

		public InventoryPieceFluid(FluidTankSmart handler, int[] xPositions, int[] yPositions) {
			super(xPositions, yPositions);
			this.handler = handler;
		}

		public InventoryPieceFluid(FluidTankSmart handler, int xPos, int yPos) {
			this(handler, new int[] { xPos }, new int[] { yPos });
		}

		public FluidTankSmart getHandler() {
			return handler;
		}

		public void setTankType(TankType type) {
			handler.setTankType(type);
		}

		@Deprecated
		public void setHandler(FluidTankSmart tank) {
			this.handler = tank;
		}
	}

	public static class InventoryPieceItem extends InventoryPiece {
		private final ItemStackHandlerExtractSpecific handler;

		public InventoryPieceItem(ItemStackHandlerExtractSpecific handler, int[] xPos, int[] yPos) {
			super(xPos, yPos);
			this.handler = handler;
		}

		public InventoryPieceItem(ItemStackHandlerExtractSpecific handler, int xPos, int yPos) {
			this(handler, new int[] { xPos }, new int[] { yPos });
		}

		public ItemStackHandlerExtractSpecific getHandler() {
			return handler;
		}
	}

	protected static class InventoryPiece {
		final int xPos[];
		final int yPos[];

		public InventoryPiece(int[] xPos, int[] yPos) {
			this.xPos = xPos;
			this.yPos = yPos;
		}

		public int getX(int i) {
			return xPos[i];
		}

		public int getY(int i) {
			return yPos[i];
		}
	}

	@Override
	public ItemStackHandlerExtractSpecific getInputHandler() {
		if(itemInput == null) {
			return null;
		}
		return itemInput.getHandler();
	}

	@Override
	public MultiFluidTank getInputTank() {
		if(fluidInput == null) {
			return null;
		}
		// TODO Unsafe cast
		return (MultiFluidTank) fluidInput.getHandler();
	}

	@Override
	public ItemStackHandler getOutputHandler() {
		if(itemOutput == null) {
			return null;
		}
		return itemOutput.getHandler();
	}

	@Override
	public MultiFluidTank getOutputTank() {
		// TODO Unsafe cast
		if(fluidOutput == null) {
			return null;
		}
		return (MultiFluidTank) fluidOutput.getHandler();
	}

	@Override
	public FluidTankSingleSmart getSteamTank() {
		if(steamTank == null) {
			return null;
		}
		return (FluidTankSingleSmart) steamTank.getHandler();
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		if(itemInput != null)
			tag.setTag("itemInput", this.itemInput.handler.serializeNBT());
		if(fluidInput != null)
			tag.setTag("fluidInput", this.fluidInput.handler.writeToNBT(new NBTTagCompound()));
		if(itemOutput != null)
			tag.setTag("itemOutput", this.itemOutput.handler.serializeNBT());
		if(fluidOutput != null)
			tag.setTag("fluidOutput", this.fluidOutput.handler.writeToNBT(new NBTTagCompound()));
		if(steamTank != null)
			tag.setTag("steamTank", this.steamTank.handler.writeToNBT(new NBTTagCompound()));
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		if(tag.hasKey("itemInput") && this.itemInput != null)
			this.itemInput.handler.deserializeNBT(tag.getCompoundTag("itemInput"));
		if(tag.hasKey("fluidInput") && this.fluidInput != null)
			this.fluidInput.handler.readFromNBT(tag.getCompoundTag("fluidInput"));
		if(tag.hasKey("itemOuput") && this.itemOutput != null)
			this.itemOutput.handler.deserializeNBT(tag.getCompoundTag("itemOutput"));
		if(tag.hasKey("fluidOutput") && this.fluidOutput != null)
			this.fluidOutput.handler.readFromNBT(tag.getCompoundTag("fluidOutput"));
		if(tag.hasKey("steamTank") && this.steamTank != null)
			this.steamTank.handler.readFromNBT(tag.getCompoundTag("steamTank"));
	}

	// Methods to enable dynamic tank sizes based on multiblock size
	public void setFluidInput(MultiFluidTank newTank) {
		this.fluidInput = new InventoryPieceFluid(newTank, fluidInput.xPos, fluidInput.yPos);
		this.fluidInput.setTankType(TankType.INPUT);
	}

	public void setFluidOutput(MultiFluidTank newTank) {
		this.fluidOutput = new InventoryPieceFluid(newTank, fluidOutput.xPos, fluidOutput.yPos);
		this.fluidOutput.setTankType(TankType.OUTPUT);
	}

	// Helpers for TE wrappers
	public ItemStackHandlerExtractSpecific getItemHandler(boolean output) {
		return output ? itemOutput.getHandler() : itemInput.getHandler();
	}

	public FluidTankSmart getFluidHandler(boolean output) {
		return output ? fluidOutput.getHandler() : fluidInput.getHandler();
	}

	public FluidTankSingleSmart getSteamHandler() {
		if(steamTank == null) {
			return null;
		}
		return (FluidTankSingleSmart) steamTank.getHandler();
	}

	@Override
	public void onTankContentsChanged(FluidTankSmart tank, TankType type, IHasInventory parent) {
		if(tank instanceof MultiFluidTank) {
			SteamAgeRevolution.instance.getPacketHandler()
					.sendToAllAround(
							new PacketMultiFluidUpdate(parent.getPos(), ((MultiFluidTank) tank).fluids,
									TankType.getNetworkID(type)),
							parent.getPos(), parent.getWorld().provider.getDimension());
		}
		else {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketFluidUpdate(parent.getPos(), tank.getFluid(), TankType.getNetworkID(type)),
					parent.getPos(), parent.getWorld().provider.getDimension());
			// Only steam tank is a single type
			parent.setCurrentRecipe(null);
			parent.setCurrentTicks(0);
		}
		if(this.getInputTank() != null && TankType.INPUT.equals(type)) {
			parent.setCurrentRecipe(null);
			parent.setCurrentTicks(0);
		}
	}

	@Override
	public void updateFluid(PacketFluidUpdate message) {
		this.steamTank.getHandler().setFluid(message.fluid);
	}

	@Override
	public void updateFluid(PacketMultiFluidUpdate message) {
		if(this.getInputTank() != null && TankType.INPUT.equals(TankType.getTypeFromID(message.id))) {
			this.getInputTank().fluids.clear();
			this.getInputTank().fluids.addAll(message.fluids);
		}
		else if(this.getOutputTank() != null && TankType.OUTPUT.equals(TankType.getTypeFromID(message.id))) {
			this.getOutputTank().fluids.clear();
			this.getOutputTank().fluids.addAll(message.fluids);
		}
	}

}
