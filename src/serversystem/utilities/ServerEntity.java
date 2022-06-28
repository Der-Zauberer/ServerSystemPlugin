package serversystem.utilities;

public abstract class ServerEntity {
	
	private final String name;
	
	public ServerEntity(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract void update();
	
	public abstract void remove();

}
