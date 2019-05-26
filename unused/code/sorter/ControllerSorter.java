package xyz.brassgoggledcoders.steamagerevolution.modules.transport.multiblock.sorter;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.SARMultiblockInventory;

public class ControllerSorter extends SARMultiblockInventory {

	public ItemStackHandler cards = new ItemStackHandler(8);
	public ItemStackHandler buffer = new ItemStackHandler(4);
	private int numberOfRateUpgrades = 1;
	private BiMap<BlockPos, Integer> outputPositions;

	protected ControllerSorter(World world) {
		super(world);
		outputPositions = HashBiMap.create();
	}

	@Override
	protected boolean updateServer() {
		int rate = 1;
		if(numberOfRateUpgrades > 0) {
			// TODO: Maths
			for(int i = 0; i < numberOfRateUpgrades; i++) {
				rate *= 2;
			}
		}

		// TODO: Take this out of tick loop
		for(IMultiblockPart part : connectedParts) {
			if(part instanceof TileEntitySorterOutput) {
				TileEntitySorterOutput buffer = (TileEntitySorterOutput) part;
				if(!outputPositions.containsValue(buffer.getBlockMetadata())) {
					outputPositions.put(buffer.getWorldPosition(), buffer.getBlockMetadata());
					return true;
				}
			}
		}

		for(int i = 0; i < cards.getSlots(); i++) {
			ItemStack card = cards.getStackInSlot(i);
			if(card.hasTagCompound()) {
				NBTTagCompound tag = card.getTagCompound();
				int colorMetadata = tag.getInteger("dye");
				if(outputPositions.containsValue(colorMetadata)) {
					InternalInventoryHandler inventory = new InternalInventoryHandler(16);
					inventory.deserializeNBT(tag.getCompoundTag("inventory"));
					NonNullList<ItemStack> acceptedStacks = inventory.getStackList();
					for(int b = 0; b < buffer.getSlots(); b++) {
						ItemStack current = buffer.getStackInSlot(b);
						current.setCount(rate);
						if(OreDictionary.containsMatch(false, acceptedStacks, current)) {
							ItemStackHandler targetOutput = ((TileEntitySorterOutput) WORLD
									.getTileEntity(outputPositions.inverse().get(colorMetadata))).inventory;
							if(ItemHandlerHelper.insertItem(targetOutput, current, true).isEmpty()) {
								ItemHandlerHelper.insertItem(targetOutput, buffer.extractItem(b, rate, false), false);
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		if(newPart instanceof TileEntitySorterRateUpgrade) {
			numberOfRateUpgrades++;
		}
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		if(oldPart instanceof TileEntitySorterOutput) {
			outputPositions.remove(oldPart.getWorldPosition());
		}
		else if(oldPart instanceof TileEntitySorterRateUpgrade) {
			numberOfRateUpgrades--;
		}
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		cards.deserializeNBT(data.getCompoundTag("cards"));
		buffer.deserializeNBT(data.getCompoundTag("buffer"));
	}

	@Override
	protected void onMachineAssembled() {
		super.onMachineAssembled();
	}

	@Override
	protected void onMachineRestored() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMachinePaused() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMachineDisassembled() {
		// TODO Auto-generated method stub

	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaximumXSize() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getMaximumZSize() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getMaximumYSize() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void onAssimilate(MultiblockControllerBase assimilated) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateClient() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isBlockGoodForFrame(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isBlockGoodForTop(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isBlockGoodForBottom(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isBlockGoodForSides(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void readFromDisk(NBTTagCompound data) {

	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setTag("cards", cards.serializeNBT());
		data.setTag("buffer", buffer.serializeNBT());
	}

	@Override
	public String getName() {
		return "Sorter";
	}

	private static class InternalInventoryHandler extends ItemStackHandler {
		public InternalInventoryHandler(int slots) {
			super(slots);
		}

		public NonNullList<ItemStack> getStackList() {
			return stacks;
		}
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCurrentTicks(int ticks) {
		// TODO Auto-generated method stub

	}

}
