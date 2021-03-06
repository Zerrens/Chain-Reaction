package com.zerren.chainreaction.client.render.item;

import com.zerren.chainreaction.client.render.model.ModelExchangerSmall;
import com.zerren.chainreaction.client.render.model.ModelGasTank;
import com.zerren.chainreaction.reference.Reference;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created by Zerren on 3/7/2015.
 */
@SideOnly(Side.CLIENT)
public class ItemRendererExchanger implements IItemRenderer {

    private final ModelGasTank gasTank = new ModelGasTank();
    private final ModelExchangerSmall exchangerSmall = new ModelExchangerSmall();

    public ItemRendererExchanger() { }

    @Override
    public boolean handleRenderType(ItemStack itemStack, ItemRenderType itemRenderType) {
        int meta = itemStack.getItemDamage();

        switch(meta) {
            case 3:case 4: return true;
        }
        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType itemRenderType, ItemStack itemStack, ItemRendererHelper itemRendererHelper) {
        int meta = itemStack.getItemDamage();
        switch(meta) {
            case 3:case 4: return true;
        }
        return false;
    }

    @Override
    public void renderItem(ItemRenderType itemRenderType, ItemStack itemStack, Object... data) {
        int meta = itemStack.getItemDamage();
        switch (itemRenderType) {
            case ENTITY: {
                render(0F, 1F, 0F, meta, false);
                break;
            }
            case EQUIPPED: {
                render(0.5F, 1.5F, 0.5F, meta, false);
                break;
            }
            case EQUIPPED_FIRST_PERSON: {
                render(0.5F, 1.5F, 0.5F, meta, true);
                break;
            }
            case INVENTORY: {
                render(0.0F, 1F, 0.0F, meta, false);
                break;
            }
            default:
                break;
        }
    }

    private void render(float x, float y, float z, int metaData, boolean firstPerson) {
        //gas tank
        if (metaData == 3) {
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(Reference.Textures.Models.GAS_TANK);

            GL11.glPushMatrix(); //start
            GL11.glTranslatef(x, y, z); //size
            GL11.glRotatef(180, 1, 0, 0);
            GL11.glRotatef(firstPerson ? 90 : -90, 0, 1, 0);
            gasTank.render();
            gasTank.renderNeedle();
            GL11.glPopMatrix(); //end
        }
        //small exchanger
        if (metaData == 4) {
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(Reference.Textures.Models.HEAT_EXCHANGER_SMALL);

            GL11.glPushMatrix(); //start
            GL11.glTranslatef(x, y, z); //size
            GL11.glRotatef(180, 1, 0, 0);
            GL11.glRotatef(firstPerson ? 90 : -90, 0, 1, 0);
            exchangerSmall.render();
            GL11.glPopMatrix(); //end
        }
    }
}