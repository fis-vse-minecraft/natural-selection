package cz.vse.fis.minecraft.naturalselection;

import cz.vse.fis.minecraft.naturalselection.listeners.DeleteObsoleteWorldListener;
import cz.vse.fis.minecraft.naturalselection.utilities.WorldGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Getter
@EqualsAndHashCode
public class NaturalSelectionRound implements Listener {

    private final World world;

    private final JavaPlugin plugin;

    private final List<Player> players;

    private static boolean isRunning = false;

    private final Listener[] listeners = new Listener[] {
            new DeleteObsoleteWorldListener(this)
    };

    public static Optional<NaturalSelectionRound> startNew(@NotNull World world, @NotNull JavaPlugin plugin) {
        if (isRunning) {
            return Optional.empty();
        }

        return Optional.of(new NaturalSelectionRound(world.getPlayers(), plugin));
    }

    public void reset() {
        if (isRunning) {
            isRunning = false;
            WorldGenerator.deleteWorld(world);
        }
    }

    private NaturalSelectionRound(@NotNull List<Player> players, @NotNull JavaPlugin plugin) {
        isRunning = true;

        this.plugin = plugin;
        this.players = players;
        this.world = WorldGenerator.generateNaturalSelectionWorld();

        this.registerEventListeners();
        this.initializeRound();
    }

    private void registerEventListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        for (Listener listener : this.listeners) {
            pluginManager.registerEvents(listener, plugin);
        }
    }

    private void initializeRound() {
        players.forEach(player -> player.teleport(world.getSpawnLocation()));
    }
}
