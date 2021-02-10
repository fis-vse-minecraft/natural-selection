package cz.vse.fis.minecraft.naturalselection.listeners;

import cz.vse.fis.minecraft.naturalselection.NaturalSelectionRound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DeleteObsoleteWorldListener implements Listener {

    private final NaturalSelectionRound round;

    public DeleteObsoleteWorldListener(@NotNull NaturalSelectionRound round) {
        this.round = round;
    }

    @EventHandler
    public void onPlayerDied(PlayerDeathEvent event) {
        deleteWorldIfObsolete();
    }

    @EventHandler
    public void onPlayerTeleported(PlayerTeleportEvent event) {
        if (event.getFrom().getWorld().getUID() == round.getWorld().getUID()) {
            deleteWorldIfObsolete();
        }
    }

    private void deleteWorldIfObsolete() {
        long alive = round.getWorld().getPlayers()
                .stream()
                .filter(player -> !player.isDead())
                .count();

        // If all players are dead
        if (alive == 0) {
            this.round.reset();
        }
    }
}
