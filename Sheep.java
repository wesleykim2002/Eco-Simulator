/**
 * Sheep.java
 */

class Sheep extends Life{
  private int mated = 0;
  
  //sets the health of a sheep
  Sheep(int health){
    super(health);
  }
  
  public void mated(){
    this.mated++;
  }
  
  public int mateVer(){
    return this.mated;
  }
}