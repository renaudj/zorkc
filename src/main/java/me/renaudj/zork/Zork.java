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