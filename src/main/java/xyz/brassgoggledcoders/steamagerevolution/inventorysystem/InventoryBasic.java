package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Maps;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceProgressBar;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.FluidTankSmart;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.ISmartTankCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ISmartStackCallback;

//TODO add validation to throw errors if duplicate or empty names are used
@SuppressWarnings("rawtypes")
public class InventoryBasic
		implements IMachineInventory, INBTSerializable<NBTTagCompound>, ISmartTankCallback, ISmartStackCallback {

	// TODO Is there a better way to do IDs than strings?
	public HashMap<String, InventoryPieceHandler<? extends ItemStackHandler>> itemPieces = Maps.newHashMap();
	// TODO Readd support for FluidHandlerMulti?
	public HashMap<String, InventoryPieceHandler<? extends FluidTankSmart>> fluidPieces = Maps.newHashMap();
	public InventoryPieceProgressBar progressBar;

	public InventoryBasic addItemPiece(String name, Pair<int[], int[]> xNy, ItemStackHandler handler) {
		this.addItemPiece(name, xNy.getLeft(), xNy.getRight(), handler);
		return this;
	}

	public InventoryBasic addItemPiece(String name, int[] xPos, int[] yPos, ItemStackHandler handler) {
		if(xPos.length < handler.getSlots() || yPos.length < handler.getSlots()) {
			throw new RuntimeException("Your inventory position array sizes do not match the number of slots");
		}
		itemPieces.put(name, new InventoryPieceHandler<ItemStackHandler>(name, handler, xPos, yPos));
		return this;
	}

	public InventoryBasic addFluidPiece(String name, int xPos, int yPos, FluidTankSmart handler) {
		fluidPieces.put(name, new InventoryPieceHandler<FluidTankSmart>(name, handler, xPos, yPos));
		return this;
	}

	public InventoryBasic setProgressBar(InventoryPieceProgressBar bar) {
		progressBar = bar;
		return this;
	}

	// TODO Save to NBT by name
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
		return itemPieces.values().stream().map(p -> p.getHandler()).collect(Collectors.toList());
	}

	@Override
	public List<FluidTankSmart> getFluidHandlers() {
		return fluidPieces.values().stream().map(p -> p.getHandler()).collect(Collectors.toList());
	}

	// Allows direct interaction with a specific, known, handler of that machine,
	// without having to rely on guessing their positions in an array
	public InventoryPieceHandler<? extends ItemStackHandler> getItemPiece(String name) {
		return this.itemPieces.get(name);
	}

	public InventoryPieceHandler<? extends FluidTankSmart> getFluidPiece(String name) {
		return this.fluidPieces.get(name);
	}

}
