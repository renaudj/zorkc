package me.renaudj.zork;

import java.util.Scanner;

public class Zork{
  
  public boolean running = false;
  public CommandHandler commandHandler;
  public Container inventory;
  public Room currentRoom;

    public Room room1 = new Room("Room1", Lang.room1);
    public Room room2 = new Room("Room2", Lang.room2);
  
  public Zork(){
	  commandHandler = new CommandHandler(this);
	  commandHandler.register("quit", new Command(){

		public boolean onCommand(String command, String[] args) {
			running = false;
			System.out.println("Goodbye!");
			return true;
		}
		  
	  });
      commandHandler.register("go", new Command(){

          public boolean onCommand(String command, String[] args) {
              if(args.length == 1){
                  Direction d = Direction.valueOf(args[0].toUpperCase());
                  goToRoom(currentRoom.getExit(d));
                  return true;
              } else {
                  System.out.println("I don't understand.. do you mean \"go north\"?");
              }
              return false;
          }
      });
      room1.addExit(Direction.UP, room2);
      room2.addExit(Direction.DOWN, room1);
    start();
    
  }

    public void goToRoom(Room room){
       currentRoom = room;
        System.out.println(room.getDescription());
    }
  
  public void start(){
    
    System.out.println(Lang.intro);
    running = true;
    Scanner userInput = new Scanner(System.in);
      goToRoom(room1);
    while(running){
      if(userInput.hasNextLine()){
    	  commandHandler.handle(userInput.nextLine());
      }
    }
  }
  
  public static void main(String[] args){
    
    new Zork();
    
  }
}