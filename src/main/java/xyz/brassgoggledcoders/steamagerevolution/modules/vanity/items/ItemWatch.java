package xyz.brassgoggledcoders.steamagerevolution.modules.vanity.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.items.ItemBaseTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ItemWatch extends ItemBaseTest
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

			String minecraftTime = "Minecraft Time: " + world.getWorldTime();
			ChatComponentText component = new ChatComponentText(minecraftTime);
			component.getChatStyle().setColor(EnumChatFormatting.GOLD);
			player.addChatComponentMessage(component);

			String realWorldTime = "Real-World Time: " + sdf.format(Calendar.getInstance().getTime());
			ChatComponentText component1 = new ChatComponentText(realWorldTime);
			component1.getChatStyle().setColor(EnumChatFormatting.GOLD);
			player.addChatComponentMessage(component1);
		}

		return stack;
	}
}
