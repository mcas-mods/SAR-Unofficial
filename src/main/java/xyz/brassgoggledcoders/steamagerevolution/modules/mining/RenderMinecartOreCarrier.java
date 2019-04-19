package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import com.teamacronymcoders.base.renderer.entity.minecart.RenderMinecartBase;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.common.FMLLog;

public class RenderMinecartOreCarrier extends RenderMinecartBase<EntityMinecartOreCarrier> {

    public RenderMinecartOreCarrier(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void renderBlock(EntityMinecartOreCarrier entity, float partialTicks) {
        FMLLog.warning("Works");
        super.renderBlock(entity, partialTicks);
    }
}