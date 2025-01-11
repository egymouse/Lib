package org.ajls.lib.utils;

import org.ajls.lib.overhaul.CombatO;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

public class EventU {
    public static double getFinalHealth(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        double finalDamage = event.getFinalDamage();
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            UUID livingEntityUUID = livingEntity.getUniqueId();
            double livingEntityHealthBefore = livingEntity.getHealth();
            if (finalDamage <= 0) return livingEntityHealthBefore;
            if (LivingEntityU.isInIFrame(livingEntity)) {
                finalDamage -= CombatO.player_lastAttackedDamage.get(livingEntityUUID);
            }
            double livingEntityAbsorptionBefore = livingEntity.getAbsorptionAmount();
            double livingEntityAbsorptionAfter;
            double livingEntityHealthAfter;// = livingEntity.getHealth() - finalDamage;
            if (livingEntityAbsorptionBefore >= finalDamage) {
                livingEntityAbsorptionAfter = livingEntityAbsorptionBefore - finalDamage;
                return livingEntityHealthBefore;
            }
            else {
                livingEntityAbsorptionAfter = 0;
                livingEntityHealthAfter = livingEntityHealthBefore + livingEntityAbsorptionBefore - finalDamage;
                if (livingEntityHealthAfter <= 0) return 0;
                return livingEntityHealthAfter;
            }

//            return livingEntityHealthAfter;
        }
        else {
            return -1;
        }


    }

    public static double getFinalDamage(EntityDamageEvent event) {  //considering entity damage in iframe and health
        Entity entity = event.getEntity();
        double finalDamage = event.getFinalDamage();
        if (entity instanceof LivingEntity) {
            if (finalDamage <= 0) return 0;
            LivingEntity livingEntity = (LivingEntity) entity;
            UUID livingEntityUUID = livingEntity.getUniqueId();
            double livingEntityHealthBefore = livingEntity.getHealth();
            if (LivingEntityU.isInIFrame(livingEntity)) {
                finalDamage -= CombatO.player_lastAttackedDamage.get(livingEntityUUID);
            }
            double livingEntityAbsorptionBefore = livingEntity.getAbsorptionAmount();
            double livingEntityHPBefore = livingEntityHealthBefore + livingEntityAbsorptionBefore;
            if (livingEntityHPBefore >= finalDamage) {
                return finalDamage;
            }
            else {
                return livingEntityHPBefore;
            }
//            finalDamage -= livingEntityAbsorptionBefore;
//            finalDamage -= livingEntityHealthBefore;
//            if (finalDamage <= 0) return 0;

//            return livingEntityHealthAfter;
        }
        else {
            return -1;
        }
    }

    public static double getNormalFinalDamage(EntityDamageEvent event) {
        double finalDamage = event.getFinalDamage();
        if (finalDamage <= 0) return 0;
        return finalDamage;
    }
}
