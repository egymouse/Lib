package org.ajls.lib.math;

import org.ajls.lib.utils.GamemodeU;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class CylinderU {
    public static ArrayList<Entity> getNearbyCylindricalEntities(Player player, double distance, double radius, double count, double playerPositionOffset) {
        HashMap<Double, Entity> distance_player = new HashMap<>();
        World world = player.getWorld();
        Vector playerPos = player.getLocation().toVector();
        Vector playerDirection = player.getLocation().getDirection();
        Vector offset = playerDirection.clone();
        playerPos.add(offset.multiply(playerPositionOffset));
        for (Entity otherPlayer : world.getEntities()) {
            if (otherPlayer != player) {
                Vector otherPlayerPos = otherPlayer.getLocation().toVector();
                Vector relativePos = otherPlayerPos.subtract(playerPos);
                double relativeDistance = relativePos.dot(playerDirection);
                if (relativeDistance < distance) {
                    double euclideanDistanceSquared = relativePos.lengthSquared();//otherPlayerPos.distanceSquared(playerPos);
                    double radiusSquared = euclideanDistanceSquared - relativeDistance * relativeDistance;
                    if (radiusSquared < radius * radius) { 
                        distance_player.put(euclideanDistanceSquared, otherPlayer);
                    }
                }

            }
        }
        ArrayList<Entity> nearbyPlayers = new ArrayList<>();
        int returnAmount = (int) Math.min(distance_player.size(), count);
        for (int i = 0; i < returnAmount  ; i++) {
            double nearest_distance = Double.MAX_VALUE;
            for (double distance2 : distance_player.keySet()) {
                if (nearest_distance > distance2) {
                    nearest_distance = distance2;
                }
            }
            Entity nearestPlayer = distance_player.get(nearest_distance);
            nearbyPlayers.add(nearestPlayer);
            distance_player.remove(nearest_distance);
        }
        return nearbyPlayers;
    }

//    public static ArrayList<Player> getNearbyCylindricalSameTeamPlayers(Player player, double distance, double radius, double count, double playerPositionOffset) {
//        HashMap<Double, Player> distance_player = new HashMap<>();
//        World world = player.getWorld();
//        Vector playerPos = player.getLocation().toVector();
//        Vector playerDirection = player.getLocation().getDirection();
//        Vector offset = playerDirection.clone();
//        playerPos.add(offset.multiply(playerPositionOffset));
//        for (Player otherPlayer : world.getPlayers()) {
//            if (GamemodeU.isPlayer2PlayableEnemy(player, otherPlayer)) {
//                Vector otherPlayerPos = otherPlayer.getLocation().toVector();
//                Vector relativePos = otherPlayerPos.subtract(playerPos);
//                double relativeDistance = relativePos.dot(playerDirection);
//                if (relativeDistance < distance) {
//                    double euclideanDistanceSquared = relativePos.lengthSquared(); //otherPlayerPos.distanceSquared(playerPos);
//                    double radiusSquared = euclideanDistanceSquared - relativeDistance * relativeDistance;
//                    if (radiusSquared < radius * radius) {
//                        distance_player.put(euclideanDistanceSquared, otherPlayer);
//                    }
//                }
//
//            }
//        }
//        ArrayList<Player> nearbyPlayers = new ArrayList<>();
//        int returnAmount = (int) Math.min(distance_player.size(), count);
//        for (int i = 0; i < returnAmount; i++) {
//            double nearest_distance = Double.MAX_VALUE;
//            for (double distance2 : distance_player.keySet()) {
//                if (nearest_distance > distance2) {
//                    nearest_distance = distance2;
//                }
//            }
//            Player nearestPlayer = distance_player.get(nearest_distance);
//            nearbyPlayers.add(nearestPlayer);
//            distance_player.remove(nearest_distance);
//        }
//        return nearbyPlayers;
//    }
}
