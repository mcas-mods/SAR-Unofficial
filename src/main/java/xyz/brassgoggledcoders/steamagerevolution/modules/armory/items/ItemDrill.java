package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.teamacronymcoders.base.IBaseMod;
import com.teamacronymcoders.base.IModAware;
import com.teamacronymcoders.base.client.models.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.utils.TinkersUtils;

public class ItemDrill extends ItemTool implements IHasModel, IModAware  {

	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.DIRT, Blocks.STONE);
	
	boolean creativeTabSet = false;
	String name;
	private IBaseMod mod;
	
	public ItemDrill(String name, ToolMaterial material) {
		super(material, EFFECTIVE_ON);
		setTranslationKey(name);
		this.name = name;
	}
	
	@Override
	@ParametersAreNonnullByDefault
	public void getSubItems(@Nullable CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if(tab != null && tab == getCreativeTab() || tab == CreativeTabs.SEARCH) {
			subItems.addAll(getAllSubItems(Lists.newArrayList()));
		}
	}

	@Override
	public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
		itemStacks.add(new ItemStack(this, 1));
		return itemStacks;
	}

	@Override
	@Nonnull
	public Item setCreativeTab(@Nonnull CreativeTabs tab) {
		if(!creativeTabSet) {
			super.setCreativeTab(tab);
			creativeTabSet = true;
		}
		return this;
	}

	@Override
	public IBaseMod getMod() {
		return mod;
	}

	@Override
	public void setMod(IBaseMod mod) {
		this.mod = mod;
	}

	@Override
	public Item getItem() {
		return this;
	}

	@Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        return Sets.newHashSet("pickaxe", "shovel");
    }
	
	@Override
	public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player)
	{
		World world = player.getEntityWorld();
		for(BlockPos extra : TinkersUtils.calcAOEBlocks(stack, world, player, pos, 3, 3, 1)) {
			TinkersUtils.breakExtraBlock(stack, world, player, extra, pos);
		}
		return false;
	}
	
	@Override
	public boolean canHarvestBlock(IBlockState blockIn)
    {
		return (Items.STONE_PICKAXE.canHarvestBlock(blockIn) || Items.STONE_SHOVEL.canHarvestBlock(blockIn));
    }

	//Elevate to public
	@Override
	  public RayTraceResult rayTrace(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, boolean useLiquids) {
	    return super.rayTrace(worldIn, playerIn, useLiquids);
	}
	
}
