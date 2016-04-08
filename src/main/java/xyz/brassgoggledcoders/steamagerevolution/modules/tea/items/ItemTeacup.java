package xyz.brassgoggledcoders.steamagerevolution.modules.tea.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.boilerplate.lib.common.items.ItemSubBase;
import xyz.brassgoggledcoders.steamagerevolution.lib.ItemTypes;

import java.util.List;

public class ItemTeacup extends ItemSubBase
{
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.DRINK;
	}

	public ItemTeacup()
	{
		super("teacup", "tea", ItemTypes.teacup);
		setHasSubtypes(true);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 20;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		if(stack.getItemDamage() > getMetaFromName("empty") && !player.capabilities.isCreativeMode)
		{
				int damage = stack.getItemDamage();
				stack.setItemDamage(damage - 1);
				player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 200, 2));
		}
		//TODO: Proper Achievements
		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
		return stack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(stack.getItemDamage() != getMetaFromName("empty"))
		{
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}

		return stack;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list)
	{
		list.add(new ItemStack(item, 1, getMetaFromName("empty")));
		list.add(new ItemStack(item, 1, getMetaFromName("full")));
	}

	@SuppressWarnings("all")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List l, boolean flag)
	{
		l.add(StatCollector.translateToLocal("desc." + getUnlocalizedName() + "." + getNameFromMeta(stack.getItemDamage())));
		l.add(getSipsLeft(stack));
	}

	public String getSipsLeft(ItemStack stack)
	{
		if(stack.getItemDamage() < getMaxMeta())
		{
			return StatCollector.translateToLocal("desc." + getUnlocalizedName() + "." + stack.getItemDamage() + "sips");
		}

		return "";
	}
}
