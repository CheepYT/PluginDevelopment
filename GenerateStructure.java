package com.cheep_yt.Coding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class GenerateStructure implements Listener {

	static List<Chunk> Generated = new ArrayList<Chunk>();

	public static int pos(int neg) {
		if (neg < 0)
			neg *= -1;

		return neg;
	}

	public static boolean Generate(World world, int x, int z) {
		int rnd = pos(x) * pos(z);
		int random = new Random(world.getSeed()).nextInt(rnd);
		return random <= rnd * 0.5;
	}

	public static Location highest(Chunk z) {
		int max = 255;

		while (z.getBlock(8, max, 8).getType() == Material.AIR || z.getBlock(8, max, 8).getType() == Material.LONG_GRASS
				|| z.getBlock(8, max, 8).getType() == Material.DOUBLE_PLANT || z.getBlock(8, max, 8).getType() == Material.SNOW)
			max--;

		return new Location(z.getWorld(), z.getBlock(8, max, 8).getX(), max + 1, z.getBlock(8, max, 8).getZ());
	}

	@EventHandler
	public static void onMove(PlayerMoveEvent e) {
		Chunk cnk = e.getPlayer().getLocation().getChunk();

		if (!Generated.contains(cnk)) {
			Generated.add(cnk);

			if (Generate(e.getPlayer().getWorld(), e.getPlayer().getLocation().getBlockX(),
					e.getPlayer().getLocation().getBlockZ())) {
				e.getPlayer().sendMessage("§3§lGenerated");

				generateStruct(highest(e.getPlayer().getLocation().getChunk()));
			} else {
				e.getPlayer().sendMessage("§4§lNot Generated");
			}
		}
	}

	public static Location addLoc(Location loc, int x, int y, int z) {
		x += loc.getBlockX();
		y += loc.getBlockY();
		z += loc.getBlockZ();

		return new Location(loc.getWorld(), x, y, z);
	}

	public static void generateStruct(Location loc) {
		loc.getBlock().setType(Material.WOOD);
		addLoc(loc, 0, 0, 1).getBlock().setType(Material.WOOD);
		addLoc(loc, 1, 0, 1).getBlock().setType(Material.WOOD);
		addLoc(loc, 1, 0, 0).getBlock().setType(Material.WOOD);

		addLoc(loc, 1, 0, -1).getBlock().setType(Material.WOOD);
		addLoc(loc, -1, 0, 1).getBlock().setType(Material.WOOD);

		addLoc(loc, 0, 0, -1).getBlock().setType(Material.WOOD);
		addLoc(loc, -1, 0, -1).getBlock().setType(Material.WOOD);
		addLoc(loc, -1, 0, 0).getBlock().setType(Material.WOOD);
	}

}
