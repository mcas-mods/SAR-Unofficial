package xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import com.teamacronymcoders.base.renderer.entity.minecart.RenderMinecartBase;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.EventHandlerClient;

public class RenderMinecartDrilling extends RenderMinecartBase<EntityMinecartDrilling> {

	private static final ResourceLocation TEX = new ResourceLocation(SteamAgeRevolution.MODID,
			"textures/models/drill_cart.png");
	protected ModelBase model = new ModelDrillingMinecart();

	public RenderMinecartDrilling(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}
	
	@Override
    public void doRender(@Nonnull EntityMinecartDrilling entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		if(entity.miningLocation != null) {
			this.drawBlockDamageTexture(Tessellator.getInstance(), Tessellator.getInstance().getBuffer(), entity.miningLocation, partialTicks, entity.getEntityWorld());
		}
	}

	@Override
	protected void renderCartModel(EntityMinecartDrilling entity) {
		this.model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMinecartDrilling entity) {
		return TEX;
	}
	
	//Copied from TiCon who copied it from Vanilla's RenderManager
	public void drawBlockDamageTexture(Tessellator tessellatorIn, BufferBuilder bufferBuilder, BlockPos blockpos, float partialTicks, World world) {
	    TextureManager renderEngine = Minecraft.getMinecraft().renderEngine;
	    //TODO
	    int progress = (int) (Minecraft.getMinecraft().playerController.curBlockDamageMP * 10f) - 1; // 0-10

	    if(progress < 0) {
	      return;
	    }

	    renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
	    //preRenderDamagedBlocks BEGIN
	    GlStateManager.tryBlendFuncSeparate(774, 768, 1, 0);
	    GlStateManager.enableBlend();
	    GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
	    GlStateManager.doPolygonOffset(-3.0F, -3.0F);
	    GlStateManager.enablePolygonOffset();
	    GlStateManager.alphaFunc(516, 0.1F);
	    GlStateManager.enableAlpha();
	    GlStateManager.pushMatrix();
	    //preRenderDamagedBlocks END

	    bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
	    bufferBuilder.setTranslation(blockpos.getX(), blockpos.getY(), blockpos.getZ());
	    bufferBuilder.noColor();
	    
		  Block block = world.getBlockState(blockpos).getBlock();
		  TileEntity te = world.getTileEntity(blockpos);
		  boolean hasBreak = block instanceof BlockChest || block instanceof BlockEnderChest
		                     || block instanceof BlockSign || block instanceof BlockSkull;
		  if(!hasBreak) {
		    hasBreak = te != null && te.canRenderBreaking();
		  }
		  
		  if(!hasBreak) {
		    IBlockState iblockstate = world.getBlockState(blockpos);
		
		    if(iblockstate.getBlock().isAir(iblockstate, world, blockpos)) {
		      TextureAtlasSprite textureatlassprite = EventHandlerClient.destroyBlockIcons[progress];
		      BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
		      blockrendererdispatcher.renderBlockDamage(iblockstate, blockpos, textureatlassprite, world);
		    }
		  }

	    tessellatorIn.draw();
	    bufferBuilder.setTranslation(0.0D, 0.0D, 0.0D);
	    // postRenderDamagedBlocks BEGIN
	    GlStateManager.disableAlpha();
	    GlStateManager.doPolygonOffset(0.0F, 0.0F);
	    GlStateManager.disablePolygonOffset();
	    GlStateManager.enableAlpha();
	    GlStateManager.depthMask(true);
	    GlStateManager.popMatrix();
	    // postRenderDamagedBlocks END
	  }
}
