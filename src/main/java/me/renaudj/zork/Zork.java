package me.renaudj.zork;

import java.util.Scanner;

public class Zork{
  
  public boolean running = false;
  public CommandHandler commandHandler;
  
  public Zork(){
	  commandHandler = new CommandHandler(this);
    start();
    
  }
  
  public void start(){
    
    System.out.println(Lang.intro);
    running = true;
    Scanner userInput = new Scanner(System.in);
    while(running){
      if(userInput.hasNext()){
    	  commandHandler.handle(userInput.next());
      }
    }
  }
  
  public static void main(String[] args){
    
    new Zork();
    
  }
}