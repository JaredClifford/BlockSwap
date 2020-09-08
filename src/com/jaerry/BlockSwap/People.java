package com.jaerry.BlockSwap;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class People {
	public static int cap;
	public static int size;
	public static int found;
	public static boolean start;
	public static Tuple[] list;
	public static Random rand;
	
	public People() {
		cap = 2;
		size = 0;
		found = 0;
		list = new Tuple[cap];
		start = false;
		rand = new Random();
	}

	public static void add(Player player) {
		if (size == cap) {
			Tuple[] temp = new Tuple[(cap*2)];
			for (int i = 0; i < cap; i++ ) {
				temp[i] = list[i];
			}
			list = temp;
			cap <<= 1;
		}
		list[size] = new Tuple(player);
		size++;
	}
	
	public static boolean isParticipant(Player player) {
		for (Tuple p:list) {
			if (p.getPlayer() == player)
				return true;
		}
		return false;
	}
	
	public static void newBlocks() {
		found = 0;
		for (int i = 0; i < size; i++) {
			int c = rand.nextInt(752);
			Material target = BlockList.list[c];
			list[i].setStatus(false);			
			list[i].setMaterial(target);
			list[i].getPlayer().sendMessage("Your new Target Block is: " + target.toString());
		}
	}
	
	public static Tuple findTuple(Player player) {
		for (int i = 0; i < size; i++) {
			if ( list[i].getPlayer() == player) {
				return list[i];
			}
		}
		return null;
	}
	
	
}
