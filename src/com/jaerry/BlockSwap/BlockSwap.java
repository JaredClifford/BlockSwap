package com.jaerry.BlockSwap;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class BlockSwap extends JavaPlugin {
	public static ScoreboardManager manager;
	public static Objective objective;
	public static Scoreboard board;
	public static int timer;
	public BossBar bossbar;
	public int scheduleID;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		getLogger().info("BlockSwap has been enabled");
		PluginManager pm = getServer().getPluginManager();
		BlockSwapListener listener = new BlockSwapListener(this);
		pm.registerEvents(listener,this);
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		objective = board.registerNewObjective("Score", "dummy");
		objective.setDisplayName("Score");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		bossbar = Bukkit.createBossBar("Time Left", BarColor.GREEN, BarStyle.SOLID, BarFlag.DARKEN_SKY);
		timer = 0;
	}
	
	@Override
	public void onDisable() {
		getLogger().info("BlockSwap has been disabled");
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("BlockSwap")) {
			Player player;		
			if (args.length == 0) {
				if ( sender instanceof Player) {
					player = (Player) sender;
					People.add(player);
					bossbar.addPlayer(player);	
					return true;
				}
			} else {
				player = Bukkit.getPlayer(args[0]);
				People.add(player);
				player.sendMessage("You have been added to BlockSwap");
				bossbar.addPlayer(player);
				return true;
			}
		}
		if (cmd.getName().equalsIgnoreCase("BSStart")) {
			if ( People.size != 0 && People.start == false) {
				People.start = true;
				scheduleID = this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
					public void run() {
						if (People.start) {
							bossbar.setProgress(1-timer/300.);
							if (timer == 0) {
								People.newBlocks();
							}
							if (timer == 300) {
								timer = 0;
							} else {
								timer++;
							}
						} else {
							People.start = false;
							for ( int i = 0; i < People.size; i++) {
								People.list[i].reset();
							}
							BlockSwapListener.updateScoreboard();
							Bukkit.getScheduler().cancelTask(scheduleID);
						}
					}
				}, 0L, 20L);
			} else {
				if ( sender instanceof Player) {
					sender.sendMessage("No one in BlockSwap");
				} else {
					getLogger().info("No one in BlockSwap");
				}
				return true;
			}
		}
		return false;
	}
	
}
