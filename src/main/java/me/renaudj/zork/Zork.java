package me.renaudj.zork;

import me.renaudj.zork.commands.Command;
import me.renaudj.zork.commands.CommandHandler;
import me.renaudj.zork.entity.Enemy;
import me.renaudj.zork.entity.Player;
import me.renaudj.zork.events.EventExecutor;
import me.renaudj.zork.events.PlayerListener;
import me.renaudj.zork.items.Container;
import me.renaudj.zork.items.InventorySlotType;
import me.renaudj.zork.items.Item;
import me.renaudj.zork.items.Weapon;
import me.renaudj.zork.room.Direction;
import me.renaudj.zork.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Zork {
    private static Zork instance;
    //Create rooms here
    public final Room room0 = new Room("Room0", Lang.r0);
    public final Room room1a = new Room("Room1a", Lang.r1a);
    public final Room room1b = new Room("Room1b", Lang.r1b);
    public final Room room2a = new Room("Room2a", Lang.r2a);
    public final Room room2b = new Room("Room2b", Lang.r2b);
    public final Room room3a = new Room("Room3a", Lang.r3a);
    public final Room room3b = new Room("Room3b", Lang.r3b);
    public final Room room1c = new Room("Room1c", Lang.r1c);
    public final Room room2c = new Room("Room2c", Lang.r2c);
    public final Room room3c = new Room("Room3c", Lang.r3c);
    public final Room room1d = new Room("Room1d", Lang.r1d);
    public final Room room2d = new Room("Room2d", Lang.r2d);
    public final Room room3d = new Room("Room3d", Lang.r3d);
    public final Room room1e = new Room("Room1e", Lang.r1e);
    public final Room room2e = new Room("Room2e", Lang.r2e);
    public final Room room3e = new Room("Room3e", Lang.r3e);
    public final Room room4a = new Room("Room4a", Lang.r4a);
    public final Room room4b = new Room("Room4b", Lang.r4b);
    public final Room room4c = new Room("Room4c", Lang.r4c);
    public final Room room4d = new Room("Room4d", Lang.r4d);
    public final Room room4e = new Room("Room4e", Lang.r4e);
    public final Room room5a = new Room("Room5a", Lang.r5a);
    public final Room room5b = new Room("Room5b", Lang.r5b);
    public final Room room5c = new Room("Room5c", Lang.r5c);
    public final Room room5d = new Room("Room5d", Lang.r5d);
    public final Room room5e = new Room("Room5e", Lang.r5e);
    public final Room room6a = new Room("Room6a", Lang.r6a);
    public final Room room6b = new Room("Room6b", Lang.r6b);
    public final Room room6c = new Room("Room6c", Lang.r6c);
    public final Room room6d = new Room("Room6d", Lang.r6d);
    public final Room room6e = new Room("Room6e", Lang.r6e);
    public final Room room7a = new Room("Room7a", Lang.r7a);
    public final Room room7b = new Room("Room7b", Lang.r7b);
    public final Room room7c = new Room("Room7c", Lang.r7c);
    public final Room room7d = new Room("Room7d", Lang.r7d);
    public final Room room7e = new Room("Room7e", Lang.r7e);
    public final Room room8a = new Room("Room8a", Lang.r8a);
    public final Room room8b = new Room("Room8b", Lang.r8b);
    public final Room room8c = new Room("Room8c", Lang.r8c);
    public final Room room8d = new Room("Room8d", Lang.r8d);
    public final Room room8e = new Room("Room8e", Lang.r8e);
    public final Room room9a = new Room("Room9a", Lang.r9a);
    public final Room room9b = new Room("Room9b", Lang.r9b);
    public final Room room9c = new Room("Room9c", Lang.r9c);
    public final Room room9d = new Room("Room9d", Lang.r9d);
    public final Room room9e = new Room("Room9e", Lang.r9e);
    public boolean running = false;
    public CommandHandler commandHandler;
    private Player player;
    private EventExecutor events;
    public Zork() {
        instance = this;
        commandHandler = new CommandHandler(this);
        events = new EventExecutor();
        player = new Player();
        events.registerListener(new PlayerListener());
        registerCommands();
        setRoomExits();
        populateRooms();
        start();
    }

    public static Zork getInstance() {
        return instance;
    }

    public static void main(String[] args) {

        new Zork();

    }

    public EventExecutor getEventExecutor() {
        return this.events;
    }

    public void start() {

        System.out.println(Lang.intro);
        running = true;
        Scanner userInput = new Scanner(System.in);
        player.goToRoom(room0);
        while (running) {
            if (userInput.hasNextLine()) {
                commandHandler.handle(userInput.nextLine());
            }
        }
    }

    public void registerCommands() {
        commandHandler.register("attack", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (args.length > 0) {
                    String name = "";
                    for(String s : args){
                        name += " " + s;
                    }
                    name = name.trim();
                    if (player.getCurrentRoom().hasCharacter(name)) {
                        player.attack(player.getCurrentRoom().getCharacter(name));
                    }
                }
                return true;
            }

        });
        commandHandler.register("use", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (args.length == 1) {
                    if (player.getInventory().hasItem(args[0])) {
                        Item item = player.getInventory().getItem(args[0]);
                        item.use(player);
                    }
                } else if (args.length == 2) {
                    if (player.getInventory().hasItem(args[0])) {
                        Item item = player.getInventory().getItem(args[0]);
                        item.use(player);
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
        commandHandler.register("unequip", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (args.length == 1) {
                    for (InventorySlotType slot : InventorySlotType.values()) {
                        if (args[0].equalsIgnoreCase(slot.name())) {
                            player.getInventory().unequip(slot);
                            System.out.println("Unequipped " + slot.name().toLowerCase().replace("_", " "));
                        }
                    }
                } else {
                    for (InventorySlotType slot : InventorySlotType.values()) {
                        if (!slot.equals(InventorySlotType.INVENTORY)) {
                            player.getInventory().unequip(slot);
                        }
                    }
                    System.out.println("Unequipped all equippable slots.");
                }
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
                    String slt = args[args.length - 1];
                    String item = "";
                    InventorySlotType slot2 = null;
                    boolean worked = false;
                    for (InventorySlotType slot : InventorySlotType.values()) {
                        if (slot.name().equalsIgnoreCase(slt) || slot.isAlias(slt)) {
                            if (slot.equals(InventorySlotType.INVENTORY)) {
                                System.out.println("Please choose an equippable slot!");
                                break;
                            }
                            slot2 = slot;
                            worked = true;
                            break;
                        }
                    }
                    if (!worked) {
                        System.out.println("Please enter a valid slot!");
                        return true;
                    }
                    for (int i = 0; i < args.length - 1; i++) {
                        item += " " + args[i];
                    }
                    item = item.trim();
                    if (!player.getInventory().hasItem(item)) {
                        System.out.println("You do not have a " + item);
                        return false;
                    }
                    Item item1 = player.getInventory().getItem(item);
                    player.getInventory().equip(slot2, item1);
                    System.out.println("Equipped " + item1.getName() + " to " + slot2.name().toLowerCase().replace("_", " "));
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
                                player.getInventory().addItem(item);
                                System.out.println("Took " + item.getName());
                                ((Room) player.getCurrentView()).removeItem(item);
                                return true;
                            } else continue;
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
                                player.getInventory().addItem(item);
                                Item i = item;
                                ((Container) player.getCurrentView()).removeItem(item);
                                System.out.println("Took " + i.getName());
                                return true;
                            } else continue;
                        }
                        System.out.println("There isn't a " + comm + " in this " + ((Container) player.getCurrentView()).getName() + "!");
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
                                if (cont.getItems().size() > 0) {
                                    System.out.print("You see");
                                String o = "";
                                for (Item it : cont.getItems()) {
                                    o += ", a " + it.getName();
                                }
                                System.out.println(o.substring(1));
                                } else {
                                    System.out.println("This " + cont.getName() + " is empty.");
                                }
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
                    System.out.println("You closed the " + ((Container) player.getCurrentView()).getName() + ".");
                    player.setCurrentView(player.getCurrentRoom());
                }
                return false;
            }
        });
        commandHandler.register("inventory", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (player.getInventory().getFullInventory().size() > 0) {
                    System.out.print("You have");
                    String o = "";
                    for (Item it : player.getInventory().getFullInventory()) {
                        o += ", a " + it.getName();
                    }
                    System.out.println(o.substring(1));
                    return true;
                }
                return false;
            }
        });
    }

    //Assign neighboring rooms to the exits in each direction desired
    public void setRoomExits() {
        room0.addExit(Direction.UP, room1a);

        room1a.addExit(Direction.DOWN, room0);
        room1a.addExit(Direction.EAST, room2a);
        room1a.addExit(Direction.SOUTH, room1b);

        room2a.addExit(Direction.SOUTH, room2b);
        room2a.addExit(Direction.EAST, room3a);
        room2a.addExit(Direction.WEST, room1a);

        room3a.addExit(Direction.WEST, room2a);
        room3a.addExit(Direction.SOUTH, room3b);
        room3a.addExit(Direction.EAST, room4a);

        room4a.addExit(Direction.WEST, room3a);
        room4a.addExit(Direction.SOUTH, room4b);
        room4a.addExit(Direction.EAST, room5a);

        room5a.addExit(Direction.WEST, room4a);
        room5a.addExit(Direction.SOUTH, room5b);
        room5a.addExit(Direction.EAST, room6a);

        room6a.addExit(Direction.WEST, room5a);
        room6a.addExit(Direction.SOUTH, room6b);
        room6a.addExit(Direction.EAST, room7a);

        room7a.addExit(Direction.WEST, room6a);
        room7a.addExit(Direction.SOUTH, room7b);
        room7a.addExit(Direction.EAST, room8a);

        room8a.addExit(Direction.WEST, room7a);
        room8a.addExit(Direction.SOUTH, room8b);
        room8a.addExit(Direction.EAST, room9a);

        room9a.addExit(Direction.WEST, room8a);
        room9a.addExit(Direction.SOUTH, room9b);

        room1b.addExit(Direction.NORTH, room1a);
        room1b.addExit(Direction.EAST, room2b);
        room1b.addExit(Direction.SOUTH, room1c);

        room2b.addExit(Direction.WEST, room1b);
        room2b.addExit(Direction.NORTH, room2a);
        room2b.addExit(Direction.EAST, room3b);
        room2b.addExit(Direction.SOUTH, room2c);

        room3b.addExit(Direction.WEST, room2b);
        room3b.addExit(Direction.NORTH, room3a);
        room3b.addExit(Direction.EAST, room4b);
        room3b.addExit(Direction.SOUTH, room3c);

        room4b.addExit(Direction.WEST, room3b);
        room4b.addExit(Direction.NORTH, room4a);
        room4b.addExit(Direction.EAST, room5b);
        room4b.addExit(Direction.SOUTH, room4c);

        room5b.addExit(Direction.WEST, room4b);
        room5b.addExit(Direction.NORTH, room5a);
        room5b.addExit(Direction.EAST, room6b);
        room5b.addExit(Direction.SOUTH, room5c);

        room6b.addExit(Direction.WEST, room5b);
        room6b.addExit(Direction.NORTH, room6a);
        room6b.addExit(Direction.EAST, room7b);
        room6b.addExit(Direction.SOUTH, room6c);

        room7b.addExit(Direction.WEST, room6b);
        room7b.addExit(Direction.NORTH, room7a);
        room7b.addExit(Direction.EAST, room8b);
        room7b.addExit(Direction.SOUTH, room7c);

        room8b.addExit(Direction.WEST, room7b);
        room8b.addExit(Direction.NORTH, room8a);
        room8b.addExit(Direction.EAST, room9b);
        room8b.addExit(Direction.SOUTH, room8c);

        room9b.addExit(Direction.WEST, room8b);
        room9b.addExit(Direction.NORTH, room9a);
        room9b.addExit(Direction.SOUTH, room9c);

        room1c.addExit(Direction.NORTH, room1b);
        room1c.addExit(Direction.EAST, room2c);
        room1c.addExit(Direction.SOUTH, room1d);

        room2c.addExit(Direction.WEST, room1c);
        room2c.addExit(Direction.NORTH, room2b);
        room2c.addExit(Direction.EAST, room3c);
        room2c.addExit(Direction.SOUTH, room2d);

        room3c.addExit(Direction.WEST, room2c);
        room3c.addExit(Direction.NORTH, room3b);
        room3c.addExit(Direction.EAST, room4c);
        room3c.addExit(Direction.SOUTH, room3d);

        room4c.addExit(Direction.WEST, room3c);
        room4c.addExit(Direction.NORTH, room4b);
        room4c.addExit(Direction.EAST, room5c);
        room4c.addExit(Direction.SOUTH, room4d);

        room5c.addExit(Direction.WEST, room4c);
        room5c.addExit(Direction.NORTH, room5b);
        room5c.addExit(Direction.EAST, room6c);
        room5c.addExit(Direction.SOUTH, room5d);

        room6c.addExit(Direction.WEST, room5c);
        room6c.addExit(Direction.NORTH, room6b);
        room6c.addExit(Direction.EAST, room7c);
        room6c.addExit(Direction.SOUTH, room6d);

        room7c.addExit(Direction.WEST, room6c);
        room7c.addExit(Direction.NORTH, room7b);
        room7c.addExit(Direction.EAST, room8c);
        room7c.addExit(Direction.SOUTH, room7d);

        room8c.addExit(Direction.WEST, room7c);
        room8c.addExit(Direction.NORTH, room8b);
        room8c.addExit(Direction.EAST, room9c);
        room8c.addExit(Direction.SOUTH, room8d);

        room9c.addExit(Direction.WEST, room8c);
        room9c.addExit(Direction.NORTH, room9b);
        room9c.addExit(Direction.SOUTH, room9d);

        room1d.addExit(Direction.NORTH, room1c);
        room1d.addExit(Direction.EAST, room2d);
        room1d.addExit(Direction.SOUTH, room1e);

        room2d.addExit(Direction.WEST, room1d);
        room2d.addExit(Direction.NORTH, room2c);
        room2d.addExit(Direction.EAST, room3d);
        room2d.addExit(Direction.SOUTH, room2e);

        room3d.addExit(Direction.WEST, room2d);
        room3d.addExit(Direction.NORTH, room3c);
        room3d.addExit(Direction.EAST, room4d);
        room3d.addExit(Direction.SOUTH, room3e);

        room4d.addExit(Direction.WEST, room3d);
        room4d.addExit(Direction.NORTH, room4c);
        room4d.addExit(Direction.EAST, room5d);
        room4d.addExit(Direction.SOUTH, room4e);

        room5d.addExit(Direction.WEST, room4d);
        room5d.addExit(Direction.NORTH, room5c);
        room5d.addExit(Direction.EAST, room6d);
        room5d.addExit(Direction.SOUTH, room5e);

        room6d.addExit(Direction.WEST, room5d);
        room6d.addExit(Direction.NORTH, room6c);
        room6d.addExit(Direction.EAST, room7d);
        room6d.addExit(Direction.SOUTH, room6e);

        room7d.addExit(Direction.WEST, room6d);
        room7d.addExit(Direction.NORTH, room7c);
        room7d.addExit(Direction.EAST, room8d);
        room7d.addExit(Direction.SOUTH, room7e);

        room8d.addExit(Direction.WEST, room7d);
        room8d.addExit(Direction.NORTH, room8c);
        room8d.addExit(Direction.EAST, room9d);
        room8d.addExit(Direction.SOUTH, room8e);

        room9d.addExit(Direction.WEST, room8d);
        room9d.addExit(Direction.NORTH, room9c);
        room9d.addExit(Direction.SOUTH, room9e);

        room1e.addExit(Direction.NORTH, room1d);
        room1e.addExit(Direction.EAST, room2e);

        room2e.addExit(Direction.WEST, room1e);
        room2e.addExit(Direction.NORTH, room2d);
        room2e.addExit(Direction.EAST, room3e);

        room3e.addExit(Direction.WEST, room2e);
        room3e.addExit(Direction.NORTH, room3d);
        room3e.addExit(Direction.EAST, room4e);

        room4e.addExit(Direction.WEST, room3e);
        room4e.addExit(Direction.NORTH, room4d);
        room4e.addExit(Direction.EAST, room5e);

        room5e.addExit(Direction.WEST, room4e);
        room5e.addExit(Direction.NORTH, room5d);
        room5e.addExit(Direction.EAST, room6e);

        room6e.addExit(Direction.WEST, room5e);
        room6e.addExit(Direction.NORTH, room6d);
        room6e.addExit(Direction.EAST, room7e);

        room7e.addExit(Direction.WEST, room6e);
        room7e.addExit(Direction.NORTH, room7d);
        room7e.addExit(Direction.EAST, room8e);

        room8e.addExit(Direction.WEST, room7e);
        room8e.addExit(Direction.NORTH, room8d);
        room8e.addExit(Direction.EAST, room9e);

        room9e.addExit(Direction.WEST, room8e);
        room9e.addExit(Direction.NORTH, room9d);
    }

    public void populateRooms() {
        Weapon sword = new Weapon("Divining Sword", 1000, 20, "Sword of the gods.", 10000, 20);
        List<Item> items = new ArrayList<Item>();
        items.add(sword);
        Container chest = new Container("Chest", items);
        room1a.addItem(chest);

        Enemy oldMan = new Enemy("Old Man", 4, "[Insert Dialogue]");

        Item lockPick = new Weapon("Lock Pick", 0, 1, "Picks locks", 0, 0);
        oldMan.addDeathDrop(lockPick);
        room0.addCharacter(oldMan);
        room1a.setRequiredItem(lockPick);
    }
}