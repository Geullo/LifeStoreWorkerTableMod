package com.geullo.workercrafttable.Oaktong;

import com.life.item.item.ItemInit;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class RecipeOakTong {
    private static RecipeOakTong RECIPE_OAK_TONG;
    private final HashMap<OaktongRipeningIngredient,ItemStack> oakTongRecipeList = new HashMap<>();

    public static RecipeOakTong getInstance() {
        if (RECIPE_OAK_TONG == null) RECIPE_OAK_TONG = new RecipeOakTong();
        return RECIPE_OAK_TONG;
    }

    private RecipeOakTong(){
        addRecipe(Items.WATER_BUCKET, ItemInit.rice_crop, ItemInit.glass, new ItemStack(ItemInit.makgeolli,1));
        addRecipe(Items.WATER_BUCKET, ItemInit.vulgare, ItemInit.glass, new ItemStack(ItemInit.beer,1));
        addRecipe(Items.WATER_BUCKET, Items.WHEAT, ItemInit.glass, new ItemStack(ItemInit.board_car,1));
        addRecipe(Items.WATER_BUCKET, Items.POTATO, ItemInit.glass, new ItemStack(ItemInit.whiskey,1));

        addRecipe(Items.WATER_BUCKET, ItemInit.gra_fru, ItemInit.glass, new ItemStack(ItemInit.wine,1));
        addRecipe(new ItemStack(Items.SUGAR,1), new ItemStack(Items.DYE,1, 3), new ItemStack(ItemInit.board_car,1), new ItemStack(ItemInit.choco_mat,1));
        addRecipe(Items.SUGAR, Items.MILK_BUCKET, ItemInit.whiskey, new ItemStack(ItemInit.milk_punch,1));
        addRecipe(new ItemStack(Items.SUGAR,1), new ItemStack(Items.DYE,1,4), new ItemStack(ItemInit.whiskey,1), new ItemStack(ItemInit.blue_hawaii,1));

        addRecipe(Items.SUGAR, ItemInit.oran_fru, ItemInit.wine, new ItemStack(ItemInit.shangria,1));
        addRecipe(new ItemStack(Items.SUGAR,1), new ItemStack(Items.DYE,1,3), new ItemStack(ItemInit.makgeolli,1), new ItemStack(ItemInit.coffee_makgeolli,1));
        addRecipe(Items.SUGAR, ItemInit.beer, ItemInit.whiskey, new ItemStack(ItemInit.god_thing,1));
        addRecipe(Items.SUGAR, ItemInit.lemon_fru, ItemInit.board_car, new ItemStack(ItemInit.lemon_drop,1));


        addRecipe(new ItemStack(ItemInit.meat_bul,1), new ItemStack(Items.DYE,1,3), new ItemStack(ItemInit.makgeolli,1), new ItemStack(ItemInit.makgeolli_jeongsik,1));
        addRecipe(Items.SUGAR, ItemInit.silver_ring, ItemInit.board_car, new ItemStack(ItemInit.fish_tear,1));
        addRecipe(ItemInit.rose_sac, Items.SUGAR, ItemInit.wine, new ItemStack(ItemInit.flower_shangria,1));
    }
    public void clearRecipe() {
        oakTongRecipeList.clear();
        addRecipe(Items.WATER_BUCKET, ItemInit.rice_crop, ItemInit.glass, new ItemStack(ItemInit.makgeolli,1));
        addRecipe(Items.WATER_BUCKET, ItemInit.vulgare, ItemInit.glass, new ItemStack(ItemInit.beer,1));
        addRecipe(Items.WATER_BUCKET, Items.WHEAT, ItemInit.glass, new ItemStack(ItemInit.board_car,1));
        addRecipe(Items.WATER_BUCKET, Items.POTATO, ItemInit.glass, new ItemStack(ItemInit.whiskey,1));
    }

    public void addRecipe(Item ripenIngre1,Item ripenIngre2, Item bottle, ItemStack output){
        addRecipe(new ItemStack(ripenIngre1,1), new ItemStack(ripenIngre2,1), new ItemStack(bottle,1),output);
    }

    public void addRecipe(ItemStack ripenIngre1, ItemStack ripenIngre2, ItemStack bottle, ItemStack output){
        if (getResult(ripenIngre1,ripenIngre2,bottle)!=ItemStack.EMPTY) return;
        if (oakTongRecipeList.containsKey(new OaktongRipeningIngredient(ripenIngre1,ripenIngre2,bottle))) return;
        oakTongRecipeList.put(new OaktongRipeningIngredient(ripenIngre1,ripenIngre2,bottle),output);
    }

    public ItemStack getResult(ItemStack ripenIngre1,ItemStack ripenIngre2,ItemStack bottle) {
        for (OaktongRipeningIngredient keys : oakTongRecipeList.keySet())
            if (isRipeningItem(keys, ripenIngre1, ripenIngre2, bottle)&&bottle.getUnlocalizedName().equals(keys.getBottle().getUnlocalizedName()))return oakTongRecipeList.get(keys);
        return ItemStack.EMPTY;
    }

    private boolean isRipeningItem(OaktongRipeningIngredient ripenIngredient,ItemStack ripenIngre1, ItemStack ripenIngre2, ItemStack bottle) {
        return
                (ripenIngredient.getBottle().getItem() == bottle.getItem()&&ripenIngredient.getBottle().getMetadata() == bottle.getMetadata()&&bottle.getUnlocalizedName().equals(ripenIngredient.getBottle().getUnlocalizedName())) && ((ripenIngredient.getFirst_material().getItem() == ripenIngre1.getItem()&&ripenIngredient.getSecond_material().getItem() == ripenIngre2.getItem()&&ripenIngredient.getFirst_material().getMetadata()==ripenIngre1.getMetadata()&&ripenIngredient.getSecond_material().getMetadata()==ripenIngre2.getMetadata()) || (ripenIngredient.getSecond_material().getItem() == ripenIngre1.getItem() && ripenIngredient.getFirst_material().getItem() == ripenIngre2.getItem() && ripenIngredient.getSecond_material().getMetadata() == ripenIngre1.getMetadata() && ripenIngredient.getFirst_material().getMetadata() == ripenIngre2.getMetadata()));
    }
    public boolean isRipeningIngredient(ItemStack stack){
        for (OaktongRipeningIngredient keys : oakTongRecipeList.keySet()) {
            if (isRipeningItem(keys,stack,keys.second_material,keys.bottle)) {return true;}
            else if (isRipeningItem(keys,keys.first_material,stack,keys.bottle)) {return true;}
            else if (isRipeningItem(keys,keys.first_material,keys.second_material,stack)) {return true;}
        }
        return false;
    }

    public boolean isIngredient(ItemStack ingredient){
        for (OaktongRipeningIngredient keys: oakTongRecipeList.keySet()) {
            if ((ingredient.getItem() == keys.getFirst_material().getItem() && ingredient.getMetadata() == keys.getFirst_material().getMetadata()) ||
                    (ingredient.getItem() == keys.getSecond_material().getItem() && ingredient.getMetadata() == keys.getSecond_material().getMetadata())){return true;}
        }
        return false;
    }

    public boolean isBottle(ItemStack bottle) {
        for (OaktongRipeningIngredient keys: oakTongRecipeList.keySet())
            if (bottle.getItem() == keys.getBottle().getItem() && bottle.getMetadata() == keys.getBottle().getMetadata()) return true;
        return false;
    }

    public boolean isResult(ItemStack result){
        for (OaktongRipeningIngredient keys: oakTongRecipeList.keySet()){
            if (result.getItem() == oakTongRecipeList.get(keys).getItem() && result.getMetadata() == oakTongRecipeList.get(keys).getMetadata()) return true;
        }
        return false;
    }


    public HashMap<OaktongRipeningIngredient, ItemStack> getOakTongRecipeList() {
        return oakTongRecipeList;
    }


    static class OaktongRipeningIngredient {
        protected ItemStack first_material;
        protected ItemStack second_material;
        protected ItemStack bottle;

        public OaktongRipeningIngredient(ItemStack input1, ItemStack input2, ItemStack bottle){
            if (input1==null||input2==null) throw new NullPointerException("OakTong Ripening input data is null.");
            this.first_material = input1;
            this.second_material = input2;
            this.bottle = bottle;
        }

        public ItemStack getFirst_material() {
            return first_material;
        }
        public ItemStack getSecond_material() {
            return second_material;
        }

        public ItemStack getBottle() {
            return bottle;
        }
    }
}
