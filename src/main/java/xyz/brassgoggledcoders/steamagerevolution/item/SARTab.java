package xyz.brassgoggledcoders.steamagerevolution.item;

import net.minecraft.item.Item;
import xyz.brassgoggledcoders.boilerplate.lib.common.BaseCreativeTab;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.init.InitItems;

public class SARTab extends BaseCreativeTab {

	public SARTab() {
		super(SteamAgeRevolution.MODID+"_tab");
	}
	
	@Override
    public boolean hasSearchBar()
    {
        return true;
    }

	@Override
	public Item getTabIconItem() {
		return InitItems.plates;
	}

}
