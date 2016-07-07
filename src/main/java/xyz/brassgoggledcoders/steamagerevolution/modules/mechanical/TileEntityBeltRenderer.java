package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;

public class TileEntityBeltRenderer extends TileEntitySpecialRenderer<TileEntityBeltEnd> {

	Tessellator tes = Tessellator.getInstance();
	net.minecraft.client.renderer.VertexBuffer wr = tes.getBuffer();

	@Override
	public void renderTileEntityAt(TileEntityBeltEnd te, double x, double y, double z, float partialTicks,
			int destroyStage) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.disableCull();

		if(te.getWorld() != null) {
			if(te.isTilePaired() && te.isMaster()) {
				BlockPos current = te.getPos();
				BlockPos paired = te.getPairedTile().getPos();
				current.getDistance(paired.getX(), paired.getY(), paired.getZ());
				wr.begin(7, DefaultVertexFormats.POSITION_TEX);
				wr.pos(0, 16, 16).tex(0, 1).endVertex();
				wr.pos(0, 16, 16).tex(0, 1).endVertex();
				wr.pos(current.getDistance(paired.getX(), paired.getY(), paired.getZ()), 16, 16).tex(0, 1).endVertex();
				// wr.pos(paired.getX() + 16, paired.getY() + 16, paired.getZ() + 16).tex(0, 1).endVertex();
				// wr.finishDrawing();
				tes.draw();
			}
		}
		GlStateManager.popMatrix();
	}

}
