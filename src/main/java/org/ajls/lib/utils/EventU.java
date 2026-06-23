package org.ajls.lib.utils;

//import org.ajls.lib.overhaul.CombatO;
import org.bukkit.Bukkit;
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

            if (LivingEntityU.isInIFrame(livingEntity)) {
                //finalDamage -= CombatO.player_lastAttackedDamage.get(livingEntityUUID);  //不行，要实时记录是否被CombatO事件处理过，CombatO事件的优先级应该是比较低的 因为防止没到别的插件修改了伤害（比如hb乘以2）自己就先做主张把它吞了
                //而且插件在你之后执行，那个时候player_lastAttackedDamage不是会被这次事件覆盖吗？那最终不就是0吗
                finalDamage -= livingEntity.getLastDamage();
            }

            UUID livingEntityUUID = livingEntity.getUniqueId();
            double livingEntityHealthBefore = livingEntity.getHealth();
            if (finalDamage <= 0) return livingEntityHealthBefore;

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
            LivingEntity livingEntity = (LivingEntity) entity;

            if (LivingEntityU.isInIFrame(livingEntity)) {
                finalDamage -= livingEntity.getLastDamage();
                //finalDamage -= CombatO.player_lastAttackedDamage.get(livingEntityUUID);
            }

            if (finalDamage <= 0) return 0;

            UUID livingEntityUUID = livingEntity.getUniqueId();
            double livingEntityHealthBefore = livingEntity.getHealth();

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
        else {  //todo 对于物品受伤怎么办？
            return -1;
        }
    }

    public static double getNormalFinalDamage(EntityDamageEvent event) {
        double finalDamage = event.getFinalDamage();
        if (finalDamage <= 0) return 0;
        return finalDamage;
    }
}
