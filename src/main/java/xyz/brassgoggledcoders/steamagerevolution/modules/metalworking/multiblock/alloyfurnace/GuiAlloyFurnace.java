package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.GuiUtils;

@SideOnly(Side.CLIENT)
public class GuiAlloyFurnace extends GuiContainer {
	private static ResourceLocation guiTexture =
			new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/alloyfurnace.png");
	private final ControllerAlloyFurnace controller;

	public GuiAlloyFurnace(EntityPlayer player, ControllerAlloyFurnace tile) {
		super(new ContainerAlloyFurnace(player, tile));
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

		for(int i = 0; i < controller.primaryTank.fluids.size(); i++) {
			FluidStack containedFluid = controller.primaryTank.fluids.get(i);
			if(containedFluid != null && containedFluid.getFluid() != null && containedFluid.amount > 0) {
				GuiUtils.renderGuiTank(containedFluid, (ControllerAlloyFurnace.inputCapacity / 2),
						containedFluid.amount, this.guiLeft + 22 + (i * 56), this.guiTop + 11, 20, 60);
				this.mc.renderEngine.bindTexture(guiTexture);
				this.drawTexturedModalRect(this.guiLeft + 22 + (i * 56), this.guiTop + 17, 176, 14, 20, 49);
			}
		}

		FluidStack output = controller.outputTank.getFluid();
		if(output != null && output.getFluid() != null && output.amount > 0) {
			GuiUtils.renderGuiTank(output, ControllerAlloyFurnace.outputCapacity, output.amount, this.guiLeft + 134,
					this.guiTop + 11, 20, 60);
			this.mc.renderEngine.bindTexture(guiTexture);
			this.drawTexturedModalRect(this.guiLeft + 134, this.guiTop + 17, 176, 14, 20, 49);
		}
	}

}
