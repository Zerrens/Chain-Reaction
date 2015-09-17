package com.zerren.chainreaction.handler;

import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.reference.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by Zerren on 2/19/2015.
 */
public class ConfigHandler {

    public static Configuration config;

    public static boolean devDebug;
    public static float vaultResistance;
    public static boolean bedrockVault;
    public static int vaultPages;
    public static float steamFactor;
    public static boolean harderStainless;
    public static int gasTankVolume;

    public static int temperature;

    public static int[] potionIDs = {66, 67, 68, 69, 70};
    public static String fluidNameSteam;

    private static final ConfigCategory CATEGORY_IDS = new ConfigCategory("ids").setRequiresMcRestart(true).setShowInGui(true);


    public static void init(File configFile){

        if (config == null) {
            config = new Configuration(configFile);
            loadConfigValues();
            if (config.hasChanged())
                config.save();
        }
    }

    private static void loadConfigValues() {
        //IDs
        config.addCustomCategoryComment(CATEGORY_IDS.getName(), "Below is used for changing the internal IDs that this mod uses: if you have potion ID conflicts, change them below.");
        potionIDs = config.get(CATEGORY_IDS.getName(), "potionIDs", potionIDs, "Potion ID array in descending order (A, B, G, N, Rad S.)", 33, 127).getIntList();
        fluidNameSteam = config.getString("fluidNameSteam", CATEGORY_IDS.getName(), "saturatedsteam", "The fluid registry name of Steam--set to 'steam' to use this mod's steam with other mods");
        //commit these values to the name registry
        Names.Fluids.initConfigValues();

        //tweaks
        devDebug = config.getBoolean("devDebug", "general", false, "System print for debugging--leave false unless you have a log spam fetish");
        vaultResistance = config.getFloat("vaultResistance", "general", 100F, 10F, 600F, "Blast resistance of a player owned and locked vault");
        bedrockVault = config.getBoolean("bedrockVault", "general", false, "If true, player owned and locked vaults are 100% immune to explosions--overwrites vaultResistance");
        vaultPages = config.getInt("vaultPages", "general", 10, 1, 20, "How many pages of 54 slots the vault will have. (1 page = double chest, default is 540 slots)");
        temperature = config.getInt("temperature", "general", 0, 0, 2, "What to display temperature as: 0 for Kelvin, 1 for Celsius, 2 for Fahrenheit");

        harderStainless = config.getBoolean("harderStainless", "general", false, "If true, the recipe for stainless steel dust requires steel dust in place of iron dust. For use with mods that add steel dust");
        steamFactor = config.getFloat("steamFactor", "general", 1F, 0.1F, 5F, "Multiplier on steam produced--(this is always a 1:160 water:steam ratio though)--best if used with 'uniSteam'");

        gasTankVolume = config.getInt("gasTankVolume", "general", 32, 16, 128, "Number of buckets of gas that the Gas Tank can hold");
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(Reference.ModInfo.MOD_ID)) {
            loadConfigValues();
        }
    }
}