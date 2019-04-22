package xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.teamacronymcoders.base.entities.EntityMinecartBase;
import com.teamacronymcoders.base.guisystem.GuiOpener;
import com.teamacronymcoders.base.guisystem.IHasGui;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemMinecart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.SARCapabilities;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidHolder;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.items.ItemMinecartSemisolid;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities.GuiSemisolid;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities.InventoryPieceSemisolid;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities.InventorySemisolid;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IMachineHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

public class EntityMinecartSemisolid extends EntityMinecartBase implements IHasGui, IMachineHasInventory<InventorySemisolid> {

	@ObjectHolder(SteamAgeRevolution.MODID + ":minecart_ore_carrier")
	private static ItemMinecartSemisolid itemMinecartSemisolid;

	InventorySemisolid inventory;

	public EntityMinecartSemisolid(World world) {
		super(world);
		this.setInventory(new InventorySemisolid(
				new InventoryPieceSemisolid(new CrushedHandlerCart(this, new SemisolidHolder(15)), 83, 16)));
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == SARCapabilities.SEMISOLID_HANDLER || super.hasCapability(capability, facing);
	}

	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == SARCapabilities.SEMISOLID_HANDLER) {
			return SARCapabilities.SEMISOLID_HANDLER.cast(inventory.ore.getHandler());
		}
		return super.getCapability(capability, facing);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.inventory.deserializeNBT(compound.getCompoundTag("inventory"));
	}

	@Override
	protected void writeEntityToNBT(@Nonnull NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setTag("inventory", this.inventory.serializeNBT());
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		GuiOpener.openEntityGui(SteamAgeRevolution.instance, this, player, world);
		return super.processInitialInteract(player, hand);
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiSemisolid(entityPlayer, this, "crushed_single");
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemMinecart getItem() {
		return itemMinecartSemisolid;
	}

	@Override
	public World getMachineWorld() {
		return this.getEntityWorld();
	}

	@Override
	public BlockPos getMachinePos() {
		return this.getPosition();
	}

	@Override
	public SARMachineRecipe getCurrentRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCurrentRecipe(SARMachineRecipe recipe) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCurrentProgress() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentMaxTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCurrentTicks(int ticks) {
		// TODO Auto-generated method stub

	}

	@Override
	public InventorySemisolid getInventory() {
		return inventory;
	}

	@Override
	public void setInventory(InventorySemisolid inventory) {
		this.inventory = inventory;
	}

}
