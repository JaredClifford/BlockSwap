package com.jaerry.BlockSwap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

import net.md_5.bungee.api.ChatColor;

public class Tuple {
	private int count;
	private Score score;
	private boolean found;
	private Player player;
	private Material target;
	
	public Tuple(Player player) { 
		this.player = player; 
		target = Material.SPONGE;
		score = BlockSwap.objective.getScore(ChatColor.GREEN + player.getDisplayName() +":");
		score.setScore(count);
		player.setScoreboard(BlockSwap.board);
	}
	
	public int getCount() { return count;}
	public Player getPlayer() { return player;}
	public Material getMaterial() { return target;}
	public Boolean getStatus() {return found;}
	public Score getScore() {return score;}
	
	public void setMaterial(Material target) { this.target = target;}
	public void setStatus(boolean status) {found = status;}
	
	public void reset() { count = 0;}
	public void incCount() { count++;}
}
