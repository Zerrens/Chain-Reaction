package com.zerren.chainreaction.tile;

import chainreaction.api.energy.IEnergyHandlerCR;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Zerren on 9/22/2015.
 */
public abstract class TEEnergyHandlerBase extends TileEntityCRBase implements IEnergyHandlerCR {

    protected EnergyStorage energyStorage = new EnergyStorage(0);
    protected int rfGenPerTick;

    protected final ForgeDirection[] sidesConnectingEnergy;

    public TEEnergyHandlerBase(int gen, int eStorage, ForgeDirection[] directions) {
        super();
        rfGenPerTick = gen;

        energyStorage = new EnergyStorage(eStorage, rfGenPerTick * 2);
        setCanTick();

        if (directions != null)
            sidesConnectingEnergy = directions;
        else
            sidesConnectingEnergy = ForgeDirection.VALID_DIRECTIONS;
    }

    protected void transferEnergyToConnectingSides() {
        for (ForgeDirection dir : sidesConnectingEnergy){

            //ForgeDirection is a useful helper class for handling directions.
            int targetX = xCoord + dir.offsetX;
            int targetY = yCoord + dir.offsetY;
            int targetZ = zCoord + dir.offsetZ;

            TileEntity tile = worldObj.getTileEntity(targetX, targetY, targetZ);
            if (tile instanceof IEnergyHandler) {

                int maxExtract = energyStorage.getMaxExtract(); //Gets the maximum amount of heat that can be extracted from this tile in one tick.
                int maxAvailable = energyStorage.extractEnergy(maxExtract, true); //Simulates removing "maxExtract" to find out how much heat is actually available.
                int energyTransferred = ((IEnergyHandler) tile).receiveEnergy(dir.getOpposite(), maxAvailable, false); //Sends "maxAvailable" to the target tile and records how much heat was accepted.

                energyStorage.extractEnergy(energyTransferred, false);//Extract the heat transferred from the internal storage.
            }
        }
    }

    protected void transferEnergyToOneSide(ForgeDirection dir) {
        int targetX = xCoord + dir.offsetX;
        int targetY = yCoord + dir.offsetY;
        int targetZ = zCoord + dir.offsetZ;

        TileEntity tile = worldObj.getTileEntity(targetX, targetY, targetZ);
        if (tile instanceof IEnergyHandler) {

            int maxExtract = energyStorage.getMaxExtract(); //Gets the maximum amount of heat that can be extracted from this tile in one tick.
            int maxAvailable = energyStorage.extractEnergy(maxExtract, true); //Simulates removing "maxExtract" to find out how much heat is actually available.
            int energyTransferred = ((IEnergyHandler) tile).receiveEnergy(dir.getOpposite(), maxAvailable, false); //Sends "maxAvailable" to the target tile and records how much heat was accepted.

            energyStorage.extractEnergy(energyTransferred, false);//Extract the heat transferred from the internal storage.
        }
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        if (isFromValidDirection(from)) {
            return energyStorage.receiveEnergy(maxReceive, simulate);
        }
        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        if (isFromValidDirection(from)) {
            return energyStorage.extractEnergy(maxExtract, simulate);
        }
        return 0;
    }

    protected boolean isFromValidDirection(ForgeDirection from) {
        for (ForgeDirection side : sidesConnectingEnergy) {
            if (from == side) return true;
        }
        return false;
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return isFromValidDirection(from);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        energyStorage.readFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        energyStorage.writeToNBT(tag);
    }

    @Override
    public EnergyStorage getEnergyStorage() {
        return energyStorage;
    }
}
