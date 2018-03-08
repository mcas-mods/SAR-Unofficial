package xyz.brassgoggledcoders.steamagerevolution.modules.transport.tileentities;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class GuiCardPuncher extends GuiContainer {

	private static ResourceLocation guiTexture =
			new ResourceLocation(SteamAgeRevolution.MODID, "textures/gui/fluid_io.png");
	private ItemStackHandler inventory;
	private GuiButton doneBtn;

	public GuiCardPuncher(TileEntityCardPuncher tile, InventoryPlayer player) {
		super(new ContainerCardPuncher(tile, player));
		this.inventory = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
				.cast(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
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

	// TODO JEI Ghost item support
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.enabled) {
			ItemStack cardIn = inventory.getStackInSlot(0);
			ItemStack cardOut = inventory.getStackInSlot(1);
			ItemStack dye = inventory.getStackInSlot(2);
			if(!cardIn.isEmpty() && cardOut.isEmpty()) {
				NBTTagCompound tag = new NBTTagCompound();
				if(dye.getItem() instanceof ItemDye) {
					tag.setInteger("dye", dye.getMetadata());
				}
				// TODO This is funky - in a bad way
				NonNullList<ItemStack> tempStacks = NonNullList.create();
				for(int i = 3; i < 13; i++) {
					tempStacks.add(inventory.getStackInSlot(i));
				}
				ItemStackHandler tempHandler = new ItemStackHandler(tempStacks);
				tag.setTag("inventory", tempHandler.serializeNBT());

				cardIn.setTagCompound(tag);
				cardOut = cardIn;
				cardIn.setCount(0);
			}
		}
	}
}
