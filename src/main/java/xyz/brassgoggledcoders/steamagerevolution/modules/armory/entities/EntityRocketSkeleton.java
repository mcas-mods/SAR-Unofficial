package xyz.brassgoggledcoders.steamagerevolution.modules.armory.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.common.FMLLog;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.ModuleArmory;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.items.ItemRocketFist;
import xyz.brassgoggledcoders.steamagerevolution.utils.EntityAIAttackRangedGeneric;

public class EntityRocketSkeleton extends EntitySkeleton {

	private final EntityAIAttackRangedGeneric<EntityRocketSkeleton> aiRocket = new EntityAIAttackRangedGeneric<EntityRocketSkeleton>(this, 1.0D, 20, 30.0F, ModuleArmory.rocket_fist);
	private final EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.2D, false);
	

	public EntityRocketSkeleton(World worldIn) {
		super(worldIn);
	}
	
	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);
        int minSteam = Math.round(50 * difficulty.getAdditionalDifficulty());
        ItemStack stack = new ItemStack(ModuleArmory.rocket_fist);
        FluidHandlerItemStack internal = (FluidHandlerItemStack) stack
				.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        internal.fill(FluidRegistry.getFluidStack("steam", minSteam + rand.nextInt(ItemRocketFist.capacity - minSteam)), true);
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, stack);
    }
	
	@Override
	public boolean attackEntityAsMob(Entity entityIn)
    {
		if(!(entityIn instanceof EntityLivingBase)) { //Wut
			return false;
		}
		ItemStack stack = this.getHeldItemMainhand();
		if(!stack.isEmpty()) {
			//Of course I have to manually call that...despite the fact the Entity instance is generic
			stack.getItem().hitEntity(stack, (EntityLivingBase) entityIn, this);
		}
        return super.attackEntityAsMob(entityIn);
    }
	
	@Override
	public void setCombatTask()
    {
        if (this.world != null && !this.world.isRemote)
        {
            this.tasks.removeTask(this.aiRocket);
            ItemStack itemstack = this.getHeldItemMainhand();

            if (itemstack.getItem() == ModuleArmory.rocket_fist)
            {
                int i = 20;

                if (this.world.getDifficulty() != EnumDifficulty.HARD)
                {
                    i = 40;
                }

                this.aiRocket.setAttackCooldown(i);
                this.tasks.addTask(4, this.aiRocket);
            }
        }
    }
	
	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
		ItemStack stack = this.getHeldItemMainhand();
		//Shouldn't need the second check because its already done in AI but you never know
		if(!stack.isEmpty() && stack.getItem() == ModuleArmory.rocket_fist) {
			((ItemRocketFist)stack.getItem()).doFistBoost(this, stack, 0); //TODO Set charge factor thing
			//TODO Make two tasks compatible
			this.tasks.removeTask(aiRocket);
			this.tasks.addTask(5, aiAttackOnCollide);
		}
    }
}
