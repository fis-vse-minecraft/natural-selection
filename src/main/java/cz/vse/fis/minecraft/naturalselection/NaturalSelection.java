package cz.vse.fis.minecraft.naturalselection;

import cz.vse.fis.minecraft.naturalselection.commands.NaturalSelectionCommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class NaturalSelection extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginCommand command = this.getCommand("natural-selection");

        if (command != null) {
            command.setExecutor(new NaturalSelectionCommandExecutor(this));
        }
    }

    @Override
    public void onDisable() {
    }
}
