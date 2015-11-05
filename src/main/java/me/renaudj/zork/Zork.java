package me.renaudj.zork;

import java.util.Scanner;

public class Zork{
  
  public boolean running = false;
  
  public Zork(){
    start();
    
  }
  
  public void start(){
    
    System.out.println(Lang.intro);
    running = true;
    Scanner userInput = new Scanner(System.in);
    while(running){
      if(userInput.hasNext()){
      	System.out.println("You said: " + userInput.next());
      }
    }
  }
  
  public static void main(String[] args){
    
    new Zork();
    
  }
}