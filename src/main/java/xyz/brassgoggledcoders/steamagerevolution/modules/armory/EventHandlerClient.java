package xyz.brassgoggledcoders.steamagerevolution.modules.armory;

import org.lwjgl.opengl.GL11;

import com.teamacronymcoders.base.util.RenderingUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class EventHandlerClient {
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onDrawBlockSelectionBox(DrawBlockHighlightEvent event) {
		if((event.getPlayer().inventory.armorItemInSlot(3) != null)
				&& (event.getPlayer().inventory.armorItemInSlot(3).getItem() == ModuleArmory.goggles)) {
			drawSelectionBox(event.getPlayer(), event.getTarget(), event.getPartialTicks());
			// event.setCanceled(true);
		}
	}

	private static void drawSelectionBox(EntityPlayer player, RayTraceResult mop, float partialTicks) {
		if(mop.typeOfHit == RayTraceResult.Type.BLOCK) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
			GL11.glLineWidth(3.5F);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(false);
			float offset = 0.002F;
			World world = player.world;
			BlockPos pos = mop.getBlockPos();
			IBlockState state = world.getBlockState(pos);

			if(!world.isAirBlock(pos)) {
				double dx = player.lastTickPosX + ((player.posX - player.lastTickPosX) * partialTicks);
				double dy = player.lastTickPosY + ((player.posY - player.lastTickPosY) * partialTicks);
				double dz = player.lastTickPosZ + ((player.posZ - player.lastTickPosZ) * partialTicks);
				drawOutlinedBoundingBox(
						state.getSelectedBoundingBox(world, pos).expand(offset, offset, offset).offset(-dx, -dy, -dz));
			}

			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
		}
		else if(mop.typeOfHit == RayTraceResult.Type.ENTITY) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
			GL11.glLineWidth(3.5F);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(false);
			float offset = 0.002F;
			Entity entity = mop.entityHit;

			if(entity != null) {
				entity.setPosition(entity.posX, entity.posY, entity.posZ);
				double dx = player.lastTickPosX + ((player.posX - player.lastTickPosX) * partialTicks);
				double dy = player.lastTickPosY + ((player.posY - player.lastTickPosY) * partialTicks);
				double dz = player.lastTickPosZ + ((player.posZ - player.lastTickPosZ) * partialTicks);
				drawOutlinedBoundingBox(
						entity.getEntityBoundingBox().expand(offset, offset, offset).offset(-dx, -dy, -dz));
			}

			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
		}
	}

	private static void drawOutlinedBoundingBox(AxisAlignedBB aaBB) {
		RenderingUtils.drawLineNoFade(aaBB.minZ, aaBB.minZ, aaBB.maxZ, aaBB.minZ, 0.5F, 0.5F, 0.5F, 0.8F, 0);
		RenderingUtils.drawLineNoFade(aaBB.minY, aaBB.minY, aaBB.maxY, aaBB.minY, 0.5F, 0.5F, 0.5F, 0.8F, 0);
	}
}
