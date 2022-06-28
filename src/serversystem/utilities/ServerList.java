package serversystem.utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ServerList<Component extends ServerComponent> implements Iterable<Component> {
	
	private final ArrayList<Component> components = new ArrayList<>();
	
	public ServerList(Consumer<ServerList<Component>> loadAction) {
		if (loadAction != null) loadAction.accept(this);
	}
	
	public boolean add(Component component) {
		if (!contains(component.getName())) {
			component.update();
			components.add(component);
			return true;
		}
		return false;
	}
	
	public boolean remove(Component component) {
		if (contains(component)) {
			component.remove();
			components.remove(component);
			return true;
		}
		return false;
	}
	
	public boolean remove(String name) {
		ServerComponent component = get(name);
		if (component != null) {
			components.remove(component);
			return true;
		}
		return false;
	}
	
	public Component get(String name) {
		return stream().filter(component -> component.getName().equals(name)).findFirst().orElse(null);
	}

	public boolean contains(Component component) {
		return component != null && get(component.getName()) != null;
	}
	
	public boolean contains(String name) {
		return stream().anyMatch(component -> component.getName().equals(name));
	}
	
	public int size() {
		return components.size();
	}
	
	public void clear() {
		components.clear();
	}
	
	public Stream<Component> stream() {
		return components.stream();
	}
	
	@Override
	public Iterator<Component> iterator() {
		return components.iterator();
	}

}
