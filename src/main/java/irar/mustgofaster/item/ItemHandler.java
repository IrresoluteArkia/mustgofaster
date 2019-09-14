package irar.mustgofaster.item;

import java.util.ArrayList;
import java.util.List;

import irar.mustgofaster.MustGoFaster;
import net.minecraft.item.Item;
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

}
