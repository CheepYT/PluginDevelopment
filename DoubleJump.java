package com.cheep_yt.Coding;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

/**
 * Copyright 2020 © Cheep-YT All rights reserved.
 * 
 * Cheep-YT.com
 * 
 * Project Name: DoubleJump
 * 
 * Referenced Library: spigot-1.8.8-R0.1-SNAPSHOT-latest.jar
 * 
 * Class: com.cheep_yt.Coding.DoubleJump
 * 
 */
public class DoubleJump implements Listener {

	static String permission = "cheep.action.doublejump";
	
	@EventHandler
	public static void onJoin(PlayerJoinEvent e) {
		if(e.getPlayer().getGameMode() == GameMode.SURVIVAL && e.getPlayer().hasPermission(permission))
			e.getPlayer().setAllowFlight(true);
	}
	
	@EventHandler
	public static void onDoubleJump(PlayerToggleFlightEvent e) {
		Player p = e.getPlayer();
		
		if(p.getGameMode() == GameMode.SURVIVAL && p.hasPermission(permission)) {
			if(e.isFlying()) {
				if(p.getLocation().subtract(0, 2, 0).getBlock().getType() != Material.AIR)
					p.setVelocity(p.getLocation().getDirection().multiply(1).setY(1));
				
				e.setCancelled(true);
			}
		}
	}
}
