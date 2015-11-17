package me.renaudj.zork;

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
        addItemsToRooms();
        start();
    }

    public static void main(String[] args) {

        new Zork();

    }

    public void goToRoom(Room room) {
        player.setCurrentRoom(room);
        player.setCurrentView(room);
        System.out.println(room.getDescription());
        if(room.hasItems()){
            System.out.print("You see");
            for(Item i : room.getItems()){
                String o = ", a " + i.getName();
                System.out.print(o.substring(1));
            }
            System.out.println();
        } else
            System.out.println("There's nothing intersting here.");
    }

    public void start() {

        System.out.println(Lang.intro);
        running = true;
        Scanner userInput = new Scanner(System.in);
        goToRoom(room1);
        while (running) {
            if (userInput.hasNextLine()) {
                commandHandler.handle(userInput.nextLine());
            }
        }
    }

    public void registerCommands() {
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
                    if(Direction.exists(args[0])){
                    Direction d = Direction.valueOf(args[0].toUpperCase());
                        if(player.getCurrentRoom().hasExit(d))
                    goToRoom(player.getCurrentRoom().getExit(d));
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

        commandHandler.register("take", new Command() {

            public boolean onCommand(String command, String[] args) {
                if (args.length > 0) {
                    if(player.getCurrentView() instanceof Room){
                        String comm = "";
                        for(int i = 0; i < args.length; i++){
                            comm += args[i] + " ";
                        }
                        comm = comm.trim();
                        for(Item item : ((Room) player.getCurrentView()).getItems()){
                            if(item.getName().toLowerCase().equals(comm.toLowerCase())){
                                if(item.getWeight() == -1){
                                    System.out.println("You can't take that!");
                                    return false;
                                }
                                player.getInventory().add(item);
                                System.out.println("Took " + item.getName());
                                ((Room) player.getCurrentView()).removeItem(item);
                                return true;
                            }
                        }
                    } else if(player.getCurrentView() instanceof Container){
                        String comm = "";
                        for(int i = 0; i < args.length; i++){
                            comm += args[i] + " ";
                        }
                        comm = comm.trim();
                        for(Item item : ((Container) player.getCurrentView()).getItems()){

                            if(item.getName().toLowerCase().equals(comm.toLowerCase())){
                                if(item.getWeight() == -1){
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
                    for(Item i : player.getCurrentRoom().getItems()){
                        if(i instanceof Container){
                            containers.add((Container)i);
                        }

                        for(Container cont : containers){
                            if(cont.getName().toLowerCase().equals(args[0].toLowerCase())){
                                player.setCurrentView(cont);
                                System.out.print("You see");
                                for(Item it : cont.getItems()){
                                    String o = ", a " + it.getName();
                                    System.out.print(o.substring(1));
                                }
                                System.out.println();
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
                if(player.getCurrentView() instanceof Container){
                    player.setCurrentView(player.getCurrentRoom());
                    System.out.println("You closed the container.");
                }
                return false;
            }
        });

        commandHandler.register("inventory", new Command() {

            public boolean onCommand(String command, String[] args) {
                System.out.print("You have");
                for(Item it : player.getInventory()){
                    String o = ", a " + it.getName();
                    System.out.print(o.substring(1));
                }
                System.out.println();
                return false;
            }
        });
    }

    public void setRoomExits() {
        room1.addExit(Direction.UP, room2);
        room2.addExit(Direction.DOWN, room1);
    }

    public void addItemsToRooms() {
        Weapon sword = new Weapon("Divining Sword", 1000, 20, "Sword of the gods.", 10000, 20);
        List<Item> items = new ArrayList<Item>();
        items.add(sword);
        Container chest = new Container("Chest", items);
        room2.addItem(chest);
    }
}