package cz.vse.fis.minecraft.naturalselection.utilities;

import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;

public class WorldGenerator {
    public static World generateNaturalSelectionWorld() {
        // TODO: Add support for custom config

        return Bukkit.createWorld(
                new org.bukkit.WorldCreator("ns_" + RandomStringUtils.randomAlphanumeric(16))
                        .generateStructures(false)
                        .type(WorldType.FLAT)
        );
    }
}
