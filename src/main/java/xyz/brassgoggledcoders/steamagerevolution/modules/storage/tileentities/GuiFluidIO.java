package xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.TextUtils;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.GuiUtils;

@SideOnly(Side.CLIENT)
public class GuiFluidIO extends GuiContainer {
	private static ResourceLocation guiTexture = new ResourceLocation(SteamAgeRevolution.MODID,
			"textures/gui/fluid_io.png");
	private final TileEntityFluidIO tile;

	public GuiFluidIO(EntityPlayer player, TileEntityFluidIO tile) {
		super(new ContainerFluidIO(player, tile));
		this.tile = tile;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
		if(isPointInRegion(78, 17, 20, 49, mouseX, mouseY)) {
			List<String> tooltip = Lists.newArrayList();
			tooltip.add(TextUtils
					.representTankContents(
							(FluidTank) tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null))
					.getText());
			this.drawHoveringText(tooltip, mouseX, mouseY, fontRenderer);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.renderEngine.bindTexture(guiTexture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		FluidStack containedFluid = tile.buffer.getFluid();
		int capacity = tile.buffer.getCapacity();

		if(containedFluid != null && containedFluid.getFluid() != null && containedFluid.amount > 0) {
			GuiUtils.renderGuiTank(containedFluid, capacity, containedFluid.amount, guiLeft + 78, guiTop + 11, 20, 60);
		}

		mc.renderEngine.bindTexture(guiTexture);
		this.drawTexturedModalRect(guiLeft + 78, guiTop + 17, 176, 14, 20, 49);
	}

}
