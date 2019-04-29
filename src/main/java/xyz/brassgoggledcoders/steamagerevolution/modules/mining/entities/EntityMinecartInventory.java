package xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities;

import javax.annotation.Nonnull;

import com.teamacronymcoders.base.entities.EntityMinecartBase;
import com.teamacronymcoders.base.guisystem.GuiOpener;
import com.teamacronymcoders.base.guisystem.IHasGui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IMachineHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

public abstract class EntityMinecartInventory<I extends InventoryMachine> extends EntityMinecartBase
		implements IHasGui, IMachineHasInventory<I> {

	I inventory;

	public EntityMinecartInventory(World world) {
		super(world);
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
	public I getInventory() {
		return inventory;
	}

	@Override
	public void setInventory(I inventory) {
		this.inventory = inventory;
	}

}
