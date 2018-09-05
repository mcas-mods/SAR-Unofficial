package xyz.brassgoggledcoders.steamagerevolution.modules.armory.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.ModuleArmory;

public class EntityBullet extends EntityThrowable {

    public EntityBullet(World worldIn) {
        super(worldIn);
        // TODO Auto-generated constructor stub
    }

    public EntityBullet(World worldIn, EntityPlayer playerIn) {
        super(worldIn, playerIn);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.typeOfHit == RayTraceResult.Type.ENTITY) {
            result.entityHit.attackEntityFrom(ModuleArmory.damageSourceBullet, 5F);
        }
    }

}
