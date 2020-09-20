/**
 * Wolf.java
 */

class Wolf extends Life implements Comparable<Wolf>{
  //sets the health of a wolf
  Wolf(int health){
    super(health);
  }
  
  //allows wolf to eat sheep and gain health
  public void eat(Sheep food){
    int life = getHealth();
    setHealth(life+food.getHealth());
  }
  
  //determines wether a wolf is stronger or weaker than another wolf
  public int compareTo(Wolf wolf){
    return (this.getHealth()-wolf.getHealth());
  }
}