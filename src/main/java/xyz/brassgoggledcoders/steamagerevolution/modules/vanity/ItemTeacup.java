package xyz.brassgoggledcoders.steamagerevolution.modules.vanity;

import java.util.Arrays;
import java.util.List;

import com.teamacronymcoders.base.items.IHasSubItems;
import com.teamacronymcoders.base.items.ItemBase;

import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public class ItemTeacup extends ItemBase implements IHasSubItems {

	// TODO
	public static List<String> teacup = Arrays.asList("empty", "quarter", "half", "threequarter", "full");

	public ItemTeacup() {
		super("teacup");
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 20;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}

	/*
	 * @Override public ItemStack onItemUseFinish(ItemStack stack, World world,
	 * EntityPlayer player) { if(stack.getItemDamage() > 0 &&
	 * !player.capabilities.isCreativeMode) { int damage = stack.getItemDamage();
	 * stack.setItemDamage(damage - 1); player.addPotionEffect(new
	 * PotionEffect(Potion.digSpeed.id, 200, 2)); } //TODO: Proper Achievements
	 * player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
	 * return stack; }
	 * 
	 * @Override public ItemStack onItemRightClick(ItemStack stack, World world,
	 * EntityPlayer player) { if(stack.getItemDamage() != 0) {
	 * player.setItemInUse(stack, this.getMaxItemUseDuration(stack)); } return
	 * stack; }
	 */

	public String getSipsLeft(ItemStack stack) {
		// if(stack.getItemDamage() < getNumberOfSubItems()) {
		// return StatCollector.translateToLocal("desc." + getTranslationKey() + "." +
		// stack.getItemDamage() +
		// "sips");
		// }

		return "";
	}
}
