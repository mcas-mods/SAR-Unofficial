package xyz.brassgoggledcoders.steamagerevolution.utils.multiblock;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.GuiUtils;
import xyz.brassgoggledcoders.steamagerevolution.utils.InventoryMachine.InventoryPieceFluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.TextUtils;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidTank;

@SideOnly(Side.CLIENT)
public class GuiMultiblockInventory extends GuiContainer {
	private ResourceLocation guiTexture;
	private final SARMultiblockInventory controller;

	public GuiMultiblockInventory(EntityPlayer player, SARMultiblockInventory controller) {
		super(new ContainerMultiblockInventory(player, controller));
		guiTexture = new ResourceLocation(SteamAgeRevolution.MODID,
				"textures/gui/" + controller.getName().toLowerCase() + ".png");
		this.controller = controller;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
		MultiFluidTank fluidInputs = controller.inventory.getFluidInputs();
		for(int i = 0; i < fluidInputs.getMaxFluids(); i++) {
			if(this.isPointInRegion(controller.inventory.fluidInput.getX(i), controller.inventory.fluidInput.getY(i),
					20, 55, mouseX, mouseY)) {
				List<String> tooltip = Lists.newArrayList();
				int capacity = fluidInputs.getCapacity();
				capacity /= fluidInputs.getMaxFluids();
				if(fluidInputs.fluids.size() > i) {
					tooltip.add(TextUtils.representTankContents(new FluidTank(fluidInputs.fluids.get(i), capacity))
							.getText());
				}
				else {
					// TODO Localization
					tooltip.add("Empty");
				}
				this.drawHoveringText(tooltip, mouseX, mouseY, fontRenderer);
			}
		}
		MultiFluidTank fluidOutputs = controller.inventory.getFluidOutputs();
		for(int i = 0; i < fluidOutputs.getMaxFluids(); i++) {
			if(this.isPointInRegion(controller.inventory.fluidInput.getX(i), controller.inventory.fluidInput.getY(i),
					20, 55, mouseX, mouseY)) {
				List<String> tooltip = Lists.newArrayList();
				int capacity = fluidOutputs.getCapacity();
				capacity /= fluidOutputs.getMaxFluids();
				if(fluidOutputs.fluids.size() > i) {
					tooltip.add(TextUtils.representTankContents(new FluidTank(fluidOutputs.fluids.get(i), capacity))
							.getText());
				}
				else {
					// TODO Localization
					tooltip.add("Empty");
				}
				this.drawHoveringText(tooltip, mouseX, mouseY, fontRenderer);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.renderEngine.bindTexture(guiTexture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		if(this.controller.inventory.steamTank != null)
			addTank(this.controller.inventory.steamTank);

		if(this.controller.inventory.fluidInput != null)
			addTank(this.controller.inventory.fluidInput);

		if(this.controller.inventory.fluidOutput != null)
			addTank(this.controller.inventory.fluidOutput);
	}

	private void addTank(InventoryPieceFluid piece) {
		FluidTank tank = piece.getHandler();
		if(tank instanceof MultiFluidTank) {
			MultiFluidTank multitank = (MultiFluidTank) tank;
			for(int i = 0; i < multitank.fluids.size(); i++) {
				draw(multitank.fluids.get(i), multitank.getCapacity() / multitank.fluids.size(), piece.getX(i),
						piece.getY(i));
			}
		}
		else {
			draw(tank.getFluid(), tank.getCapacity(), piece.getX(0), piece.getY(0));
		}
	}

	private void draw(FluidStack stack, int capacity, int xPos, int yPos) {
		if(stack != null && stack.getFluid() != null && stack.amount > 0) {
			GuiUtils.renderGuiTank(stack, capacity, stack.amount, guiLeft + xPos, guiTop + yPos, 20, 60);
			mc.renderEngine.bindTexture(guiTexture);
			this.drawTexturedModalRect(guiLeft + xPos, guiTop + yPos + 6, 176, 14, 20, 49);
		}
	}

}
