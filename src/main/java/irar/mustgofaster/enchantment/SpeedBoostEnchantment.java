package irar.mustgofaster.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class SpeedBoostEnchantment extends Enchantment {

	private final int minLevel = 1;
	private final int maxLevel = 5;

	protected SpeedBoostEnchantment() {
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
		return true;//TODO
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
		return true;//TODO
	}

	@Override
	public boolean isAllowedOnBooks() {
		return true;
	}
	
	

}
