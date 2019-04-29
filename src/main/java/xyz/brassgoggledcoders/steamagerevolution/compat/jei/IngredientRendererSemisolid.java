package xyz.brassgoggledcoders.steamagerevolution.compat.jei;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.api.semisolid.SemisolidStack;

public class IngredientRendererSemisolid implements IIngredientRenderer<SemisolidStack> {

	@Override
	public void render(Minecraft minecraft, int xPosition, int yPosition, SemisolidStack ingredient) {
		Minecraft.getMinecraft().renderEngine
				.bindTexture(new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/crushed_single.png"));
		int rgb = ingredient.getMaterial().getColor();
		int red = (rgb >> 16) & 0xFF;
		int green = (rgb >> 8) & 0xFF;
		int blue = rgb & 0xFF;
		GL11.glColor3f(red, green, blue);
		drawTexturedModalRect(xPosition, yPosition, 176, 100, 16, 16);
		GL11.glColor3f(0, 0, 0);
	}

	public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos((double) (x + 0), (double) (y + height), 0)
				.tex((double) ((float) (textureX + 0) * 0.00390625F),
						(double) ((float) (textureY + height) * 0.00390625F))
				.endVertex();
		bufferbuilder.pos((double) (x + width), (double) (y + height), 0)
				.tex((double) ((float) (textureX + width) * 0.00390625F),
						(double) ((float) (textureY + height) * 0.00390625F))
				.endVertex();
		bufferbuilder.pos((double) (x + width), (double) (y + 0), 0)
				.tex((double) ((float) (textureX + width) * 0.00390625F),
						(double) ((float) (textureY + 0) * 0.00390625F))
				.endVertex();
		bufferbuilder.pos((double) (x + 0), (double) (y + 0), 0)
				.tex((double) ((float) (textureX + 0) * 0.00390625F), (double) ((float) (textureY + 0) * 0.00390625F))
				.endVertex();
		tessellator.draw();
	}

	public List<String> getTooltip(Minecraft minecraft, SemisolidStack ingredient, ITooltipFlag tooltipFlag) {
		List<String> s = new ArrayList<>();
		s.add(ingredient.getMaterial().getRegistryName().toString());
		s.add("" + ingredient.amount);
		return s;
	}

}
