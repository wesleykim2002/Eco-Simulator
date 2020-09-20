/**
 * EcoSimulator.java
 * Version 6.0
 * @11/22/2018
 * @Wesley Kim
 * Code for eco system simulator
 * in this simulator, wolves, and sheep will mate when they turn 20 days old. sheep will require a health of 20 and wolves will require a health of 100
 * baby wolves will be unable to eat adult sheep
 * in order to prevent overpopulation, sheep can only mate 5 times
 * additions: sheep and wolves can eat things around them, there is a speed setting, animals die of old age, reproducing is optional
 */

import java.util.Scanner;

class EcoSimulator{
  public static void main(String[] args) {
    Boolean extinction = false;
    Boolean mateW, mateS;
    int populationW, populationS, populationP, plantRate, plantChance, plantValue, spawnHealthW, ageW, spawnHealthS, ageS, length, width, wave, speed, year;
    String response; //this variable is temporary
    String speedType;
    wave = 0;
    year = 0;
    speed = 0;
    Scanner myScanner = new Scanner (System.in);
    
    //-----data input-----//
    System.out.println("Welcome to the Eco-System Simulator");
    System.out.println();
    
    //wolf input
    System.out.println("Enter the wolf population: (recommend 4)");
    populationW = myScanner.nextInt();
    System.out.println("Enter the spawn health of wolves: (recommend 50)");
    spawnHealthW = myScanner.nextInt();
    System.out.println("How many years can wolves be alive for? (recommend 2)");
    ageW = myScanner.nextInt();
    ageW = ageW*365;
    myScanner.nextLine();
    System.out.println("Will wolves be able to mate? (yes or no)");
    response = myScanner.nextLine();
    if (response.equals("yes")){
      mateW = true;
    }else{
      mateW = false;
    }
    
    //sheep input
    System.out.println("Enter the sheep population: (recommend 20 if wolf can breed; otherwise 10)");
    populationS = myScanner.nextInt();
    System.out.println("Enter the spawn health of sheep: (recommend 50)");
    spawnHealthS = myScanner.nextInt();
    System.out.println("How many years can sheep be alive for? (recommend 1)");
    ageS = myScanner.nextInt();
    ageS = ageS*365;
    myScanner.nextLine();
    System.out.println("Will sheep be able to mate? (yes or no)");
    response = myScanner.nextLine();
    if (response.equals("yes")){
      mateS = true;
    }else{
      mateS = false;
    }
    
    //plant input
    System.out.println("Enter the plant spawning rate: (#of plants per turn) (recommend 50)");
    plantRate = myScanner.nextInt();    
    System.out.println("Enter the percent chance of a plant spawning in a square: (ex. 25 would mean there is a 25 out of 100 percent chance of a plant spawning in a square) (recommend 25)");
    plantChance = myScanner.nextInt();
    System.out.println("Enter the nutritional value of the plants: (recommend 50)");
    plantValue = myScanner.nextInt();
    
    //map input
    System.out.println("Enter the width of the map: ");
    width = myScanner.nextInt();
    System.out.println("Enter the length of the map: ");
    length = myScanner.nextInt();
    myScanner.nextLine();
    System.out.println("Enter the speed of the simulator: (slow, medium, fast)");
    speedType = myScanner.nextLine();
    if (speedType.equals("slow")){
      speed = 1000;
    }else if (speedType.equals("medium")){
      speed = 500;
    }else if (speedType.equals("fast")){
      speed = 100;
    }
   
    //Set up Map
    Eco world = new Eco(length, width);
    
    // Initialize Map
    world.initialMap(populationW, populationS, spawnHealthW, spawnHealthS, mateW, mateS);
        
    //Set up Grid Panel
    DisplayGrid grid = new DisplayGrid(world);
    
    //Display the grid on a Panel
    grid.refresh();
    
    wave++;
    
    //----------main code----------//
    while(extinction == false){
      //Small delay
      try{ Thread.sleep(speed); }catch(Exception e){};
      
      //move animals Map
      world.moveItemsOnGrid(plantRate, plantChance, plantValue);
      
      //resets the moved numbers to allow the animals to move the next turn
      world.reset();
      
      //makes the animals lose 1 health and age
      world.mature(ageW, ageS);
      
      populationW = world.wolfPopulation();
      populationS = world.sheepPopulation();
      populationP = world.plantPopulation();
      
      //determines wether the game will continue or not
      if (populationW==0){
        extinction = true;
      }else if (populationS == 0){
        extinction = true;
      }
      
      //updates the grid information
      grid.update(populationS, populationW, populationP, wave, year);
      
      wave++;
      
      //determines the year; simulator starts at year 0
      if (wave%365 == 0){
        year++;
      }
      
      //Display the grid on a Panel
      grid.refresh();
    }
    myScanner.close();
  }
}