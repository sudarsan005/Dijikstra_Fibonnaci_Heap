/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




import java.io.IOException;

/**
 * dijikstra Class
 * @author Sudarsan
 */
public class dijikstra
{
    
        //Variables for storing the graph properties from the user input
        static Integer NoofNodes;
        static Integer NoofEdges;
        static Integer rootNode;
        static double density;
        
        //Variables for storing the details of the edge
        static int source, target,weight;
       
       
        //Variable for checking whether the graph is connected
        static boolean checkConnection = false;
        //Variable for checking whether there are enough edges to generate a graph        
        static boolean isEnoughEdge = false;
        
        
       
        
      /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        //Variable for storing the input count
        Integer argumentCount;
        
        /*Variable for storing the input mode
        -r - For Random Mode
        -s - For simple scheme with user input mode
        -f - For fibonacci heap with user input mode*/              
        String inputMode;
        
        try
        {
            //Get the argument count from the command line
            argumentCount = args.length;
            
            //Check the argument count - if the user doesn't supply any argument, 
            //throw a message asking the user to enter the command line arguments
            if(argumentCount>0)
            {              
            //Example input - $dijikstra–r
            //The args[0] is equal to the inputMode as per the requirement    
            inputMode = args[0];
            
            //Call the required functionbased on the inputmode
            switch(inputMode.toUpperCase())
            {
                case "-R":
                    randomMode(args);
                    break;
                case "-S":
                    userInputSimpleHeap(args);
                    break;
                case "-F":
                    //createGraphfromFile(args);
                    userInputFibonacciHeap(args);
                    break;
                default:
                    System.out.println("Invalid input mode");
                    break;
            } 
            }
            else
            {
                System.out.println("Please provide command line arguments.");
            }

         }
        
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Function used for creating the graph from the input file
     * @param args Command Line Arguments
     */
    
    public static Graph createGraphfromFile(String[] args) throws IOException
    {
        try
        {
                //Variable for storing the contents of the input file
                String[] inputFile;
                //Class variable for reading the contents of the input file
                FileInput fig = new FileInput();
                
                //Read the input file by passing the path of the file
                if(args.length!=2)
                {
                    throw new IllegalArgumentException("Input not in correct format");
                }
                else
                {
                    inputFile = fig.readFileInput(args[1]);
                }
                
                //Variable used for getting the Node and Edge Count
                String[] Nodecount;
                
                //Get the Node and edge count from the file
                Nodecount = inputFile[1].split(" ");
                if(Nodecount.length!=2)
                {
                    throw new IllegalArgumentException("Input not in correct format. Please check the file");
                }
                else
                {
                    if((Integer.valueOf(Nodecount[0])<=0)||(Integer.valueOf(Nodecount[1])<=0))
                    {
                        throw new IllegalArgumentException("No of nodes or edges can't be less than or equal to zero");
                    }
                    else
                    {
                    NoofNodes = Integer.valueOf(Nodecount[0]);
                    NoofEdges = Integer.valueOf(Nodecount[1]);
                    }
                }
                
                if(NoofEdges<(NoofNodes-1))
                {
                    throw new IllegalArgumentException("Number of edges is not enough to generate a graph");
                }
                
                if(((inputFile.length)-2)!=NoofEdges)
                {
                    throw new IllegalArgumentException("The Number of edges provided is not equal to the given input");
                }
                
                
               
                
                
                    //Set the Root Node
                if((Integer.valueOf(inputFile[0])<0)||(Integer.valueOf(inputFile[0])>=NoofNodes))
                {
                    throw new IllegalArgumentException("The Root Node should be between 0 and No of Nodes . Please provide valid root node");
                }
                else
                {
                    rootNode = Integer.valueOf(inputFile[0]);      
                }
               
                
                //Value for storing the minimum distance of each node
                Integer[] minDist = new Integer[NoofNodes];          
                Graph gph = new Graph(NoofNodes);      
                 
                 //Add the Edges to the grapth from the file
                 for(int i=2;i<inputFile.length;i++)
                 {
                     String[] GraphValues;
                     GraphValues = inputFile[i].split(" ");
                     if(GraphValues.length!=3)
                     {
                         throw new IllegalArgumentException("Input not in correct format. Please check the file");
                     }
                     else
                     {
                     if((Integer.valueOf(GraphValues[0])<0)||(Integer.valueOf(GraphValues[0])>=NoofNodes))
                     {
                         throw new IllegalArgumentException("The Source Node should be between 0 and No of Nodes . Please provide valid Source node");
                     }
                     else
                     {
                         source = Integer.valueOf(GraphValues[0]);
                     }
                     
                     if((Integer.valueOf(GraphValues[1])<0)||(Integer.valueOf(GraphValues[1])>=NoofNodes))
                     {
                         throw new IllegalArgumentException("The Destination Node should be between 0 and No of Nodes . Please provide valid Destination node");
                     }
                     else
                     {
                         target = Integer.valueOf(GraphValues[1]);
                     }
                     
                     if(source == target)
                     {
                         throw new IllegalArgumentException("Source and the target can't be same. Please check the input");
                     }
                     
                     
                     if(Integer.valueOf(GraphValues[2])<=0)
                     {
                         throw new IllegalArgumentException("Cost can't be less than zero");
                     }
                     else
                     {
                         weight = Integer.valueOf(GraphValues[2]);
                     }
                     
                     }
                     
                     if((source>=NoofNodes)||(target>=NoofNodes))
                     {
                         throw new IllegalArgumentException("Incorrect Node Name. Please provide the Node name which is less than or equal to the number of nodes");
                     }
                     else
                     {
                         gph.addEdge(source, target, weight);
                     }                     
                     
                 }
                 return gph;
            }
             catch(Exception e)
               {
                    throw e;
               }
                   
      }
                

    /**Function for performing the dijikstra algorithm using simple heap
     * Calculates the minimum distance for the nodes given in the user input using simple heap and 
     * displays the minimum distance for each node
     * @param args
     * @throws IOException 
     */
     
     public static void userInputSimpleHeap(String[] args) throws IOException
        {
           
            try
            {
                //Class variable for creating graph and performing dijikstra algorithm
                Graph gph = createGraphfromFile(args);
                
                //Run dfs and check whether all the nodes are connected
                checkConnection = gph.dfs(rootNode);
                
                //Variable for storing the minimum distance 
                Integer[] minDist = new Integer[NoofNodes];
                
                //If the graph is connected, continue with dijikstra algorithm.
                //Else throw a message saying that the graph is not connected
                if(checkConnection == true)
                    {
                        
                        System.out.println("Graph generated");
                        
                        //alg.printGraph();
                        
                        long start = System.currentTimeMillis();
                        minDist =   gph.dijikstraSimpleHeap(rootNode);
                        long stop = System.currentTimeMillis();

                        long diff = stop-start;
                        System.out.format("Dijikstra Algorithm using Simple Heap completed in %d ms\n",diff);
                        for(Integer i=0;i<NoofNodes;i++)
                        {
                            System.out.format("%d\n",minDist[i]);
                        }
                   }
                else
                    {
                        //isConnected = false;
                        gph = new Graph(NoofNodes);
                        System.out.println("Graph is not connected.");            
                    }
            }
            
            catch(Exception e)
            {
                throw e;
            }
            
            

        }
        
        
        /**
         * Function for performing the dijkstra algorithm using Fibonacci heap
         * Calculates the minimum distance for the nodes given in the user input using Fibonacci heap and 
         * displays the minimum distance for each node
         * @param args
         * @throws IOException 
         */
        public static void userInputFibonacciHeap(String[] args) throws IOException
        {         
              try
              {
                Graph gph = createGraphfromFile(args);

                //Class variable for creating graph and performing dijikstra algorithm
                //Graph gph = new Graph(NoofNodes);
                checkConnection = gph.dfs(rootNode);
                
                //Variable for storing the minimum distance 
                Integer[] minDist = new Integer[NoofNodes];
                
                
                    //If the graph is connected, continue with dijikstra algorithm.
                    //Else throw a message saying that the graph is not connected
                    if(checkConnection == true)
                    {
                        
                        System.out.println("Graph generated");
                        
                        long start = System.currentTimeMillis();
                        minDist =   gph.dijkstraFibonacci(rootNode);
                        long stop = System.currentTimeMillis();

                        long diff = stop-start;
                        System.out.format("Dijikstra Algorithm using Fibonacci Heap completed in %d ms\n",diff);
                        
                        for(Integer i=0;i<NoofNodes;i++)
                        {
                            System.out.format("%d\n",minDist[i]);
                        }
                      
                    }
                    else
                    {
                        gph = new Graph(NoofNodes);
                        System.out.println("Graph is not connected.");
                    }
              }
              catch(Exception e)
                {
                    throw e;
                }

        }
        
        /**Function for performing the dijkstra algorithm using simple heap and fibonacci heap for random mode
         * Calculates the minimum distance for the nodes given in the user input using simple heap and fibonacci heap
         * displays the minimum distance for each node
         * @param args 
         */
        
        public static void randomMode(String[] args)
        {
            try
            {
                
            if(args.length !=4)
            {
                throw new IllegalArgumentException("Input not in correct format");
            }
            
            //Set the values for NoofNodes, density and rootnode
            if(Integer.valueOf(args[1])<=0)
            {
                throw new IllegalArgumentException("No of nodes cannot be less than or equal to zero");
            }
            else
            {
              NoofNodes = Integer.valueOf(args[1]);  
            }
            
            
            if((Double.parseDouble(args[2])<0)||(Double.parseDouble(args[2])>100))
            {
                throw new IllegalArgumentException("Density value should be between 0 and 100");
            }
            else
            {
                density = Double.parseDouble(args[2]) ;
            }
            if((Integer.valueOf(args[3])<0)||(Integer.valueOf(args[3])>=NoofNodes))
            {
                throw new IllegalArgumentException("Invalid Root Value");
            }
            else
            {
                rootNode = Integer.valueOf(args[3]);
            }
                
            Integer[] minDist = new Integer[NoofNodes];          
            Graph gph = new Graph(NoofNodes);          
        
          
            do
             {
               //Create a graph  
               isEnoughEdge = gph.createGraph(NoofNodes,density);
               
               //If the graph has enough edge, continue with the dfs and dijikstra
               if(isEnoughEdge)
               {
                //alg.printGraph();
             
                checkConnection = gph.dfs(rootNode);
                
                if(checkConnection == true)
                    {
                        System.out.println("Graph generated");
                        
                        
                        
                        long start = System.currentTimeMillis();
                        minDist =   gph.dijikstraSimpleHeap(rootNode);
                        long stop = System.currentTimeMillis();

                        long diff = stop-start;
                        System.out.format("Dijikstra Algorithm using Simple Heap completed in %d ms\n",diff);
                        
                      
                        
                        
                        long start1 = System.currentTimeMillis();
                        minDist =   gph.dijkstraFibonacci(rootNode);
                        long stop1 = System.currentTimeMillis();

                        long diff1 = stop1-start1;
                        System.out.format("Dijikstra Algorithm using Fibonacci Heap completed in %d ms\n",diff1);
                        
                    }
                else
                    {
                        gph = new Graph(NoofNodes);
                        System.out.println("Graph is not connected. Generating graph again");
                    }
               }
               else
               {
                   System.out.println("Not enough edges to generate graph.");
                   break;
               }
             } while(checkConnection == false);
            }
            catch(Exception e)
            {
                throw e;
            }
        }
}