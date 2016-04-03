package xyz.brassgoggledcoders.steamagerevolution.item;

import java.security.InvalidParameterException;
import java.util.List;

import me.modmuss50.jsonDestroyer.api.ITexturedItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.boilerplate.lib.common.items.BaseItem;
import xyz.brassgoggledcoders.boilerplate.lib.common.registries.ItemRegistry;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.lib.ItemTypes;

public class ItemTeacup extends BaseItem implements ITexturedItem
{

	public String[] types = ItemTypes.teacup;

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.DRINK;
	}

	public ItemStack getStackByName(String name, int count)
	{
		for(int i = 0; i < types.length; i++)
		{
			if(types[i].equalsIgnoreCase(name))
			{
				return new ItemStack(ItemRegistry.getItem(this.getUnlocalizedName()), i);
			}
		}
		throw new InvalidParameterException(name + " could not be found.");
	}

	public int getMetaByName(String name)
	{
		for(int i = 0; i < types.length; i++)
		{
			if(types[i].equalsIgnoreCase(name))
			{
				return i;
			}
		}
		throw new InvalidParameterException(name + " could not be found.");
	}

	public ItemStack getStackByName(String name)
	{
		return getStackByName(name, 1);
	}

	public ItemTeacup()
	{
		super("teacup");
		setHasSubtypes(true);
		SteamAgeRevolution.jsonDestroyer.registerObject(this);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining)
	{
		return new ModelResourceLocation(SteamAgeRevolution.MODID + ":" + getUnlocalizedName(stack).substring(5),
				"inventory");
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		int meta = itemStack.getItemDamage();
		if(meta < 0 || meta >= types.length)
		{
			meta = 0;
		}

		return super.getUnlocalizedName() + "." + types[meta];
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 20;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		if(stack.getItemDamage() > getMetaByName("empty") && !player.capabilities.isCreativeMode)
		{
			if(stack.stackSize != 1)
			{
				int damage = stack.getItemDamage();
				--stack.stackSize;
				player.inventory.addItemStackToInventory(new ItemStack(this, 1, damage - 1));
				player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 50, 10));
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 50, 10));
			}
			else
			{
				stack.setItemDamage(stack.getItemDamage() - 1);
			}
		}
		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
		return stack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(stack.getItemDamage() != getMetaByName("empty"))
		{
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}

		return stack;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list)
	{
		list.add(new ItemStack(item, 1, getMetaByName("empty")));
		list.add(new ItemStack(item, 1, getMetaByName("full")));
	}

	@SuppressWarnings("all")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List l, boolean flag)
	{
		l.add(StatCollector.translateToLocal("desc." + getUnlocalizedName() + "." + types[stack.getItemDamage()]));
		l.add(getSipsLeft(stack));
	}

	public String getSipsLeft(ItemStack stack)
	{
		if(stack.getItemDamage() == getMetaByName("full"))
			return StatCollector.translateToLocal("desc." + getUnlocalizedName() + ".4sips");
		if(stack.getItemDamage() == getMetaByName("threequarter"))
			return StatCollector.translateToLocal("desc." + getUnlocalizedName() + ".3sips");
		if(stack.getItemDamage() == getMetaByName("half"))
			return StatCollector.translateToLocal("desc." + getUnlocalizedName() + ".2sips");
		if(stack.getItemDamage() == getMetaByName("quarter"))
			return StatCollector.translateToLocal("desc." + getUnlocalizedName() + ".1sips");
		if(stack.getItemDamage() == getMetaByName("empty"))
			return StatCollector.translateToLocal("desc." + getUnlocalizedName() + ".0sips");

		return "null sips left";
	}

	@Override
	public String getTextureName(int damage)
	{
		return SteamAgeRevolution.MODID + ":items/teacup_" + types[damage];
	}

	@Override
	public int getMaxMeta()
	{
		return types.length;
	}
	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		if(stack.getMetadata() != getMetaByName("empty"))
			return 1;
		else
		return this.getItemStackLimit();
	}
}
