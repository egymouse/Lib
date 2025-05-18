package org.ajls.lib.utils;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;


public class GamemodeU {

    public static boolean isPlayerSpectator(Player player) {
        return player.getGameMode().equals(GameMode.SPECTATOR);
    }

    public static boolean isPlayerDead(Player player) {
        return player.isDead();
    }

    public static boolean isPlayerUnplayable(Player player) {
        return isPlayerSpectator(player) || isPlayerDead(player);
    }

    public static boolean isPlayerPlayable(Player player) {
        return !isPlayerUnplayable(player);
    }

    public static boolean isPlayerPlayableEnemy(Player player1, Player player2) {
        return !isPlayerUnplayable(player1) && !isPlayerUnplayable(player2) && !PlayerU.isPlayerSameTeam(player1, player2);
    }

    public static boolean isPlayer2PlayableEnemy(Player player1, Player player2) {
        return !isPlayerUnplayable(player2) && !PlayerU.isPlayerSameTeam(player1, player2);
    }

    public static boolean isPlayerAttackableEnemy(Player player1, Player player2) {
        boolean result;
        result = !PlayerU.isPlayerSameTeam(player1, player2); //enemy
        if (!result) { //sameteam
            result = ScoreboardU.getPlayerTeam(player1).allowFriendlyFire();
        }
        return result;
    }
}
