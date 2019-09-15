package irar.mustgofaster.crafting;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.common.crafting.IngredientNBT;

public class CheckOnlySpecifiedNBTIngredient extends IngredientNBT {

	private ItemStack stack;

	protected CheckOnlySpecifiedNBTIngredient(ItemStack stack) {
		super(stack);
		this.stack = stack;
	}
	
    @Override
    public boolean test(@Nullable ItemStack input)
    {
        if (input == null)
            return false;
        return this.stack.getItem() == input.getItem() && this.stack.getDamage() == input.getDamage() && checkHasTags(input.getTag(), this.stack.getTag());
    }

	private boolean checkHasTags(CompoundNBT input, CompoundNBT check) {
		for(String key : check.keySet()) {
			if(input.contains(key)) {
				if(!input.get(key).equals(check.get(key))) {
					return false;
				}
			}
		}
		return true;
	}

    public static class Serializer implements IIngredientSerializer<CheckOnlySpecifiedNBTIngredient>
    {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public CheckOnlySpecifiedNBTIngredient parse(PacketBuffer buffer) {
            return new CheckOnlySpecifiedNBTIngredient(buffer.readItemStack());
        }

        @Override
        public CheckOnlySpecifiedNBTIngredient parse(JsonObject json) {
            return new CheckOnlySpecifiedNBTIngredient(CraftingHelper.getItemStack(json, true));
        }

        @Override
        public void write(PacketBuffer buffer, CheckOnlySpecifiedNBTIngredient ingredient) {
            buffer.writeItemStack(ingredient.stack);
        }	
    }
}
