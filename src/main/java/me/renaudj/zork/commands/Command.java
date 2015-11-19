package me.renaudj.zork.commands;

public interface Command {
    public boolean onCommand(String command, String[] args);
}
