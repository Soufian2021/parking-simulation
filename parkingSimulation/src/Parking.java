import java.awt.Graphics;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import java.util.concurrent.Semaphore;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Parking extends JPanel{
    int filledPlaces ;
    int parkingCapacity ; 
    ImageIcon image;
    boolean Place[] ;
    public  Semaphore semaphore;
    public ArrayList<Car> allCars;
    public HashSet<Car> infoCars = new HashSet<Car>();

    Parking(int size){

        parkingCapacity = size;
        this.filledPlaces = 0;
        this.allCars = new ArrayList();
        this.semaphore = new Semaphore(5,true);

        Place      =  new boolean[4];
        for(int i=0 ; i<Place.length;i++)
        {
            Place[i]=false ; 
        }
    } 

    int places(){ 
        return (parkingCapacity - this.filledPlaces);
    }  
	
//    Function For Access A Car To Go Inside A Parking
//        Check If Found A Palce Free 
//               If Found A Place For A Car Then Incremment Number Of Place Busy   
    boolean  accept(Car car) {

	if  (places() > 0 ){ 
            

                filledPlaces ++ ;
                this.verifierEndroitsVides(car);
		infoCars.add(car); 
		System.out.format("[Parking] : la voiture %s est acceptée, il reste %d places vides \n", car.nom,places());
		System.out.format("La voiture est a l'intérieur\n");
		System.out.println(infoCars);
		return (true) ; 
	    }else{
	    System.out.format("Parking : la voiture %s est refusée, il reste  %d places \n", car.nom,places());
            allerAutourParking(car,car.posx,car.posy);
	    return(false);
	   }
    }
    
    public void allerAutourParking(Car car,int x,int y){
        
        while(y > 70){
                y-=10;
                car.setLocation(x,y);
          try { 
                Thread.sleep(50);  
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
 
        if(car.side =="left"){
               
                while(y > 0){
                    y-=10;
                    car.setLocation(x,y);
                    try { 
                        Thread.sleep(50);  
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                 
        }
            car.changeImage("images/1.png");
            car.setBounds(x, y, 150,141);
        while(x < 940){       
            x+=10;
            car.setLocation(x,y);
            try { 
                Thread.sleep(50);  
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
          if(car.side == "left"){  
            x+=70;
            car.setLocation(x,y);
        }
           
 
        car.changeImage("images/2.png");
        while(y < 690){
            y += 10;
            car.setLocation(x,y);
             try { 
                Thread.sleep(50);  
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        car.changeImage("images/4.png");
        car.setBounds(x, y, 150,141);
        
        while(x > car.positionX){
                
                x-=10;
                car.setLocation(x,y);
          try { 
                Thread.sleep(50);  
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       

        car.changeImage("images/3.png");
        while(y > car.positionY){
            y -= 10;
            car.setLocation(x,y);
             try { 
                Thread.sleep(50);  
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        

        car.posx = x;
        car.posy = y;
    
    }
    void leave(Car car) {

	 filledPlaces --; 
	 infoCars.remove(car);
	 System.out.format("Parking :[%s] est sortie, reste  %d places and posY = %d\n", car.nom, places(),car.posy);
           
        switch (car.posy) {
            case 10 :
                    Place[0]=false;
            
                   break;
            case 160:
                    Place[1]=false;
          
                       break;
            case 330:
                    Place[2]=false;

                   break;
            
            case 490:
                        Place[3]=false;
                      break;
             default :
                   Place[0]=false;
                 
                 break;
        }
          this.deplacerEndehors(car,car.posx,car.posy);
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        image = new ImageIcon("images/bg.jpg");
        image.paintIcon(this, g, WIDTH,WIDTH);
    }

    public void verifierEndroitsVides(Car car) {


            if(!Place[0]){
                Place[0] = true;
                this.deplacerDirectement(car,car.posx,car.posy);
            }

            else if(!Place[1]){
                Place[1] = true;   
                this.moveCarByRound(car, car.posx,car.posy,160);
            
             
            }
                
            else if(!Place[2]){
                Place[2] = true;
                this.moveCarByRound(car, car.posx,car.posy,330);
              
            }
    
            else if(!Place[3]){
                 Place[3] = true;
                 this.moveCarByRound(car, car.posx,car.posy,490);
              
            }else{
                
                System.out.println("Tous les endroits sont remplis");
            }
            
    }
    
    public void deplacerDirectement(Car car,int x,int y){
            
          while(y > 60){
                y-=10;
                car.setLocation(x,y);
          try { 
                Thread.sleep(50);  
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
//            Up To Top
           if(car.side == "left"){
               
           while(y > 0){
                y-=10;
                car.setLocation(x,y);
                try { 
                      Thread.sleep(50);  
                  } catch (InterruptedException ex) {
                      Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
                  }
                  }
                 
           }
            car.changeImage("images/1.png");
            car.setBounds(x, y, 150,141);
          
            while(x < 1200){
                
                x+=10;
                car.setLocation(x,y);
          try { 
                Thread.sleep(50);  
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            car.posx = x;
            car.posy = y;

        
    }
    
    public void moveCarByRound(Car car,int x,int y,int Distance){
          while(y > 70){
                
                y-=10;
                car.setLocation(x,y);
          try { 
                Thread.sleep(50);  
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
          //            Up To Top
              if(car.side =="left"){
               
               while(y > 0){
                y-=10;
                car.setLocation(x,y);
                try { 
                      Thread.sleep(50);  
                  } catch (InterruptedException ex) {
                      Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
                  }
                  }
                 
           }
            car.changeImage("images/1.png");
            car.setBounds(x,y, 150,141);
           while(x < 940){       
            x+=10;
            car.setLocation(x,y);
            try { 
                Thread.sleep(50);  
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
           
         if(car.side == "left"){  
            x+=70;
            car.setLocation(x,y);
        }
           
//           Switch Icon Maening Round The Car 
        car.changeImage("images/2.png");
      
        while(y < Distance){
          
            y += 10;
            car.setLocation(x,y);
             try { 
                Thread.sleep(50);  
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
//         Return A Defualt Image

       
        if(car.side == "right"){
            
                   for(Car cl:this.allCars){
           
              if(y == (int)cl.getLocation().getY()){
                  
                  try {
                      Thread.sleep(500);
                  } catch (InterruptedException ex) {
                      Logger.getLogger(Parking.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
           
       }
      }
        car.changeImage("images/1.png");
        car.setBounds(x,y ,150,141);
    
       while(x < 1200){
                
                x+=10;
                car.setLocation(x,y);
          try { 
                Thread.sleep(50);  
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
       
        car.posx = x;
        car.posy = y;
    
    }
   

    public void deplacerEndehors(Car car, int x, int y) {
          

       while(x > 1500){       
            x-=10;
            car.setLocation(x,y);
            try { 
                Thread.sleep(50);  
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
        for(Car cl:this.allCars){
           
              if(y == (int)cl.getLocation().getY()){
                  
                  try {
                      Thread.sleep(500);
                  } catch (InterruptedException ex) {
                      Logger.getLogger(Parking.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
           
       }
        while(x > 1020){       
            x-=10;
            car.setLocation(x,y);
            try { 
                Thread.sleep(50);  
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        car.changeImage("images/2.png");
        while( y < 690){
          
            y += 10;
            car.setLocation(x,y);
             try { 
                Thread.sleep(50);  
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
             
        
    }
      car.changeImage("images/4.png");
      car.setBounds(x,y, 150,141);
       while(x >  car.positionX){
                 x-=10;
                 car.setLocation(x,y);
            try { 
                Thread.sleep(50);  
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
           

        car.changeImage("images/3.png");
        while(y > car.positionY){
                 y-=10;
                 car.setLocation(x,y);
            try { 
                Thread.sleep(50);  
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        car.posx = x;
        car.posy = y;

    }    
}
