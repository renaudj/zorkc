package me.renaudj.zork;

public interface Command {
	public boolean onCommand(String command, String[] args);
}
