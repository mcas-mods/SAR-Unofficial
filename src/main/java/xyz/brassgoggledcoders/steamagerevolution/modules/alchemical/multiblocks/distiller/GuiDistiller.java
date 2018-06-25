package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.GuiUtils;

@SideOnly(Side.CLIENT)
public class GuiDistiller extends GuiContainer {
	private static ResourceLocation guiTexture = new ResourceLocation(SteamAgeRevolution.MODID,
			"textures/gui/distiller.png");
	private final ControllerDistiller controller;

	public GuiDistiller(EntityPlayer player, ControllerDistiller tile) {
		super(new ContainerDistiller(player, tile));
		controller = tile;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
		// if(this.isPointInRegion(78, 17, 20, 49, mouseX, mouseY)) {
		// List<String> tooltip = Lists.newArrayList();
		// tooltip.add(TextUtils.representTankContents(
		// (IFluidTank)
		// controller.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
		// null))
		// .getText());
		// this.drawHoveringText(tooltip, mouseX, mouseY, fontRenderer);
		// }
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.renderEngine.bindTexture(guiTexture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		FluidStack steam = controller.inventory.getSteamTank().getFluid();
		if(steam != null && steam.getFluid() != null && steam.amount > 0) {
			GuiUtils.renderGuiTank(steam, controller.inventory.getSteamTank().getCapacity(), steam.amount,
					guiLeft + controller.inventory.steamTank.getX(), guiTop + controller.inventory.steamTank.getY(), 20,
					60);
			mc.renderEngine.bindTexture(guiTexture);
			this.drawTexturedModalRect(guiLeft + 10, guiTop + 15, 176, 14, 20, 49);
		}

		FluidStack input = controller.inventory.getFluidInputs().getFluid();
		if(input != null && input.getFluid() != null && input.amount > 0) {
			GuiUtils.renderGuiTank(input, controller.inventory.getFluidInputs().getCapacity(), input.amount,
					guiLeft + 41, guiTop + 9, 20, 60);
			mc.renderEngine.bindTexture(guiTexture);
			this.drawTexturedModalRect(guiLeft + 41, guiTop + 15, 176, 14, 20, 49);
		}

		FluidStack output = controller.inventory.getFluidInputs().getFluid();
		if(output != null && output.getFluid() != null && output.amount > 0) {
			GuiUtils.renderGuiTank(output, controller.inventory.getFluidInputs().getCapacity(), output.amount,
					guiLeft + 97, guiTop + 9, 20, 60);
			mc.renderEngine.bindTexture(guiTexture);
			this.drawTexturedModalRect(guiLeft + 97, guiTop + 15, 176, 14, 20, 49);
		}
	}

}
