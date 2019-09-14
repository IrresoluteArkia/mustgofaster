package irar.mustgofaster.enchantment;

import irar.mustgofaster.item.FastBootItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class StepAssistEnchantment extends Enchantment {

	private final int minLevel = 1;
	private final int maxLevel = 1;

	protected StepAssistEnchantment() {
		super(Rarity.COMMON, EnchantmentType.ARMOR_FEET, new EquipmentSlotType[] {EquipmentSlotType.FEET});
	}

	@Override
	public int getMinLevel() {
		return minLevel;
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return !(stack.getItem() instanceof FastBootItem);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return false;
	}

	@Override
	public boolean isCurse() {
		return false;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return !(stack.getItem() instanceof FastBootItem);
	}

	@Override
	public boolean isAllowedOnBooks() {
		return true;
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel);
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMaxEnchantability(enchantmentLevel) + 50;
	}
	
	

}
