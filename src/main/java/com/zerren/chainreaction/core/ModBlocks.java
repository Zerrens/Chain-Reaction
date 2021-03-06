package com.zerren.chainreaction.core;

import com.zerren.chainreaction.ChainReaction;
import chainreaction.api.block.CRBlocks;
import com.zerren.chainreaction.block.*;
import com.zerren.chainreaction.block.fluid.*;
import com.zerren.chainreaction.item.itemblock.*;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;

/**
 * Created by Zerren on 2/19/2015.
 */
public class ModBlocks {

    public static void init() {
        CRBlocks.ores = new BlockCR(Names.Blocks.ORE, Names.Blocks.ORE_SUBTYPES, Material.rock, 3F, 5F, Block.soundTypeStone, Reference.Textures.Folders.MATERIAL_FOLDER, ChainReaction.cTabCR);
        CRBlocks.metals = new BlockCR(Names.Blocks.METAL, Names.Blocks.METAL_SUBTYPES, Material.iron, 3F, 10F, Block.soundTypeMetal, Reference.Textures.Folders.MATERIAL_FOLDER, ChainReaction.cTabCR);
        CRBlocks.vault = new BlockVault(Names.Blocks.VAULT, Names.Blocks.VAULT_SUBTYPES, Material.rock, 3F, 15F, Block.soundTypeStone, Reference.Textures.Folders.VAULT_FOLDER, ChainReaction.cTabCR);
        CRBlocks.chest = new BlockCRChest(Names.Blocks.CHEST, Names.Blocks.CHEST_SUBTYPES, Material.rock, 3F, 15F, Block.soundTypeStone, Reference.Textures.Folders.VAULT_FOLDER, ChainReaction.cTabCR);
        CRBlocks.plumbing = new BlockPlumbing(Names.Blocks.PLUMBING, Names.Blocks.PLUMBING_SUBTYPES, Material.iron, 3F, 10F, Block.soundTypeMetal, Reference.Textures.Folders.PLUMBING_FOLDER, ChainReaction.cTabCR);
        CRBlocks.reactor = new BlockReactor(Names.Blocks.REACTOR, Names.Blocks.REACTOR_SUBTYPES, Material.iron, 3F, 15F, Block.soundTypeMetal, Reference.Textures.Folders.REACTOR_FOLDER, ChainReaction.cTabCR);
        CRBlocks.mechanism = new BlockMechanism(Names.Blocks.MECHANISM, Names.Blocks.MECHANISM_SUBTYPES, Material.rock, 2F, 10F, Block.soundTypeStone, Reference.Textures.Folders.MECHANISM_FOLDER, ChainReaction.cTabCR);

        register();
    }
    
    private static void register() {

        GameRegistry.registerBlock(CRBlocks.ores, ItemBlockStoneMaterial.class, Names.Blocks.ORE);
        GameRegistry.registerBlock(CRBlocks.metals, ItemBlockMetalMaterial.class, Names.Blocks.METAL);
        GameRegistry.registerBlock(CRBlocks.vault, ItemBlockVault.class, Names.Blocks.VAULT);
        GameRegistry.registerBlock(CRBlocks.chest, ItemBlockChest.class, Names.Blocks.CHEST);
        GameRegistry.registerBlock(CRBlocks.plumbing, ItemBlockExchanger.class, Names.Blocks.PLUMBING);
        GameRegistry.registerBlock(CRBlocks.reactor, ItemBlockReactor.class, Names.Blocks.REACTOR);
        GameRegistry.registerBlock(CRBlocks.mechanism, ItemBlockMechanism.class, Names.Blocks.MECHANISM);

        fluidBlocks();
    }

    private static void fluidBlocks() {
        if (ModFluids.coolantColdFluid.getBlock() == null) {
            CRBlocks.coolantCold = new BlockFluidCoolantCold(ModFluids.coolantColdFluid, Material.water, Names.Fluids.COOLANT_COLD, 7, 5, 100F, 3);
            GameRegistry.registerBlock(CRBlocks.coolantCold, Names.Fluids.COOLANT_COLD);
            ModFluids.coolantColdFluid.setBlock(CRBlocks.coolantCold);
        }
        else {
            CRBlocks.coolantCold = ModFluids.coolantColdFluid.getBlock();
        }

        if (ModFluids.coolantHotFluid.getBlock() == null) {
            CRBlocks.coolantHot = new BlockFluidCoolantHot(ModFluids.coolantHotFluid, Material.water, Names.Fluids.COOLANT_HOT, 8, 5, 100F, 3);
            GameRegistry.registerBlock(CRBlocks.coolantHot, Names.Fluids.COOLANT_HOT);
            ModFluids.coolantHotFluid.setBlock(CRBlocks.coolantHot);
        }
        else {
            CRBlocks.coolantHot = ModFluids.coolantHotFluid.getBlock();
        }

        if (ModFluids.distilledWater.getBlock() == null) {
            CRBlocks.distilledWater = new BlockFluidCR(ModFluids.distilledWater, Material.water, Names.Fluids.DISTILLED_WATER, 8, 5, 100F, 3);
            GameRegistry.registerBlock(CRBlocks.distilledWater, Names.Fluids.DISTILLED_WATER);
            ModFluids.distilledWater.setBlock(CRBlocks.distilledWater);
        }
        else {
            CRBlocks.distilledWater = ModFluids.distilledWater.getBlock();
        }

        if (ModFluids.steam.getBlock() == null) {
            CRBlocks.steam = new BlockFluidSteam(ModFluids.steam, new MaterialLiquid(MapColor.silverColor), Names.Fluids.STEAM, 5, 2, 1F, 1);
            GameRegistry.registerBlock(CRBlocks.steam, Names.Fluids.STEAM);
            ModFluids.steam.setBlock(CRBlocks.steam);
        }
        else {
            CRBlocks.steam = ModFluids.steam.getBlock();
        }

        if (ModFluids.uf6.getBlock() == null) {
            CRBlocks.uf6 = new BlockFluidUF6(ModFluids.uf6, new MaterialLiquid(MapColor.greenColor), Names.Fluids.UF6, 4, 20, 1F, 4);
            GameRegistry.registerBlock(CRBlocks.uf6, Names.Fluids.UF6);
            ModFluids.uf6.setBlock(CRBlocks.uf6);
        }
        else {
            CRBlocks.uf6 = ModFluids.uf6.getBlock();
        }

        if (ModFluids.hydrogenGas.getBlock() == null) {
            CRBlocks.hydrogenGas = new BlockFluidGasBase(ModFluids.hydrogenGas, new MaterialLiquid(MapColor.pinkColor), Names.Fluids.HYDROGEN, 5, 2, 1F, 1);
            GameRegistry.registerBlock(CRBlocks.hydrogenGas, Names.Fluids.HYDROGEN);
            ModFluids.hydrogenGas.setBlock(CRBlocks.hydrogenGas);
        }
        else {
            CRBlocks.hydrogenGas = ModFluids.hydrogenGas.getBlock();
        }

        if (ModFluids.oxygenGas.getBlock() == null) {
            CRBlocks.oxygenGas = new BlockFluidGasBase(ModFluids.oxygenGas, new MaterialLiquid(MapColor.lightBlueColor), Names.Fluids.OXYGEN, 5, 2, 1F, 1);
            GameRegistry.registerBlock(CRBlocks.oxygenGas, Names.Fluids.OXYGEN);
            ModFluids.oxygenGas.setBlock(CRBlocks.oxygenGas);
        }
        else {
            CRBlocks.oxygenGas = ModFluids.oxygenGas.getBlock();
        }

        if (ModFluids.hydrogenLiquid.getBlock() == null) {
            CRBlocks.hydrogenLiquid = new BlockFluidCryogenic(ModFluids.hydrogenLiquid, Material.water, Names.Fluids.HYDROGEN_LIQUID, 7, 4, 1F, 2, 2, 3.0F);
            GameRegistry.registerBlock(CRBlocks.hydrogenLiquid, Names.Fluids.HYDROGEN_LIQUID);
            ModFluids.hydrogenLiquid.setBlock(CRBlocks.hydrogenLiquid);
        }
        else {
            CRBlocks.hydrogenLiquid = ModFluids.hydrogenLiquid.getBlock();
        }

        if (ModFluids.oxygenLiquid.getBlock() == null) {
            CRBlocks.oxygenLiquid = new BlockFluidCryogenic(ModFluids.oxygenLiquid, Material.water, Names.Fluids.OXYGEN_LIQUID, 7, 4, 1F, 2, 1, 2.0F);
            GameRegistry.registerBlock(CRBlocks.oxygenLiquid, Names.Fluids.OXYGEN_LIQUID);
            ModFluids.oxygenLiquid.setBlock(CRBlocks.oxygenLiquid);
        }
        else {
            CRBlocks.oxygenLiquid = ModFluids.oxygenLiquid.getBlock();
        }
    }
}