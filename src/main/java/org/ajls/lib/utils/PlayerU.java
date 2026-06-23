package org.ajls.lib.utils;

import com.google.common.base.Charsets;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerU extends LivingEntityU{
    public static int getPlayerEPF(Player player) {
//        player.getInventory().getArmorContents();
        EntityEquipment entityEquipment = player.getEquipment();
        ItemStack helmet = entityEquipment.getHelmet();
        ItemStack chestplate = entityEquipment.getChestplate();
        ItemStack leggings = entityEquipment.getLeggings();
        ItemStack boots = entityEquipment.getBoots();
        ItemStack[] equipments = {helmet, chestplate, leggings, boots};
        int EPF = 0;
        for (int i = 0; i < equipments.length; i++) {
            ItemStack equipment = equipments[i];
            if (equipment == null) continue;
            EPF += ItemStackU.getEnchantmentLevel(equipment, Enchantment.PROTECTION);
        }
//        EPF *= 4;
        return EPF;
    }

    @Deprecated
    public static void addHealth(LivingEntity entity, double health) {
        double absorptionPlayer = entity.getAbsorptionAmount();
        double healthPlayer = entity.getHealth();
        if (health < 0 ) {  //damage
            Bukkit.getServer().getPluginManager().callEvent(new EntityDamageEvent(entity, EntityDamageEvent.DamageCause.CUSTOM, -health));
            if (absorptionPlayer >= -health) { // absorption able to absorb damage
                absorptionPlayer += health;
                health = 0;
            }
            else {  // absorption not able to absorb damage
                health += absorptionPlayer; // damage remove absorption amount
                absorptionPlayer = 0;
//                healthPlayer += health; // damage to player's health
            }
        }
        entity.setAbsorptionAmount(absorptionPlayer);
        healthPlayer += health;  // damage to player's health
//        else { // healing
//            healthPlayer += health;
//        }
//        healthPlayer = healthPlayer + health;
        if (0 < healthPlayer && healthPlayer < entity.getMaxHealth()) {
            entity.setHealth(healthPlayer);
//            EntityRegainHealthEvent entityRegainHealthEvent = new EntityRegainHealthEvent(entity, health, EntityRegainHealthEvent.RegainReason.CUSTOM);
//            Bukkit.getServer().getPluginManager().callEvent(entityRegainHealthEvent);
        }
        else if (healthPlayer <= 0) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
//                playerDeathEvent(player);
            }
            else {
                entity.setHealth(0);
            }
        }
        else {
            entity.setHealth(entity.getMaxHealth());
        }
    }

    public static double predictAddHealth(LivingEntity entity, double health) {
        double absorptionPlayer = entity.getAbsorptionAmount();
        double healthPlayer = entity.getHealth();
        if (health < 0 ) {  //damage
            if (absorptionPlayer >= -health) { // absorption able to absorb damage
                absorptionPlayer += health;
                health = 0;
            }
            else {  // absorption not able to absorb damage
                health += absorptionPlayer; // damage remove absorption amount
                absorptionPlayer = 0;
            }
        }
        entity.setAbsorptionAmount(absorptionPlayer);
        healthPlayer += health;  // damage to player's health

        if (0 < healthPlayer && healthPlayer < entity.getMaxHealth()) {
            return healthPlayer;
        }
        else if (healthPlayer <= 0) {
            return 0;
        }
        else {
            return entity.getMaxHealth();
        }
    }


    public static void addFoodLevel(Player player, int amount) {
        // increase saturation if more , saturation cap at foodlevel if decrease
        int current_food_level = player.getFoodLevel() + amount;
        float saturation = player.getSaturation();
        if (current_food_level < saturation) {
            // saturation must cap at food level
            if (current_food_level < 0) {
                // starve
                current_food_level = 0;
                saturation = 0;
            }
            else {
                // cap at food level
                saturation = current_food_level;
            }
        }
        if (current_food_level > 20) {
            // increase saturation
            saturation = saturation + current_food_level - 20;
            // saturation added the more part
            if (saturation > 20) {
                saturation = 20;
                // cap at 20
            }
            current_food_level = 20;
            // cap at 20
        }
        player.setFoodLevel(current_food_level);
        player.setSaturation(saturation);
    }

    public static boolean isPlayerSameTeam(Player player1, Player player2) {
        if (player1 == null || player2 == null) return false;
        String teamName1 = ScoreboardU.getPlayerTeamName(player1, false);
        String teamName2 = ScoreboardU.getPlayerTeamName(player2, false);
//        String teamName1 = getPlayerTeam(player1).getName();
//        String teamName2 = getPlayerTeam(player2).getName();
        if (player1 == player2) return true;
        if (teamName1 == null || teamName2 == null) return false;
        return teamName1.equals(teamName2);
    }

    public static boolean isPlayerSpectator(Player player) {
        if (player == null) return false;
        return player.getGameMode().equals(GameMode.SPECTATOR);
    }

    public static boolean isPlayerDead(Player player) {
        if (player == null) return false;
        return player.isDead();
    }

    public static boolean isPlayerUnplayable(Player player) {
        if (player == null) return true;
        return isPlayerSpectator(player) || isPlayerDead(player);
    }

    public static boolean isPlayerPlayable(Player player) {
        return !isPlayerUnplayable(player);
    }

    public static boolean isPlayerPlayableEnemy(Player player1, Player player2) {
        return !isPlayerUnplayable(player1) && !isPlayerUnplayable(player2) && !isPlayerSameTeam(player1, player2);
    }

    public static boolean isPlayer2PlayableEnemy(Player player1, Player player2) {
        return !isPlayerUnplayable(player2) && !isPlayerSameTeam(player1, player2);
    }

    public static boolean isPlayerAttackableEnemy(Player player1, Player player2) {
        boolean result;
        result = !isPlayerSameTeam(player1, player2); //enemy
        if (!result) { //sameteam
            result = ScoreboardU.getPlayerTeam(player1).allowFriendlyFire();
        }
        return result;
    }


    public static UUID getUUIDFromName(String name) {
        if (name == null) return null;
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(Charsets.UTF_8));
    }

}
