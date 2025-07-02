/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.practicab;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.*;
/**
 *
 * @author HP
 */
public class PracticaB {
    public static Scanner src = new Scanner (System.in);
    public static int incomingStream[], n, fr[], frames, fs[], index, k, l, flag1 = 0, flag2 =  0, pf = 0, framesize = 3, i, j,menu;
    //framesize = 3
    //m=frames, p=incomingStream
    //fr,sr colas de marcos
    //n tam cadena
    
    public static void display ()
    {
      System.out.println ("\n");
      for (i = 0; i < frames; i++)
        {
          if (fr[i] == -1)
            System.out.println ("[   ]");
          else
            System.out.println ("[" + fr[i] + "]");
        }
    }
    
    
  public static void lru ()
  {
    for (i = 0; i < frames; i++)
      {
	fr[i] = -1;     //un -1 en la cola fr implica un hueco
      }
    
    for (j = 0; j < n; j++)
      {
	flag1 = 0;
	flag2 = 0;
	for (i = 0; i < frames; i++)
	  {
	    if (fr[i] == incomingStream[j])

	      {
		flag1 = 1;
		flag2 = 1;      //FLAG1 && FLAG2==TRUE IMPLICA QUE LA PAGINA YA ESTA EN LA COLA
		break;
	      }
	  }
        
	if (flag1 == 0)     //SI LA NUEVA PAGINA NO ESTABA EN LA COLA, HAY QUE COMPROBAR SI HAY HUECOS DISPONIBLES
	  { 
	    for (i = 0; i < frames; i++)

	      {
		if (fr[i] == -1)

		  {
		    fr[i] = incomingStream[j];
		    flag2 = 1;
		    break;
		  }
	      }
	  }
        
	if (flag2 == 0)     //SI LA NUEVA PAGINA NO ESTABA EN LA COLA Y NO HABIA HUECOS
	  {
            //for (i = 0; i < 3; i++)
	    for (i = 0; i < frames; i++)
	      fs[i] = -1;
            /*
	    for (k = j - 1, l = 1; l <= frames - 1; l++, k--)
	      {
		for (i = 0; i < frames; i++)
		  {
		    if (fr[i] == incomingStream[k])
		      fs[i] = 1;
		  }
	      }
            
	    for (i = 0; i < frames; i++)
	      {
		if (fs[i] == 0)
		  index = i;
	      }
	    fr[index] = incomingStream[j];
            */
                
            for(i = 0; i < frames-1; i++)
                fs[i] = fr[i+1];
            fr=fs;
            fs = new int[frames];
            for (i = 0; i < frames; i++)
	      {
		if (fr[i] == -1)

		  {
		    fr[i] = incomingStream[j];
		    //flag2 = 1;
		    break;
		  }
	      }
            
	    pf++;
	  }
        
        if ((flag2 == 1) &&(flag1==1)){
            int aux=0;
            
            for (i = 0; i < frames-1; i++)
                if(fr[i]==incomingStream[j]){
                    aux=fr[i];
                    fr[i]=fr[i+1];
                    fr[i+1]=aux;
                }
                    
        }
                
            
	System.out.print ("\nPage   :   " + incomingStream[j]);
	display ();
      }
    System.out.println ("\n   Number of page fault:" + pf+ "\n");
    System.out.println("Razon de fallos: " + (double) pf/n);
    double r=(double)1-(double)pf/n;
    System.out.println("Rendimiento: " + r);
  }
    ///END LRU
    
    
    static int pageFaults(int incomingStream[], int n, int frames)
    {
        System.out.println("Incoming \t Pages");
        // Using Hashset to quickly check if a given
        // incoming stream item in set or not
        HashSet s = new HashSet<>(frames);

        // Queue created to store pages in FIFO manner
        // since set will not store order or entry
        // we will use queue to note order of entry of incoming page
        Queue queue = new LinkedList<>();

        int page_faults = 0;

        for (int i=0; i < n; i++)
        {
            // if set has lesser item than frames
            if (s.size() < frames)
            {
                // If incoming item is not present, add to set
                if (!s.contains(incomingStream[i]))
                {
                    s.add(incomingStream[i]);
                    page_faults++;

                    // Push the incoming page into the queue
                    queue.add(incomingStream[i]);


                }
            }

            // If the set is full then we need to do page replacement
            // in FIFO manner that is remove first item from both
            // set and queue then insert incoming page
            else
            {
                // If incoming item is not present
                if (!s.contains(incomingStream[i]))
                {
                    // remove the first page from the queue
                    int val = (int) queue.peek();

                    // remove from queue
                    queue.poll();

                    // Remove from set
                    s.remove(val);

                    // insert incoming page to set
                    s.add(incomingStream[i]);

                    // push incoming page to queue
                    queue.add(incomingStream[i]);
                    page_faults++;


                }
            }
            // printing happens here
            System.out.print(incomingStream[i] + "\t");
            System.out.print(queue + " \n");
        }


        return page_faults;
    }

    public static void main(String args[])
    {
        //Scanner src = new Scanner (System.in);
        //int incomingStream[], n, fr[], frames, fs[], index, k, l, flag1 = 0, flag2 =  0, pf = 0, framesize = 3, i, j,menu;
        //m=frame, p=incomingStream
        System.out.println ("INGRESE EL TAMANO DE LA CADENA");
        n = src.nextInt ();
        incomingStream = new int[n];
        System.out.println ("INGRESE LA CADENA DE REFERENCIA");
        for (i = 0; i < n; i++)
            incomingStream[i] = src.nextInt ();
        System.out.println ("INGRESE EL TOTAL DE MARCOS");
        frames = src.nextInt ();
        fr = new int[frames];
        fs = new int[frames];
        
        System.out.println("SELECCIONE EL ALGORITMO:");
        System.out.println("1) FIFO");
        System.out.println("2) LRU");
        menu=src.nextInt();
        
        switch(menu){
            case 1: 
                    int len = incomingStream.length;
                    int pageFaults = pageFaults(incomingStream, len, frames);
                    int hit = len - pageFaults;
                    System.out.println("Page faults: " + pageFaults);
                    System.out.println("Page fault Ratio: " + (double) pageFaults/len);
                    System.out.println("Hits: " + hit);
                    System.out.println("Hit Ratio : " + (double) hit/len);
                    double r=(double)1-(double)hit/len;
                    System.out.println("Rendimiento: " + r);
                break;
                
            case 2: 
                    lru();
                    display();
                break;
        }
        
        //int incomingStream2[] = {7, 0, 1, 2, 0 , 3, 0, 4, 2, 3, 0, 3, 2, 1};
        //int frames2 = 3;

        
    }
}
