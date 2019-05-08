package xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import com.teamacronymcoders.base.renderer.entity.minecart.RenderMinecartBase;
import com.teamacronymcoders.base.util.RenderingUtils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;

public class RenderMinecartCarrier extends RenderMinecartBase<EntityMinecartCarrier> {

	public RenderMinecartCarrier(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public void doRender(@Nonnull EntityMinecartCarrier entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		ItemStack stack = entity.getDataManager().get(EntityMinecartCarrier.CONTENTS);
		if (!stack.isEmpty()) {
			int color = 0;
			for (int tintIndex = 0; tintIndex < 3; tintIndex++) {
				if (Minecraft.getMinecraft().getItemColors().colorMultiplier(stack, tintIndex) > 0) {
					color = Minecraft.getMinecraft().getItemColors().colorMultiplier(stack, tintIndex);
					break;
				} else if (Minecraft.getMinecraft().getBlockColors().colorMultiplier(
						Block.getBlockFromItem(stack.getItem()).getDefaultState(), entity.getEntityWorld(),
						entity.getPosition(), tintIndex) > 0) {

					color = Minecraft.getMinecraft().getBlockColors().colorMultiplier(
							Block.getBlockFromItem(stack.getItem()).getDefaultState(), entity.getEntityWorld(),
							entity.getPosition(), tintIndex);
					break;
				}
			}
			RenderingUtils.pre(x, y, z);
			float red = (float) (color >> 16 & 255) / 255.0F;
			float green = (float) (color >> 8 & 255) / 255.0F;
			float blue = (float) (color & 255) / 255.0F;
			int brightness = entity.getBrightnessForRender();
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightness % 0x10000 / 1f,
					brightness / 0x10000 / 1f);

			GlStateManager.translate(0.5f, 1.5f, 0.5f);
			GlStateManager.scale(0.5f, 0.5f, 0.5f);
			GlStateManager.color(red, green, blue);
			GL11.glDepthMask(false);
			IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack,
					entity.getEntityWorld(), null);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
			GL11.glDepthMask(true);
			RenderingUtils.post();
			GlStateManager.color(1F, 1F, 1F);
		}
	}
}
