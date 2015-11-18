package me.renaudj.zork;

import me.renaudj.zork.commands.Command;
import me.renaudj.zork.commands.CommandHandler;
import me.renaudj.zork.entity.Character;
import me.renaudj.zork.entity.Player;
import me.renaudj.zork.items.Container;
import me.renaudj.zork.items.Item;
import me.renaudj.zork.items.Weapon;
import me.renaudj.zork.room.Direction;
import me.renaudj.zork.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Zork {

    public final Room room1 = new Room("Room1", Lang.room1);
    public final Room room2 = new Room("Room2", Lang.room2);
    public boolean running = false;
    public CommandHandler commandHandler;
    private Player player;

    public Zork() {
        commandHandler = new CommandHandler(this);
        player = new Player();
        registerCommands();
        setRoomExits();
        populateRooms();
        start();
    }

    public static void main(String[] args) {

        new Zork();

    }

    public void start() {

        System.out.println(Lang.intro);
        running = true;
        Scanner userInput = new Scanner(System.in);
        player.goToRoom(room1);
        while (running) {
            if (userInput.hasNextLine()) {
                commandHandler.handle(userInput.nextLine());
            }
        }
    }

    public void registerCommands() {
        commandHandler.register("attack", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (args.length == 1) {
                    if (player.getCurrentRoom().hasCharacter(args[0])) {
                        player.attack(player.getCurrentRoom().getCharacter(args[0]));
                    }
                }
                return true;
            }

        });
        commandHandler.register("quit", new Command() {

            public boolean onCommand(String command, String[] args) {
                running = false;
                System.out.println("Goodbye!");
                return true;
            }

        });
        commandHandler.register("go", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (args.length == 1) {
                    if (Direction.exists(args[0])) {
                        Direction d = Direction.valueOf(args[0].toUpperCase());
                        if (player.getCurrentRoom().hasExit(d))
                            player.goToRoom(player.getCurrentRoom().getExit(d));
                        else
                            System.out.println("There is no exit in that direction.");
                        return true;
                    } else {
                        System.out.println("Your choices are north, south, east, west, up, down.");
                    }
                } else {
                    System.out.println("I don't understand.. do you mean \"go north\"?");
                }
                return false;
            }
        });

        commandHandler.register("equip", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (args.length > 0) {
                    String hand = args[args.length - 1];
                    String item = "";
                    if (!hand.equalsIgnoreCase("right") && !hand.equalsIgnoreCase("left")) {
                        System.out.println("Use a right hand or left hand!");
                        return false;
                    }
                    for (int i = 0; i < args.length - 1; i++) {
                        item += " " + args[i];
                    }
                    item = item.trim();
                    if (!player.hasItem(item)) {
                        System.out.println("You do not have a " + item);
                        return false;
                    }
                    Item item1 = player.getItem(item);
                    if (hand.equalsIgnoreCase("right")) {
                        if (player.getRightHand() == null) {
                            player.setRightHand(item1);
                        } else {
                            player.addItem(player.getRightHand());
                            player.setRightHand(item1);
                        }
                    } else if (hand.equalsIgnoreCase("left")) {
                        if (player.getLeftHand() == null) {
                            player.setLeftHand(item1);
                        } else {
                            player.addItem(player.getLeftHand());
                            player.setLeftHand(item1);
                        }
                    }

                    player.removeItem(item1);
                    System.out.println("Equipped " + item1.getName() + " to " + hand + " hand.");
                }
                return true;
            }

        });

        commandHandler.register("take", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (args.length > 0) {
                    if (player.getCurrentView() instanceof Room) {
                        String comm = "";
                        for (int i = 0; i < args.length; i++) {
                            comm += args[i] + " ";
                        }
                        comm = comm.trim();
                        for (Item item : ((Room) player.getCurrentView()).getItems()) {
                            if (item.getName().toLowerCase().equals(comm.toLowerCase())) {
                                if (item.getWeight() == -1) {
                                    System.out.println("You can't take that!");
                                    return false;
                                }
                                player.getInventory().add(item);
                                System.out.println("Took " + item.getName());
                                ((Room) player.getCurrentView()).removeItem(item);
                                return true;
                            }
                        }
                        System.out.println("There isn't a " + comm + " in this room!");
                        return true;
                    } else if (player.getCurrentView() instanceof Container) {
                        String comm = "";
                        for (int i = 0; i < args.length; i++) {
                            comm += args[i] + " ";
                        }
                        comm = comm.trim();
                        for (Item item : ((Container) player.getCurrentView()).getItems()) {

                            if (item.getName().toLowerCase().equals(comm.toLowerCase())) {
                                if (item.getWeight() == -1) {
                                    System.out.println("You can't take that!");
                                    return false;
                                }
                                player.getInventory().add(item);
                                Item i = item;
                                ((Container) player.getCurrentView()).removeItem(item);
                                System.out.println("Took " + i.getName());
                                return true;
                            }
                        }
                    }
                    return true;
                } else {
                    System.out.println("I don't understand.. do you mean \"take item\"?");
                }
                return false;
            }
        });

        commandHandler.register("open", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (args.length == 1) {
                    List<Container> containers = new ArrayList<Container>();
                    for (Item i : player.getCurrentRoom().getItems()) {
                        if (i instanceof Container) {
                            containers.add((Container) i);
                        }

                        for (Container cont : containers) {
                            if (cont.getName().toLowerCase().equals(args[0].toLowerCase())) {
                                player.setCurrentView(cont);
                                System.out.print("You see");
                                String o = "";
                                for (Item it : cont.getItems()) {
                                    o += ", a " + it.getName();
                                }
                                System.out.println(o.substring(1));
                            }
                        }
                    }
                    return true;
                } else {
                    System.out.println("I don't understand.. do you mean \"take item\"?");
                }
                return false;
            }
        });

        commandHandler.register("close", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (player.getCurrentView() instanceof Container) {
                    player.setCurrentView(player.getCurrentRoom());
                    System.out.println("You closed the container.");
                }
                return false;
            }
        });

        commandHandler.register("inventory", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (player.getInventory().size() > 0) {
                System.out.print("You have");
                    String o = "";
                for (Item it : player.getInventory()) {
                    o += ", a " + it.getName();
                }
                    System.out.println(o.substring(1));
                    return true;
            }
                return false;
            }
        });
    }

    public void setRoomExits() {
        room1.addExit(Direction.UP, room2);
        room2.addExit(Direction.DOWN, room1);
    }

    public void populateRooms() {
        Weapon sword = new Weapon("Divining Sword", 1000, 20, "Sword of the gods.", 10000, 20);
        List<Item> items = new ArrayList<Item>();
        items.add(sword);
        Container chest = new Container("Chest", items);
        room2.addItem(chest);

        Character retard = new Character("Retard", 4, "CRICKEM NIGFOPS!");
        retard.setRightHand(new Item("Dildo", 0, 1, "Penetrate them all!"));
        room1.addCharacter(retard);
    }
}