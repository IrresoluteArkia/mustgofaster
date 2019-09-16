package irar.mustgofaster;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.BasicState;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import irar.mustgofaster.crafting.CheckOnlySpecifiedNBTIngredient;
import irar.mustgofaster.crafting.FastBootsUpgradeRecipe;
import irar.mustgofaster.enchantment.EnchantmentHandler;
import irar.mustgofaster.event.ServerEventHandler;
import irar.mustgofaster.item.ItemHandler;

import java.util.Map;
import java.util.stream.Collectors;

@Mod(MustGoFaster.MODID)
public class MustGoFaster
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "mustgofaster";

    public MustGoFaster() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
        this.registerServerEventHandler();
    }
    
    public void registerServerEventHandler() {
        MinecraftForge.EVENT_BUS.register(new ServerEventHandler());
    }

    private void setup(final FMLCommonSetupEvent event){
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event){
    }

    private void processIMC(final InterModProcessEvent event){
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onEnchantmentsRegistry(final RegistryEvent.Register<Enchantment> event) {
        	EnchantmentHandler.init();
        	EnchantmentHandler.register(event.getRegistry());
        }
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
        	ItemHandler.init();
        	ItemHandler.register(event.getRegistry());
        }
    	@SubscribeEvent
    	public static void modelRegistryReady(ModelRegistryEvent event) {
    		ItemHandler.registerCustomModels(event);
    		ModelLoaderRegistry.registerLoader(new BootModelLoader());
    	}
    	
    	@SubscribeEvent
    	public static void modelBake(ModelBakeEvent event) {
    		ResourceLocation loc = new ModelResourceLocation(new ResourceLocation(MustGoFaster.MODID, "fast_boots"), "inventory");
    		event.getModelRegistry().put(loc, ModelLoaderRegistry.getModelOrMissing(loc)
    		.bake(event.getModelLoader(), Minecraft.getInstance().getTextureMap()::getSprite, ModelRotation.X0_Y0, DefaultVertexFormats.ITEM));
    	}
        @SubscribeEvent
        public static void onRecipeSerializersRegistry(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
        	event.getRegistry().register(new FastBootsUpgradeRecipe.Serializer().setRegistryName(MODID, "fast_boots_upgrade"));
        	
        	CraftingHelper.register(new ResourceLocation(MODID, "cos_nbt"), CheckOnlySpecifiedNBTIngredient.Serializer.INSTANCE);
        }
    }
}
