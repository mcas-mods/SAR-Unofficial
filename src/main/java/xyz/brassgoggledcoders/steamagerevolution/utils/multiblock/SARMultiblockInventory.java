package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.network.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.*;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ISmartStackCallback;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.RecipeMachineHelper;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

public abstract class SARMultiblockInventory extends SARMultiblockBase
		implements ISmartTankCallback, ISmartStackCallback, IHasInventory {

	protected int currentTicks = 0;
	SARMachineRecipe currentRecipe;
	@SideOnly(Side.CLIENT)
	public int currentRecipeMaxTicks;
	public InventoryMachine inventory;

	protected SARMultiblockInventory(World world) {
		super(world);
	}

	public void setInventory(InventoryMachine inventory) {
		this.inventory = inventory;
	}

	@Override
	protected boolean updateServer() {
		onTick();
		if(canRun()) {
			onActiveTick();
			currentTicks++;
			if(canFinish()) {
				onFinish();
			}
			return true; // TODO
		}
		return false;
	}

	// 'Simulate' recipe progress on client for progress bar rendering
	@Override
	protected void updateClient() {
		if(currentRecipeMaxTicks != 0) {
			if(currentRecipeMaxTicks >= currentTicks) {
				currentTicks++;
			}
			else {
				currentTicks = 0;
			}
		}
	}

	protected void onTick() {
		// NO-OP
	}

	protected void onActiveTick() {
		// NO-OP
	}

	protected void onFinish() {
		RecipeMachineHelper.onFinish(currentRecipe, inventory);
		currentTicks = 0;
		currentRecipe = null; // TODO Only null when inputs hit zero
	}

	protected boolean canFinish() {
		return RecipeMachineHelper.canFinish(currentTicks, currentRecipe, inventory);
	}

	protected boolean canRun() {
		return RecipeMachineHelper.canRun(WORLD, this.getReferenceCoord(), this,
				getName().toLowerCase()/* .replace(' ', '_')TODO */, currentRecipe, inventory);
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		currentTicks = data.getInteger("progress");
		inventory.deserializeNBT(data.getCompoundTag("inventory"));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setInteger("progress", currentTicks);
		data.setTag("inventory", inventory.serializeNBT());
	}

	@Override
	public void onTankContentsChanged(FluidTankSmart tank) {
		if(tank instanceof MultiFluidTank) {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketMultiFluidUpdate(getReferenceCoord(), ((MultiFluidTank) tank), tank.getId()),
					getReferenceCoord(), WORLD.provider.getDimension());
		}
		else {
			SteamAgeRevolution.instance.getPacketHandler().sendToAllAround(
					new PacketFluidUpdate(getReferenceCoord(), tank.getFluid(), tank.getId()), getReferenceCoord(),
					WORLD.provider.getDimension());
		}
	}

	@Override
	public void updateFluid(PacketFluidUpdate message) {
		inventory.getSteamTank().setFluid(message.fluid);
	}

	@Override
	public void updateFluid(PacketMultiFluidUpdate message) {
		// TODO
		if(message.id == 0) {
			this.inventory.getInputTank().fluids.clear();
			this.inventory.getInputTank().fluids.addAll(message.tank.fluids);
		}
		else if(message.id == 1) {
			this.inventory.getOutputTank().fluids.clear();
			this.inventory.getOutputTank().fluids.addAll(message.tank.fluids);
		}
	}

	@Override
	public void updateStack(PacketItemUpdate message) {
		// TODO
	}

	@Override
	public void onContentsChanged(int slot) {
		// TODO
	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		// TODO Auto-generated method stub
		return new GuiInventory(entityPlayer, this);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		// TODO Auto-generated method stub
		return new ContainerInventory(entityPlayer, this);
	}

	@Override
	public InventoryMachine getInventory() {
		return inventory;
	}

	@Override
	public void setCurrentRecipe(SARMachineRecipe recipe) {
		this.currentRecipe = recipe;
	}

	@Override
	public SARMachineRecipe getCurrentRecipe() {
		return currentRecipe;
	}

	@Override
	public int getCurrentProgress() {
		return currentTicks;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getCurrentMaxTicks() {
		return currentRecipeMaxTicks;
	}
}
