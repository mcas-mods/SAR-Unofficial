package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.ContainerInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.multiblock.SARMultiblockInventory;

public abstract class SARMultiblockRecipe<I extends InventoryRecipe> extends SARMultiblockInventory<I> {

	protected SARMultiblockRecipe(World world) {
		super(world);
	}

	@Override
	protected boolean updateServer() {
		onTick();
		return this.getInventory().onTick();
	}

	protected void onTick() {
		// NO-OP
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiInventory(entityPlayer, this);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new ContainerInventory(entityPlayer, this);
	}

	@Override
	public void markMachineDirty() {
		this.markReferenceCoordDirty();
	}
}
