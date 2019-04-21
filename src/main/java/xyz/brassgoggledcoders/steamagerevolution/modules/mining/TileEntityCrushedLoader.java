package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

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
import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.CrushedHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.CrushedHolder;
import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.ICrushedHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.recipe.SARMachineRecipe;

public class TileEntityCrushedLoader extends TileEntitySidedBase<ICrushedHandler> implements ITickable, IHasInventory<InventoryCrushed>, IHasGui {
	InventoryCrushed inventory;
    int updateTest = -1;
    
    public TileEntityCrushedLoader() {
    	this.setInventory(new InventoryCrushed(new InventoryPieceCrushed(new CrushedHandler(new CrushedHolder(60)), 0, 0)));
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
                        //noinspection ResultOfMethodCallIgnored
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

    private boolean transfer(SideType sideType, ICrushedHandler otherCapability) {
        if (sideType == SideType.INPUT) {
            return transfer(otherCapability, this.getInternalCapability());
        } else if (sideType == SideType.OUTPUT) {
            return transfer(this.getInternalCapability(), otherCapability);
        } else {
            return false;
        }
    }

    private boolean transfer(ICrushedHandler internalCapability, ICrushedHandler otherCapability) {
		return false;
    	//return ((CrushedHandler)internalCapability).transfer(otherCapability);
	}

	private boolean tryTransferToTile(SideType sideType, EnumFacing facing) {
        return Optional.ofNullable(world.getTileEntity(this.getPos().offset(facing)))
                .filter(tileEntity -> tileEntity.hasCapability(this.getCapabilityType(), facing.getOpposite()))
                .map(tileEntity -> tileEntity.getCapability(this.getCapabilityType(), facing.getOpposite()))
                .map(cap -> transfer(sideType, cap))
                .orElse(false);
    }

    @Override
    protected void readCapability(NBTTagCompound data) {
    	//NO-OP
    }

    @Override
    protected void writeCapability(NBTTagCompound data) {
    	//NO-OP
    }

    @Override
    public Capability<ICrushedHandler> getCapabilityType() { 
        return SARCapabilities.CRUSHED_HANDLER;
    }

    @Override
    public ICrushedHandler getInternalCapability() {
        return this.getInventory().ore.getHandler();
    }

    @Override
    public ICrushedHandler getOutputCapability() {
        return this.getInventory().ore.getHandler();
    }

    @Override
    public ICrushedHandler getInputCapability() {
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
	public InventoryCrushed getInventory() {
		return this.inventory;
	}

	@Override
	public void setInventory(InventoryCrushed inventory) {
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
		return new GuiLoader(entityPlayer, this);
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return null;
	}
}