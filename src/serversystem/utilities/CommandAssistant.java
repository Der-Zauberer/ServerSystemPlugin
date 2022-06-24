package serversystem.utilities;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import serversystem.commands.VanishCommand;

public interface CommandAssistant {
	
	default boolean getLayer(int layer, String args[]) {
		return args.length == layer && (layer < 2 || !args[layer - 2].isEmpty());
	}
	
	default List<String> getPlayerList(CommandSender sender) {
		return Bukkit.getOnlinePlayers().stream().filter(everyPlayer -> !(sender instanceof Player) || sender == everyPlayer || !VanishCommand.isVanished(everyPlayer)).map(player -> player.getName()).collect(Collectors.toList());
	}
	
	default <T extends Enum<?>> List<String> getEnumList(T t[]) {
		return Arrays.stream(t).map(value -> value.name().toLowerCase()).collect(Collectors.toList());
	}
	
	default <T> List<String> getList(List<T> list, Function<T, String> map) {
		return list.stream().map(map).collect(Collectors.toList());
	}
	
	default List<String> removeWrong(List<String> list, String args[]) {
		list.removeIf(commandString -> !commandString.startsWith(args[args.length - 1]));
		return list;
	}

}
