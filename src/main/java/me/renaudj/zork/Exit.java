package me.renaudj.zork;

public class Exit {
	private Direction direction;
	private Room room;
	public Exit(Direction d, Room r){
		direction = d;
		room = r;
	}
	
	public Room getRoom(){
		return room;
	}
	
	public Direction getDirection(){
		return direction;
	}

}
