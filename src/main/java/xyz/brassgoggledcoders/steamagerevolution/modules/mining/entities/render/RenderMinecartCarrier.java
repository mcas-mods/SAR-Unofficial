package xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities.render;

import org.lwjgl.opengl.GL11;

import com.teamacronymcoders.base.renderer.entity.minecart.RenderMinecartBase;
import com.teamacronymcoders.base.util.RenderingUtils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities.EntityMinecartCarrier;

public class RenderMinecartCarrier extends RenderMinecartBase<EntityMinecartCarrier> {
	private static final ResourceLocation TEX = new ResourceLocation(SteamAgeRevolution.MODID,
			"textures/models/carrier_cart.png");
	protected ModelCarrierMinecart model = new ModelCarrierMinecart();
	
	public RenderMinecartCarrier(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}
	
	@Override
	protected void renderCartModel(EntityMinecartCarrier entity) {
		this.model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		ItemStack stack = entity.getDataManager().get(EntityMinecartCarrier.CONTENTS);
		if (!stack.isEmpty()) {
			int color = 0;
			for (int tintIndex = 0; tintIndex < 3; tintIndex++) {
				if (Minecraft.getMinecraft().getItemColors().colorMultiplier(stack, tintIndex) != -1) {
					color = Minecraft.getMinecraft().getItemColors().colorMultiplier(stack, tintIndex);
					break;
				} else if (Minecraft.getMinecraft().getBlockColors().colorMultiplier(
						Block.getBlockFromItem(stack.getItem()).getDefaultState(), entity.getEntityWorld(),
						entity.getPosition(), tintIndex) != -1) {
					color = Minecraft.getMinecraft().getBlockColors().colorMultiplier(
							Block.getBlockFromItem(stack.getItem()).getDefaultState(), entity.getEntityWorld(),
							entity.getPosition(), tintIndex);
					break;
				}
			}
			RenderingUtils.pre(0, 0, 0);
			float red = (float) (color >> 16 & 255) / 255.0F;
			float green = (float) (color >> 8 & 255) / 255.0F;
			float blue = (float) (color & 255) / 255.0F;
			GlStateManager.color(red, green, blue);
			this.model.renderContents(0.0625F);
			RenderingUtils.post();
			GlStateManager.color(1F, 1F, 1F);
		}
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityMinecartCarrier entity) {
		return TEX;
	}
}
