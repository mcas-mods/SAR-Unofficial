package xyz.brassgoggledcoders.steamagerevolution.item;

import java.security.InvalidParameterException;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.init.InitItems;
import xyz.brassgoggledcoders.steamagerevolution.lib.MetalTypes;

public class ItemNuggets extends Item {

    public static final String[] types = MetalTypes.metalsNugget;
    
    public static ItemStack getStackByName(String name, int count) {
        for (int i = 0; i < types.length; i++) {
            if (types[i].equalsIgnoreCase(name)) {
                return new ItemStack(InitItems.ingots, i);
            }
        }
        throw new InvalidParameterException(name + " could not be found.");
    }

    public static ItemStack getStackByName(String name) {
        return getStackByName(name, 1);
    }


    public ItemNuggets() {
        setCreativeTab(CreativeTabs.tabMaterials);
        setHasSubtypes(true);
        setUnlocalizedName(SteamAgeRevolution.MODID+":nugget");
    }


    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        int meta = itemStack.getItemDamage();
        if (meta < 0 || meta >= types.length) {
            meta = 0;
        }

        return super.getUnlocalizedName() + "." + types[meta];
    }
    
    public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
        for (int meta = 0; meta < types.length; ++meta) {
            list.add(new ItemStack(item, 1, meta));
        }
    }
}
