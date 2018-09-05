package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import xyz.brassgoggledcoders.steamagerevolution.utils.RenderUtil;

public class TileEntitySteamHammerAnvilRenderer extends TileEntitySpecialRenderer<TileEntitySteamHammerAnvil> {

    protected static Minecraft mc = Minecraft.getMinecraft();

    // TODO Scaling
    @Override
    public void render(TileEntitySteamHammerAnvil tile, double x, double y, double z, float partialTicks,
                       int destroyStage, float alpha) {
        // Stolen from TiCon :)
        if (!tile.isConnected() || !tile.getMultiblockController().isAssembled()) {
            return;
        }
        ItemStack stack = tile.getMultiblockController().inventory.getInputHandler().getStackInSlot(0);
        if (stack.isEmpty()) {
            stack = tile.getMultiblockController().inventory.getOutputHandler().getStackInSlot(0);
        }
        if (!stack.isEmpty()) {
            RenderUtil.pre(x, y, z);
            int brightness = tile.getWorld().getCombinedLight(tile.getPos(), 0);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightness % 0x10000 / 1f,
                    brightness / 0x10000 / 1f);

            GlStateManager.translate(0.5f, 1.5f, 0.5f);
            GlStateManager.scale(0.5f, 0.5f, 0.5f);

            if (!(stack.getItem() instanceof ItemBlock)
                    || Block.getBlockFromItem(stack.getItem()) instanceof BlockPane) {
                GlStateManager.rotate(-90, 1, 0, 0);
            }

            GL11.glDepthMask(false);
            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack,
                    tile.getWorld(), null);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
            GL11.glDepthMask(true);
            RenderUtil.post();
        }
    }
}