package com.cheep_yt.Coding;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Copyright 2020 © Cheep-YT All rights reserved.
 * 
 * Cheep-YT.com
 * 
 * Project Name: Lobby Vanish
 * 
 * Referenced Library: spigot-1.8.8-R0.1-SNAPSHOT-latest.jar
 * 
 * Class: com.cheep_yt.Coding.LobbyVanish
 * 
 */
public class LobbyVanish implements Listener {

	static ItemStack[] vanisher = new ItemStack[3];

	final static String permissionVanish = "cheep.action.lobby.vanish";
	final static String permissionVIP = "cheep.rank.vip";

	static HashMap<Player, Integer> Vanish = new HashMap<Player, Integer>();

	public LobbyVanish() {
		vanisher[0] = CustomStack(Material.INK_SACK, 1, 10, "§2§lAll Players Visible");
		vanisher[1] = CustomStack(Material.INK_SACK, 1, 5, "§5§lOnly VIPs Visible");
		vanisher[2] = CustomStack(Material.INK_SACK, 1, 8, "§7§lNo Players Visible");
	}

	public static ItemStack CustomStack(Material material, int amount, int metatype, String displayname) {
		ItemStack stack = new ItemStack(material, amount, (byte) metatype);

		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(displayname);
		stack.setItemMeta(meta);

		return stack;
	}

	public static void ShowPlayers(Player p) {
		if (Vanish.containsKey(p))
			if (Vanish.get(p) == 0)
				for (Player t : Bukkit.getOnlinePlayers())
					p.showPlayer(t);
			else
				for (Player t : Bukkit.getOnlinePlayers())
					if (!t.hasPermission(permissionVIP))
						p.showPlayer(t);

		if (Vanish.containsKey(p))
			Vanish.remove(p);
	}

	static void HidePlayers(Player p, int type) {
		if (type == 0)
			for (Player t : Bukkit.getOnlinePlayers())
				p.hidePlayer(t);
		else
			for (Player t : Bukkit.getOnlinePlayers())
				if (!t.hasPermission(permissionVIP))
					p.hidePlayer(t);

		Vanish.put(p, type);
	}

	@EventHandler
	public static void onJoin(PlayerJoinEvent e) {
		if (e.getPlayer().hasPermission(permissionVanish))
			e.getPlayer().getInventory().setItem(4, vanisher[0]);

		for (Player p : Vanish.keySet())
			if (Vanish.get(p) == 0)
				p.hidePlayer(e.getPlayer());
			else if (!e.getPlayer().hasPermission(permissionVIP))
				p.hidePlayer(e.getPlayer());
	}

	@EventHandler
	public static void onQuit(PlayerQuitEvent e) {
		ShowPlayers(e.getPlayer());
	}

	@EventHandler
	public static void onInteract(PlayerInteractEvent e) {
		if (Arrays.asList(vanisher).contains(e.getItem())) {
			e.setCancelled(true);

			switch (Arrays.asList(vanisher).indexOf(e.getItem())) {
			case 0:
				HidePlayers(e.getPlayer(), 1);
				e.getPlayer().getInventory().setItem(4, vanisher[1]);
				break;

			case 1:
				HidePlayers(e.getPlayer(), 0);
				e.getPlayer().getInventory().setItem(4, vanisher[2]);
				break;

			case 2:
				ShowPlayers(e.getPlayer());
				e.getPlayer().getInventory().setItem(4, vanisher[0]);
				break;
			}
		}
	}
}
