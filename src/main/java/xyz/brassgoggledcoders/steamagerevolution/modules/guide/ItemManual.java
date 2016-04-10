package xyz.brassgoggledcoders.steamagerevolution.modules.guide;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.lib.client.guis.IOpenableGUI;
import xyz.brassgoggledcoders.boilerplate.lib.client.manual.GuiLexicon;
import xyz.brassgoggledcoders.boilerplate.lib.common.items.ItemBase;
import xyz.brassgoggledcoders.boilerplate.lib.common.manual.ILexicon;
import xyz.brassgoggledcoders.boilerplate.mod.Boilerplate;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class ItemManual extends ItemBase implements ILexicon, IOpenableGUI {

	public ItemManual() {
		super("manual");
		setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			player.openGui(SteamAgeRevolution.instance, 0, world, 0, 0, 0);
		}
		return stack;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, BlockPos blockPos) {
		Boilerplate.proxy.setLexiconStack(player.getCurrentEquippedItem());
		GuiLexicon lex = GuiLexicon.currentOpenLexicon;
		return lex;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, BlockPos blockPos) {
		return null;
	}

}
