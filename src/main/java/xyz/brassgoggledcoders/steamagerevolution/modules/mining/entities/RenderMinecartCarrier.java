package xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities;

import com.teamacronymcoders.base.renderer.entity.minecart.RenderMinecartBase;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumBlockRenderType;

public class RenderMinecartCarrier extends RenderMinecartBase<EntityMinecartCarrier> {

	public RenderMinecartCarrier(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}
	
	@Override
	protected void renderBlock(EntityMinecartCarrier entity, float partialTicks) {
        IBlockState iblockstate = entity.getDisplayTile();

        if (iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE) {
            GlStateManager.pushMatrix();
            this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            float scale = 0.75F;
            GlStateManager.scale(scale, 0.2F, scale);
            GlStateManager.translate(-0.5F, 0.5F, 0.5F);
            this.renderCartContents(entity, partialTicks, iblockstate);
            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
