package com.teammetallurgy.metallurgycm.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityBaseMachine extends TileEntity
{

    private ForgeDirection facing;

    public void setFacing(ForgeDirection facingDirection)
    {
        facing = facingDirection;
    }

    public ForgeDirection getFacing()
    {
        return facing;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtCompound)
    {
        super.readFromNBT(nbtCompound);

        facing = ForgeDirection.getOrientation(nbtCompound.getShort("facing"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtCompound)
    {
        super.writeToNBT(nbtCompound);

        nbtCompound.setShort("facing", (short) facing.ordinal());
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        NBTTagCompound nbtCompound = pkt.func_148857_g();
        if (nbtCompound == null) return;

        readFromNBT(nbtCompound);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbtCompound = new NBTTagCompound();

        writeToNBT(nbtCompound);

        S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbtCompound);
        return packet;
    }
}
