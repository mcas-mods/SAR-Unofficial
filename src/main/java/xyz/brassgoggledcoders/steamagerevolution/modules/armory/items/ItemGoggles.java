package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items;

import org.lwjgl.opengl.GL11;

import com.teamacronymcoders.base.client.ClientHelper;
import com.teamacronymcoders.base.items.ItemArmorBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.ModuleArmory;

public class ItemGoggles extends ItemArmorBase {

	public static ResourceLocation overlay = new ResourceLocation(SteamAgeRevolution.MODID,
			"textures/misc/goggles.png");

	public ItemGoggles() {
		super(ModuleArmory.GOGGLES, EntityEquipmentSlot.HEAD, "goggles");
		this.setMaxStackSize(1);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		NBTTagCompound tag = new NBTTagCompound();
		// tag.setInteger("theoneprobe", 1);
		stack.setTagCompound(tag);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}

		for(int i = 0; i < ModuleArmory.lenseTypes.size(); i++) {
			if(stack.getTagCompound().getBoolean("lens" + i)) {
				ModuleArmory.lenseTypes.get(i).onArmorTick(world, player, stack);
			}
		}

		// stack.getTagCompound().setInteger("theoneprobe", 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution,
			float partialTicks) {
		if((ClientHelper.player() == null) || (ClientHelper.screen() != null)) {
			return;
		}

		Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
		final int i = resolution.getScaledWidth();
		final int k = resolution.getScaledHeight();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(overlay);
		final Tessellator tessellator = Tessellator.getInstance();
		tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		tessellator.getBuffer().pos(i / 2 - k, k, -90D).tex(0.0D, 1.0D).endVertex();
		tessellator.getBuffer().pos(i / 2 + k, k, -90D).tex(1.0D, 1.0D).endVertex();
		tessellator.getBuffer().pos(i / 2 + k, 0.0D, -90D).tex(1.0D, 0.0D).endVertex();
		tessellator.getBuffer().pos(i / 2 - k, 0.0D, -90D).tex(0.0D, 0.0D).endVertex();
		tessellator.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
