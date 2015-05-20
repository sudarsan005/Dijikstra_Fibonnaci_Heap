/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.ArrayList;
import java.util.Collections;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.Stack;


/**
 *  Class for creating graph and calculating minimum distance between nodes using dijikstra algorithm
 * @author Sudarsan
 */

public class Graph 
{
    //List for Storing the Node and the Edges
    List<HashMap<Integer,Integer>> graph = new ArrayList<HashMap<Integer,Integer>>();
    //Variable for storing the edgecount
    static int edgecount=0;
    //Variable for holding the Number of nodes
    int size;
   //Variable for checking whether all the nodes are connected(used in dfs)
    boolean[] isVisited;
   
    /**
     * Constructor for initializing the Lists and the size
     * @param nodes Number of nodes for the graph
     */
   public Graph(int nodes) 
    {
        isVisited = new boolean[nodes];
        size = nodes;
        for (int i = 0; i < nodes; i++)
        graph.add(new HashMap<Integer,Integer>());
            
    }
   
   /**
    * Function for checking whether all nodes are connected after dfs
    * @return - returns true if all the nodes are connected
    */

    public boolean checkConnection()
        {
         for(boolean b : isVisited) if(!b) return false;
            return true;
        }
    
    /**
     * Function for returning the children of the input node in a stack
     * @param s The stack in which the child nodes need to be filled
     * @param node The Node for which the child nodes need to be got
     * @return The stack with the child of the given node
     */
  
    public Stack getChild(Stack s,int node)
     {
         Set<Entry<Integer, Integer>> mapSet = graph.get(node).entrySet();
         Iterator<Entry<Integer, Integer>> mapIterator = mapSet.iterator();
          while (mapIterator.hasNext())
          {
              Entry<Integer, Integer> mapEntry = mapIterator.next();
              Integer childValue = mapEntry.getKey();                      
              s.push(childValue);
          }
          return s;
     }
    
    /**
     * Function for finding the minimum value of the array using the priority queue
     * @param Q - List containing the vertices of the graph
     * @param array - Array containing the distance of the vertices
     * @return - The Vertex with the minimum distance
     */
    public static Integer findMin(List Q ,Integer[] array)
     {  

      //Set the first element in the array as minimum value
      Integer minValue = array[Integer.valueOf(Q.get(0).toString())];
      //Set the first element in the vertex as the vertx with 
      Integer vertexNum = Integer.valueOf(Q.get(0).toString());

      for(int i=1;i<Q.size();i++)
      {
      if(array[Integer.valueOf(Q.get(i).toString())] < minValue)
         {  
         minValue = array[Integer.valueOf(Q.get(i).toString())];
         vertexNum = Integer.valueOf(Q.get(i).toString());
         }
      }
         return vertexNum;  
     }
   

    /**
     * Function to find the minimum distance of each node by dijikstra algorithm using simple heap
     * @param source - The Source node of the graph
     * @return - Minimum Distance for each Node
     */
    public Integer[] dijikstraSimpleHeap(int source)
    {
        //Variable for storing the minimum distance of each node
        Integer[] dist = new Integer[size];
        //For stroing the vertex with the minimum distance     
        Integer min;
        //Variable for storing the new distance of node     
        Integer newDist;

        //Initialize distance of all the node to infinity
        for(int i=0;i<size;i++)
        {
            dist[i] = Integer.MAX_VALUE;
        }

        //Set the distance of source node to 0
        dist[source] = 0;
        //Create a priority queue wtih the nodes
        List<Integer> VertexQ = new ArrayList<>(size);

        for(Integer i=0;i<size;i++)
        {
           VertexQ.add(i);
        }

        while(!VertexQ.isEmpty())
        {
           //Find the vertex with the minimum distance
           min = findMin(VertexQ,dist);

           //Remove the vertex with minimum distance from the queue
           VertexQ.remove(min);

           Map<Integer, Integer> neighborMap = graph.get(min);
           Iterator<Integer>  iter= neighborMap.keySet().iterator();
           while (iter.hasNext())
           {
               Integer neighbor = iter.next();
               Integer cost = neighborMap.get(neighbor);
               //Calculate the new distance of the child node
               newDist = dist[min] + cost;
               //If the new distance is less than the previous value, alter the distance
               if (newDist< dist[neighbor])
              {
                   dist[neighbor] = newDist;
              }
           }
        }
        return dist;
    }
    
    /**
     * Function to find the minimum distance of each node by dijikstra algorithm using fibonacci heap
     * @param source - The Source node of the graph
     * @return - minimum distance of each node 
     */ 
    public Integer[] dijkstraFibonacci(int source)
    {
        //Variable for storing the minimum distance of each node
        Integer[] dist = new Integer[size];
        //Variable for storing the new distance of node
        Integer newDist;
        //For stroing the vertex with the minimum distance
        Integer minNode;

        //Initialize distance of all the node to infinity
        for(int i=0;i<size;i++)
        {
            dist[i] = Integer.MAX_VALUE;
        }

        //Set the distance of root node to 0
        dist[source] = 0;

        //Create a fibonacci Heap and nodes of the Heap   
        FibonacciHeap fHeap = new FibonacciHeap();  
        FibonacciHeap.Node[] fNode = new FibonacciHeap.Node[size];

        //Add the Nodes with thier distance to the Heap
        for(int j=0;j<size;j++) 
        {
           fNode[j]= fHeap.insertNode(j,dist[j]);
        }


       while(fHeap.minimum() != null)
       {
           //Get the Node with the minimum distance
           minNode = Integer.valueOf(fHeap.minimum().getValue().toString()) ;
           //Delete the node with the minimum distance value from the heap        
           fHeap.removeMin();

           Map<Integer, Integer> neighborMap = graph.get(minNode);
           Iterator<Integer>  iter= neighborMap.keySet().iterator();
           while (iter.hasNext()) {
               Integer neighbor = iter.next();
               Integer cost = neighborMap.get(neighbor);
               newDist = dist[minNode] + cost;

               //if the new distance is less than the previous one, update the distance of the node
               //in the heap using decreasekey
               if (newDist< dist[neighbor])
              {              
                  fHeap.decreaseKey(fNode[neighbor],newDist);
                  dist[neighbor] = newDist;     
              }
           }
       }

       return dist;
    }
    
    
    /**
     * Function for creating Graph in random mode for the given number of nodes and density
     * @param NoofNodes - NoofNodes for the graph
     * @param density - Density for the graph
     * @return 
     */
   
    public boolean createGraph(int NoofNodes,double density)
    {
         int edgenumbers = 0;              
         int edgecount = 0;
         double d = density;
         int src,dest = 0;
         int cost = 0;
         //Variable for checking whether there are enough edges to generate a graph
         boolean isSufficientEdge  = false; 

         int initialsource,initialdest= 0;

         Random generator = new Random();
         List<Integer> Nodes = new ArrayList<>(NoofNodes);
         int r1,r2;

         //Calculate the number of edges required for the given no. of nodes and density
         edgenumbers = (int)(((NoofNodes*(NoofNodes-1))/2)*(d/100));

         //if the edge is less than the (n-1) nodes, set isSufficient edge to false
         if(edgenumbers<(NoofNodes-1))
         {
             isSufficientEdge= false;
         }
         else
         {
             isSufficientEdge = true;
         }

                 if(isSufficientEdge)
                 {
                  if(density == 0.1)
                  {
                         while(edgecount<(NoofNodes-1))
                         {
                             if(edgecount==0)
                             {
                                 initialsource = generator.nextInt(NoofNodes);
                                 initialdest = generator.nextInt(NoofNodes);
                                 cost = generator.nextInt(1000)+1;

                                 if (addEdge(initialsource, initialdest, cost) == true)
                                    {
                                    Nodes.add(initialsource);
                                    edgecount = edgecount+1;                                  
                                    }
                             }
                             else
                             {
                                 Collections.shuffle(Nodes);
                                 r1 = Nodes.get(0);
                                 r2 = generator.nextInt(NoofNodes);
                                 cost = generator.nextInt(1000)+1;
                                 if(!Nodes.contains(r2))
                                 {
                                 if (addEdge(r1,r2, cost) == true)
                                    {
                                    Nodes.add(r2);
                                    edgecount = edgecount+1;
                                    //System.out.println(edgecount);
                                    }
                                 }
                             }
                         }
                  }
                 while(edgecount != edgenumbers)
                 {

                     src = generator.nextInt(NoofNodes);
                     dest = generator.nextInt(NoofNodes);
                     cost = generator.nextInt(1000)+1;


                     if (addEdge(src, dest, cost) == true)
                     {
                     edgecount = edgecount+1;
                     }

                 }

                 return true;
              }
                 else
                 {
                     return false;
                 }
    }
    
    
    /**
     * Function performing the depth first search
     * @param root - Root Node of the graph
     * @return true - if the graph is connected, False - if the graph is not connected
     */
    public boolean dfs(int root)
         {
                 boolean allConnected = false;
                 Stack s=new Stack();
                 int node;

                 isVisited[root] = true;

                 getChild(s,root);

                 while(!s.isEmpty())
                 {
                     node = (int)s.pop();
                     if ((isVisited[node])==false)
                     {  
                         isVisited[node] = true;
                         getChild(s,node);
                     }            
                 }

                 allConnected =  checkConnection();

                 return allConnected;

         }
	
    /**
     * Function for adding edges to the graph
     * @param source - Source Node of the graph
     * @param target - Target Node of the graph
     * @param cost - Cost for the Edge
     * @return 
     */
    public boolean addEdge(int source, int target, int cost) 

     {

        boolean isNodeEmpty = false;

        isNodeEmpty = graph.get(source).isEmpty();

        //Add the edges to the node, only when its empty or the edge is not present in the graph
        if(!isNodeEmpty)

        {
         if((graph.get(source).containsKey(target)) || (source == target))
        {
            return false;
        }
        else
         {
              graph.get(source).put(target, cost);
              graph.get(target).put(source,cost);
              return true;
         }
        }

        else
        {

            if(source == target)
            {
                return false;
            }
            else
            {

                    graph.get(source).put(target, cost);
                    graph.get(target).put(source,cost);

                 return true;
            }
        }
     }
    
   
    /**
     * Utility function for printing the generated graph
     */
    public void printGraph()
    {
         for(int i =0;i<size;i++) 
         {
              
            Set<Entry<Integer, Integer>> mapSet = graph.get(i).entrySet();
            Iterator<Entry<Integer, Integer>> mapIterator = mapSet.iterator();
            while (mapIterator.hasNext()) 
            {
            Entry<Integer, Integer> mapEntry = mapIterator.next();
            Integer keyValue = mapEntry.getKey();
            Integer value = mapEntry.getValue();
            System.out.format("%d\t%d\t\t%d\n",i,keyValue,value);
            
            }
          }
    }
}
