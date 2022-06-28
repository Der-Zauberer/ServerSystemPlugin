package serversystem.utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ServerList<Entity extends ServerEntity> implements Iterable<Entity> {
	
	private final ArrayList<Entity> entities = new ArrayList<>();
	
	public ServerList(Consumer<ServerList<Entity>> loadAction) {
		if (loadAction != null) loadAction.accept(this);
	}
	
	public boolean add(Entity entity) {
		if (!contains(entity.getName())) {
			entity.update();
			entities.add(entity);
			return true;
		}
		return false;
	}
	
	public boolean remove(Entity entity) {
		if (contains(entity)) {
			entity.remove();
			entities.remove(entity);
			return true;
		}
		return false;
	}
	
	public boolean remove(String name) {
		ServerEntity entity = get(name);
		if (entity != null) {
			entities.remove(entity);
			return true;
		}
		return false;
	}
	
	public Entity get(String name) {
		return stream().filter(entity -> entity.getName().equals(name)).findFirst().orElse(null);
	}

	public boolean contains(Entity entity) {
		return entity != null && get(entity.getName()) != null;
	}
	
	public boolean contains(String name) {
		return stream().anyMatch(entity -> entity.getName().equals(name));
	}
	
	public int size() {
		return entities.size();
	}
	
	public void clear() {
		entities.clear();
	}
	
	public Stream<Entity> stream() {
		return entities.stream();
	}
	
	@Override
	public Iterator<Entity> iterator() {
		return entities.iterator();
	}

}
