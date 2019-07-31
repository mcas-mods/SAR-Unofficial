package xyz.brassgoggledcoders.steamagerevolution.machinesystem;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;

public class MultiblockMachineType extends MachineType {

    private List<Block> parts;

    public MultiblockMachineType(String uid, Block catalyst, Block... requiredParts) {
        super(uid, catalyst);
        this.parts = Lists.newArrayList(requiredParts);
    }

    public MultiblockMachineType(String uid) {
        this(uid, null);
    }

    public List<Block> getRequiredParts() {
        return parts;
    }
}
