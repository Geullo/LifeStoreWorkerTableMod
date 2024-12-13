package com.geullo.workercrafttable.Cauldron;

import com.life.item.item.ItemInit;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
public class RecipeCauldron {
    private static RecipeCauldron RECIPE_CAULDRON;
    private final HashMap<CauldronIngredient,ItemStack> cauldronCookRecipe = new HashMap<>();
    private final HashMap<ItemStack,Integer> FUEL_LIST = new HashMap<>();
    private final HashMap<String,Integer> FUEL_LIST_S = new HashMap<>();

    public static RecipeCauldron getInstance() {
        if (RECIPE_CAULDRON == null) RECIPE_CAULDRON = new RecipeCauldron();
        return RECIPE_CAULDRON;
    }

    private RecipeCauldron(){
        addRecipe(ItemInit.rice_crop, Items.AIR, Items.WATER_BUCKET, Items.AIR, new ItemStack(ItemInit.jmt_rice,1));
        addRecipe(Items.AIR, ItemInit.rice_crop, Items.AIR, Items.WATER_BUCKET, new ItemStack(ItemInit.jmt_rice,1));
        addRecipe(Items.PORKCHOP, Items.AIR, Items.WATER_BUCKET, Items.AIR, new ItemStack(ItemInit.pork,1));
        addRecipe(Items.AIR, Items.PORKCHOP, Items.AIR, Items.WATER_BUCKET, new ItemStack(ItemInit.pork,1));
        addRecipe(Items.CHICKEN, Items.AIR, Items.WATER_BUCKET, Items.AIR, new ItemStack(ItemInit.chicken_soup,1));
        addRecipe(Items.AIR, Items.CHICKEN, Items.AIR, Items.WATER_BUCKET, new ItemStack(ItemInit.chicken_soup,1));
        addRecipe(Items.BEEF, Items.AIR, Items.CARROT, Items.AIR, new ItemStack(ItemInit.meat_bul,1));
        addRecipe(Items.AIR, Items.BEEF, Items.AIR, Items.CARROT, new ItemStack(ItemInit.meat_bul,1));
        addRecipe(Items.BEEF, Items.EGG, Items.AIR, ItemInit.jmt_rice, new ItemStack(ItemInit.meat_rice,1));
        addRecipe(Items.BEEF, Items.EGG, ItemInit.jmt_rice, Items.AIR, new ItemStack(ItemInit.meat_rice,1));
        addRecipe(new ItemStack(ItemInit.instance_kimchi,1), ItemStack.EMPTY, new ItemStack(Items.FISH,1,0), new ItemStack(ItemInit.jmt_rice,1), new ItemStack(ItemInit.korean_meal,1));
        addRecipe(ItemStack.EMPTY, new ItemStack(ItemInit.instance_kimchi,1), new ItemStack(Items.FISH,1,0), new ItemStack(ItemInit.jmt_rice,1), new ItemStack(ItemInit.korean_meal,1));
        addRecipe(Items.WHEAT, Items.AIR, Items.CHICKEN, Items.WATER_BUCKET, new ItemStack(ItemInit.chicken,1));
        addRecipe(Items.AIR, Items.WHEAT, Items.CHICKEN, Items.WATER_BUCKET, new ItemStack(ItemInit.chicken,1));
//        addRecipe(Items.WHEAT, Items.AIR, ItemInit.instance_kimchi, Items.WATER_BUCKET, new ItemStack(ItemInit.fried_kimchi,1));
//        addRecipe(Items.AIR, Items.WHEAT, ItemInit.instance_kimchi, Items.WATER_BUCKET, new ItemStack(ItemInit.fried_kimchi,1));

        addRecipe(Items.WHEAT, Items.AIR, ItemInit.old_kimchi, Items.WATER_BUCKET, new ItemStack(ItemInit.fried_old_kimchi,1));
        addRecipe(Items.AIR, Items.WHEAT, ItemInit.old_kimchi, Items.WATER_BUCKET, new ItemStack(ItemInit.fried_old_kimchi,1));
        addRecipe(ItemInit.instance_kimchi, Items.EGG, Items.AIR, ItemInit.jmt_rice, new ItemStack(ItemInit.kimchi_rice,1));
        addRecipe(ItemInit.instance_kimchi, Items.EGG, ItemInit.jmt_rice, Items.AIR, new ItemStack(ItemInit.kimchi_rice,1));
        addRecipe(ItemInit.pepper, ItemInit.old_kimchi, Items.BEEF, Items.WATER_BUCKET, new ItemStack(ItemInit.pork_old_kimchi,1));
        addRecipe(ItemInit.pork, ItemInit.instance_kimchi, Items.AIR, Items.AIR, new ItemStack(ItemInit.bossam,1));
        addRecipe(Items.AIR, Items.AIR, ItemInit.pork, ItemInit.instance_kimchi, new ItemStack(ItemInit.bossam,1));
        addRecipe(ItemInit.pepper, ItemInit.old_kimchi, Items.AIR, Items.WATER_BUCKET, new ItemStack(ItemInit.kimchi_soup,1));
        addRecipe(ItemInit.pepper, ItemInit.old_kimchi, Items.WATER_BUCKET, Items.AIR, new ItemStack(ItemInit.kimchi_soup,1));

        addRecipe(ItemInit.oak_shelf, ItemInit.pork, new ItemStack(ItemInit.dding_grand_bossam,1));
        addRecipe(ItemInit.chicken, ItemInit.beer, new ItemStack(ItemInit.chicken_beer,1));
        addRecipe(ItemInit.instance_kimchi, ItemInit.empty_cd, new ItemStack(ItemInit.donut_fried_kimchi,1));

        addFuel(new ItemStack(Items.LAVA_BUCKET),8);
        addFuel(new ItemStack(Item.getItemFromBlock(Blocks.COAL_BLOCK)),36);
        addFuel(new ItemStack(Items.BLAZE_ROD),6);
        addFuel(new ItemStack(Items.COAL),4);
        addFuel(new ItemStack(Items.COAL,1,1),4);

    }
    public void addFuel(ItemStack fuel,int amt) {
        FUEL_LIST_S.put(fuel.getItem().getRegistryName() + "/" + fuel.getMetadata(),amt);
        FUEL_LIST.put(fuel,amt);
    }

    public void clearRecipe() {
        cauldronCookRecipe.clear();
        addRecipe(ItemInit.rice_crop, Items.AIR, Items.WATER_BUCKET, Items.AIR, new ItemStack(ItemInit.jmt_rice,1));
        addRecipe(Items.AIR, ItemInit.rice_crop, Items.AIR, Items.WATER_BUCKET, new ItemStack(ItemInit.jmt_rice,1));
        addRecipe(Items.PORKCHOP, Items.AIR, Items.WATER_BUCKET, Items.AIR, new ItemStack(ItemInit.pork,1));
        addRecipe(Items.AIR, Items.PORKCHOP, Items.AIR, Items.WATER_BUCKET, new ItemStack(ItemInit.pork,1));
        addRecipe(Items.CHICKEN, Items.AIR, Items.WATER_BUCKET, Items.AIR, new ItemStack(ItemInit.chicken_soup,1));
        addRecipe(Items.AIR, Items.CHICKEN, Items.AIR, Items.WATER_BUCKET, new ItemStack(ItemInit.chicken_soup,1));
        addRecipe(Items.BEEF, Items.AIR, Items.CARROT, Items.AIR, new ItemStack(ItemInit.meat_bul,1));
        addRecipe(Items.AIR, Items.BEEF, Items.AIR, Items.CARROT, new ItemStack(ItemInit.meat_bul,1));
    }
    public void addRecipe(Item cookIngre1,Item cookIngre2,ItemStack result) {
        addRecipe(cookIngre1, cookIngre2, Items.AIR, Items.AIR, result);
        addRecipe(Items.AIR, Items.AIR, cookIngre1, cookIngre2, result);
        addRecipe(cookIngre1, Items.AIR, cookIngre2, Items.AIR, result);
        addRecipe(Items.AIR, cookIngre1, Items.AIR, cookIngre2, result);
        addRecipe(cookIngre2, cookIngre1, Items.AIR, Items.AIR, result);
        addRecipe(Items.AIR, Items.AIR, cookIngre2, cookIngre1, result);
        addRecipe(cookIngre2, Items.AIR, cookIngre1, Items.AIR, result);
        addRecipe(Items.AIR, cookIngre2, Items.AIR, cookIngre1, result);
    }

    public void addRecipe(Item cookIngre1,Item cookIngre2,Item cookIngre3,Item cookIngre4, ItemStack result){
        addRecipe(cookIngre1.equals(Items.AIR)?ItemStack.EMPTY:new ItemStack(cookIngre1,1), cookIngre2.equals(Items.AIR)?ItemStack.EMPTY:new ItemStack(cookIngre2,1), cookIngre3.equals(Items.AIR)?ItemStack.EMPTY:new ItemStack(cookIngre3,1), cookIngre4.equals(Items.AIR)?ItemStack.EMPTY:new ItemStack(cookIngre4,1), result);
    }

    public void addRecipe(ItemStack cookIngre1, ItemStack cookIngre2,ItemStack cookIngre3,ItemStack cookIngre4, ItemStack result){
        if (getResult(cookIngre1,cookIngre2,cookIngre3,cookIngre4)!=ItemStack.EMPTY) return;
        if (cauldronCookRecipe.containsKey(new CauldronIngredient(cookIngre1,cookIngre2,cookIngre3,cookIngre4))) return;
        cauldronCookRecipe.put(new CauldronIngredient(cookIngre1,cookIngre2,cookIngre3,cookIngre4),result);
    }

    public ItemStack getResult(ItemStack cookIngre1,ItemStack cookIngre2,ItemStack cookIngre3,ItemStack cookIngre4) {
        for (CauldronIngredient keys : cauldronCookRecipe.keySet()) {
            if (isCookingRecipe(keys, cookIngre1, cookIngre2,cookIngre3,cookIngre4)) {
                return cauldronCookRecipe.get(keys);
            }
        }
        return ItemStack.EMPTY;
    }

    private boolean isCookingRecipe(CauldronIngredient cauldronIngredient, ItemStack cookIngre1, ItemStack cookIngre2,ItemStack cookIngre3,ItemStack cookIngre4) {
        return isRecipeIn(cauldronIngredient,cookIngre1,cookIngre2,cookIngre3,cookIngre4);
    }
    private boolean isRecipeIn(CauldronIngredient cauldronIngredient, ItemStack cookIngre1, ItemStack cookIngre2,ItemStack cookIngre3,ItemStack cookIngre4){
        return cauldronIngredient.getFirst_material().getItem() == cookIngre1.getItem() && cauldronIngredient.getSecond_material().getItem() == cookIngre2.getItem() && cauldronIngredient.getThird_material().getItem() == cookIngre3.getItem() &&cauldronIngredient.getFourth_material().getItem() == cookIngre4.getItem();
    }

    public boolean isCookingIngredient(ItemStack stack){
        for (CauldronIngredient keys : cauldronCookRecipe.keySet()) {
            if (isCookingRecipe(keys,stack,keys.second_material,keys.getThird_material(),keys.getFourth_material())|| isCookingRecipe(keys,keys.getFirst_material(),stack,keys.getThird_material(),keys.getFourth_material())|| isCookingRecipe(keys,keys.getFirst_material(),keys.getSecond_material(),stack,keys.getFourth_material())|| isCookingRecipe(keys,keys.getFirst_material(),keys.getSecond_material(),keys.getThird_material(),stack)) return true;
        }
        return false;
    }

    public boolean isIngredient(ItemStack ingredient){
        for (CauldronIngredient keys: cauldronCookRecipe.keySet()) {
            if (isSameItem(ingredient,keys.getFirst_material())||isSameItem(ingredient,keys.getSecond_material())||isSameItem(ingredient,keys.getThird_material())||isSameItem(ingredient,keys.getFourth_material())) return true;
        }
        return false;
    }

    private boolean isSameItem(ItemStack stack1,ItemStack stack2){
        return (stack1.getItem() == stack2.getItem() && stack1.getMetadata() == stack2.getMetadata());
    }
    public boolean isResult(ItemStack result){
        for (CauldronIngredient keys: cauldronCookRecipe.keySet()){
            if (result.getItem() == cauldronCookRecipe.get(keys).getItem() && result.getMetadata() == cauldronCookRecipe.get(keys).getMetadata())return true;
        }
        return false;
    }

    public boolean isFuel(ItemStack fuel){
        return FUEL_LIST.keySet().stream().anyMatch(key -> isSameItem(fuel,key));
    }
    public Integer getFuelProgress(ItemStack fuel) {
        return FUEL_LIST_S.get(fuel.getItem().getRegistryName() + "/" + fuel.getMetadata());
    }

    public HashMap<CauldronIngredient, ItemStack> getCauldronCookRecipe() {
        return cauldronCookRecipe;
    }
    public HashMap<ItemStack, Integer> getFuelList() {
        return FUEL_LIST;
    }

    static class CauldronFuel {
        protected ItemStack fuel;
        protected Integer progress;
        public CauldronFuel(ItemStack fuel,int progress) {
            this.fuel = fuel;
            this.progress = progress;
        }
    }

    static class CauldronIngredient {
        protected ItemStack first_material;
        protected ItemStack second_material;
        protected ItemStack third_material;
        protected ItemStack fourth_material;

        public CauldronIngredient(ItemStack input1, ItemStack input2,ItemStack input3, ItemStack input4){
            if (input1==null||input2==null) throw new NullPointerException("Cauldron Cooking input data is null.");
            this.first_material = input1;
            this.second_material = input2;
            this.third_material = input3;
            this.fourth_material = input4;
        }

        public ItemStack getFirst_material() {
            return first_material;
        }
        public ItemStack getSecond_material() {
            return second_material;
        }
        public ItemStack getThird_material() {
            return third_material;
        }
        public ItemStack getFourth_material() {
            return fourth_material;
        }
    }
}
