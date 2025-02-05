package org.ajls.lib.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardU {
    //create scoreboard
    //should be forced
    //boolean forced
    public static Objective createScoreboard(String name) {
        return createScoreboard(name, false);
    }
    public static Objective createScoreboard(String name, boolean forced) {//@Nullable String type, @Nullable String displayname
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective currentObjective = scoreboard.getObjective(name);
        boolean create = true;
        if (currentObjective != null) {
            if (forced) {
                currentObjective.unregister();
            }
            else {
                create = false;
            }
        }
        if (create) {
            Objective objective = scoreboard.registerNewObjective(name, "dummy");
            return objective;
        }
        else {
            return currentObjective;
        }
//        if (currentObjective == null) {
//            ScoreboardManager manager = Bukkit.getScoreboardManager();
//            Scoreboard board = manager.getMainScoreboard();
//            Objective objective = board.registerNewObjective(name, "dummy");
//            return objective;
//        }
//        else {
//            if (forced) {
//                currentObjective.unregister();
//                Objective objective = scoreboard.registerNewObjective(name, "dummy");
//                return objective;
//            }
//        }
//        return null;
    }

    public static boolean tryCreateScoreboard(String name) {//@Nullable String type, @Nullable String displayname
        if (Bukkit.getScoreboardManager().getMainScoreboard().getObjective(name) == null) {
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard board = manager.getMainScoreboard();
            Objective objective = board.registerNewObjective(name, "dummy");
            return true;
        }
        return false;
    }


    public static Objective getScoreboard(String name){
        //get the scoreboard of this name
        Objective objective = Bukkit.getScoreboardManager().getMainScoreboard().getObjective(name);
        return objective;
    }

    public static Objective getObjective(String name){
        //get the scoreboard of this name
        Objective objective = Bukkit.getScoreboardManager().getMainScoreboard().getObjective(name);
        return objective;
    }

    public static void setDisplaySlot(String objective, DisplaySlot slot) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        Objective objective1 = board.getObjective(objective);
        if (objective1 == null) return;
        objective1.setDisplaySlot(slot);
    }
    public static void setDisplayName(String objective, String name) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        Objective objective1 = board.getObjective(objective);
        if (objective1 == null) return;
        objective1.setDisplayName(name);
    }

    public static boolean isScoreSet(Player player, String name, boolean noObjectiveReturn){  //hasScore
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        Objective objective = board.getObjective(name);
        if (objective == null) { return noObjectiveReturn; }  // default false
        return objective.getScore(player.getName()).isScoreSet();
    }

    public static int getScore(Player player, String name) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        Objective objective = board.getObjective(name);
        int score;
        score = objective.getScore(player.getName()).getScore();
        return score;
    }

    public static void setScore(Player player, String name, int score) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        Objective objective = board.getObjective(name);
        if (objective == null) { return; }
        objective.getScore(player.getName()).setScore(score);
    }

    public static void resetScore(Player player, String name) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        Objective objective = board.getObjective(name);
        if (objective == null) { return;}
        objective.getScore(player.getName()).resetScore();
    }

    public static void addScore(Player player, String name, int score) {
        setScore(player, name, getScore(player, name) + score);
    }

    public static void registerTeam(String teamName){
        // register team
        if (Bukkit.getScoreboardManager().getMainScoreboard().getTeam(teamName) == null) {
            Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(teamName);
        }
    }

    public static void setTeamRule(String teamName, Team.Option option, Team.OptionStatus optionStatus){
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Team team = scoreboardManager.getMainScoreboard().getTeam(teamName);
        team.setOption(option,optionStatus);
    }

    public static void setFriendlyFire(String teamName, boolean friendlyFire){
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Team team = scoreboardManager.getMainScoreboard().getTeam(teamName);
        team.setAllowFriendlyFire(friendlyFire);
    }

    public static void setTeamColor(String teamName, ChatColor chatColor){
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Team team = scoreboardManager.getMainScoreboard().getTeam(teamName);
        team.setColor(chatColor);
    }

    public static Team getTeam(String teamName){
        //get the team of this name
        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(teamName);
        return team;
    }

    public static Team getPlayerTeam(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getPlayerTeam(player);
        return team;
    }

    public static String getPlayerTeamName(Player player) {
//        return getPlayerTeam(player).getName();

//        Team team = getPlayerTeam(player);
//        if (team == null) return null;
//        return team.getName();
        return getPlayerTeamName(player, true);
    }
    public static String getPlayerTeamName(Player player, boolean notNull) {
        Team team = getPlayerTeam(player);
        if (team == null) {
            if (notNull) return "";
            return null;
        }
        return team.getName();
//        String TeamName = getPlayerTeamName(player);
//        if (notNull) {
//            if (TeamName == null) return "";
//        }
//        return TeamName;
    }

    public static Team.OptionStatus getPlayerTeamOption(Player player, Team.Option option) {
        Team team = getPlayerTeam(player);
        return team.getOption(option);
    }

    public static void setPlayerTeamFriendlyFire(Player player, boolean friendlyFire){
        getPlayerTeam(player).setAllowFriendlyFire(friendlyFire);
    }

    public static boolean getPlayerTeamFriendlyFire(Player player){
        return getPlayerTeam(player).allowFriendlyFire();
    }

    public static ChatColor getPlayerTeamColor(Player player, boolean notNull){
        if (getPlayerTeam(player) == null) {
            if (notNull) {
                return ChatColor.WHITE;
            }
            return null;
        }
        return getPlayerTeam(player).getColor();
    }

    public static ChatColor getPlayerTeamColor(Player player) {
        return getPlayerTeamColor(player, true);
    }

//    public static boolean isPlayerSameTeam(Player player1, Player player2) {
//        String teamName1 = getPlayerTeam(player1).getName();
//        String teamName2 = getPlayerTeam(player2).getName();
//        return teamName1.equals(teamName2);
//    }

    public static boolean joinTeam(String teamName, Player player) {
        Team team = getTeam(teamName);
        if (team == null) return false;
        boolean added = !team.hasPlayer(player); //team.getEntries().contains(player);
        team.addPlayer(player);
//        team.addEntry(player.getName());
        return added;
    }

    public static boolean leaveTeam(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getEntryTeam(player.getName());
        if (team == null) {
//            player.sendMessage("You are not in any team!");
            return false;
        }

        // Remove player from the team
        team.removeEntry(player.getName());
        return true;
    }

}

