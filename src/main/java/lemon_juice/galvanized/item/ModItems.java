package lemon_juice.galvanized.item;

import lemon_juice.galvanized.Galvanized;
import lemon_juice.galvanized.creativetab.ModCreativeModeTab;
import lemon_juice.galvanized.item.custom.HammerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Galvanized.MOD_ID);

    //Excavators

    //Hammers
    public static final RegistryObject<Item> WOODEN_HAMMER = ITEMS.register("wooden_hammer", () -> new HammerItem(Tiers.WOOD, new Item.Properties().tab(ModCreativeModeTab.GALVANIZED_TAB).durability(177), 1));
    public static final RegistryObject<Item> STONE_HAMMER = ITEMS.register("stone_hammer", () -> new HammerItem(Tiers.STONE, new Item.Properties().tab(ModCreativeModeTab.GALVANIZED_TAB).durability(393), 1));
    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer", () -> new HammerItem(Tiers.IRON, new Item.Properties().tab(ModCreativeModeTab.GALVANIZED_TAB).durability(750), 1));
    public static final RegistryObject<Item> GOLDEN_HAMMER = ITEMS.register("golden_hammer", () -> new HammerItem(Tiers.GOLD, new Item.Properties().tab(ModCreativeModeTab.GALVANIZED_TAB).durability(96), 1));
    public static final RegistryObject<Item> DIAMOND_HAMMER = ITEMS.register("diamond_hammer", () -> new HammerItem(Tiers.DIAMOND, new Item.Properties().tab(ModCreativeModeTab.GALVANIZED_TAB).durability(4692), 1));
    public static final RegistryObject<Item> NETHERITE_HAMMER = ITEMS.register("netherite_hammer", () -> new HammerItem(Tiers.NETHERITE, new Item.Properties().tab(ModCreativeModeTab.GALVANIZED_TAB).durability(6093), 1));

    //Lumberaxes

    //Obliterators (Excavators + Hammers + Lumberaxes)

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
