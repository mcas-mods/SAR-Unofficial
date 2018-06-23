package xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.sawmill;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.utils.RenderUtil;

public class TileEntitySawmillAxleRenderer extends TileEntitySpecialRenderer<TileEntitySawmillAxle> {

	protected static Minecraft mc = Minecraft.getMinecraft();

	// TODO Scaling
	@Override
	public void render(TileEntitySawmillAxle tile, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		// Stolen from TiCon :)
		if(!tile.isConnected() || !tile.getMultiblockController().isAssembled()) {
			return;
		}
		ItemStack stack = tile.getMultiblockController().inputInventory.getStackInSlot(0);
		if(!stack.isEmpty()) {
			RenderUtil.pre(x, y, z);
			int brightness = tile.getWorld().getCombinedLight(tile.getPos().up(), 0);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightness % 0x10000 / 1f,
					brightness / 0x10000 / 1f);

			GlStateManager.translate(1.5f, 1.5f, 0.5f);
			GlStateManager.scale(1f, 1f, 1f);

			GL11.glDepthMask(false);
			IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack,
					tile.getWorld(), null);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
			GL11.glDepthMask(true);
			RenderUtil.post();
		}
		RenderUtil.pre(x, y, z);
		int brightness = tile.getWorld().getCombinedLight(tile.getPos().up(), 0);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightness % 0x10000 / 1f,
				brightness / 0x10000 / 1f);

		GlStateManager.translate(0.5f, 1.5f, 0.5f);
		GlStateManager.scale(3f, 3f, 3f);
		GlStateManager.rotate(partialTicks * 100, 0, 0, 1f);

		GL11.glDepthMask(false);
		IBakedModel model = Minecraft.getMinecraft().getRenderItem()
				.getItemModelWithOverrides(new ItemStack(Items.IRON_SWORD), tile.getWorld(), null);
		Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
		GL11.glDepthMask(true);
		RenderUtil.post();
	}
}