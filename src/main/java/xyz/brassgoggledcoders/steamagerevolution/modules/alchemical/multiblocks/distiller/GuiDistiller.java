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
	private static ResourceLocation guiTexture =
			new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/distiller.png");
	private final ControllerDistiller controller;

	public GuiDistiller(EntityPlayer player, ControllerDistiller tile) {
		super(new ContainerDistiller(player, tile));
		this.controller = tile;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
		// if(this.isPointInRegion(78, 17, 20, 49, mouseX, mouseY)) {
		// List<String> tooltip = Lists.newArrayList();
		// tooltip.add(TextUtils.representTankContents(
		// (IFluidTank) controller.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null))
		// .getText());
		// this.drawHoveringText(tooltip, mouseX, mouseY, fontRenderer);
		// }
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.mc.renderEngine.bindTexture(guiTexture);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

		FluidStack steam = controller.steamTank.getFluid();
		if(steam != null && steam.getFluid() != null && steam.amount > 0) {
			GuiUtils.renderGuiTank(steam, controller.steamTank.getCapacity(), steam.amount, this.guiLeft + 10,
					this.guiTop + 9, 20, 60);
			this.mc.renderEngine.bindTexture(guiTexture);
			this.drawTexturedModalRect(this.guiLeft + 10, this.guiTop + 15, 176, 14, 20, 49);
		}

		FluidStack input = controller.fluidInput.getFluid();
		if(input != null && input.getFluid() != null && input.amount > 0) {
			GuiUtils.renderGuiTank(input, controller.fluidInput.getCapacity(), input.amount, this.guiLeft + 41,
					this.guiTop + 9, 20, 60);
			this.mc.renderEngine.bindTexture(guiTexture);
			this.drawTexturedModalRect(this.guiLeft + 41, this.guiTop + 15, 176, 14, 20, 49);
		}

		FluidStack output = controller.fluidOutput.getFluid();
		if(output != null && output.getFluid() != null && output.amount > 0) {
			GuiUtils.renderGuiTank(output, controller.fluidOutput.getCapacity(), output.amount, this.guiLeft + 97,
					this.guiTop + 9, 20, 60);
			this.mc.renderEngine.bindTexture(guiTexture);
			this.drawTexturedModalRect(this.guiLeft + 97, this.guiTop + 15, 176, 14, 20, 49);
		}
	}

}
