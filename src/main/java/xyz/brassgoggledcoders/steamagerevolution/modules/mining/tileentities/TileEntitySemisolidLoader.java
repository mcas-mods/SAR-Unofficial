package xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities;

import java.util.List;

import com.teamacronymcoders.base.blocks.properties.SideType;
import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.tileentities.TileEntitySidedBase;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.ContainerInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.HandlerForceStack;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IMachineHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceItem;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

public class TileEntitySemisolidLoader extends TileEntitySidedBase<IItemHandler>
		implements ITickable, IMachineHasInventory<InventoryMachine>, IHasGui {
	InventoryMachine inventory;
	int updateTest = -1;

	public TileEntitySemisolidLoader() {
		this.setInventory(new InventoryMachine(new InventoryPieceItem(new HandlerForceStack(8), new int[] {0,0,0,0,0,0,0,0}, new int[] {0,0,0,0,0,0,0,0}), null, null, null, null));
	}

	@Override
	public void update() {
		if (!this.getWorld().isRemote && this.getWorld().getWorldTime() % 10 == updateTest) {
			int x = this.getPos().getX();
			int y = this.getPos().getY();
			int z = this.getPos().getZ();
			AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x - 1, y - 1, z - 1, x + 2, y + 2, z + 2);
			List<Entity> entities = this.world.getEntitiesInAABBexcluding(null, axisAlignedBB, Entity::isEntityAlive);
			for (EnumFacing facing : EnumFacing.VALUES) {
				SideType sideType = this.getSideValue(facing);
				if (sideType != SideType.NONE) {
					IBlockState otherBlockState = world.getBlockState(this.getPos().offset(facing));
					if (otherBlockState.isFullBlock()) {
						// noinspection ResultOfMethodCallIgnored
						tryTransferToTile(sideType, facing);
					} else {
						if (otherBlockState.getBlock() == Blocks.AIR || !tryTransferToTile(sideType, facing)) {
							for (Entity entity : entities) {
								if (transferToEntity(sideType, facing, entity)) {
									break;
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void onLoad() {
		super.onLoad();
		updateTest = this.getWorld().rand.nextInt(10);
	}

	private boolean transferToEntity(SideType sideType, EnumFacing facing, Entity entity) {
		EnumFacing opposite = facing.getOpposite();
		if (entity.getPosition().equals(this.getPos().offset(facing))) {
			if (entity.hasCapability(this.getCapabilityType(), opposite)) {
				//return transfer(sideType, entity.getCapability(this.getCapabilityType(), opposite));
			}
		}
		return false;
	}

	private boolean transfer(SideType sideType, IItemHandler otherCapability) {
		if (sideType == SideType.INPUT) {
			//return transfer(otherCapability, this.getInternalCapability());
		} else if (sideType == SideType.OUTPUT) {
			//return transfer(this.getInternalCapability(), otherCapability);
		} else {
			return false;
		}
		return false;
	}

	private boolean transfer(IItemHandler from, IItemHandler to) {
//		if (from.getHolders().length > 0 && from.getHolders()[0].getCrushed() != null) {
//			ISemisolid material = from.getHolders()[0].getCrushed().getMaterial();
//			int amount = from.getHolders()[0].getAmount();
//			from.drain(material, amount);
//			to.fill(new SemisolidStack(material, amount));
//		}
		return false;
	}

	private boolean tryTransferToTile(SideType sideType, EnumFacing facing) {
		return false;
//		return Optional.ofNullable(world.getTileEntity(this.getPos().offset(facing)))
//				.filter(tileEntity -> tileEntity.hasCapability(this.getCapabilityType(), facing.getOpposite()))
//				.map(tileEntity -> tileEntity.getCapability(this.getCapabilityType(), facing.getOpposite()))
//				.map(cap -> transfer(sideType, cap)).orElse(false);
	}

	@Override
	protected void readCapability(NBTTagCompound data) {
		this.inventory.deserializeNBT(data.getCompoundTag("inventory"));
	}

	@Override
	protected void writeCapability(NBTTagCompound data) {
		data.setTag("inventory", this.inventory.serializeNBT());
	}

	@Override
	public Capability<IItemHandler> getCapabilityType() {
		return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	}

	@Override
	public IItemHandler getInternalCapability() {
		return this.inventory.getInputHandler();
	}

	@Override
	public IItemHandler getOutputCapability() {
		return this.inventory.getOutputHandler();
	}

	@Override
	public IItemHandler getInputCapability() {
		return this.inventory.getInputHandler();
	}

	@Override
	public World getMachineWorld() {
		return this.getWorld();
	}

	@Override
	public BlockPos getMachinePos() {
		return this.getPos();
	}

	@Override
	public String getName() {
		return "Crushed Material Loader";
	}

	@Override
	public InventoryMachine getInventory() {
		return this.inventory;
	}

	@Override
	public void setInventory(InventoryMachine inventory) {
		this.inventory = inventory;
	}

	@Override
	public SARMachineRecipe getCurrentRecipe() {
		return null;
	}

	@Override
	public void setCurrentRecipe(SARMachineRecipe recipe) {

	}

	@Override
	public int getCurrentProgress() {
		return 0;
	}

	@Override
	public int getCurrentMaxTicks() {
		return 0;
	}

	@Override
	public void setCurrentTicks(int ticks) {

	}

	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiInventory(entityPlayer, this);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new ContainerInventory(entityPlayer, this);
	}
}