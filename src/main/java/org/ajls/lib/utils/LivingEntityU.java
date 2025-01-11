package org.ajls.lib.utils;

import org.bukkit.entity.LivingEntity;

public class LivingEntityU {
    public static boolean isInIFrame(LivingEntity livingEntity) {
        int maxNoDamageTicks = livingEntity.getMaximumNoDamageTicks();
        int noDamageTicks = livingEntity.getNoDamageTicks();
        if (maxNoDamageTicks % 2 == 1) {
            maxNoDamageTicks++;
        }
        return noDamageTicks > maxNoDamageTicks / 2;
    }
}
