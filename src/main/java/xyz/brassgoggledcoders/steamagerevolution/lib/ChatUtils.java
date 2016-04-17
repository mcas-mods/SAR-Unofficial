package xyz.brassgoggledcoders.steamagerevolution.lib;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

/**
 * Class stolen from BloodMagic, which was stolen from EnderCore, which stole the idea from ExtraUtilities.
 * Original class link:
 * https://github.com/SleepyTrousers/EnderCore/blob/master/src/main/java/com/enderio/core/common/util/ChatUtil.java
 */

public class ChatUtils
{
	private static final int DELETION_ID = 2525277;
	private static int lastAdded;

	private static void sendNoSpamMessages(IChatComponent[] messages)
	{
		GuiNewChat chat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
		for(int i = DELETION_ID + messages.length - 1; i <= lastAdded; i++)
		{
			chat.deleteChatLine(i);
		}
		for(int i = 0; i < messages.length; i++)
		{
			chat.printChatMessageWithOptionalDeletion(messages[i], DELETION_ID + i);
		}
		lastAdded = DELETION_ID + messages.length - 1;
	}

	public static IChatComponent wrap(String s)
	{
		return new ChatComponentText(s);
	}

	public static IChatComponent[] wrap(String... s)
	{
		IChatComponent[] ret = new IChatComponent[s.length];
		for(int i = 0; i < ret.length; i++)
		{
			ret[i] = wrap(s[i]);
		}
		return ret;
	}

	public static IChatComponent wrapFormatted(String s, Object... args)
	{
		return new ChatComponentTranslation(s, args);
	}

	public static void sendChat(EntityPlayer player, String... lines)
	{
		sendChat(player, wrap(lines));
	}

	public static void sendChat(EntityPlayer player, IChatComponent... lines)
	{
		for(IChatComponent c : lines)
		{
			player.addChatComponentMessage(c);
		}
	}

	public static void sendNoSpamClient(String... lines)
	{
		sendNoSpamClient(wrap(lines));
	}

	public static void sendNoSpamClient(IChatComponent... lines)
	{
		sendNoSpamMessages(lines);
	}
}