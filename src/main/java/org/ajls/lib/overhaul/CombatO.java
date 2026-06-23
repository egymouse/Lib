package org.ajls.lib.overhaul;

import org.ajls.lib.Lib;
import org.ajls.lib.advanced.HashMapInteger;
import org.ajls.lib.advanced.hashMap.HashMapDouble;
import org.ajls.lib.advanced.hashMap.HaxhMapTimes;
import org.ajls.lib.references.Time;
import org.ajls.lib.utils.EventU;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.UUID;

public class CombatO implements Listener {
    static HashMapInteger<UUID> player_lastAttackedTimeStamp = new HashMapInteger<>();
    public static HashMapDouble<UUID> player_lastAttackedDamage = new HashMapDouble<>();
//    static HashMapDouble<UUID>
//    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
//    public void onEntityDamagedOld(EntityDamageEvent event) {
//        Entity entity = event.getEntity();
//        UUID entityUUID = entity.getUniqueId();
//        double finalDamage = EventU.getFinalDamage(event);//event.getFinalDamage();
////        player_lastAttackedTimeStamp.putMaxTime(entityUUID, 10);
//        if (entity instanceof LivingEntity) {
//            LivingEntity livingEntity = (LivingEntity) entity;
////            livingEntity.setMaximumNoDamageTicks(20);  //0 and 1 and 2 are same  if <= /2 then damage
//            if (livingEntity instanceof Player player) {
//                    player.sendMessage("a" + Time.getTime() % 20);
//            }
//
//            if (!player_lastAttackedTimeStamp.biggerThanTime(entityUUID)) {  //OrEqualToTime
//                player_lastAttackedTimeStamp.putMaxTime(entityUUID, 10);
//                livingEntity.setMaximumNoDamageTicks(0);  // originally 1
//                player_lastAttackedDamage.put(entityUUID, finalDamage);
//                if (livingEntity instanceof Player player) {
//                    player.sendMessage(ChatColor.YELLOW + "a." + Time.getTime() % 20);
//                }
//
//
//
//            }
//            else {
//                /*
// //               event.setCancelled(true); // damage by the difference value but not cause knockback by resetting the MaxNoDamageTick or just use addhealth(remember to consider absorptions
////                livingEntity.setNoDamageTicks(0);
//                if (finalDamage - livingEntity.getLastDamage() > 0) {
////                    event.setFinalDamage(finalDamage - livingEntity.getLastDamage());
//                }
//
//                 */
//                double lastDamage = player_lastAttackedDamage.get(entityUUID);
//                if (lastDamage >= finalDamage) {
//                    event.setCancelled(true);
//                }
//                else {
//                    livingEntity.setMaximumNoDamageTicks(20);
//                    livingEntity.setNoDamageTicks(20);
//                    livingEntity.setLastDamage(lastDamage);
//                    BukkitScheduler scheduler = Bukkit.getScheduler();
//                    scheduler.runTaskLater(Lib.getLib(), () -> {
//                        livingEntity.setNoDamageTicks(0);
//                        livingEntity.setMaximumNoDamageTicks(0);
//                    },0);
//                }
//
//            }
//
//
//        }
//        else {
//            Bukkit.broadcastMessage("lib combatO exception damaged entity not alive");
//        }
//
//    }



    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)  //懒得维护了，但是如果真的要维护可以用hashmap标记事件，如果eventU handle事件时这里没有标记就可以用hashmap来提取上一次的伤害（也不对，会受到114514的影响），如果标记了就不要用hashmap了，final damage就是对的，所以应该正解就是只有后面这一种
    public void onEntityDamaged(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.CUSTOM) {return;}
        Entity entity = event.getEntity();
        UUID entityUUID = entity.getUniqueId();
        double finalDamage = EventU.getNormalFinalDamage(event);  //getFinalDamage
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.setMaximumNoDamageTicks(20);
            int noDamageTicks = livingEntity.getNoDamageTicks();
            int maximumNoDamageTicks = livingEntity.getMaximumNoDamageTicks();
            if (maximumNoDamageTicks % 2 == 1) {
                maximumNoDamageTicks++;
            }
//            if (livingEntity instanceof Player player) {
//                player.sendMessage("b" + Time.getTime() % 20);
//            }

            if (noDamageTicks <= maximumNoDamageTicks / 2) {  //时间计算：如果在 Tick 0 受到攻击，那么无敌帧会覆盖 Tick 0 到 Tick 9。从 Tick 10 开始，无敌状态就结束了，所以这里可以取等
                player_lastAttackedDamage.put(entityUUID, finalDamage);
                BukkitScheduler scheduler = Bukkit.getScheduler();
                scheduler.runTaskLater(Lib.getLib(), () -> {
                    livingEntity.setLastDamage(-114514);
                },0);
//                if (livingEntity instanceof Player player) {
//                    player.sendMessage(ChatColor.YELLOW + "b." + Time.getTime() % 20);
//                }
            }
            else {
                /*
 //               event.setCancelled(true); // damage by the difference value but not cause knockback by resetting the MaxNoDamageTick or just use addhealth(remember to consider absorptions
//                livingEntity.setNoDamageTicks(0);
                if (finalDamage - livingEntity.getLastDamage() > 0) {
//                    event.setFinalDamage(finalDamage - livingEntity.getLastDamage());
                }

                 */

//                if (lastDamage >= finalDamage) {
//                    event.setCancelled(true);
//                }
//                else {
//                    Bukkit.broadcastMessage(ChatColor.YELLOW + "damage add");
//                    livingEntity.setLastDamage(lastDamage);
//                    BukkitScheduler scheduler = Bukkit.getScheduler();
//                    scheduler.runTaskLater(Lib.getLib(), () -> {
//                        livingEntity.setLastDamage(-1);
//                    },10);
//                }


                double lastDamage = player_lastAttackedDamage.get(entityUUID);
                if (finalDamage > lastDamage) {
//                    if (livingEntity instanceof Player player) {
//                        player.sendMessage(ChatColor.GOLD + "b." + Time.getTime() % 20);
//                    }
                    livingEntity.setLastDamage(lastDamage);
                    player_lastAttackedDamage.putMax(entityUUID, finalDamage);  //这样同一tick内的攻击也能正常结算，比如tick0造成了1伤害和2伤害，造成1伤害时如果记录lastDamage在runLater，那么在造成2伤害时lastDamage他还以为是0，显然是错误的
                    BukkitScheduler scheduler = Bukkit.getScheduler();
                    scheduler.runTaskLater(Lib.getLib(), () -> {
                        livingEntity.setLastDamage(-114514);  // to prevent modifying the last damage before event ended thus making getFinalDamage return 0
                        //todo 但是还是有个问题，毕竟延迟了1tick执行，那么同一tick内更低的伤害因为setLastDamage没改过来还是不会触发事件，还有下一tick如果是先事件后runTaskLater一样也有这个问题呢
                    },0);
                }
                else {
                    event.setCancelled(true);
                }

            }


        }
        else {
            if (entity instanceof Item) {

            }
            else {
                Bukkit.broadcastMessage("lib combatO exception damaged entity not alive: " + entity.getName());
            }
        }
    }

}
