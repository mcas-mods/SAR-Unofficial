package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;

import net.minecraft.nbt.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.*;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;

//TODO add validation to throw errors if duplicate or empty names are used
@SuppressWarnings("rawtypes")
public class InventoryBasic implements INBTSerializable<NBTTagCompound> {

	@Nonnull
	public final IHasInventory parent;

	// Masterlist. Pieces are added to this list, and relevant sublists, in their
	// constructor
	public HashMap<String, InventoryPiece> inventoryPieces = Maps.newHashMap();

	// Typed sublists
	public HashMap<String, InventoryPieceItemHandler> itemPieces = Maps.newHashMap();
	public HashMap<String, InventoryPieceFluidTank> fluidPieces = Maps.newHashMap();

	public InventoryBasic(IHasInventory parent) {
		this.parent = parent;
	}

	public InventoryBasic addItemPiece(String name, int[] xPos, int[] yPos, ItemStackHandlerSync handler) {
		if(xPos.length < handler.getSlots() || yPos.length < handler.getSlots()) {
			throw new RuntimeException("Your inventory position array sizes do not match the number of slots");
		}
		new InventoryPieceItemHandler(name, this, null, handler, xPos, yPos);
		return this;
	}

	public InventoryBasic addFluidPiece(String name, int xPos, int yPos, int capacity) {
		fluidPieces.put(name,
				new InventoryPieceFluidTank(name, this, new FluidTankSync(name, capacity, parent), xPos, yPos));
		return this;
	}

	// TODO Genericise
	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		if(!itemPieces.isEmpty()) {
			NBTTagList list = new NBTTagList();
			itemPieces.forEach((name, piece) -> list.appendTag(piece.serializeNBT()));
			tag.setTag("itemPieces", list);
		}
		if(!fluidPieces.isEmpty()) {
			NBTTagList list = new NBTTagList();
			fluidPieces.forEach((name, piece) -> list.appendTag(piece.serializeNBT()));
			tag.setTag("fluidPieces", list);
		}
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound tag) {
		for(NBTBase iTag : tag.getTagList("itemPieces", 10/* List of TagCompounds */)) {
			itemPieces.get(((NBTTagCompound) iTag).getString("name")).deserializeNBT((NBTTagCompound) iTag);
		}
		for(NBTBase fTag : tag.getTagList("fluidPieces", 10/* List of TagCompounds */)) {
			fluidPieces.get(((NBTTagCompound) fTag).getString("name")).deserializeNBT((NBTTagCompound) fTag);
		}
	}

	public List<ItemStackHandler> getItemHandlers() {
		return itemPieces.values().stream().map(p -> p.getHandler()).collect(Collectors.toList());
	}

	public List<FluidTankSync> getFluidHandlers() {
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

	public void updateFluid(PacketFluidUpdate message) {
		this.getFluidPiece(message.name).getHandler().setFluid(message.fluid);
	}

	public Collection<InventoryPiece> getInventoryPieces() {
		return inventoryPieces.values();
	}
}
