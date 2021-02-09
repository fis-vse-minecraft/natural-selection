package cz.vse.fis.minecraft.naturalselection;

import lombok.EqualsAndHashCode;
import org.bukkit.World;
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

    private NaturalSelectionRound(@NotNull World world, @NotNull JavaPlugin plugin) {
        this.world = world;
        this.plugin = plugin;
        this.players = world.getPlayers();

        isRunning = true;

        // TODO: Initialize the round and register all event listeners n shit
    }

    public static Optional<NaturalSelectionRound> startNew(@NotNull World world, @NotNull JavaPlugin plugin) {
        if (isRunning) {
            return Optional.empty();
        }

        return Optional.of(new NaturalSelectionRound(world, plugin));
    }
}
