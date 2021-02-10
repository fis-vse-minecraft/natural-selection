package cz.vse.fis.minecraft.naturalselection.listeners;

import cz.vse.fis.minecraft.naturalselection.NaturalSelectionRound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

public class PvpPreventionListener implements Listener {

    private final NaturalSelectionRound round;

    public PvpPreventionListener(@NotNull NaturalSelectionRound round) {
        this.round = round;
    }

    @EventHandler
    public void onPvp(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            if (!round.getState().isPvpEnabled()) {
                event.setCancelled(true);
            }
        }
    }
}
