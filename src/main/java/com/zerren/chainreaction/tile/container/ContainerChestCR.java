package com.zerren.chainreaction.tile.container;

import com.zerren.chainreaction.tile.chest.TEChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 2/28/2015.
 */
public class ContainerChestCR extends ContainerCR {

    public static final int BRICK_ROWS = 3;
    public static final int BRICK_COLUMNS = 9;

    public static final int THAUMIUM_ROWS = 5;
    public static final int THAUMIUM_COLUMNS = 9;

    public static final int VOID_ROWS = 7;
    public static final int VOID_COLUMNS = 11;

    private TEChest chest;
    private int rows, columns;

    public ContainerChestCR(InventoryPlayer inv, TEChest tile) {
        super(tile);
        this.chest = tile;
        chest.openInventory();

        int playerInvX = 8, playerInvY = 84;

        switch (chest.getState()) {
            case 0:
                rows = BRICK_ROWS;
                columns = BRICK_COLUMNS;
                playerInvY = 84;
                break;
            case 1:
                rows = THAUMIUM_ROWS;
                columns = THAUMIUM_COLUMNS;
                playerInvY = 120;
                break;
            case 2:
                rows = VOID_ROWS;
                columns = VOID_COLUMNS;
                playerInvX = 26;
                playerInvY = 156;
                break;
        }

        bindChestInventory(tile, rows, columns);
        bindPlayerInventory(inv, playerInvX, playerInvY);
    }

    private void bindChestInventory(TEChest te, int rows, int columns) {
        int slotIndex = 0;
        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
                this.addSlotToContainer(new Slot(te, slotIndex, 8 + columnIndex * 18, 17 + rowIndex * 18));
                slotIndex++;
            }
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        chest.closeInventory();
    }
}
