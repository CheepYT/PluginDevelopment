package com.cheep_yt.Coding;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

/**
 * Copyright 2020 © Cheep-YT All rights reserved.
 * 
 * Cheep-YT.com
 * 
 * Project Name: JoinSigns
 * 
 * Referenced Library: spigot-1.8.8-R0.1-SNAPSHOT-latest.jar
 * 
 * Class: com.cheep_yt.Coding.JoinSignManager
 * 
 */
public class JoinSignManager implements Listener, PluginMessageListener {

	static List<String> SignServersServer = new ArrayList<String>();
	static List<Sign> SignServersSigns = new ArrayList<Sign>();
	static List<String> SignServersMaxPlayers = new ArrayList<String>();

	static String permission = "cheep.action.joinsign.create";

	static JavaPlugin plugin;

	public JoinSignManager(JavaPlugin pl) {
		plugin = pl;

		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
		plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {
				for (String server : SignServersServer) {
					ByteArrayDataOutput out = ByteStreams.newDataOutput();
					out.writeUTF("PlayerCount");
					out.writeUTF(server);
					plugin.getServer().sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
				}
			}
		}, 50, 50);
	}

	@Override
	public void onPluginMessageReceived(String channel, Player p, byte[] msg) {
		if (!channel.equals("BungeeCord"))
			return;

		ByteArrayDataInput in = ByteStreams.newDataInput(msg);
		String subchannel = in.readUTF();

		if (subchannel.equals("PlayerCount")) {
			String server = in.readUTF();
			int playercount = in.readInt();

			if (SignServersServer.contains(server)) {
				Sign sign = SignServersSigns.get(SignServersServer.indexOf(server));

				sign.setLine(2,
						"§3" + playercount + "§f/§3" + SignServersMaxPlayers.get(SignServersServer.indexOf(server)));
				sign.update();
			}
		}
	}

	@EventHandler
	public static void onSignPlace(SignChangeEvent e) {
		if (e.getPlayer().hasPermission(permission)) {
			if (e.getLine(0).equalsIgnoreCase("[joinsign]")) {
				String server = e.getLine(1);
				String maxCount = e.getLine(2);

				Sign sign = (Sign) e.getBlock().getState();

				sign.setLine(0, "§3§l[§2§lJoinSigns§3§l]");
				sign.setLine(1, "§5" + server);
				sign.setLine(2, "§30§f/§3" + maxCount);
				sign.setLine(3, e.getLine(3).replace("&", "§"));

				sign.update();

				SignServersServer.add(server);
				SignServersSigns.add(sign);
				SignServersMaxPlayers.add(maxCount);
			}
		}
	}

	@EventHandler
	public static void onInteract(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null)
			return;

		if (e.getClickedBlock().getState() instanceof Sign) {
			Sign sign = (Sign) e.getClickedBlock().getState();
			if (!SignServersSigns.contains(sign))
				return;

			String server = SignServersServer.get(SignServersSigns.indexOf(sign));

			e.getPlayer().sendMessage("§3§l[§2Cheep§3§l] §aConnecting to §6" + server + "§a...");

			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("Connect");
			out.writeUTF(server);
			e.getPlayer().sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
		}
	}

}
