package com.geullo.workercrafttable.Item;

import com.geullo.workercrafttable.Item.Base.ItemBase;
import com.geullo.workercrafttable.Item.utils.IRegisterable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
public class ItemsRegiter {

    public static final ArrayList<IRegisterable> ITEMS = new ArrayList<>();
    public static final BusinessCard d7297_business_card = new BusinessCard("d7297");
    public static final BusinessCard samsik23_business_card = new BusinessCard("samsik23");
    public static final BusinessCard rutaey_business_card = new BusinessCard("rutaey");
    public static final BusinessCard huchu95_business_card = new BusinessCard("huchu95");
    public static final BusinessCard kong7_business_card = new BusinessCard("kong7");
    public static final BusinessCard noonkkob_business_card = new BusinessCard("noonkkob");
    public static final BusinessCard seoneng_business_card = new BusinessCard("seoneng");
    public static final BusinessCard daju__business_card = new BusinessCard("daju_");
    public static final EmploymentAgency shaman_employment_agency = new EmploymentAgency("shaman");
    public static final EmploymentAgency furniture_maker_employment_agency = new EmploymentAgency("furniture_maker");
    public static final EmploymentAgency jeweler_employment_agency = new EmploymentAgency("jeweler");
    public static final EmploymentAgency artist_employment_agency = new EmploymentAgency("artist");
    public static final EmploymentAgency chef_employment_agency = new EmploymentAgency("chef");
    public static final EmploymentAgency designer_employment_agency = new EmploymentAgency("designer");
    public static final EmploymentAgency barkeeper_employment_agency = new EmploymentAgency("barkeeper");
    public static final EmploymentAgency supermarket_employment_agency = new EmploymentAgency("supermarket");
    public static final JobCoin miner_job_coin = new JobCoin("miner");
    public static final JobCoin lumber_jack_job_coin = new JobCoin("lumber_jack");
    public static final JobCoin hunter_job_coin = new JobCoin("hunter");
    public static final JobCoin farmer_job_coin = new JobCoin("farmer");
    public static final VillageToken berenty_village_token = new VillageToken("berenty");
    public static final VillageToken ekeulle_village_token = new VillageToken("ekeulle");
    public static final VillageToken popo_village_token = new VillageToken("popo");
    public static final VillageToken piosa_village_token = new VillageToken("piosa");
    public static final VillageToken haldeseu_village_token = new VillageToken("haldeseu");
    static Item item = (new ItemWaterBucket(Blocks.AIR,"bucket")).setRegistryName("minecraft:bucket").setMaxStackSize(32);
    public static final ItemWaterBucket itemWaterBucket = (ItemWaterBucket) new ItemWaterBucket(Blocks.FLOWING_WATER,"bucketWater").setRegistryName("minecraft:water_bucket").setContainerItem(Items.BUCKET);
    public static final ItemBucketMilk itemMilkBucket = (ItemBucketMilk) new ItemBucketMilk().setUnlocalizedName("milk").setRegistryName("minecraft:milk_bucket").setContainerItem(Items.BUCKET).setMaxStackSize(32);
    public static final ItemShears shear = (ItemScissor) new ItemScissor().setUnlocalizedName("shears").setRegistryName("minecraft:shears");
    public static CreativeTabs workerCraftTableTab = new CreativeTabs("tabWorkerCraftTable") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(Item.getItemById(58));
        }

    }.setBackgroundImageName("items.png");

    public static CreativeTabs items = new CreativeTabs("tabItems") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(d7297_business_card);
        }

    }.setBackgroundImageName("items.png");
    public static final ItemBase meal_coupon = new ItemBase("meal_coupon").setCreativeTab(items);
    public static final LikabilityCoupon likability_coupon = new LikabilityCoupon();
    public static final Phone cellPhone = new Phone();
    public static final OMRCard omr_card = new OMRCard();
    public static final ItemBase chart = new ItemBase("chart").setCreativeTab(items);
    public static final ItemSpadeBase spade_sand = new ItemSpadeBase();
    public static void register(IForgeRegistry<Item> registry){
        registry.registerAll(d7297_business_card,samsik23_business_card,rutaey_business_card,
                huchu95_business_card,kong7_business_card,noonkkob_business_card,seoneng_business_card,daju__business_card,
                shaman_employment_agency,furniture_maker_employment_agency,jeweler_employment_agency,artist_employment_agency,
                chef_employment_agency,designer_employment_agency,barkeeper_employment_agency,supermarket_employment_agency,cellPhone,
                miner_job_coin,lumber_jack_job_coin,hunter_job_coin,farmer_job_coin,berenty_village_token,ekeulle_village_token,popo_village_token,piosa_village_token,haldeseu_village_token,likability_coupon,itemWaterBucket,itemMilkBucket,shear,item,meal_coupon,spade_sand,omr_card,chart
                );
    }
    public static void registerModel(){
        for (IRegisterable item : ITEMS){
            item.registerItemModel();
        }
    }
}
