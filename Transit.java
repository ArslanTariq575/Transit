package transit;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */ 
	public Transit() { trainZero = null; }

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit(TNode tz) { trainZero = tz; }
	
	/*
	 * Getter method for trainZero
	 *
	 * DO NOT remove from this file.
	 */
	public TNode getTrainZero () {
		return trainZero;
	}

	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0. Store the zero node in the train layer in
	 * the instance variable trainZero.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 */
	public void makeList(int[] trainStations, int[] busStops, int[] locations) {
		// Train Layer
	    trainZero = new TNode();

		// Bus Layer
		trainZero.setDown(new TNode());

		// Walk Layer
		trainZero.getDown().setDown(new TNode());

		// Layer Below Walk is set to NULL
		trainZero.getDown().getDown().setDown(null);

		for (int i = 0; i < trainStations.length; i++)
        {
            addTrain(trainZero, trainStations[i]);
        }

		for (int i = 0; i < busStops.length; i++)
        {
            addBus(trainZero, busStops[i]);

        }

		for (int i = 0; i < locations.length; i++)
        {
            addWalk(trainZero, locations[i]);
        }
       
	}

	public static TNode findNode ( TNode Zero, int Stop){
		TNode temp = Zero;
	 while(temp != null){
		 if(temp.getLocation() ==  Stop){
			 return temp;
		 }else{
			 temp = temp.getNext();
		 }
 
		}
		return null;
	}
	
	
	public static void addTrain(TNode trainZero, int trainStop) {
		TNode new_Node = new TNode(trainStop);
        if (trainZero.getNext() == null)
        {
            trainZero.setNext(new_Node);
            
        
        }else{
            TNode prev = trainZero;
            TNode temp = trainZero.getNext();
            
            
            while (temp != null && temp.getLocation()!= trainStop)
            {
               
                if (temp.getLocation() < trainStop)
                {
                    //add new new after 2
                    if (temp.getNext() == null)
					    temp.setNext(new_Node);
                      
                }
                else
                
                {
                    //Add New Node between 0 and 2
                    TNode temp2 = new_Node;
					prev.setNext(temp2);
                    temp2.setNext(temp);
    
                }
				prev = prev.getNext();
                temp = temp.getNext();
                
            }
        }
		TNode busStop = findNode(trainZero.getDown(), trainStop);
            if( busStop != null){
                new_Node.setDown(busStop); 
            }

	}

	public static void addBus(TNode trainZero, int busStop ) {
        TNode new_Node = new TNode(busStop);
        if (trainZero.getDown().getNext() == null)
        {
			trainZero.getDown().setNext(new_Node);
            
        }else{
			TNode prev = trainZero.getDown();
			TNode temp = trainZero.getDown().getNext();
            
            while (temp != null && temp.getLocation()!= busStop)
            {
    
                if (temp.getLocation() < busStop)
                {
                    //add new new after 2
                    if (temp.getNext() == null)
						temp.setNext(new_Node);
                }
                else{
                    
                        //Add New Node between 0 and 2
                        TNode temp2 = new_Node;
						prev.setNext(temp2);
						temp2.setNext(temp);
                           break;
                    
                }
                
                prev = prev.getNext();
                temp = temp.getNext();
    
            }

        }
        
       
         TNode trainStop = findNode(trainZero, busStop);
         if( trainStop != null){
            trainStop.setDown(new_Node);
        }
        TNode walkStop = findNode(trainZero.getDown().getDown(), busStop);
         if( walkStop != null){
            new_Node.setDown(walkStop);
        }
        
    }
 
	public static void addWalk(TNode trainZero, int locations) {
        TNode new_Node = new TNode(locations);
        if (trainZero.getDown().getDown().getNext() == null)
        {
            trainZero.getDown().getDown().setNext(new_Node);
            
        }else{
            TNode prev = trainZero.getDown().getDown();
             TNode temp = trainZero.getDown().getDown().getNext();
        
            while (temp != null && temp.getLocation()!= locations)
            {

                if (temp.getLocation() < locations)
                {
                    //add new new after 2
                    if (temp.getNext() == null){
                        temp.setNext(new_Node);
                    }
                }else{
                        TNode temp2 = new_Node;
                            prev.setNext(temp2);
                            temp2.setNext(temp);
                }
              
                
                prev = prev.getNext();
                temp = temp.getNext();
             }
                
        }
   
        TNode busStop = findNode(trainZero.getDown(), locations);
        if( busStop != null){
           busStop.setDown(new_Node);
       }
        
	
	}
	

		
		
     
	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {
	    
		TNode temp = trainZero;
		TNode prev = null;
		
		if (temp != null && temp.getLocation() == station) {
            trainZero = temp.getNext();// Changed head
            return;
        }
		while (temp != null && temp.getLocation() != station) {
            prev = temp;
            temp = temp.getNext();
        }
		if (temp == null)
            return;
			prev.setNext(temp.getNext());
	}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {
	    addBus(trainZero, busStop);
	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param destination An int representing the destination
	 * @return
	 */
	public ArrayList<TNode> bestPath(int destination) {
	
		ArrayList<TNode> list = new ArrayList<TNode>();
        
		TNode lp = trainZero;
		TNode cp = trainZero;
		list.add(lp);
		lp = lp.getNext();

		while(lp.getLocation()<= destination ){
			
		   if(lp.getLocation()>destination){
		 	    break;
		    }
			if(lp.getNext()== null){
				//list.add(lp);
				cp = lp;
				break;
				 }
			
			if(lp.getDown() == null && lp.getLocation()> destination){
				break;
			}
			if(lp.getDown() == null && lp.getLocation()<= destination){
				lp = lp;
				
				
			}else{
				//list.add(lp);
				cp = lp;
			}

		    if (lp.getLocation()<=destination){
			lp=lp.getNext();
		   }

          }
		  TNode IPL = trainZero;
		  IPL = IPL.getNext();
		  while(IPL.getLocation()<=cp.getLocation()){
			  if(IPL.getNext()==null){
				  list.add(IPL);
				  break;
			  }
			  list.add(IPL);
			  IPL = IPL.getNext();
		   }

		  cp = cp.getDown();
	      list.add(cp);
		  TNode gp = cp;

		  // Bus Layer

		  while(cp.getLocation()<= destination ){
			cp=cp.getNext();
			if(cp == null){
				break;
			}
			if(cp.getLocation()>destination){
				  break;
			 }
			 if(cp.getNext()== null){
				 list.add(cp);
				 gp = cp;
				 break;
				  }
			 
			 if(cp.getDown() == null && cp.getLocation()> destination){
				 break;
			 }
			 if(cp.getDown() == null && cp.getLocation()<= destination){
				 cp = cp;
				 
				 
			 }else{
				 list.add(cp);
				 gp = cp;
				 
			 }

		}
		gp = gp.getDown();
		list.add(gp);

		// Train Layer

		while(gp.getLocation()<=destination){
			gp = gp.getNext();
			if(gp == null){
				break;
			}
			if(gp.getLocation()== destination){
				list.add(gp);
				break;
			}
		   
		   if(gp.getNext().getLocation()>destination){
			 break;
		   }
		   list.add(gp);
		   
		}


          return list;
	}
	


	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep copy
	 */
	public TNode duplicate() {
		TNode pp = new TNode();
		pp.setDown(new TNode());
		pp.getDown().setDown(new TNode());

		// Train Layer

		TNode tp = trainZero;
		TNode temp = pp;
		
		while(tp.getNext()!= null){
			tp = tp.getNext();
			TNode new_Node = new TNode(tp.getLocation());
			temp.setNext(new_Node);
			temp = temp.getNext();
			
		}

		// Bus Layer

		TNode bp = trainZero;
		TNode temp2 = pp.getDown();
		bp = bp.getDown();

		while(bp.getNext() != null){
			bp = bp.getNext();
			TNode new_Nodee = new TNode(bp.getLocation());
			temp2.setNext(new_Nodee);
			temp2 = temp2.getNext();
			
		}

		
		// Walk Layer
		TNode wp = trainZero;
		TNode temp3 = pp.getDown().getDown();
		wp = wp.getDown().getDown();

		while(wp.getNext() != null){
			wp = wp.getNext();
			TNode new_Nodeee = new TNode(wp.getLocation());
			temp3.setNext(new_Nodeee);
			temp3 = temp3.getNext();
			
		}
	
		// Connecting Down Nodes

		TNode Scoot1 = pp;
        TNode WP1 = pp.getDown();
        while(Scoot1.getNext() != null){
            
            if(Scoot1.getNext().getLocation() == WP1.getNext().getLocation()){
             Scoot1.getNext().setDown(WP1.getNext());
             Scoot1 = Scoot1.getNext();
            }else{
                WP1 = WP1.getNext();
            }
           
        
        }

		TNode Scoot = pp.getDown();
        TNode WP = pp.getDown().getDown();
        while(Scoot.getNext() != null){
            
            if(Scoot.getNext().getLocation() == WP.getNext().getLocation()){
             Scoot.getNext().setDown(WP.getNext());
             Scoot = Scoot.getNext();
            }else{
                WP = WP.getNext();
            }
           
        
        }

		
    	return pp;
    
	}


	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public void addScooter(int[] scooterStops) {

		TNode walkZero = trainZero.getDown().getDown();
         
         trainZero.getDown().setDown( new TNode());    
           
		for (int i = 0; i < scooterStops.length; i++)
		{
			AddScooter(trainZero, scooterStops[i]);

		}
        trainZero.getDown().getDown().setDown(new TNode());   
        TNode walkP = trainZero.getDown().getDown().getDown();
        while(walkP.getNext() == null){
            walkP.setNext(new TNode());
            walkP.setNext( walkZero.getNext());
        }
        
        TNode Scoot = trainZero.getDown().getDown();
        TNode WP = trainZero.getDown().getDown().getDown();
        while(Scoot.getNext() != null){
            
            if(Scoot.getNext().getLocation() == WP.getNext().getLocation()){
             Scoot.getNext().setDown(WP.getNext());
             Scoot = Scoot.getNext();
            }else{
                WP = WP.getNext();
            }
           
        
        }
	}
	public static void AddScooter(TNode trainZero, int scooterStops) {
        
        TNode new_Node = new TNode(scooterStops);
        if (trainZero.getDown().getDown().getNext() == null)
        {
            trainZero.getDown().getDown().setNext(new_Node);
            
        }else{
            TNode prev = trainZero.getDown().getDown();
             TNode temp = trainZero.getDown().getDown().getNext();
        
            while (temp != null && temp.getLocation()!= scooterStops)
            {

                if (temp.getLocation() < scooterStops)
                {
                    //add new new after 2
                    if (temp.getNext() == null){
                        temp.setNext(new_Node);
                    }
                }else{
                        TNode temp2 = new_Node;
                            prev.setNext(temp2) ;
                            temp2.setNext(temp);
                }
              
                
                prev = prev.getNext();
                temp = temp.getNext();
             }
                
        }
        TNode busStop = findNode(trainZero.getDown(), scooterStops);
         if( busStop != null){
            busStop.setDown(new_Node);
        
       }
    }

	/**
	 * Used by the driver to display the layered linked list. 
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * Used by the driver to display best path. 
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
