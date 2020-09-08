package com.jaerry.BlockSwap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.meta.FireworkMeta;

public class BlockSwapListener implements Listener{
	
	BlockSwapListener(BlockSwap plugin){
		new BlockList();
		new People();
	}
	//Event Handler
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (People.start) {
			if (People.isParticipant(event.getPlayer())) {
				Tuple player = People.findTuple(event.getPlayer());
				Location below = (player.getPlayer().getLocation());
				below.setY(below.getY()-1);
				if (below.getBlock().getType() == player.getMaterial() && !player.getStatus()) {
					Bukkit.getServer().broadcastMessage(player.getPlayer().getDisplayName() + " has found " + below.getBlock().getType().toString());
					player.setStatus(true);
					player.incCount();
					below.getBlock().setType(Material.DIAMOND_BLOCK);
					Firework fw = (Firework) player.getPlayer().getWorld().spawnEntity(below, EntityType.FIREWORK); 
					FireworkMeta fwm = fw.getFireworkMeta();
					FireworkEffect effect = FireworkEffect.builder().with(Type.BALL).withColor(Color.GREEN).build();
					fwm.addEffect(effect);
					fw.setFireworkMeta(fwm);
					updateScoreboard();
					if (player.getCount() == 5) {
						Bukkit.getServer().broadcastMessage(player.getPlayer().getDisplayName() + " is the winner");
						People.start = false;
					}
				}
			}
		}		
	}
	
	
	public static void updateScoreboard() {
		for ( int i = 0; i < People.size; i++) {
			People.list[i].getScore().setScore(People.list[i].getCount());
		}
	}
}