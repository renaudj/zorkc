package me.renaudj.zork;

import java.util.HashMap;

public class CommandHandler {
    private Zork game;
    private HashMap<String, Command> commands;

    public CommandHandler(Zork zork) {
        commands = new HashMap<String, Command>();
        this.game = zork;
    }

    public void register(String command, Command cmd) {
        commands.put(command, cmd);
    }

    public boolean handle(String line) {
        String[] split = line.split(" ");
        String cmd = split[0];
        String[] args = new String[split.length - 1];
        for (int i = 1; i < split.length; i++) {
            args[i - 1] = split[i];
        }
        if (commands.containsKey(cmd)) {
            Command c = commands.get(cmd);
            return c.onCommand(cmd, args);
        } else {
            System.out.println("Unknown command: " + cmd + "!");
            return false;
        }
    }

}
