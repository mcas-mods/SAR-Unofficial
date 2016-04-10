package xyz.brassgoggledcoders.steamagerevolution.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemSubBaseTest extends ItemBaseTest
{
	public List<String> metaNames;

	public ItemSubBaseTest(String mod, String name, List<String> metaNames)
	{
		super(mod, name);
		this.metaNames = metaNames;
		this.setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return getUnlocalizedName() + "." + getMetaName(itemstack.getItemDamage());
	}

	public String getMetaName(int meta)
	{
		return metaNames.get(meta);
	}

	public int getNumberOfSubItems()
	{
		return metaNames.size();
	}

	@Override
	public String[] getResourceLocations()
	{
		int numberOfSubItems = getNumberOfSubItems();
		String[] locations = new String[getNumberOfSubItems()];
		for(int i = 0; i < numberOfSubItems; i++)
		{
			locations[i] = texturePath + name + "_" + getMetaName(i);
		}
		return locations;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list)
	{
		for (int i = 0; i < getNumberOfSubItems(); i++)
		{
			ItemStack stack = new ItemStack(item, 1, i);
			list.add(stack);
		}
	}
}
