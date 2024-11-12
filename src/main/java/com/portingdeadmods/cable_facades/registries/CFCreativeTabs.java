package com.portingdeadmods.cable_facades.registries;

import com.portingdeadmods.cable_facades.CFMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CFCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CFMain.MODID);

    public static final RegistryObject<CreativeModeTab> CF_TAB = CREATIVE_MODE_TABS.register("cf_tab", () -> CreativeModeTab.builder().withTabsBefore(CreativeModeTabs.COMBAT).icon(Items.IRON_BLOCK::getDefaultInstance).displayItems((parameters, output) -> {
        for (RegistryObject<Item> item : CFItems.ITEMS.getEntries()){
            output.accept(item.get().getDefaultInstance());
        }
        for (RegistryObject<Block> block : CFBlocks.BLOCKS.getEntries()){
            output.accept(block.get().asItem().getDefaultInstance());
        }
    }).build());

}