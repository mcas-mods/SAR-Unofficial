package xyz.brassgoggledcoders.steamagerevolution.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.ISARMachineInventory;

public class InventoryMachine implements ISARMachineInventory, INBTSerializable<NBTTagCompound> {

	public final InventoryPieceItem itemInput;
	public final InventoryPieceFluid fluidInput;
	public final InventoryPieceItem itemOutput;
	public final InventoryPieceFluid fluidOutput;
	public final InventoryPieceFluid steamTank;

	public InventoryMachine(InventoryPieceItem itemInput, InventoryPieceFluid fluidInput, InventoryPieceItem itemOutput,
			InventoryPieceFluid fluidOutput, InventoryPieceFluid steamTank) {
		this.itemInput = itemInput;
		this.fluidInput = fluidInput;
		this.itemOutput = itemOutput;
		this.fluidOutput = fluidOutput;
		this.steamTank = steamTank;
	}

	public static class InventoryPieceFluid extends InventoryPiece {
		private final FluidTankSmart handler;

		public InventoryPieceFluid(FluidTankSmart handler, int[] xPos, int[] yPos) {
			super(xPos, yPos);
			this.handler = handler;
		}

		public InventoryPieceFluid(FluidTankSmart handler2, int i, int j) {
			this(handler2, new int[] { i }, new int[] { j });
		}

		public FluidTankSmart getHandler() {
			return handler;
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

	public static class InventoryPiece {
		private final int xPos[], yPos[];

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
	public ItemStackHandlerExtractSpecific getItemInput() {
		return itemInput.getHandler();
	}

	@Override
	public MultiFluidTank getFluidInputs() {
		// TODO Unsafe cast
		return (MultiFluidTank) fluidInput.getHandler();
	}

	@Override
	public ItemStackHandler getItemOutput() {
		return itemOutput.getHandler();
	}

	@Override
	public MultiFluidTank getFluidOutputs() {
		// TODO Unsafe cast
		return (MultiFluidTank) fluidOutput.getHandler();
	}

	@Override
	public FluidTankSingleSmart getSteamTank() {
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
		if(tag.hasKey("itemInput"))
			this.itemInput.handler.deserializeNBT(tag.getCompoundTag("itemInput"));
		if(tag.hasKey("fluidInput"))
			this.fluidInput.handler.readFromNBT(tag.getCompoundTag("fluidInput"));
		if(tag.hasKey("itemOuput"))
			this.itemOutput.handler.deserializeNBT(tag.getCompoundTag("itemOutput"));
		if(tag.hasKey("fluidOutput"))
			this.fluidOutput.handler.readFromNBT(tag.getCompoundTag("fluidOutput"));
		if(tag.hasKey("steamTank"))
			this.steamTank.handler.readFromNBT(tag.getCompoundTag("steamTank"));
	}

}
