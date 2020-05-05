package com.cheep_yt.Coding;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Copyright 2020 © Cheep-YT All rights reserved.
 * 
 * Cheep-YT.com
 * 
 * Project Name: Timber
 * 
 * Referenced Library: spigot-1.8.8-R0.1-SNAPSHOT-latest.jar
 * 
 * Class: com.cheep_yt.Coding.Timber
 * 
 */
public class Timber implements Listener {

	@EventHandler
	public static void onBlockBreak(BlockBreakEvent e) {
		if(!e.getPlayer().isSneaking())
			breakBlock(e.getBlock());
	}
	
	static void breakBlock(Block b) {
		if(b.getType() != Material.LOG && b.getType() != Material.LOG_2)
			return;
		
		b.getWorld().playSound(b.getLocation(), Sound.DIG_WOOD, 20, 1);
		
		b.breakNaturally();

		breakBlock(b.getLocation().add(0, 1, 0).getBlock());
		breakBlock(b.getLocation().add(1, 0, 0).getBlock());
		breakBlock(b.getLocation().add(0, 0, 1).getBlock());

		breakBlock(b.getLocation().subtract(0, 1, 0).getBlock());
		breakBlock(b.getLocation().subtract(1, 0, 0).getBlock());
		breakBlock(b.getLocation().subtract(0, 0, 1).getBlock());
	}
}
