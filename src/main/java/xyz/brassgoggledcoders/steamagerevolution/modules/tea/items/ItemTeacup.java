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
	public ItemTeacup()
	{
		super("tea", "teacup", ItemTypes.teacup);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 20;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.DRINK;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		if(stack.getItemDamage() > 0 && !player.capabilities.isCreativeMode)
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
		if(stack.getItemDamage() != 0)
		{
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}

		return stack;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list)
	{
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, getNumberOfSubItems() - 1));
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean flag)
	{
		list.add(StatCollector.translateToLocal("desc." + getUnlocalizedName() + "." + getMetaName(stack)));
		list.add(getSipsLeft(stack));
	}

	public String getSipsLeft(ItemStack stack)
	{
		if(stack.getItemDamage() < getNumberOfSubItems())
		{
			return StatCollector.translateToLocal("desc." + getUnlocalizedName() + "." + stack.getItemDamage() + "sips");
		}

		return "";
	}
}
