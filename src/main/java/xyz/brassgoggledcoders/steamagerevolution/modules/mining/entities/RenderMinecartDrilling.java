package xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities;

import com.teamacronymcoders.base.renderer.entity.minecart.RenderMinecartBase;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class RenderMinecartDrilling extends RenderMinecartBase<EntityMinecartDrilling> {
	
	private static final ResourceLocation TEX = new ResourceLocation(SteamAgeRevolution.MODID, "textures/models/drill_cart.png");
    protected ModelBase model = new ModelDrillingMinecart();
	
	public RenderMinecartDrilling(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }
	
	@Override
	protected void renderCartModel(EntityMinecartDrilling entity) {
        this.model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityMinecartDrilling entity) {
		return TEX;
	}

}
