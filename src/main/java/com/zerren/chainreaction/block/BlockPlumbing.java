package com.zerren.chainreaction.block;

import buildcraft.api.tools.IToolWrench;
import chainreaction.api.item.IScanner;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.core.proxy.ClientProxy;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.tile.TileEntityCRBase;
import com.zerren.chainreaction.tile.plumbing.*;
import com.zerren.chainreaction.client.render.block.ISBRHPlumbing;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.utility.CoreUtility;
import com.zerren.chainreaction.utility.NBTHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import java.util.Random;
import java.util.UUID;

/**
 * Created by Zerren on 3/6/2015.
 */
public class BlockPlumbing extends BlockCR implements ITileEntityProvider {

    @SideOnly(Side.CLIENT)
    private IIcon[] tubes;

    /**
     * pipeMouth 0, distroInput 1
     */

    public BlockPlumbing(String name, String[] subtypes, Material material, float hardness, float resistance, Block.SoundType sound, String folder, CreativeTabs tab) {
        super(name, subtypes, material, hardness, resistance, sound, folder, tab);
    }


    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float sideX, float sideY, float sideZ) {
        super.onBlockActivated(world, x, y, z, player, meta, sideX, sideY, sideZ);
        if (super.onBlockActivated(world, x, y, z, player, meta, sideX, sideY, sideZ)) return true;

        TileEntityCRBase tile = CoreUtility.get(world, x, y, z, TileEntityCRBase.class);

        if (tile == null) return false;
        if (tile instanceof TEHeatExchanger) return activateExchanger(world, x, y, z, player, (TEHeatExchanger)tile);
        if (tile instanceof TEDistroChamber) return activateDistroChamber(world, x, y, z, player, (TEDistroChamber) tile, sideX, sideY, sideZ);
        if (tile instanceof TEGasTank) return activateGasTank(world, x, y, z, player, (TEGasTank) tile);
        if (tile instanceof TEHeatExchangerSmall) return activateExchangerSmall(world, x, y, z, player, (TEHeatExchangerSmall) tile);


        return false;
    }

    private boolean activateGasTank(World world, int x, int y, int z, EntityPlayer player, TEGasTank tile) {
        ItemStack held = player.inventory.getCurrentItem();

        if (tile != null && held != null) {
            if (held.getItem() instanceof IScanner && ((IScanner) held.getItem()).canScan(player, x, y, z)) {
                byte mode = NBTHelper.getByte(held, Names.NBT.SCANNER_MODE);

                switch (mode) {
                    case 0: {
                        CoreUtility.addChat("Direction: " + tile.getOrientation(), player);
                        return true;
                    }
                    case 2: {
                        if (tile.tank.getFluid() != null)
                            CoreUtility.addChat("Fluid Tank: " + tile.tank.getFluid().getLocalizedName() + " " + tile.tank.getFluidAmount(), player);
                        return true;
                    }
                    default:
                        break;
                }
            }
        }
        return false;
    }

    private boolean activateExchangerSmall(World world, int x, int y, int z, EntityPlayer player, TEHeatExchangerSmall tile) {
        ItemStack held = player.inventory.getCurrentItem();

        if (tile != null && held != null && !world.isRemote) {
            if (held.getItem() instanceof IScanner && ((IScanner) held.getItem()).canScan(player, x, y, z)) {
                byte mode = NBTHelper.getByte(held, Names.NBT.SCANNER_MODE);

                switch (mode) {
                    case 0: {
                        CoreUtility.addChat("Direction: " + tile.getOrientation(), player);
                        return true;
                    }
                    case 1: {
                        CoreUtility.addChat("Thermal Units: " + tile.getHeatStored(ForgeDirection.UNKNOWN), player);
                        return true;
                    }
                    case 2: {
                        if (tile.inputTank.getFluid() != null)
                            CoreUtility.addChat("Fluid Tank: " + tile.inputTank.getFluid().getLocalizedName() + " " + tile.inputTank.getFluidAmount(), player);
                        if (tile.outputTank.getFluid() != null)
                            CoreUtility.addChat("Fluid Tank: " + tile.outputTank.getFluid().getLocalizedName() + " " + tile.outputTank.getFluidAmount(), player);
                        return true;
                    }
                    default:
                        break;
                }
            }
        }
        return false;
    }

    private boolean activateExchanger(World world, int x, int y, int z, EntityPlayer player, TEHeatExchanger tile) {
        ItemStack held = player.inventory.getCurrentItem();
        if (tile != null  && held != null && held.getItem() instanceof IToolWrench) {

            if (((IToolWrench) held.getItem()).canWrench(player, x, y, z) && !tile.hasValidMaster()) {
                tile.initiateController(UUID.randomUUID(), player);
                tile.setOwnerUUID(player.getPersistentID());
                return true;
            }
        }

        if (tile != null && held != null && !world.isRemote) {
            if (held.getItem() instanceof IScanner && ((IScanner) held.getItem()).canScan(player, x, y, z)) {
                byte mode = NBTHelper.getByte(held, Names.NBT.SCANNER_MODE);

                switch (mode) {
                    case 0: {
                        CoreUtility.addChat("Controlled by: " + tile.getMasterUUID(), player);
                        CoreUtility.addChat("Direction: " + tile.getOrientation(), player);
                        CoreUtility.addChat("Is Master: " + tile.isMaster(), player);
                        CoreUtility.addChat("Slave Location: " + tile.getMultiblockPartNumber(), player);
                        return true;
                    }
                    case 1: {
                        CoreUtility.addChat("Thermal Units: " + tile.getThermalUnits(), player);
                        return true;
                    }
                    case 2: {
                        if (tile.coolantInletTank.getFluid() != null)
                            CoreUtility.addChat("Inlet tank: " + tile.coolantInletTank.getFluid().getLocalizedName() + " " + tile.coolantInletTank.getFluidAmount(), player);
                        if (tile.waterTank.getFluid() != null)
                            CoreUtility.addChat("Water tank: " + tile.waterTank.getFluid().getLocalizedName() + " " + tile.waterTank.getFluidAmount(), player);
                        if (tile.steamTank.getFluid() != null)
                            CoreUtility.addChat("Steam tank: " + tile.steamTank.getFluid().getLocalizedName() + " " + tile.steamTank.getFluidAmount(), player);
                        if (tile.coolantOutputTank.getFluid() != null)
                            CoreUtility.addChat("Outlet tank: " + tile.coolantOutputTank.getFluid().getLocalizedName() + " " + tile.coolantOutputTank.getFluidAmount(), player);
                        return true;
                    }
                    default: break;
                }
            }
        }
        return false;
    }

    private boolean activateDistroChamber(World world, int x, int y, int z, EntityPlayer player, TEDistroChamber tile, float sideX, float sideY, float sideZ) {
        ItemStack held = player.inventory.getCurrentItem();

        if (tile != null && held != null) {
            if (held.getItem() instanceof IScanner && ((IScanner) held.getItem()).canScan(player, x, y, z)) {
                byte mode = NBTHelper.getByte(held, Names.NBT.SCANNER_MODE);
                switch (mode) {
                    case 0: {
                        CoreUtility.addChat("Direction: " + tile.getOrientation(), player);
                        return true;
                    }
                    case 2: {
                        if (tile.tank.getFluid() != null)
                            CoreUtility.addChat("Fluid Tank: " + tile.tank.getFluid().getLocalizedName() + " " + tile.tank.getFluidAmount(), player);
                        return true;
                    }
                    default:
                        break;
                }
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister registry) {
        super.registerBlockIcons(registry);

        tubes = new IIcon[2];

        ClientProxy.tex_replacements[0] = registry.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + folder + "pipe_mouth");
        ClientProxy.tex_replacements[1] = registry.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + folder + "stainlessPipe");
        ClientProxy.tex_replacements[2] = registry.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + folder + "distributionChamber_input");

        tubes[0] = registry.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + folder + "tubes_front");
        tubes[1] = registry.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + folder + "tubes_side");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metaData) {
        //LHE
        if (metaData == 0) {
            switch (side) {
                case 2:case 4: return tubes[0];
                default: return tubes[1];
            }
        }
        //pipe
        if (metaData == 2) {
            switch (side) {
                case 0:case 1: return ClientProxy.tex_replacements[0];
            }
        }
        metaData = MathHelper.clamp_int(metaData, 0, subtypes.length - 1);
        return icon[metaData];
    }

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
        TileEntity te = world.getTileEntity(x, y, z);
        if(!world.isRemote && !player.capabilities.isCreativeMode) {
            if(te instanceof TEGasTank) {
                ItemStack stack = new ItemStack(this, 1, meta);

                //NBTTagCompound tag = new NBTTagCompound();
                if (((TEGasTank) te).tank.getFluid() != null) {
                    NBTHelper.setTagCompound(stack, Names.NBT.TANK, ((TEGasTank) te).tank.writeToNBT(new NBTTagCompound()));
                    //((TEGasTank) te).tank.writeToNBT(tag);
                    //if(!tag.hasNoTags())
                      //  NBTHelper.setTagCompound(stack, Names.NBT.TANK, ((TEGasTank) te).tank.writeToNBT(new NBTTagCompound()));
                }
                world.spawnEntityInWorld(new EntityItem(world, x+.5, y+.5, z+.5, stack));
            }
        }
    }

    @Override
    public Item getItemDropped(int meta, Random random, int p2) {

        switch (meta) {
            //so the gas tank doesn't drop duplicate items
            case 3: return null;
            default: return Item.getItemFromBlock(this);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        switch(meta) {
            case 0:
                return new TEHeatExchanger();
            case 1:
                return new TEDistroChamber();
            case 2:
                return new TEPressurePipe();
            case 3:
                return new TEGasTank();
            case 4:
                return new TEHeatExchangerSmall();
            default:
                return null;
        }
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return ISBRHPlumbing.model;
    }
}
