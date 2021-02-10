package cz.vse.fis.minecraft.naturalselection.utilities;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class WorldGenerator {
    public static World generateNaturalSelectionWorld() {
        // TODO: Add support for custom config
        World generated = Bukkit.createWorld(
                new org.bukkit.WorldCreator("ns_" + RandomStringUtils.randomAlphanumeric(16))
                        .generateStructures(false)
                        .type(WorldType.FLAT)
        );

        Objects.requireNonNull(generated);

        generated.getWorldBorder().setSize(100);
        generated.setGameRule(GameRule.DO_MOB_SPAWNING, false);

        return generated;
    }

    public static void deleteWorld(@NotNull World world) {
        World defaultWorld = Bukkit.getWorld("world");

        if (defaultWorld != null) {
            for (Player player : world.getPlayers()) {
                player.teleport(defaultWorld.getSpawnLocation());
            }
        }

        world.setKeepSpawnInMemory(false);
        Bukkit.unloadWorld(world, false);

        TimerTask deleteWorldFolder = new TimerTask() {
            @Override
            @SneakyThrows
            public void run() {
                System.out.println("Deleting world directory " + world.getName());
                FileUtils.deleteDirectory(world.getWorldFolder());
            }
        };

        new Timer().schedule(deleteWorldFolder, 10 * 1000);
    }
}
