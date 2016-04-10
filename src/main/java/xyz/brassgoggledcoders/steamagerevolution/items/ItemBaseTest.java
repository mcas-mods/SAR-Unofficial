package xyz.brassgoggledcoders.steamagerevolution.items;

import net.minecraft.item.Item;
import xyz.brassgoggledcoders.boilerplate.lib.BoilerplateLib;
import xyz.brassgoggledcoders.boilerplate.lib.client.models.IHasModel;

public class ItemBaseTest extends Item implements IHasModel
{
	protected String texturePath;
	protected String name;

	public ItemBaseTest(String texturePath, String name)
	{
		super();
		this.name = name;
		this.texturePath = texturePath;
		if(!texturePath.isEmpty() && !texturePath.endsWith("/"))
		{
			this.texturePath += "/";
		}
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(BoilerplateLib.getMod().getCreativeTab());
	}

	@Override
	public String[] getResourceLocations()
	{
		return new String[] {texturePath + name};
	}
}
