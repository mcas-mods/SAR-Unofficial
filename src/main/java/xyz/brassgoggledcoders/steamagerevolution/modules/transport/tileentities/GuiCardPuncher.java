package xyz.brassgoggledcoders.steamagerevolution.modules.transport.tileentities;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class GuiCardPuncher extends GuiContainer {

	private static ResourceLocation guiTexture =
			new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/fluid_io.png");
	private NonNullList<ItemStack> stacks;
	private GuiButton doneBtn;

	public GuiCardPuncher(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		stacks = inventorySlotsIn.inventoryItemStacks;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.mc.renderEngine.bindTexture(guiTexture);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}

	@Override
	public void initGui() {
		this.buttonList.clear();
		this.doneBtn =
				this.addButton(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, I18n.format("gui.done")));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.enabled) {
			ItemStack cardIn = stacks.get(0);
			ItemStack program = stacks.get(1);
			ItemStack cardOut = stacks.get(2);
			if(!cardIn.isEmpty() && cardOut.isEmpty()) {
				NBTTagCompound tag = new NBTTagCompound();
				if(cardIn.getItem() instanceof ItemDye) {
					tag.setInteger("code", EnumDyeColor.byMetadata(program.getMetadata()).getColorValue());
				}
				else {
					tag.setInteger("code", Item.getIdFromItem(program.getItem()));
				}

				cardIn.setTagCompound(tag);
				cardOut = cardIn;
				cardIn.setCount(0);
			}
		}
	}

}
