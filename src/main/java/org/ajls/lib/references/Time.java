package org.ajls.lib.references;

import org.ajls.lib.Lib;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

public class Time {
    static int time = 0;
    static boolean started = false;

    public static void startTimer() {
        if (started) return;
        started = true;
        BukkitScheduler scheduler = Bukkit.getScheduler();
//        Cooldown.registerCooldowns();
        scheduler.runTaskTimer(Lib.getLib(), () -> {
            time++;
//            PassiveSkills.baseDegree++;
//            if (PassiveSkills.baseDegree >= 360) {
//                PassiveSkills.baseDegree = 0;
//            }
//            for (HashMap<UUID, Integer> cooldown : Cooldown.playerCooldowns) {
//                Cooldown.removeCooldown(cooldown, 1);
//            }
//            Cooldown.removeCooldown(Cooldown.player_passiveSkill1Cooldown, 1);
//            Cooldown.removeCooldown(Cooldown.player_passiveSkill2Cooldown, 1);
        }, 1, 1);
    }

    public static int getTime() {
        return time;
    }
}
