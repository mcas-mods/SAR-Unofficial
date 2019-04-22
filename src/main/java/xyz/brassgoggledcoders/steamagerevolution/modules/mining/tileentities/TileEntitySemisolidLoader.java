package xyz.brassgoggledcoders.steamagerevolution.modules.mining.tileentities;

import java.util.List;
import java.util.Optional;

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
import xyz.brassgoggledcoders.steamagerevolution.SARCapabilities;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.ISemisolid;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.ISemisolidHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidHolder;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidStack;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IMachineHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

public class TileEntitySemisolidLoader extends TileEntitySidedBase<ISemisolidHandler>
		implements ITickable, IMachineHasInventory<InventorySemisolid>, IHasGui {
	InventorySemisolid inventory;
	int updateTest = -1;

	public TileEntitySemisolidLoader() {
		this.setInventory(
				new InventorySemisolid(new InventoryPieceSemisolid(new SemisolidHandler(new SemisolidHolder(60)), 83, 16)));
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
				return transfer(sideType, entity.getCapability(this.getCapabilityType(), opposite));
			}
		}
		return false;
	}

	private boolean transfer(SideType sideType, ISemisolidHandler otherCapability) {
		if (sideType == SideType.INPUT) {
			return transfer(otherCapability, this.getInternalCapability());
		} else if (sideType == SideType.OUTPUT) {
			return transfer(this.getInternalCapability(), otherCapability);
		} else {
			return false;
		}
	}

	private boolean transfer(ISemisolidHandler from, ISemisolidHandler to) {
		if (from.getHolders().length > 0 && from.getHolders()[0].getCrushed() != null) {
			ISemisolid material = from.getHolders()[0].getCrushed().getMaterial();
			int amount = from.getHolders()[0].getAmount();
			from.drain(material, amount);
			to.fill(new SemisolidStack(material, amount));
		}
		return false;
	}

	private boolean tryTransferToTile(SideType sideType, EnumFacing facing) {
		return Optional.ofNullable(world.getTileEntity(this.getPos().offset(facing)))
				.filter(tileEntity -> tileEntity.hasCapability(this.getCapabilityType(), facing.getOpposite()))
				.map(tileEntity -> tileEntity.getCapability(this.getCapabilityType(), facing.getOpposite()))
				.map(cap -> transfer(sideType, cap)).orElse(false);
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
	public Capability<ISemisolidHandler> getCapabilityType() {
		return SARCapabilities.SEMISOLID_HANDLER;
	}

	@Override
	public ISemisolidHandler getInternalCapability() {
		return this.getInventory().ore.getHandler();
	}

	@Override
	public ISemisolidHandler getOutputCapability() {
		return this.getInventory().ore.getHandler();
	}

	@Override
	public ISemisolidHandler getInputCapability() {
		return this.getInventory().ore.getHandler();
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
	public InventorySemisolid getInventory() {
		return this.inventory;
	}

	@Override
	public void setInventory(InventorySemisolid inventory) {
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
		return new GuiSemisolid(entityPlayer, this, "crushed_single");
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return null;
	}
}