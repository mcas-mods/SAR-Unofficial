package xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IHasInventory;

@SideOnly(Side.CLIENT)
public class GuiDrill extends GuiInventory {
	
	public GuiDrill(EntityPlayer player, IHasInventory holder) {
		super(player, holder);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		ControllerDrill drill = (ControllerDrill) holder;
		if(drill.oreLevels.containsKey("Gold")) {
			this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.GOLDEN_APPLE, drill.oreLevels.get("Gold")), 50, 50);
		}
	}
}
