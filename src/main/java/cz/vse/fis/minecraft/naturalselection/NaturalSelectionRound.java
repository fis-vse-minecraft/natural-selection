package cz.vse.fis.minecraft.naturalselection;

import lombok.EqualsAndHashCode;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@EqualsAndHashCode
public class NaturalSelectionRound {

    private final World world;

    private final JavaPlugin plugin;

    private final List<Player> players;

    private static boolean isRunning = false;

    private final static Listener[] listeners = new Listener[] {
    };

    private NaturalSelectionRound(@NotNull List<Player> players, @NotNull JavaPlugin plugin) {
        isRunning = true;

        this.plugin = plugin;
        this.players = players;
        this.world = generateNaturalSelectionRound();

        // TODO: register event listeners

        Location spawn = world.getSpawnLocation();
        players.forEach(player -> player.teleport(spawn));
    }

    private World generateNaturalSelectionRound() {
        return Bukkit.createWorld(
                new WorldCreator("ns_" + RandomStringUtils.randomAlphanumeric(16))
                    .generateStructures(false)
                    .type(WorldType.FLAT)
        );
    }

    public static Optional<NaturalSelectionRound> startNew(@NotNull World world, @NotNull JavaPlugin plugin) {
        if (isRunning) {
            return Optional.empty();
        }

        return Optional.of(new NaturalSelectionRound(world.getPlayers(), plugin));
    }
}
