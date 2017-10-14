package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.tileentities;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.utils.RenderUtil;

public class TileEntitySteamHammerAnvilRenderer extends TileEntitySpecialRenderer<TileEntitySteamHammerAnvil> {

	protected static Minecraft mc = Minecraft.getMinecraft();
	protected float yMin = 4 / 16f;
	protected float yMax = 1f;
	protected float xzMin = 2 / 16f;
	protected float xzMax = 14 / 16f;

	protected float yScale;
	protected float xzScale = 0.751f;
	protected float yOffset;
	protected float xzOffset;

	// TODO Scaling
	@Override
	public void render(TileEntitySteamHammerAnvil tile, double x, double y, double z, float partialTicks,
			int destroyStage, float alpha) {

		this.yOffset = yMin + (yMax - yMin) / 2f;
		this.xzOffset = xzMin + (xzMax - xzMin) / 2f;

		this.xzScale = (this.xzMax - this.xzMin);
		this.yScale = xzScale;
		// Stolen from TiCon :)
		ItemStack stack = tile.getMultiblockController().inventory.getStackInSlot(0);
		if(stack.isEmpty()) {
			stack = tile.getMultiblockController().inventory.getStackInSlot(1);
		}
		if(!stack.isEmpty()) {
			RenderUtil.pre(x, y, z);
			int brightness = tile.getWorld().getCombinedLight(tile.getPos(), 0);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightness % 0x10000 / 1f,
					brightness / 0x10000 / 1f);

			GlStateManager.translate(xzOffset, yOffset + 1f, xzOffset);
			GlStateManager.scale(xzScale, yScale, xzScale);

			if(!(stack.getItem() instanceof ItemBlock)
					|| Block.getBlockFromItem(stack.getItem()) instanceof BlockPane) {
				GlStateManager.rotate(-90, 1, 0, 0);
			}

			GL11.glDepthMask(false);
			IBakedModel model =
					Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, tile.getWorld(), null);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
			GL11.glDepthMask(true);
			RenderUtil.post();
		}
	}
}