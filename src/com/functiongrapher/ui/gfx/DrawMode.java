package com.functiongrapher.ui.gfx;

public enum DrawMode {

	LINES("Lines only"),
	LINES_WITH_FILL("Lines with fill"),
	FILL("Fill only"),
	TRIANGLES("Triangle mesh only"),
	TRIANGLES_WITH_FILL("Triangle mesh with fill");

	private final String description;

	private DrawMode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return description;
	}

}
