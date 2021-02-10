package cz.vse.fis.minecraft.naturalselection;

import cz.vse.fis.minecraft.naturalselection.listeners.DeleteObsoleteWorldListener;
import cz.vse.fis.minecraft.naturalselection.listeners.PvpPreventionListener;
import cz.vse.fis.minecraft.naturalselection.utilities.WorldGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
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

    private final JavaPlugin plugin;

    private final World world;

    private final List<Player> players;

    private final int tickTaskId;

    private final NaturalSelectionRoundState state;

    private static boolean isRunning = false;

    private final Listener[] listeners = new Listener[] {
            new DeleteObsoleteWorldListener(this),
            new PvpPreventionListener(this)
    };

    public static Optional<NaturalSelectionRound> startNew(@NotNull World world, @NotNull JavaPlugin plugin) {
        if (isRunning) {
            return Optional.empty();
        }

        return Optional.of(new NaturalSelectionRound(world.getPlayers(), plugin));
    }

    private NaturalSelectionRound(@NotNull List<Player> players, @NotNull JavaPlugin plugin) {
        isRunning = true;

        this.plugin = plugin;
        this.players = players;
        this.world = WorldGenerator.generateNaturalSelectionWorld();
        this.state = new NaturalSelectionRoundState();

        this.registerEventListeners();
        this.initializeRound();

        this.tickTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::tick, 0L, 20L);
    }

    private void registerEventListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        for (Listener listener : this.listeners) {
            pluginManager.registerEvents(listener, plugin);
        }
    }

    private void initializeRound() {
        this.state.setPvpEnabled(false);
        this.state.setPvpTimer(Bukkit.createBossBar(new NamespacedKey(plugin, "pvp_timer"), "Time until PVP", BarColor.RED, BarStyle.SOLID));

        players.forEach(player -> {
            player.getInventory().clear();
            player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
            player.teleport(world.getSpawnLocation());

            this.state.getPvpTimer().addPlayer(player);
        });
    }

    private void tick() {
        performPvpTick();
    }

    private void performPvpTick() {
        if (!state.isPvpEnabled()) {
            int remaining = state.getTimeUntilPvpEnabled();

            if (remaining <= 0) {
                state.getPvpTimer().removeAll();
                state.setPvpEnabled(true);
                players.forEach(player ->
                        player.sendTitle(
                                ChatColor.RED + "PVP Enabled" + ChatColor.RESET,
                                "Glfh bois",
                                20, 60, 60
                        )
                );
            }
            else {
                state.getPvpTimer().setProgress((double) remaining / (double) NaturalSelectionRoundState.DURATION_UNTIL_PVP);
                state.setTimeUntilPvpEnabled(remaining - 1);
            }
        }
    }

    public void reset() {
        if (isRunning) {
            isRunning = false;
            Bukkit.removeBossBar(new NamespacedKey(plugin, "pvp_timer"));

            Bukkit.getScheduler().cancelTask(tickTaskId);
            WorldGenerator.deleteWorld(world);
        }
    }
}
