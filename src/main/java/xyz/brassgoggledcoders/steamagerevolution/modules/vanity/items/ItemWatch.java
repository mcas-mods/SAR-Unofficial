package xyz.brassgoggledcoders.steamagerevolution.modules.vanity.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.lib.common.items.ItemBase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ItemWatch extends ItemBase
{
	public ItemWatch()
	{
		super("vanity", "watch");
		this.setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(final ItemStack stack, final World world, final EntityPlayer player)
	{
		if(!world.isRemote)
		{
			final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

			String mcTimeMessage = StatCollector.translateToLocal("desc.item.watch.mcTime") + world.getWorldTime();
			ChatComponentText mcTimeComponent = new ChatComponentText(mcTimeMessage);
			mcTimeComponent.getChatStyle().setColor(EnumChatFormatting.GOLD);
			player.addChatComponentMessage(mcTimeComponent);

			String rwTimeMessage = StatCollector.translateToLocal("desc.item.watch.rwTime")
					+ sdf.format(Calendar.getInstance().getTime());
			ChatComponentText rwTimeComponent = new ChatComponentText(rwTimeMessage);
			rwTimeComponent.getChatStyle().setColor(EnumChatFormatting.GOLD);
			player.addChatComponentMessage(rwTimeComponent);
		}

		return stack;
	}
}
