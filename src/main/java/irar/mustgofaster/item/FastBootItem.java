package irar.mustgofaster.item;

import java.util.List;

import irar.mustgofaster.MustGoFaster;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class FastBootItem extends ArmorItem{

	public FastBootItem() {
		super(ArmorMaterial.IRON, EquipmentSlotType.FEET, new Properties().group(ItemGroup.COMBAT));
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		int speedLevel = getSpeedLevel(stack);
		tooltip.add(new TranslationTextComponent(MustGoFaster.MODID + ".speed_level", new StringTextComponent("" + speedLevel).applyTextStyle(TextFormatting.RED)).applyTextStyle(TextFormatting.AQUA));
		if(hasStep(stack)) {
			tooltip.add(new TranslationTextComponent(MustGoFaster.MODID + ".step").applyTextStyle(TextFormatting.AQUA));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	public static boolean hasStep(ItemStack stack) {
		if(stack.hasTag() && stack.getTag().contains("has_step")) {
			return stack.getTag().getBoolean("has_step");
		}
		return false;
	}

	public static int getSpeedLevel(ItemStack stack) {
		if(stack.hasTag() && stack.getTag().contains("speed_level")) {
			return stack.getTag().getInt("speed_level");
		}
		return 1;
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if(group.equals(this.getGroup())) {
			for(int i = 0; i < 5; i++) {
				ItemStack stack = new ItemStack(this);
				setSpeedLevel(stack, i+1);
				setStep(stack, false);
				items.add(stack);
			}
			for(int i = 0; i < 5; i++) {
				ItemStack stack = new ItemStack(this);
				setSpeedLevel(stack, i+1);
				setStep(stack, true);
				items.add(stack);
			}
		}
	}

	private void setSpeedLevel(ItemStack stack, int level) {
		CompoundNBT tag = stack.hasTag() ? stack.getTag() : new CompoundNBT();
		tag.putInt("speed_level", level);
		stack.setTag(tag);
	}
	
	private void setStep(ItemStack stack, boolean step) {
		CompoundNBT tag = stack.hasTag() ? stack.getTag() : new CompoundNBT();
		tag.putBoolean("has_step", step);
		stack.setTag(tag);
	}
	
	

}
