package serversystem.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import serversystem.config.Config;

public class ServerGroup {
	
	private final String name;
	private int priority;
	private ChatColor color;
	private String prefix;
	private ServerGroup parent;
	private Team team;
	private final List<String> permissions;
	
	private static List<ServerGroup> groups = Config.loadGroups();
	
	public ServerGroup(String name) {
		this.name = name;
		this.priority = 99;
		this.color = ChatColor.WHITE;
		this.prefix = "";
		this.permissions = new ArrayList<>();
		update();
	}
	
	public ServerGroup(String name, int priority, ChatColor color, String prefix, List<String> permissions) {
		this(name, priority, color, prefix, permissions, true);
	}
	
	public ServerGroup(String name, int priority, ChatColor color, String prefix, List<String> permissions, boolean update) {
		this.name = name;
		this.priority = priority > 99 ? 99 : priority;
		this.color = color;
		this.prefix = prefix != null ? prefix : "";
		this.permissions = permissions;
		if (update) update();
	}
	
	public void update() {
		String teamName = Integer.toString(priority);
		if (teamName.length() == 1) teamName = "0" + teamName;
		teamName += name;
		if (team != null) {
			final Set<String> entries = team.getEntries();
			TeamUtil.removeTeam(team);
			team = TeamUtil.createTeam(teamName, prefix, color);
			for (String entry : entries) {
				team.addEntry(entry);
				final Player player = Bukkit.getPlayer(entry);
				if (player != null) PermissionUtil.loadPlayerPermissions(player);
			}
		} else {
			team = TeamUtil.createTeam(teamName, prefix, color);
		}
		Config.saveGroup(this);
	}
	
	public String getName() {
		return name;
	}
	
	public void setPriority(int priority) {
		this.priority = priority > 99 ? 99 : priority;
	}
	
	public int getPriority() {
		return priority;
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
	
	public Team getTeam() {
		if (TeamUtil.getTeam(team.getName()) != null) return team;
		else return null;
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
