package irar.mustgofaster.enchantment;

import java.util.ArrayList;
import java.util.List;

import irar.mustgofaster.MustGoFaster;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.registries.IForgeRegistry;

public class EnchantmentHandler {
	
	public static Enchantment speedBoost;
	public static Enchantment stepAssist;
	
	private static List<Enchantment> allEnchantments = new ArrayList<>();

	public static void init() {
		speedBoost = new SpeedBoostEnchantment().setRegistryName(MustGoFaster.MODID, "speed_boost");
		stepAssist = new StepAssistEnchantment().setRegistryName(MustGoFaster.MODID, "step_assist");
		
		allEnchantments.add(speedBoost);
		allEnchantments.add(stepAssist);
	}

	public static void register(IForgeRegistry<Enchantment> registry) {
		registry.registerAll(allEnchantments.toArray(new Enchantment[0]));
	}

}
