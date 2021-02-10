package cz.vse.fis.minecraft.naturalselection;

import lombok.Data;
import org.bukkit.boss.BossBar;

@Data
public class NaturalSelectionRoundState {
    public static final int DURATION_UNTIL_PVP = 30;

    private int timeUntilPvpEnabled = DURATION_UNTIL_PVP;
    private boolean pvpEnabled = false;
    private BossBar pvpTimer;
}
