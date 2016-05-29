package xyz.brassgoggledcoders.steamagerevolution.modules.guide;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.client.guis.IOpenableGUI;
import xyz.brassgoggledcoders.boilerplate.client.manual.GuiLexicon;
import xyz.brassgoggledcoders.boilerplate.items.ItemBase;
import xyz.brassgoggledcoders.boilerplate.manual.ILexicon;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class ItemManual extends ItemBase implements ILexicon, IOpenableGUI {

	public ItemManual() {
		super("manual");
		setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{
		if (world.isRemote)
		{
			player.openGui(SteamAgeRevolution.instance, 0, world, 0, 0, 0);
			return new ActionResult(EnumActionResult.SUCCESS, stack);
		}
		return new ActionResult(EnumActionResult.FAIL, stack);
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, BlockPos blockPos) {
		//TODO
		//SteamAgeRevolution.getProxy().setLexiconStack(player.getCurrentEquippedItem());
		return GuiLexicon.currentOpenLexicon;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, BlockPos blockPos) {
		return null;
	}

}
