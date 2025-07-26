package de.lacertis.client.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "legacyutils")
@Config.Gui.Background("minecraft:textures/block/prismarine.png")
public class LegacyModConfig implements ConfigData {
    @ConfigEntry.Category("appearance")
    @ConfigEntry.ColorPicker
    @ConfigEntry.Gui.Tooltip
    public int primaryColor = 0x03BEFC;

}