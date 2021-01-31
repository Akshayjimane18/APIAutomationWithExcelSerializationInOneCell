package resources;

public enum APIResources {

	Addjsonplacehoder("/posts");

	private String resource;

	APIResources(String resource) {
		this.resource = resource;
	}

	public String getResources() {
		return resource;
	}
}
