package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;

import net.minecraft.nbt.*;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.*;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketFluidUpdate;

//TODO add validation to throw errors if duplicate or empty names are used
@SuppressWarnings("rawtypes")
public class InventoryBasic implements ICapabilitySerializable<NBTTagCompound> {

    @Nonnull
    public final IHasInventory enclosingMachine;

    // Masterlist.
    public HashMap<String, InventoryPiece> inventoryPieces = Maps.newHashMap();

    public HashMap<Class<? extends InventoryPiece>, HashMap<String, InventoryPiece>> pieceLists = Maps.newHashMap();

    // You MUST use the builder...
    public InventoryBasic(IHasInventory parent) {
        this.enclosingMachine = parent;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        if(!inventoryPieces.isEmpty()) {
            NBTTagList list = new NBTTagList();
            inventoryPieces.values().stream().filter(piece -> piece instanceof INBTSerializable)
                    .forEach(piece -> list.appendTag(((INBTSerializable) piece).serializeNBT()));
            tag.setTag("pieces", list);
        }
        return tag;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void deserializeNBT(NBTTagCompound tag) {
        for(NBTBase entry : tag.getTagList("pieces", 10/* List of TagCompounds */)) {
            ((INBTSerializable<NBTTagCompound>) inventoryPieces.get(((NBTTagCompound) entry).getString("name")))
                    .deserializeNBT((NBTTagCompound) entry);
        }
    }

    // TODO
    public List<ItemStackHandler> getItemHandlers() {
        return this.getInventoryPiecesOfType(InventoryPieceItemHandler.class).stream().map(p -> p.getHandler())
                .collect(Collectors.toList());
    }

    // TODO
    public List<FluidTankSync> getFluidHandlers() {
        return this.getInventoryPiecesOfType(InventoryPieceFluidTank.class).stream().map(p -> p.getHandler())
                .collect(Collectors.toList());
    }

    // Allows direct interaction with a specific, known, handler of that machine,
    // without having to rely on guessing their positions in an array
    // TODO these lists should include things that extend the passed in class
    @SuppressWarnings("unchecked")
    public <H extends INBTSerializable<NBTTagCompound>> H getHandler(String name, Class<H> handlerType) {
        if(this.inventoryPieces.containsKey(name)) {
            return ((InventoryPieceHandler<H>) this.inventoryPieces.get(name)).getHandler();
        }
        else {
            return null;
        }
    }

    public void updateFluid(PacketFluidUpdate message) {
        this.getHandler(message.name, FluidTankSync.class).setFluid(message.fluid);
    }

    public Collection<InventoryPiece> getInventoryPieces() {
        return inventoryPieces.values();
    }

    public void createSublists() {
        for(InventoryPiece piece : inventoryPieces.values()) {
            if(!pieceLists.containsKey(piece.getClass())) {
                pieceLists.put(piece.getClass(), new HashMap<>());
            }
            pieceLists.get(piece.getClass()).put(piece.getName(), piece);
        }
    }

    public <P extends InventoryPiece> ArrayList<P> getInventoryPiecesOfType(Class<P> type) {
        if(!pieceLists.containsKey(type)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(pieceLists.get(type).values());
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return false;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return null;
    }
}
