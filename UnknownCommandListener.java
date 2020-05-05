package com.cheep_yt.Coding;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class UnknownCommandListener implements Listener {

	@EventHandler
	public static void onCommand(PlayerCommandPreprocessEvent e) {
		String msg = e.getMessage();
		String[] args = msg.split(" ");
		Player p = e.getPlayer();
		
		if(Bukkit.getServer().getHelpMap().getHelpTopic(args[0]) == null) {
			e.setCancelled(true);
			
			p.sendMessage("§3§l[§2§lCheep§3§l] §cThis command does not exist.");
		}
		
	}
}
