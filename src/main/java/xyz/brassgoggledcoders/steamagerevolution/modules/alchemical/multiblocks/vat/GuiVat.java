package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.GuiUtils;

@SideOnly(Side.CLIENT)
public class GuiVat extends GuiContainer {
	private static ResourceLocation guiTexture = new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/vat.png");
	private final ControllerVat controller;

	public GuiVat(EntityPlayer player, ControllerVat tile) {
		super(new ContainerVat(player, tile));
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

		for(int i = 0; i < controller.fluidInput.fluids.size(); i++) {
			FluidStack containedFluid = controller.fluidInput.fluids.get(i);
			if(containedFluid != null && containedFluid.getFluid() != null && containedFluid.amount > 0) {
				GuiUtils.renderGuiTank(containedFluid, (controller.fluidInput.getCapacity() / 3), containedFluid.amount,
						this.guiLeft + 12 + (i * 25), this.guiTop + 9, 20, 60);
				this.mc.renderEngine.bindTexture(guiTexture);
				this.drawTexturedModalRect(this.guiLeft + 12 + (i * 25), this.guiTop + 15, 176, 14, 20, 49);
			}
		}
		FluidStack containedFluid = controller.output.getFluid();
		if(containedFluid != null && containedFluid.getFluid() != null && containedFluid.amount > 0) {
			GuiUtils.renderGuiTank(containedFluid, (controller.fluidInput.getCapacity() / 3), containedFluid.amount,
					this.guiLeft + 143, this.guiTop + 9, 20, 60);
			this.mc.renderEngine.bindTexture(guiTexture);
			this.drawTexturedModalRect(this.guiLeft + 143, this.guiTop + 15, 176, 14, 20, 49);
		}
	}

}
