package xyz.brassgoggledcoders.steamagerevolution.entities;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBasic;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryBuilder;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GUIInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.MiningUtils;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.ContainerForceStack;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.HandlerForceStack;

public class EntityMinecartCarrier extends EntityMinecartInventory<InventoryBasic> {

	public static final DataParameter<ItemStack> CONTENTS = EntityDataManager.createKey(EntityMinecartCarrier.class,
			DataSerializers.ITEM_STACK);

	public EntityMinecartCarrier(World world) {
		super(world);
		Pair<int[], int[]> positions = MiningUtils.getGUIPositionGrid(53, 31, 4, 2);
		this.setInventory(new InventoryBuilder<InventoryBasic>(new InventoryBasic(this)).addPiece("inventory",
				new InventoryPieceItemHandler(new HandlerForceStack(8), positions.getRight(), positions.getLeft()))
				.build());
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(CONTENTS, ItemStack.EMPTY);
		super.entityInit();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GUIInventory(entityPlayer, this, new ContainerForceStack(entityPlayer, this));
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new ContainerForceStack(entityPlayer, this);
	}

	@Override
	public ItemMinecart getItem() {
		return SARObjectHolder.minecart_carrier;
	}

	@Override
	public void markDirty() {
		for(int i = 0; i < this.getInventory().getItemHandlers().get(0).getSlots(); i++) {
			ItemStack stack = this.getInventory().getItemHandlers().get(0).getStackInSlot(i);
			if(!stack.isEmpty()) {
				this.getDataManager().set(CONTENTS, stack);
				break;
			}
		}
	}

	@Override
	public String getUID() {
		return "carrier_cart";
	}
}
