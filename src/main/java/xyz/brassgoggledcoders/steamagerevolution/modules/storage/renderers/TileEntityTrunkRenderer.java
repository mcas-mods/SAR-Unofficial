package xyz.brassgoggledcoders.steamagerevolution.modules.storage.renderers;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities.TileEntityTrunk;

@SideOnly(Side.CLIENT)
public class TileEntityTrunkRenderer extends TileEntitySpecialRenderer<TileEntityTrunk> {
	private static final ResourceLocation TEXTURE_NORMAL =
			new ResourceLocation(SteamAgeRevolution.MODID, "textures/blocks/trunk.png");
	private final ModelChest simpleChest = new ModelChest();

	@Override
	public void renderTileEntityAt(TileEntityTrunk te, double x, double y, double z, float partialTicks,
			int destroyStage) {
		GlStateManager.enableDepth();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);
		int i;

		if(te != null && te.hasWorld()) {
			i = te.getBlockMetadata();
		}
		else
			i = 0;

		if(destroyStage >= 0) {
			this.bindTexture(DESTROY_STAGES[destroyStage]);
			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.scale(4.0F, 4.0F, 1.0F);
			GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
			GlStateManager.matrixMode(5888);
		}
		else {
			this.bindTexture(TEXTURE_NORMAL);
		}

		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();

		if(destroyStage < 0)
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		GlStateManager.translate((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GlStateManager.scale(1.0F, -1.0F, -1.0F);
		GlStateManager.translate(0.5F, 0.5F, 0.5F);
		int j = 0;

		if(i == 2)
			j = 180;

		if(i == 3)
			j = 0;

		if(i == 4)
			j = 90;

		if(i == 5)
			j = -90;

		GlStateManager.rotate(j, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(-0.5F, -0.5F, -0.5F);
		float f = 0;
		if(te != null) {
			f = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;
		}

		f = 1.0F - f;
		f = 1.0F - f * f * f;
		simpleChest.chestLid.rotateAngleX = -(f * ((float) Math.PI / 2F));
		simpleChest.renderAll();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		if(destroyStage >= 0) {
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}
	}

}