package serversystem.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import serversystem.config.Config;

public class ServerGroup {
	
	private final String id;
	private final String name;
	private ChatColor color;
	private String prefix;
	private ServerGroup parent;
	private final List<String> permissions;
	
	private static List<ServerGroup> groups = Config.loadGroups();
	
	public ServerGroup(String id, String name) {
		this.id = id;
		this.name = name;
		this.color = ChatColor.WHITE;
		this.prefix = "";
		this.permissions = new ArrayList<>();
		update();
	}
	
	public ServerGroup(String id, String name, ChatColor color, String prefix, List<String> permissions) {
		this(id, name, color, prefix, permissions, true);
	}
	
	public ServerGroup(String id, String name, ChatColor color, String prefix, List<String> permissions, boolean update) {
		this.id = id;
		this.name = name;
		this.color = color;
		this.prefix = prefix != null ? prefix : "";
		this.permissions = permissions;
		if (update) update();
	}
	
	public void update() {
		if (TeamUtil.getTeam(id) != null) {
			final Set<String> entries = TeamUtil.getTeam(id).getEntries();
			TeamUtil.removeTeam(id);
			TeamUtil.createTeam(id, prefix, color);
			for (String entry : entries) {
				TeamUtil.getTeam(id).addEntry(entry);
				final Player player = Bukkit.getPlayer(entry);
				if (player != null) PermissionUtil.loadPlayerPermissions(player);
			}
		} else {
			TeamUtil.createTeam(id, prefix, color);
		}
		Config.saveGroup(this);
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setColor(ChatColor color) {
		this.color = color;
	}
	
	public ChatColor getColor() {
		return color;
	}
	
	public void setPrefix(String prefix) {
		if (prefix == null) this.prefix = "";
		else this.prefix = prefix;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public void setParent(ServerGroup parent) {
		this.parent = parent;
	}
	
	public ServerGroup getParent() {
		return parent;
	}
	
	public boolean hasParent() {
		return parent != null;
	}
	
	public void addPermission(String permission) {
		permissions.add(permission);
	}
	
	public void addAllPermissions(List<String> permissions) {
		this.permissions.addAll(permissions);
	}
	
	public void removePermission(String permission) {
		if (permissions.contains(permission)) permissions.remove(permission);
	}
	
	public boolean containsPermission(String permission) {
		return permissions.contains(permission);
	}
	
	public List<String> getPermissions() {
		List<String> permissions = new ArrayList<>();
		permissions.addAll(this.permissions);
		return permissions;
	}
	
	public List<String> getSelfAndParentPermissions() {
		List<String> permissions = new ArrayList<>();
		ServerGroup group = this;
		permissions.addAll(group.getPermissions());
		while ((group = group.getParent()) != null) permissions.addAll(group.getPermissions());
		return permissions;
	}
	
	public static List<String> getPlayerPermissions(Player player) {
		List<String> permissions = new ArrayList<>();
		final ServerGroup group = getGroup(player);
		if (group != null) permissions.addAll(group.getSelfAndParentPermissions());
		final List<String> playerPermissions = Config.getPlayerSpecificPermissions(player);
		if (playerPermissions != null) permissions.addAll(playerPermissions);
		return permissions;
	}
	
	public static void reloadAll() {
		TeamUtil.resetTeams();
		Config.reloadConfig();
		groups = Config.loadGroups();
		TeamUtil.createTeams();
		for (Player player : Bukkit.getOnlinePlayers()) {
			PermissionUtil.loadPlayerPermissions(player);
			TeamUtil.addGroupToPlayer(player);
		}
	}
	
	public static ServerGroup getGroup(Player player) {
		return getGroup(Config.getPlayerGroup(player));
	}
	
	public static ServerGroup getGroup(String name) {
		for (ServerGroup group : groups) {
			if (group.getName().equals(name)) return group;
		}
		return null;
	}
	
	public static List<ServerGroup> getGroups() {
		return groups;
	}

}
