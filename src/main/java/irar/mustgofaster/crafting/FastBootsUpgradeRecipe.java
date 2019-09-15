package irar.mustgofaster.crafting;

import java.util.Map;

import com.google.gson.JsonObject;

import irar.mustgofaster.MustGoFaster;
import irar.mustgofaster.item.FastBootItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class FastBootsUpgradeRecipe extends ShapedRecipe {
	
	private String modify;
	private String modifyType;

	public FastBootsUpgradeRecipe(ResourceLocation idIn, String groupIn, int recipeWidthIn, int recipeHeightIn,
			NonNullList<Ingredient> recipeItemsIn, ItemStack recipeOutputIn, String modify, String modifyType) {
		super(idIn, groupIn, recipeWidthIn, recipeHeightIn, recipeItemsIn, recipeOutputIn);
		this.modify = modify;
		this.modifyType = modifyType;
	}

	@Override
	public ItemStack getCraftingResult(CraftingInventory inv) {
		ItemStack boots = ItemStack.EMPTY;
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if(stack.getItem() instanceof FastBootItem) {
				boots = stack.copy();
			}
		}
		if(boots.isEmpty()) {
			return ItemStack.EMPTY;
		}
		CompoundNBT tag = boots.hasTag() ? boots.getTag() : new CompoundNBT();
		if(modifyType.contentEquals("toggle_boolean")) {
			if(tag.contains(modify)) {
				tag.putBoolean(modify, !tag.getBoolean(modify));
			}else {
				tag.putBoolean(modify, true);
			}
		}else if(modifyType.contentEquals("increment_integer")) {
			if(tag.contains(modify)) {
				tag.putInt(modify, tag.getInt(modify)+1);
			}else {
				tag.putInt(modify, 2);
			}
		}else if(modifyType.contentEquals("decrement_integer")) {
			if(tag.contains(modify)) {
				tag.putInt(modify, tag.getInt(modify)-1);
			}else {
				tag.putInt(modify, 0);
			}
		}
		boots.setTag(tag);
		return boots;
	}
	
    public static class Serializer extends ShapedRecipe.Serializer {
	      public ShapedRecipe read(ResourceLocation recipeId, JsonObject json) {
	    	  ShapedRecipe recipeBase = super.read(recipeId, json);
	    	  String modify = JSONUtils.getString(json, "modify");
	    	  String modifyType = JSONUtils.getString(json, "modify_type");
	    	  FastBootsUpgradeRecipe recipeModified = new FastBootsUpgradeRecipe(recipeId, recipeBase.getGroup(), recipeBase.getRecipeWidth(), recipeBase.getRecipeHeight(), recipeBase.getIngredients(), recipeBase.getRecipeOutput(), modify, modifyType);
	    	  return recipeModified;
	      }
		@Override
		public ShapedRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
			ShapedRecipe recipeBase = super.read(recipeId, buffer);
			String modify = buffer.readString();
			String modifyType = buffer.readString();
	    	FastBootsUpgradeRecipe recipeModified = new FastBootsUpgradeRecipe(recipeId, recipeBase.getGroup(), recipeBase.getRecipeWidth(), recipeBase.getRecipeHeight(), recipeBase.getIngredients(), recipeBase.getRecipeOutput(), modify, modifyType);
	    	return recipeModified;
		}
		@Override
		public void write(PacketBuffer buffer, ShapedRecipe recipe) {
			super.write(buffer, recipe);
			if(recipe instanceof FastBootsUpgradeRecipe) {
				FastBootsUpgradeRecipe recipe2 = (FastBootsUpgradeRecipe) recipe;
				buffer.writeString(recipe2.modify);
				buffer.writeString(recipe2.modifyType);
			}
		}
	      
	      
    }	
}
