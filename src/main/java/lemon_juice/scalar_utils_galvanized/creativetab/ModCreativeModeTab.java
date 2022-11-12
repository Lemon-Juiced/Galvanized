package lemon_juice.scalar_utils_galvanized.creativetab;

import lemon_juice.scalar_utils_galvanized.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab GALVANIZED_TAB = new CreativeModeTab("galvanizedTab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.WOODEN_HAMMER.get());
        }
    };
}
