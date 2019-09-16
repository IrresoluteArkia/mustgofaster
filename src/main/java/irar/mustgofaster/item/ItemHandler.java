package irar.mustgofaster.item;

import java.util.ArrayList;
import java.util.List;

import irar.mustgofaster.MustGoFaster;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemHandler {
	
	public static Item fastBoots;
	
	private static List<Item> allItems = new ArrayList<>();

	public static void init() {
		fastBoots = new FastBootItem().setRegistryName(MustGoFaster.MODID, "fast_boots");
		
		allItems.add(fastBoots);
	}

	public static void register(IForgeRegistry<Item> registry) {
		registry.registerAll(allItems.toArray(new Item[0]));
	}

	public static void registerCustomModels(ModelRegistryEvent event) {
		String[] extraLocs = new String[] {
				"fast_boots_base",
				"lvl_2_overlay",
				"lvl_3_overlay",
				"lvl_4_overlay",
				"lvl_5_overlay",
				"step_up_overlay"
		};
		for(String loc : extraLocs) {
			ModelLoader.addSpecialModel(new ModelResourceLocation(new ResourceLocation(MustGoFaster.MODID, loc), "inventory"));
		}
	}

}
