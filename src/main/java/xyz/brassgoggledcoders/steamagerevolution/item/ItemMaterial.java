package xyz.brassgoggledcoders.steamagerevolution.item;

import java.security.InvalidParameterException;
import java.util.List;

import me.modmuss50.jsonDestroyer.api.ITexturedItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.boilerplate.lib.common.items.BaseItem;
import xyz.brassgoggledcoders.boilerplate.lib.common.registries.ItemRegistry;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.lib.ItemTypes;

public class ItemMaterial extends BaseItem implements ITexturedItem
{

	public String[] types = ItemTypes.metalsAll;

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

	public ItemStack getStackByName(String name)
	{
		return getStackByName(name, 1);
	}

	public ItemMaterial(String[] typesList, String name)
	{
		super(name);
		this.types = typesList;
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
	public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list)
	{
		for(int meta = 0; meta < types.length; ++meta)
		{
			list.add(new ItemStack(item, 1, meta));
		}
	}

	@Override
	public String getTextureName(int damage)
	{
		return SteamAgeRevolution.MODID + ":items/metals/" + types[damage] + "_" + getUnlocalizedName().substring(5);
	}

	@Override
	public int getMaxMeta()
	{
		return types.length;
	}
}
