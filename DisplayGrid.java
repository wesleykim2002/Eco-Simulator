/**
 * DisplayGrid.java
 */

// Graphics Imports
import javax.swing.*;
import java.awt.*;

class DisplayGrid{
  private JFrame frame;
  private int maxX,maxY, GridToScreenRatio;
  private Life[][] world;
  private int populationS, populationW, populationP, day, year;
  
  DisplayGrid(Eco w){
    this.world = w.getMap();
    
    maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    GridToScreenRatio = maxY / (world.length+1);  //ratio to fit in screen as square map
    
    System.out.println("Map size: "+world.length+" by "+world[0].length + "\nScreen size: "+ maxX +"x"+maxY+ " Ratio: " + GridToScreenRatio);
    
    this.frame = new JFrame("Map of World");
    
    GridAreaPanel worldPanel = new GridAreaPanel();
    
    frame.getContentPane().add(BorderLayout.CENTER, worldPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    frame.setVisible(true);
  }
  
  public void update(int s, int w, int p, int d, int y){
    this.populationS = s;
    this.populationW = w;
    this.populationP = p;
    this.day = d;
    this.year = y;
  }
  
  public void refresh() {
    frame.repaint();
  }
  
  class GridAreaPanel extends JPanel {
    public void paintComponent(Graphics g) {
      Image grownSheep = Toolkit.getDefaultToolkit().getImage("Sheep.png");
      Image babySheep = Toolkit.getDefaultToolkit().getImage("Baby_Sheep.png");
      Image grownWolf = Toolkit.getDefaultToolkit().getImage("Wolf.png");
      Image babyWolf = Toolkit.getDefaultToolkit().getImage("babywolf.png");
      Image plant = Toolkit.getDefaultToolkit().getImage("Large_Fern.png");
      Image empty = Toolkit.getDefaultToolkit().getImage("empty.png");
      Image backdrop = Toolkit.getDefaultToolkit().getImage("Grass_top.png");
      
      //super.repaint();
      
      setDoubleBuffered(true); 
      g.setColor(Color.BLACK);
      
      for(int i = 0; i<world.length;i=i+1)
      {
        for(int j = 0; j<world[0].length;j=j+1) 
        {
          //this displays images corresponding to the living things and their age
          g.drawImage(backdrop,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
          
          if ((world[i][j] instanceof Wolf)&&(world[i][j].getAge()>=20))
            g.drawImage(grownWolf,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
          else if ((world[i][j] instanceof Wolf)&&(world[i][j].getAge()<20))
            g.drawImage(babyWolf,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
          else if ((world[i][j] instanceof Sheep)&&(world[i][j].getAge()>=30))
            g.drawImage(grownSheep,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
          else if ((world[i][j] instanceof Sheep)&&(world[i][j].getAge()<30))
            g.drawImage(babySheep,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
          else if (world[i][j] instanceof Plant)
            g.drawImage(plant,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
          else
            g.drawImage(empty,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
          //this displays the wolf, sheep, and plant population and the day
          g.drawString("Year: "+year, world.length*GridToScreenRatio+100,100);
          g.drawString("Days: "+day, world.length*GridToScreenRatio+100,200);
          g.drawString("Wolf Population: "+populationW, world.length*GridToScreenRatio+100,300);
          g.drawString("Sheep Population: "+populationS,world.length*GridToScreenRatio+100,400);
          g.drawString("Plant Population: "+populationP,world.length*GridToScreenRatio+100,500);
          g.setColor(Color.BLACK);
          g.drawRect(j*GridToScreenRatio, i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
        }
      }
    }
  }//end of GridAreaPanel
} //end of DisplayGrid