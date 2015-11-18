package me.renaudj.zork.room;

public enum Direction {
	NORTH, SOUTH, EAST, WEST, UP, DOWN;

	public static boolean exists(String direction) {
		for (Direction d : Direction.values()) {
			if (d.toString().equalsIgnoreCase(direction)) {
				return true;
			}
		}
		return false;
	}
}
