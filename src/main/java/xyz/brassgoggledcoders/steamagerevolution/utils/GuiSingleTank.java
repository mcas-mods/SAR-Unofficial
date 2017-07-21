package xyz.brassgoggledcoders.steamagerevolution.utils;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

@SideOnly(Side.CLIENT)
public class GuiSingleTank extends GuiContainer {
	private static ResourceLocation guiTexture =
			new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/single_tank.png");
	private final TileEntity tile;

	public GuiSingleTank(EntityPlayer player, TileEntity tile) {
		super(new ContainerSingleTank(player, tile));
		this.tile = tile;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.mc.renderEngine.bindTexture(guiTexture);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

		FluidStack containedFluid =
				tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null).getTankProperties()[0]
						.getContents();
		int capacity = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null).getTankProperties()[0]
				.getCapacity();

		if(containedFluid != null && containedFluid.getFluid() != null && containedFluid.amount > 0) {
			GuiUtils.renderGuiTank(containedFluid, capacity, containedFluid.amount, this.guiLeft + 78, this.guiTop + 11,
					20, 60);
		}

		this.mc.renderEngine.bindTexture(guiTexture);
		this.drawTexturedModalRect(this.guiLeft + 78, this.guiTop + 17, 176, 14, 20, 49);
	}

}
