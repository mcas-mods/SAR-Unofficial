package xyz.brassgoggledcoders.steamagerevolution.modules.armory.items;

import java.util.HashSet;
import java.util.List;

import javax.annotation.*;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.IBaseMod;
import com.teamacronymcoders.base.IModAware;
import com.teamacronymcoders.base.client.models.IHasModel;
import com.teamacronymcoders.base.util.TinkersUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.armory.ModuleArmory;

public class ItemDrill extends ItemTool implements IHasModel, IModAware {

	boolean creativeTabSet = false;
	String name;
	private IBaseMod mod;

	public ItemDrill(String name, ToolMaterial material) {
		super(material, new HashSet<>());
		setTranslationKey(name);
		this.name = name;
		setHarvestLevel("pickaxe", material.getHarvestLevel());
		setHarvestLevel("shovel", material.getHarvestLevel());
	}

	@Override
	public List<String> getModelNames(List<String> modelNames) {
		modelNames.add(name);
		return modelNames;
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
	public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
		World world = player.getEntityWorld();

		// TODO Move this to earlier
		if(ModuleArmory.KNOWN_ORES.contains(world.getBlockState(pos).getBlock())) {
			return true;
		}

		for(BlockPos extra : TinkersUtils.calcAOEBlocks(stack, world, player, pos, 3, 3, 1)) {
			TinkersUtils.breakExtraBlock(stack, world, player, extra, pos);
		}
		return false;
	}

	@Override
	public boolean canHarvestBlock(IBlockState blockIn, ItemStack stack) {
		Block block = blockIn.getBlock();
		if(block == Blocks.OBSIDIAN) {
			return toolMaterial.getHarvestLevel() == 3;
		}
		return Items.STONE_SHOVEL.canHarvestBlock(blockIn, stack);
	}

}
