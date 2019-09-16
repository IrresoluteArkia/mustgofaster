package irar.mustgofaster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import irar.mustgofaster.item.FastBootItem;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.ISprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.ItemLayerModel;

public class BootModelLoader implements ICustomModelLoader {
	
	private static final ModelResourceLocation ACCEPTS = new ModelResourceLocation(new ResourceLocation(MustGoFaster.MODID, "fast_boots"), "inventory");
	private static List<ResourceLocation> LOCATIONS = new ArrayList<>();
	
	static {
		String[] extraLocs = new String[] {
				"fast_boots_base",
				"lvl_2_overlay",
				"lvl_3_overlay",
				"lvl_4_overlay",
				"lvl_5_overlay",
				"step_up_overlay"
		};
		for(String loc : extraLocs) {
			LOCATIONS.add(new ResourceLocation(MustGoFaster.MODID, "items/" + loc));
		}
	}
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
	}

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		return modelLocation.equals(ACCEPTS);
	}

	@Override
	public IUnbakedModel loadModel(ResourceLocation modelLocation) throws Exception {
		return new BootModel();
	}

	public class BootModelOverrideList extends ItemOverrideList {
		
		
		public BootModelOverrideList() {
			super();
		}

		@Override
		public IBakedModel getModelWithOverrides(IBakedModel model, ItemStack stack, World worldIn, LivingEntity entityIn) {
			BootBaked boot = (BootBaked) model;
			int modelId = FastBootItem.getSpeedLevel(stack) * 2 + (FastBootItem.hasStep(stack) ? 1 : 0);
			if(boot.cache.containsKey(modelId)) {
				return boot.cache.get(modelId);
			}
			Builder<ResourceLocation> locs = ImmutableList.<ResourceLocation>builder();
			locs.add(LOCATIONS.get(0));
			int speedLevel = FastBootItem.getSpeedLevel(stack);
			switch(speedLevel) {
			case 2:
			case 3:
			case 4:
			case 5:
				locs.add(LOCATIONS.get(speedLevel-1));
			}
			if(FastBootItem.hasStep(stack)) {
				locs.add(LOCATIONS.get(5));
			}
			IBakedModel result = new ItemLayerModel(locs.build()).bake(boot.bakery, boot.spriteGetter, boot.sprite, boot.format);
			boot.cache.put(modelId, result);
			return result;
		}

	}
	
	public class BootModel implements IUnbakedModel{
		
		public ResourceLocation def = new ModelResourceLocation(new ResourceLocation(MustGoFaster.MODID, "fast_boots_base"), "inventory");
		

		@Override
		public IBakedModel bake(ModelBakery bakery, Function<ResourceLocation, TextureAtlasSprite> spriteGetter, ISprite sprite, VertexFormat format) {
			return new BootBaked(bakery.getUnbakedModel(def).bake(bakery, spriteGetter, sprite, format), bakery, spriteGetter, sprite, format);
		}

		@Override
		public Collection<ResourceLocation> getDependencies() {
			return LOCATIONS;
		}

		@Override
		public Collection<ResourceLocation> getTextures(Function<ResourceLocation, IUnbakedModel> modelGetter,
				Set<String> missingTextureErrors) {
			return LOCATIONS;
		}
		
	}
	
	public class BootBaked implements IBakedModel{

		private HashMap<Integer, IBakedModel> cache = new HashMap<>();
		private ModelBakery bakery;
		private Function<ResourceLocation, TextureAtlasSprite> spriteGetter;
		private ISprite sprite;
		private VertexFormat format;
		private IBakedModel def;

		public BootBaked(IBakedModel def, ModelBakery bakery, Function<ResourceLocation, TextureAtlasSprite> spriteGetter, ISprite sprite, VertexFormat format) {
			this.def = def;
			this.bakery = bakery;
			this.spriteGetter = spriteGetter;
			this.sprite = sprite;
			this.format = format;
		}

		@SuppressWarnings("deprecation")
		@Override
		public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand) {
			return def.getQuads(state, side, rand);
		}

		@Override
		public boolean isAmbientOcclusion() {
			return def.isAmbientOcclusion();
		}

		@Override
		public boolean isGui3d() {
			return def.isGui3d();
		}

		@Override
		public boolean isBuiltInRenderer() {
			return def.isBuiltInRenderer();
		}

		@SuppressWarnings("deprecation")
		@Override
		public TextureAtlasSprite getParticleTexture() {
			return def.getParticleTexture();
		}

		@Override
		public ItemOverrideList getOverrides() {
			return new BootModelOverrideList();
		}
		
	}
	
}
