package xyz.brassgoggledcoders.steamagerevolution.utils;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.*;

// Sto...borrowed from EnderCore.
public class GuiUtils {
	public static final ResourceLocation BLOCK_TEX = TextureMap.LOCATION_BLOCKS_TEXTURE;

	public static TextureManager engine() {
		return Minecraft.getMinecraft().renderEngine;
	}

	public static void bindBlockTexture() {
		engine().bindTexture(BLOCK_TEX);
	}

	public static TextureAtlasSprite getStillTexture(FluidStack fluid) {
		if(fluid == null || fluid.getFluid() == null) {
			return null;
		}
		return getStillTexture(fluid.getFluid());
	}

	public static TextureAtlasSprite getStillTexture(Fluid fluid) {
		ResourceLocation iconKey = fluid.getStill();
		if(iconKey == null) {
			return null;
		}
		return Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(iconKey.toString());
	}

	public static void renderGuiTank(FluidTank tank, double x, double y, double width, double height) {
		renderGuiTank(tank.getFluid(), tank.getCapacity(), tank.getFluidAmount(), x, y, width, height);
	}

	public static void renderGuiTank(FluidStack fluid, int capacity, int amount, double x, double y, double width,
			double height) {
		if(fluid == null || fluid.getFluid() == null || fluid.amount <= 0) {
			return;
		}

		TextureAtlasSprite icon = getStillTexture(fluid);
		if(icon == null) {
			return;
		}

		int renderAmount = (int) Math.max(Math.min(height, amount * height / capacity), 1);
		int posY = (int) (y + height - renderAmount);

		bindBlockTexture();
		int color = fluid.getFluid().getColor(fluid);
		GL11.glColor3ub((byte) (color >> 16 & 0xFF), (byte) (color >> 8 & 0xFF), (byte) (color & 0xFF));

		GlStateManager.enableBlend();
		for(int i = 0; i < width; i += 16) {
			for(int j = 0; j < renderAmount; j += 16) {
				int drawWidth = (int) Math.min(width - i, 16);
				int drawHeight = Math.min(renderAmount - j, 16);

				int drawX = (int) (x + i);
				int drawY = posY + j;

				double minU = icon.getMinU();
				double maxU = icon.getMaxU();
				double minV = icon.getMinV();
				double maxV = icon.getMaxV();

				Tessellator tessellator = Tessellator.getInstance();
				BufferBuilder tes = tessellator.getBuffer();
				tes.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
				tes.pos(drawX, drawY + drawHeight, 0).tex(minU, minV + (maxV - minV) * drawHeight / 16F).endVertex();
				tes.pos(drawX + drawWidth, drawY + drawHeight, 0)
						.tex(minU + (maxU - minU) * drawWidth / 16F, minV + (maxV - minV) * drawHeight / 16F)
						.endVertex();
				tes.pos(drawX + drawWidth, drawY, 0).tex(minU + (maxU - minU) * drawWidth / 16F, minV).endVertex();
				tes.pos(drawX, drawY, 0).tex(minU, minV).endVertex();
				tessellator.draw();
			}
		}
		GlStateManager.disableBlend();
	}
}
