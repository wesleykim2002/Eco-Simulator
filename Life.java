/**
 * Life.java
 */

class Life{
  private int health;
  private int age = 0;
  private int move = 0;
  
  //gives a living thing health
  Life(int h){
    this.health = h;
  }
  
  //sets a living things health (this is mainly used when wolves or sheep mate or attack or eat each other)
  public void setHealth(int h){
    this.health = h;
  }
  
  //returns the health of a living thing
  public int getHealth(){
    return this.health;
  }
  
  //matures a living thing
  public void mature(){
    this.health--;
    this.age++;
  }
  
  //returns age
  public int getAge(){
    return this.age;
  }
  
  //determines wether an animal has moved yet or not
  public Boolean moveVer(){
    if (this.move>0){
      return false;
    }else{
      return true;
    }
  }
  
  //resets the move variable, enabling it to move the next round
  public void moveReset(){
    this.move = 0;
  }
  
  //sets the move variable to 1 to show that the living thing has moved already
  public void moved(){
    this.move++;
  }
}