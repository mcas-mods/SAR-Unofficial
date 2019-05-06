package xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities;

import com.teamacronymcoders.base.materialsystem.MaterialSystem;
import com.teamacronymcoders.base.materialsystem.items.ItemMaterialPart;
import com.teamacronymcoders.base.materialsystem.materialparts.MaterialPart;
import com.teamacronymcoders.base.materialsystem.materials.Material;

import net.minecraft.block.Block;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.MiningUtils;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.ModuleMining;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.ContainerForceStack;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.HandlerForceStack;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceItem;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryRecipeMachine;

public class EntityMinecartCarrier extends EntityMinecartInventory<InventoryRecipeMachine>  {
	
	public EntityMinecartCarrier(World world) {
		super(world);
		this.setInventory(new InventoryRecipeMachine(new InventoryPieceItem(new HandlerForceStack(this, 8), MiningUtils.getGUIPositionGrid(52, 30, 4, 2)), null, null, null, null));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiInventory(entityPlayer, this, new ContainerForceStack(entityPlayer, this), "carrier_cart");
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new ContainerForceStack(entityPlayer, this);
	}

	@Override
	public ItemMinecart getItem() {
		return ModuleMining.minecart_carrier;
	}
	
	@Override
	public void markDirty() {
		for(int i = 0 ; i < this.getInventory().getInputHandler().getSlots(); i++) {
			ItemStack stack = this.getInventory().getInputHandler().getStackInSlot(i);
			//FIXME Horrible hack
			if(stack.getItem() instanceof ItemMaterialPart) {
				ItemMaterialPart part = (ItemMaterialPart) stack.getItem();
				Material mat = part.getItemMaterialParts().get(stack.getItemDamage()).getMaterial();
				MaterialPart heavy_ore = MaterialSystem.getMaterialPart(mat.getOreDictSuffix().toLowerCase() + "_heavy_ore");
				if(Block.getBlockFromItem(heavy_ore.getItemStack().getItem()) != Blocks.AIR) {
					this.setDisplayTile(Block.getBlockFromItem(heavy_ore.getItemStack().getItem()).getDefaultState());
				}
			}
			if(!stack.isEmpty()) {
				if(Block.getBlockFromItem(stack.getItem()) != Blocks.AIR) {
					this.setDisplayTile(Block.getBlockFromItem(stack.getItem()).getDefaultState());
				}
			}
		}
		super.markDirty();
	}
}
