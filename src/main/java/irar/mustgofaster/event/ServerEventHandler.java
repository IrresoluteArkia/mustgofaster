package irar.mustgofaster.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import irar.mustgofaster.MustGoFaster;
import irar.mustgofaster.enchantment.EnchantmentHandler;
import irar.mustgofaster.item.FastBootItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ServerEventHandler {
	
	private static final UUID SPEED_BOOST_UUID = UUID.fromString("f1b2bc1a-d721-11e9-8a34-2a2ae2dbcce4");
	private static List<LivingEntity> hasStepAssist = new ArrayList<>();

	@SubscribeEvent
	public void entTick(LivingEvent.LivingUpdateEvent event) {
		LivingEntity entity = event.getEntityLiving();
		updateSpeed(entity);
		updateStepAssist(entity);
	}

	private void updateStepAssist(LivingEntity entity) {
		if(hasStepAssist.contains(entity)) {
			if(shouldPlayerHaveAssist(entity)) {
				if(entity instanceof PlayerEntity) {
					PlayerEntity player = (PlayerEntity) entity;
					if(player.isSneaking()) {
						player.stepHeight = 0.60001F;
					}else {
						player.stepHeight = 1.25F;
					}
				}else {
					entity.stepHeight = 1.25F;
				}
			}else {
				entity.stepHeight = 0.6F;
				hasStepAssist.remove(entity);
			}
		}else if(shouldPlayerHaveAssist(entity)) {
			hasStepAssist.add(entity);
			entity.stepHeight = 1.25F;
		}
	}

	private boolean shouldPlayerHaveAssist(LivingEntity entity) {
		for(ItemStack armor : entity.getArmorInventoryList()) {
			Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(armor);
			if(enchantments.containsKey(EnchantmentHandler.stepAssist)) {
				return true;
			}
			if(armor.getItem() instanceof FastBootItem) {
				return FastBootItem.hasStep(armor);
			}
		}
		return false;
	}

	private void updateSpeed(LivingEntity entity) {
		IAttributeInstance speedAttribute = entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
		if(speedAttribute.getModifier(SPEED_BOOST_UUID) != null) {
			speedAttribute.removeModifier(speedAttribute.getModifier(SPEED_BOOST_UUID));
		}
		int modifierStrength = 0;
		for(ItemStack armor : entity.getArmorInventoryList()) {
			Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(armor);
			if(enchantments.containsKey(EnchantmentHandler.speedBoost)) {
				modifierStrength += enchantments.get(EnchantmentHandler.speedBoost) + 1;
			}
			if(armor.getItem() instanceof FastBootItem) {
				modifierStrength += FastBootItem.getSpeedLevel(armor);
			}
		}
		if(modifierStrength > 0) {
			speedAttribute.applyModifier(getSpeedAttributeModifier(modifierStrength));
		}
	}

	private AttributeModifier getSpeedAttributeModifier(int modifierStrength) {
		return new AttributeModifier(SPEED_BOOST_UUID, MustGoFaster.MODID + "_speed_boost", modifierStrength * 0.25, AttributeModifier.Operation.MULTIPLY_BASE);
	}
	
}
