package xyz.brassgoggledcoders.steamagerevolution.modules.armory.tileenties;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.network.PacketGunCraft;

@SideOnly(Side.CLIENT)
public class GuiGunsmithingBench extends GuiContainer {
	private static ResourceLocation guiTexture = new ResourceLocation("minecraft",
			"textures/gui/container/crafting_table.png");
	TileEntityGunsmithingBench tile;

	public GuiGunsmithingBench(EntityPlayer entityPlayer, TileEntityGunsmithingBench tile) {
		super(new ContainerGunsmithingBench(entityPlayer, tile));
		this.tile = tile;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.renderEngine.bindTexture(guiTexture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		this.addButton(new GuiButtonImage(0, 80, 40, 22, 22, 90, 220, 0,
				new ResourceLocation("minecraft", "textures/gui/container/beacon.png")));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		SteamAgeRevolution.instance.getPacketHandler().sendToServer(new PacketGunCraft(tile.getPos()));
	}

}
