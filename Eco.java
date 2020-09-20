/**
 * Eco.java
 * class that stores all animals and moves them
 */

class Eco implements Move{
  private Life[][] world;
  private Boolean mateW, mateS;
  
  //sets up the eco system on a 2d array
  Eco(int length, int width){
    this.world = new Life[length][width];
  }
  
  //returns the animal in a given spot
  public Life getAnimal(int y, int x){
    return this.world[y][x];
  }
  
  //puts an animal in a given square
  public void setAnimal(String type, int y, int x, int health){
    if (type.equals("wolf")){
      this.world[y][x] = new Wolf(health);
    }else if(type.equals("sheep")){
      this.world[y][x] = new Sheep(health);
    }else if(type.equals("plant")){
      this.world[y][x] = new Plant(health);
    }else{
      this.world[y][x] = null;
    }
  }
  
  //returns the 2d array (this is used in the display grid class)
  public Life[][] getMap(){
    return this.world;
  }
  
  //-----movement methods for animals-----//
  public void moveUp(int y, int x){
    if (y>0){
      this.collision(y,x,(y-1),x);
    }else{
      this.world[y][x] = this.world[y][x];
    }
  }
  
  public void moveDown(int y, int x){
    if (y<this.world.length-1){
      this.collision(y,x,(y+1),x);
    }else{
      this.world[y][x] = this.world[y][x];
    }
  }
  
  public void moveLeft(int y, int x){
    if (x>0){
      this.collision(y,x,y,(x-1));
    }else{
      this.world[y][x] = this.world[y][x];
    }
  }
  
  public void moveRight(int y, int x){
    if (x<this.world[0].length-1){
      this.collision(y,x,y,(x+1));
    }else{
      this.world[y][x] = this.world[y][x];
    }
  }
  
  //collision method determines what happens when 2 life forms collide
  public void collision(int y1, int x1, int y2, int x2){
    //when a sheep is about to move towards a wolf, this code makes the sheep freeze and implies that the sheep freezes in fear
    if ((this.world[y1][x1] instanceof Sheep)&&(this.world[y2][x2] instanceof Wolf)){
      this.world[y1][x1] = this.world[y1][x1];
      
      
    //code for when a baby wolf tries to eat a sheep; the sheep will lose 10 health as the wolf will only be able to attack it
    }else if(((this.world[y1][x1] instanceof Wolf)&&(this.world[y2][x2] instanceof Sheep))&&(this.world[y1][x1].getAge()<20)&&(this.wolfPopulation()>8)){
      int life = ((Sheep)this.world[y2][x2]).getHealth();
      this.world[y2][x2].setHealth(life - 10);
      
    //code for when a wolf eats a sheep
    }else if((this.world[y1][x1] instanceof Wolf)&&(this.world[y2][x2] instanceof Sheep)){
      ((Wolf)this.world[y1][x1]).eat((Sheep)this.world[y2][x2]);
      this.world[y2][x2] = this.world[y1][x1];
      this.world[y1][x1] = null;
      this.world[y2][x2].moved();
      
    //when a wolf collides with another wolf, they can mate or attack each other
    }else if((this.world[y1][x1] instanceof Wolf)&&(this.world[y2][x2] instanceof Wolf)){
      if (mateW == false){
        //if the number is negative, the wolf is weaker than the other wolf and therefore will lose health
        if(((Wolf)this.world[y1][x1]).compareTo((Wolf)this.world[y2][x2])<0){
          int life = ((Wolf)this.world[y1][x1]).getHealth();
          ((Wolf)this.world[y1][x1]).setHealth(life - 10);
        }else if(((Wolf)this.world[y1][x1]).compareTo((Wolf)this.world[y2][x2])>0){
          int life = ((Wolf)this.world[y2][x2]).getHealth();
          ((Wolf)this.world[y2][x2]).setHealth(life - 10);
        }
      }else{
        if ((this.world[y1][x1].getHealth()>=100)&&(this.world[y1][x1].getAge()>=20)&&(this.world[y2][x2].getHealth()>=100)&&(this.world[y2][x2].getAge()>=20)){
          //checks for available spots
          int temp = 0;
          for (int i=0; i<this.world.length; i++){
            for (int j=0; j<this.world[0].length; j++){
              if(this.world[i][j]==null){
                temp++;
              }
            }
          }
          if ((temp > 0)&&(mateW == true)){
            //creating new wolf
            int life1 = this.world[y1][x1].getHealth();
            int life2 = this.world[y2][x2].getHealth();
            this.world[y1][x1].setHealth(life1 - 10);
            this.world[y2][x2].setHealth(life2 - 10);
            int wolf = 0;
            while (wolf<1){
              int r1 = (int)(Math.random()*world.length);
              int r2 = (int)(Math.random()*world[0].length);
              if (world[r1][r2]==null){
                world[r1][r2] = new Wolf(20);            
                wolf++;
              }
            }
          }
        }else{
          //if the number is negative, the wolf is weaker than the other wolf and therefore will lose health
          if(((Wolf)this.world[y1][x1]).compareTo((Wolf)this.world[y2][x2])<0){
            int life = ((Wolf)this.world[y1][x1]).getHealth();
            ((Wolf)this.world[y1][x1]).setHealth(life - 10);
          }else if(((Wolf)this.world[y1][x1]).compareTo((Wolf)this.world[y2][x2])>0){
            int life = ((Wolf)this.world[y2][x2]).getHealth();
            ((Wolf)this.world[y2][x2]).setHealth(life - 10);
          }
        }
      }
    //when a sheep eats a plant, the plants health is transferred to the sheep
    }else if((this.world[y1][x1] instanceof Sheep)&&(this.world[y2][x2] instanceof Plant)){
      int life = ((Sheep)this.world[y1][x1]).getHealth();
      ((Sheep)this.world[y1][x1]).setHealth(life + this.world[y2][x2].getHealth());
      this.world[y2][x2] = this.world[y1][x1];
      this.world[y1][x1] = null;
      this.world[y2][x2].moved();
      
    //when a sheep reproduces
    }else if((this.world[y1][x1] instanceof Sheep)&&(this.world[y2][x2] instanceof Sheep)){
      if ((this.world[y1][x1].getHealth()>=20)&&(this.world[y1][x1].getAge()>=30)&&(this.world[y2][x2].getHealth()>=20)&&(this.world[y2][x2].getAge()>=30)){
        //checks for available spots
        int temp = 0;
        for (int i=0; i<this.world.length; i++){
          for (int j=0; j<this.world[0].length; j++){
            if(this.world[i][j]==null){
              temp++;
            }
          }
        }
        //if mateS is not true, the sheep will just stand still next to each other
        if ((temp > 0)&&(mateS == true)&&(((Sheep)this.world[y1][x1]).mateVer()<5)&&(((Sheep)this.world[y2][x2]).mateVer() <5)){
          //creating new sheep
          int life1 = this.world[y1][x1].getHealth();
          int life2 = this.world[y2][x2].getHealth();
          this.world[y1][x1].setHealth(life1 - 10);
          this.world[y2][x2].setHealth(life2 - 10);
          ((Sheep)this.world[y1][x1]).mated();
          ((Sheep)this.world[y2][x2]).mated();
          int sheep = 0;
          while (sheep<1){
            int r1 = (int)(Math.random()*world.length);
            int r2 = (int)(Math.random()*world[0].length);
            if (world[r1][r2]==null){
              world[r1][r2] = new Sheep(20);            
              sheep++;
            }
          }
        }
      }else{
        this.world[y1][x1] = this.world[y1][x1];
      }
    }else{
      //if none of the previous scenarios occur, the animal will simply move
      this.world[y2][x2] = this.world[y1][x1];
      this.world[y1][x1] = null;
      if (this.world[y2][x2] instanceof Wolf){
        world[y2][x2].moved();
      }else if(this.world[y2][x2] instanceof Sheep){
        world[y2][x2].moved();
      }
    }
  }
  
  //all of the move variables are reset
  public void reset(){
    for (int i=0; i<this.world.length; i++){
      for (int j=0; j<this.world[0].length; j++){
        if (this.world[i][j] instanceof Wolf){
          ((Wolf)world[i][j]).moveReset();
        }else if(this.world[i][j] instanceof Sheep){
          ((Sheep)world[i][j]).moveReset();
        }
      }
    }
  }
  
  //returns the number of wolves in the simulator
  public int wolfPopulation(){
    int populationW = 0;
    for (int i=0; i<this.world.length; i++){
      for (int j=0; j<this.world[0].length; j++){
        if (this.world[i][j] instanceof Wolf){
          populationW++;
        }
      }
    }
    return populationW;
  }
  
  //returns the number of sheep in the simulator
  public int sheepPopulation(){
    int populationS = 0;
    for (int i=0; i<this.world.length; i++){
      for (int j=0; j<this.world[0].length; j++){
        if(this.world[i][j] instanceof Sheep){
          populationS++;
        }
      }
    }
    return populationS;
  }
  
  //returns the number of plants in the simulator
  public int plantPopulation(){
    int populationP = 0;
    for (int i=0; i<this.world.length; i++){
      for (int j=0; j<this.world[0].length; j++){
        if(this.world[i][j] instanceof Plant){
          populationP++;
        }
      }
    }
    return populationP;
  }
  
  //all of the sheep and wolves age and lose 1 health indicating that they become hungry
  public void mature(int limitW, int limitS){
    for (int i=0; i<this.world.length; i++){
      for (int j=0; j<this.world[0].length; j++){
        if((this.world[i][j] instanceof Sheep)||(this.world[i][j] instanceof Wolf)){
          world[i][j].mature();
          if (world[i][j].getHealth()<=0){
            world[i][j] = null;
          }else if(world[i][j] instanceof Sheep){
            if (((Sheep)this.world[i][j]).getAge()==limitS){
              world[i][j] = null;
            }
          }else if(world[i][j] instanceof Wolf){
            if (((Wolf)this.world[i][j]).getAge() == limitW){
              world[i][j] = null;
            }
          }
        }
      }
    }
  }
  
  //Method to initialize map
  public void initialMap(int pW, int pS, int healthW, int healthS, Boolean mW, Boolean mS){
    int wolf, sheep, r1, r2;
    wolf = 0;
    sheep = 0;
    this.mateW = mW;
    this.mateS = mS;
    
    while (wolf<pW){
      r1 = (int)(Math.random()*this.world.length);
      r2 = (int)(Math.random()*this.world[0].length);
      if (this.world[r1][r2]==null){
        this.world[r1][r2] = new Wolf(healthW);
        wolf++;
      }
    }
    
    while (sheep<pS){
      r1 = (int)(Math.random()*this.world.length);
      r2 = (int)(Math.random()*this.world[0].length);
      if (this.world[r1][r2]==null){
        this.world[r1][r2] = new Sheep(healthS);
        sheep++;
      }
    }
  }
  
  // Method to simulate grid movement
  public void moveItemsOnGrid(int plantR, int plantC, int healthP){
    int r, r1, r2;
    
    for (int i=0; i<plantR; i++){
      r = (int)(Math.random()*100+1);
      r1 = (int)(Math.random()*this.world.length);
      r2 = (int)(Math.random()*this.world[0].length);
      
      //there is a plantC in 100 chance of a plant actually spawning
      if (r<=plantC){
        if (this.world[r1][r2]==null){
          this.world[r1][r2] = new Plant(healthP);
        }
      }
    }
    
    for (int i=0; i<this.world.length; i++){
      for (int j=0; j<this.world[0].length; j++){
        if ((this.world[i][j] instanceof Wolf)||(this.world[i][j] instanceof Sheep)){
          if (this.world[i][j].moveVer()){
            //move generator for animals
            r = this.logic(i,j);
            //move up
            if (r==1){
              this.moveUp(i,j);
              //move down
            }else if (r==2){
              this.moveDown(i,j);
              //move left
            }else if (r==3){
              this.moveLeft(i,j);
              //move right
            }else if (r==4){
              this.moveRight(i,j);
            }
          }
        }
      }
    }
  }
  
  //this method allows animals to see what is directly in front of them and allows them to pick the best move
  public int logic(int y, int x){
    int result = 0;
    //when returning 1, the animal moves up
    //when returning 2, the animal moves down
    //when returning 3, the animal moves left
    //when returning 4, the animal moves right
    if (this.world[y][x] instanceof Wolf){
      if (y>0){
        if (this.world[y-1][x] instanceof Sheep){
          result =  1;
        }else if (((this.world[y-1][x] instanceof Wolf)&&(((Wolf)this.world[y][x]).compareTo((Wolf)this.world[y-1][x])<0))&&(mateW == false)){
          result =  2;
        }else{
          result =  (int)(Math.random()*4+1);
        }
      }else if (y<this.world.length){
        if (this.world[y+1][x] instanceof Sheep){
          result =  2;
        }else if (((this.world[y+1][x] instanceof Wolf)&&(((Wolf)this.world[y][x]).compareTo((Wolf)this.world[y+1][x])<0))&&(mateW == false)){
          result =  1;
        }else{
          result =  (int)(Math.random()*4+1);
        }
      }else if (x>0){
        if (this.world[y][x-1] instanceof Sheep){
          result =  3;
        }else if (((this.world[y][x-1] instanceof Wolf)&&(((Wolf)this.world[y][x]).compareTo((Wolf)this.world[y][x-1])<0))&&(mateW == false)){
          result =  4;
        }else{
          result =  (int)(Math.random()*4+1);
        }
      }else if (x<this.world[0].length){
        if (this.world[y][x+1] instanceof Sheep){
          result =  4;
        }else if (((this.world[y][x+1] instanceof Wolf)&&(((Wolf)this.world[y][x]).compareTo((Wolf)this.world[y][x+1])<0))&&(mateW == false)){
          result =  3;
        }else{
          result =  (int)(Math.random()*4+1);
        }
      }
    }else if(this.world[y][x] instanceof Sheep){
      if (y>0){
        if (this.world[y-1][x] instanceof Plant){
          result =  1;
        }else if (this.world[y-1][x] instanceof Wolf){
          result =  2;
        }else{
          result =  (int)(Math.random()*4+1);
        }
      }else if(y<this.world.length){
        if (this.world[y+1][x] instanceof Plant){
          result =  2;
        }else if (this.world[y+1][x] instanceof Wolf){
          result =  1;
        }else{
          result =  (int)(Math.random()*4+1);
        }
      }else if(x>0){
        if (this.world[y][x-1] instanceof Plant){
          result =  3;
        }else if (this.world[y][x-1] instanceof Wolf){
          result =  4;
        }else{
          result =  (int)(Math.random()*4+1);
        }
      }else if(x<this.world[0].length){
        if (this.world[y][x+1] instanceof Plant){
          result = 4;
        }else if (this.world[y][x+1] instanceof Wolf){
          result = 3;
        }else{
          result =  (int)(Math.random()*4+1);
        }
      }
    }
    return result;
  }
}