package xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.furnace;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.RectangularMultiblockController;

public class FurnaceController extends RectangularMultiblockController {

	private Set<TileEntityFurnaceItemInput> attachedItemInputs;
	private Set<TileEntityFurnaceItemOutput> attachedItemOutputs;

	private CombinedInvWrapper combinedInputInventory;
	private IItemHandlerModifiable combinedOutputInventory;

	private boolean needsToRebuildInputInventory;
	private boolean needsToRebuildOutputInventory;

	public FurnaceController(World world) {
		super(world);
		attachedItemInputs = new HashSet<TileEntityFurnaceItemInput>();
		attachedItemOutputs = new HashSet<TileEntityFurnaceItemOutput>();
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		if(newPart instanceof TileEntityFurnaceItemInput) {
			attachedItemInputs.add((TileEntityFurnaceItemInput) newPart);
			needsToRebuildInputInventory = true;
		}
		else if(newPart instanceof TileEntityFurnaceItemOutput) {
			attachedItemOutputs.add((TileEntityFurnaceItemOutput) newPart);
			needsToRebuildOutputInventory = true;
		}
		super.onBlockAdded(newPart);
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		if(oldPart instanceof TileEntityFurnaceItemInput) {
			attachedItemInputs.remove(oldPart);
			needsToRebuildInputInventory = true;
		}
		else if(oldPart instanceof TileEntityFurnaceItemOutput) {
			attachedItemOutputs.remove(oldPart);
			needsToRebuildOutputInventory = true;
		}
		super.onBlockRemoved(oldPart);
	}

	@Override
	protected boolean updateServer() {
		super.updateServer();
		boolean flag = false;
		// FMLLog.warning("ticking");
		if(needsToRebuildInputInventory) {
			FMLLog.warning("Rebuilding input combi-inventory");
			ArrayList<IItemHandlerModifiable> inventories = new ArrayList<IItemHandlerModifiable>();
			for(TileEntityFurnaceItemInput input : attachedItemInputs) {
				inventories.add((IItemHandlerModifiable) input
						.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
			}
			combinedInputInventory = new CombinedInvWrapper(inventories.toArray(new IItemHandlerModifiable[0]));
			needsToRebuildInputInventory = false;
			FMLLog.warning(combinedInputInventory.toString());
			return flag;
		}
		if(needsToRebuildOutputInventory) {
			ArrayList<IItemHandlerModifiable> inventories = new ArrayList<IItemHandlerModifiable>();
			for(TileEntityFurnaceItemOutput output : attachedItemOutputs) {
				inventories.add((IItemHandlerModifiable) output
						.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
			}
			combinedOutputInventory = new CombinedInvWrapper(inventories.toArray(new IItemHandlerModifiable[0]));
			needsToRebuildOutputInventory = false;
			return flag;
		}

		for(int i = 0; i < combinedInputInventory.getSlots(); i++) {
			if(ItemStackUtils.isItemNonNull(combinedInputInventory.getStackInSlot(i)) && ItemStackUtils.isItemNonNull(
					SteamFurnaceRecipes.instance().getResult(combinedInputInventory.getStackInSlot(i)))) {
				ItemStack resultItem =
						SteamFurnaceRecipes.instance().getResult(combinedInputInventory.getStackInSlot(i));
				if(ItemHandlerHelper.insertItem(combinedOutputInventory, resultItem, true) == null) {
					ItemHandlerHelper.insertItem(combinedOutputInventory, resultItem, false);
					combinedInputInventory.getStackInSlot(i).splitStack(resultItem.stackSize);
					break;
				}
			}
		}

		return flag;
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		if(world.isAirBlock(new BlockPos(x, y, z)))
			return true;
		else
			return false;
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 1;
		// return 26;
	}

	@Override
	protected int getMinimumXSize() {
		return 3;
	}

	@Override
	protected int getMinimumZSize() {
		return 3;
	}

	@Override
	protected int getMinimumYSize() {
		return 3;
	}

	@Override
	protected int getMaximumXSize() {
		return 6;
	}

	@Override
	protected int getMaximumZSize() {
		return 6;
	}

	@Override
	protected int getMaximumYSize() {
		return 6;
	}

	@Override
	public void readFromDisk(NBTTagCompound data) {
		needsToRebuildInputInventory = true;
		needsToRebuildOutputInventory = true;
		super.readFromDisk(data);
	}

}
