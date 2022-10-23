/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Cursor;
import java.util.concurrent.Semaphore;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class Car extends JLabel implements Runnable { 
	String nom; 
	Parking park;
        ImageIcon carImg ;
        int posx;
        int posy;
        int positionY;
        int positionX;
        String side;
        public static Thread Cars[];


    
    public Car(String name, Parking park,int x,int y){
	this.nom    = name; 
	this.park   = park; 
        this.posx   = x;
        this.posy   = y ;
        this.positionY = y;
        this.positionX = x;
        this.carImg = new ImageIcon("images/3.png");
        this.setIcon(carImg);
        Dimension size = this.getPreferredSize();
        this.setBounds(posx, posy, size.width,size.height);

       }
      public boolean rentrer() throws InterruptedException{
          
	if(this.park.accept(this)){
            return true;          
	}else{   
            return false;
        }
      }
      
      public void changeImage(String name){
          
        this.setIcon(new ImageIcon(name));
      }
      
      public String toString(){
          
          return "Car informations : "+this.nom + " x_position = "+posx + " y_position = "+this.posy + " car Side : "+this.side;
      }
	public void run(){ 
	 System.out.println(this);
	try {
	    while(true){
                Thread.sleep((long)  (600* Math.random()));
                System.out.format("[%s]: Je demande à rentrer  \n", this.nom);

		this.park.semaphore.acquire();
		if(!(this.rentrer())){
                    
                     System.out.format("[%s]  : Car want go inside the parking\n", this.nom);
                     
                }
                this.park.semaphore.release();
		Thread.sleep((long)  (600* Math.random()));
                if(this.park.infoCars.contains(this)){
		System.out.format("[%s]: Je demande à sortir  \n", this.nom);
                this.park.semaphore.acquire();
                this.park.leave(this);
                this.park.semaphore.release();
                
                }
               
	    }
            
          }catch(InterruptedException e){
              
              System.out.println(e.getMessage());
          }
        }
        
    public static void main(String[] args) {
        int parkingSize=4;
        JFrame frame = new JFrame("Simulation_parking");
	Parking  parking = new Parking(parkingSize);
        frame.setContentPane(parking);
        parking.setLayout(null);
        frame.setSize(1445,831);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	int nbCars = 6 ;
        String CarName = "car_abc";
        JButton startAnimation = new JButton("Commencer la simulation");
        parking.add(startAnimation);
        startAnimation.setVisible(true);
        startAnimation.setBounds(500, 400, 150, 50);
        startAnimation.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startAnimation.setBorder(null);
 
//        Creer les threads
        Thread Cars[][] = new Thread[4][2];
        int x ;
        int y = 150;
	for (int i = 0; i< 4; i++){
            x = 60;
            for(int j=0;j<2;j++){
                
            Car car = new Car(CarName +" " + i+j,parking,x,y);
            if(j == 0){
                car.side = "l";
                 
            }else{
                
                car.side = "r";
            }
            Cars[i][j] =  new Thread(car);
        
            parking.add(car);
            parking.allCars.add(car);
           
            x = x + 80;

            }   
            y = y + 160;
        }
        
        
      
        ActionListener actionListenerForButoon = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {

                for (int i = 0; i< 4; i++){
                    for(int j=0;j<2;j++){
                             Cars[i][j].start();
                    }

                }
          }
        };
         startAnimation.addActionListener(actionListenerForButoon);
    }
}

        
