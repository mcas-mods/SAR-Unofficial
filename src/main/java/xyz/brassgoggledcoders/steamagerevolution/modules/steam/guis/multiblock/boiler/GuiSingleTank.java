package xyz.brassgoggledcoders.steamagerevolution.modules.steam.guis.multiblock.boiler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.boilerplate.client.guis.GuiScreenBase;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.containers.multiblock.boiler.ContainerSingleTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.GuiUtils;

@SideOnly(Side.CLIENT)
public class GuiSingleTank extends GuiScreenBase {
	private static ResourceLocation guiTexture =
			new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/multiblock/single_tank.png");
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
			// TODO Move scaling to utils
			int fluidLevel = (int) (((float) containedFluid.amount / (float) capacity) * 60);
			GuiUtils.renderTiledFluid(this.guiLeft + 78, this.guiTop + 30, 20, fluidLevel, 3, containedFluid);
		}

		// TODO
		// this.mc.renderEngine.bindTexture(guiTexture);
		// this.drawTexturedModalRect(this.guiLeft + 78, this.guiTop + 20, 176, 14, 20, 49);

		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}

}
