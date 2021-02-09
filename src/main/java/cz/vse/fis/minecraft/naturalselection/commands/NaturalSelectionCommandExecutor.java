package cz.vse.fis.minecraft.naturalselection.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NaturalSelectionCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (!player.isOp()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 2));
                player.sendMessage(ChatColor.RED + "You need to be an OP to start new natural selection round.\nYee yee ass retardo." + ChatColor.RESET);
                return false;
            }

            List<Player> players = player.getWorld().getPlayers();

            players.forEach(it -> it.sendMessage(ChatColor.AQUA + "Yo yo yo, get ready... new round of natural selection starting soon(tm)." + ChatColor.RESET));

            return true;
        }

        return false;
    }
}
