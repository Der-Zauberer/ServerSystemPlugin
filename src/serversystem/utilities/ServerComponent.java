package serversystem.utilities;

public abstract class ServerComponent {
	
	private final String name;
	
	public ServerComponent(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract void update();
	
	public abstract void remove();

}
