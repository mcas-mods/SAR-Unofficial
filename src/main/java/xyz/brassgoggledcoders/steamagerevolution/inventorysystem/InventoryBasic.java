package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.invpieces.InventoryPieceHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.invpieces.InventoryPieceProgressBar;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.ISmartTankCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ISmartStackCallback;

@SuppressWarnings("rawtypes")
public class InventoryBasic
		implements IMachineInventory, INBTSerializable<NBTTagCompound>, ISmartTankCallback, ISmartStackCallback {

	public List<InventoryPieceHandler<ItemStackHandler>> itemPieces = new ArrayList<>();
	// TODO Readd support for FluidHandlerMulti?
	public List<InventoryPieceHandler<? extends FluidTankSmart>> fluidPieces = new ArrayList<>();
	public InventoryPieceProgressBar progressBar;

	public InventoryBasic addItemPiece(Pair<int[], int[]> xNy, ItemStackHandler handler) {
		this.addItemPiece(xNy.getLeft(), xNy.getRight(), handler);
		return this;
	}

	public InventoryBasic addItemPiece(int[] xPos, int[] yPos, ItemStackHandler handler) {
		if(xPos.length < handler.getSlots() || yPos.length < handler.getSlots()) {
			throw new RuntimeException("Your inventory position array sizes do not match the number of slots");
		}
		itemPieces.add(new InventoryPieceHandler<ItemStackHandler>(handler, xPos, yPos));
		return this;
	}

	public InventoryBasic addFluidPiece(int xPos, int yPos, FluidTankSmart handler) {
		fluidPieces.add(new InventoryPieceHandler<FluidTankSmart>(handler, xPos, yPos));
		return this;
	}

	public InventoryBasic setProgressBar(InventoryPieceProgressBar bar) {
		progressBar = bar;
		return this;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		if(!itemPieces.isEmpty()) {
			tag.setInteger("itemPieces", itemPieces.size());
			for(int i = 0; i < itemPieces.size(); i++) {
				tag.setTag("itemHandler" + i, itemPieces.get(i).getHandler().serializeNBT());
			}
		}
		if(!fluidPieces.isEmpty()) {
			tag.setInteger("fluidPieces", fluidPieces.size());
			for(int i = 0; i < fluidPieces.size(); i++) {
				tag.setTag("fluidHandler" + i, fluidPieces.get(i).getHandler().serializeNBT());
			}
		}
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		for(int i = 0; i < tag.getInteger("itemPieces"); i++) {
			itemPieces.get(i).getHandler().deserializeNBT(tag.getCompoundTag("itemHandler" + i));
		}
		for(int i = 0; i < tag.getInteger("fluidPieces"); i++) {
			fluidPieces.get(i).getHandler().deserializeNBT(tag.getCompoundTag("fluidHandler" + i));
		}
	}

	// Methods to enable dynamic tank sizes based on multiblock size
	@Deprecated
	public void setFluidInput(FluidTankSmart newTank) {
		// fluidInputPiece = new InventoryPieceHandler<FluidTankSmart>(IOType.INPUT,
		// newTank, fluidInputPiece.xPos,
		// fluidInputPiece.yPos);
	}

	@Deprecated
	public void setFluidOutput(FluidTankSmart newTank) {
		// fluidOutputPiece = new InventoryPieceHandler<FluidTankSmart>(IOType.OUTPUT,
		// newTank, fluidOutputPiece.xPos,
		// fluidOutputPiece.yPos);
	}

	@Override
	public void onTankContentsChanged(FluidTankSmart tank, IOType type, IMachineHasInventory parent) {
		SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
				new PacketFluidUpdate(parent.getMachinePos(), tank.getFluid(), type.networkID), parent.getMachinePos(),
				parent.getMachineWorld().provider.getDimension());
		// If we have a recipe, when a tank is changed, check input tank(s), if they
		// were emptied. You can't hotswap fluids like you can items.
		// if(type == IOType.INPUT && getInputTank() != null &&
		// getInputTank().getFluidAmount() == 0) {
		// parent.setCurrentRecipe(null);
		// parent.setCurrentTicks(0);
		// }
	}

	// TODO
	@Override
	public void updateFluid(PacketFluidUpdate message) {
		// if(getInputFluidHandler() != null && IOType.INPUT.networkID ==
		// message.typeID) {
		// this.getInputTank().setFluid(message.fluid);
		// }
		// else if(getOutputFluidHandler() != null && IOType.OUTPUT.networkID ==
		// message.typeID) {
		// this.getOutputTank().setFluid(message.fluid);
		// }
	}

	@Override
	public void onContentsChanged(IOType type, int slot, IMachineHasInventory parent) {
		// if(type.equals(IOType.INPUT)) {
		// // If we have a recipe, when a slot is changed, check if it was changed to
		// air,
		// // or an item that doesn't match the recipe, and if so reset the recipe
		// if(parent.getCurrentRecipe() != null &&
		// this.getInputHandler().getStackInSlot(slot).isEmpty()) {
		// SteamAgeRevolution.instance.getLogger().devInfo("Resetting recipe due to item
		// change");
		// parent.setCurrentRecipe(null);
		// parent.setCurrentTicks(0);
		// }
		// }
	}

	@Override
	public List<ItemStackHandler> getItemHandlers() {
		return itemPieces.stream().map(p -> p.getHandler()).collect(Collectors.toList());
	}

	@Override
	public List<FluidTankSmart> getFluidHandlers() {
		return fluidPieces.stream().map(p -> p.getHandler()).collect(Collectors.toList());
	}

}
