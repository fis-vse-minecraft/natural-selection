package cz.vse.fis.minecraft.naturalselection.commands;

import cz.vse.fis.minecraft.naturalselection.NaturalSelectionRound;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class NaturalSelectionCommandExecutor implements CommandExecutor {

    private final JavaPlugin plugin;

    public NaturalSelectionCommandExecutor(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

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

            NaturalSelectionRound.startNew(player.getWorld(), plugin)
                    .ifPresentOrElse(
                            round -> {
                                player.getWorld()
                                        .getPlayers()
                                        .forEach(target -> target.sendTitle(
                                                ChatColor.AQUA + "Yo yo yo, get ready..." + ChatColor.RESET,
                                                "A new round of " + ChatColor.YELLOW + "natural selection" + ChatColor.RESET + " is starting",
                                                20, 100, 20)
                                        );
                            },
                            () -> player.sendMessage(ChatColor.RED + "Another round of natural selection is already running." + ChatColor.RESET)
                    );


            return true;
        }

        return false;
    }
}
