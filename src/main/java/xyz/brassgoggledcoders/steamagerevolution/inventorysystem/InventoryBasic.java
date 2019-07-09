package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Maps;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.*;

//TODO add validation to throw errors if duplicate or empty names are used
@SuppressWarnings("rawtypes")
public class InventoryBasic implements IMachineInventory, INBTSerializable<NBTTagCompound> {

	public final IHasInventory parent;
	// TODO Is there a better way to do IDs than strings?
	public HashMap<String, InventoryPieceItemHandler> itemPieces = Maps.newHashMap();
	// TODO Readd support for FluidHandlerMulti?
	public HashMap<String, InventoryPieceFluidTank> fluidPieces = Maps.newHashMap();

	public InventoryBasic(IHasInventory parent) {
		this.parent = parent;
	}

	public InventoryBasic addItemPiece(String name, Pair<int[], int[]> xNy, ItemStackHandler handler) {
		this.addItemPiece(name, xNy.getLeft(), xNy.getRight(), handler);
		return this;
	}

	public InventoryBasic addItemPiece(String name, int[] xPos, int[] yPos, ItemStackHandler handler) {
		if(xPos.length < handler.getSlots() || yPos.length < handler.getSlots()) {
			throw new RuntimeException("Your inventory position array sizes do not match the number of slots");
		}
		itemPieces.put(name, new InventoryPieceItemHandler(name, this, null, handler, xPos, yPos));
		return this;
	}

	public InventoryBasic addFluidPiece(String name, int xPos, int yPos, FluidTankSmart handler) {
		fluidPieces.put(name, new InventoryPieceFluidTank(name, this, handler, xPos, yPos));
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
	public List<ItemStackHandler> getItemHandlers() {
		return itemPieces.values().stream().map(p -> p.getHandler()).collect(Collectors.toList());
	}

	@Override
	public List<FluidTankSmart> getFluidHandlers() {
		return fluidPieces.values().stream().map(p -> p.getHandler()).collect(Collectors.toList());
	}

	// Allows direct interaction with a specific, known, handler of that machine,
	// without having to rely on guessing their positions in an array
	public InventoryPieceItemHandler getItemPiece(String name) {
		return this.itemPieces.get(name);
	}

	// As above
	public InventoryPieceFluidTank getFluidPiece(String name) {
		return this.fluidPieces.get(name);
	}

	public void onContentsChanged(Object handler) {
		this.parent.markMachineDirty();
	}

	// Mainly for enabling callbacks. Probably should be cached.
	public List<InventoryPiece> getInventoryPieces() {
		return Stream.of(itemPieces.values(), fluidPieces.values()).flatMap(x -> x.stream())
				.collect(Collectors.toList());
	}
}
