package xyz.brassgoggledcoders.steamagerevolution.modules.vanity.items;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.modmuss50.jsonDestroyer.api.ITexturedItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.lib.common.items.BaseItem;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class ItemWatch extends BaseItem implements ITexturedItem
{
	public ItemWatch()
	{
		super("watch");
		this.setMaxStackSize(1);
		SteamAgeRevolution.jsonDestroyer.registerObject(this);
	}

	@Override
	public ItemStack onItemRightClick(final ItemStack stack, final World world, final EntityPlayer player)
	{
		if(!world.isRemote)
		{
			final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

			String message = StatCollector.translateToLocal("desc.item.watch.mcTime") + world.getWorldTime();
			ChatComponentText component = new ChatComponentText(message);
			component.getChatStyle().setColor(EnumChatFormatting.GOLD);
			player.addChatComponentMessage(component);

			String message1 = StatCollector.translateToLocal("desc.item.watch.rwTime")
					+ sdf.format(Calendar.getInstance().getTime());
			ChatComponentText component1 = new ChatComponentText(message1);
			component1.getChatStyle().setColor(EnumChatFormatting.GOLD);
			player.addChatComponentMessage(component1);
		}

		return stack;
	}

	@Override
	public String getTextureName(int damage)
	{
		return SteamAgeRevolution.MODID + ":items/watch";
	}

	@Override
	public int getMaxMeta()
	{
		return 1;
	}
}
