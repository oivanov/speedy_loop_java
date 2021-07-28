package speedy_loop;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Routes.NoSuchRouteException, FileNotFoundException {

        Routes graph = new Routes();



        String strFileNameToLoad = args[0];

        // need to add error handling here
        Scanner inFile1 = new Scanner(new File(strFileNameToLoad)).useDelimiter(",\\s*");
        ArrayList<String> edges = new ArrayList<>();


        while (inFile1.hasNext()) {
            // find next line
            edges.add(inFile1.next());
        }
        inFile1.close();

        Collections.sort(edges);

        Map<String,Town> mapTowns = new HashMap<>();


        for(int i = 0; i < edges.size(); i++){
            //we will parse the edge. we expect the format like so: AB<weight>

            String oneEdge = edges.get(i);
            String origin = oneEdge.substring(0,1);
            String destination = oneEdge.substring(1,1);
            int weight = Integer.parseInt(oneEdge.substring(2));
            Town townOrigin = new Town(origin.toUpperCase(Locale.ROOT));
            Town townDestination = new Town(destination.toUpperCase(Locale.ROOT));
            Edge newEdge = graph.routingTable.put(townOrigin, new Edge(townOrigin,townDestination,weight));

            if (mapTowns.containsKey(origin.toUpperCase(Locale.ROOT)) == false){
                mapTowns.put(origin.toUpperCase(Locale.ROOT),townOrigin);
            }
            if (mapTowns.containsKey(destination.toUpperCase(Locale.ROOT)) == false){
                mapTowns.put(destination.toUpperCase(Locale.ROOT),townDestination);
            }

            while(i < edges.size() && origin.toUpperCase() == edges.get(i+1).substring(0,1).toUpperCase()){
                i++;
                System.out.println("i ==  " + i);
                String nextEdge = edges.get(i);
                String nextDestination = nextEdge.substring(1,1);
                int nextWeight = Integer.parseInt(nextEdge.substring(2));
                Town nextTownDestination = new Town(nextDestination.toUpperCase(Locale.ROOT));
                newEdge.next(new Edge(townOrigin,nextTownDestination,nextWeight));

                if (mapTowns.containsKey(nextDestination.toUpperCase(Locale.ROOT)) == false){
                    mapTowns.put(nextDestination.toUpperCase(Locale.ROOT),nextTownDestination);
                }
            }

        }

//        Town  a = new Town("A");
//        Town  b = new Town("B");
//        Town  c = new Town("C");
//        Town  d = new Town("D");
//        Town  e = new Town("E");

      /*  Adjacency List

          A | {B,5}, {D,5}, {E,7}
          B | {C,4}
          C | {D,8}, {E,2}
          D | {C,8}, {E,6}
          E | {B,3}

      Created in code below.
      */
//        graph.routingTable.put(a, new Edge(a,b,5).next(new Edge(a,d,5).next(new Edge(a,e,7))));
//        graph.routingTable.put(b, new Edge(b,c,4));
//        graph.routingTable.put(c, new Edge(c,d,8).next(new Edge(c,e,2)));
//        graph.routingTable.put(d, new Edge(d,c,8).next(new Edge(d,e,6)));
//        graph.routingTable.put(e, new Edge(e,b,3));

        // }
        //1. Distance of route A-B-C
        ArrayList<Town> routes1 = new ArrayList<Town>();
        routes1.add(a);
        routes1.add(b);
        routes1.add(c);

        try{
            System.out.println("Output#1  " + graph.findDistanceBetweenTowns(routes1));
        } catch (Routes.NoSuchRouteException noSuchExc){
            System.out.println(noSuchExc.getMessage());
        }

        //2. Distance of route A-D
        ArrayList<Town> routes2 = new ArrayList<Town>();
        routes2.add(a);
        routes2.add(d);

        try{
            System.out.println("Output#2  " + graph.findDistanceBetweenTowns(routes2));
        } catch (Routes.NoSuchRouteException noSuchExc){
            System.out.println(noSuchExc.getMessage());
        }
        //3. Distance of route A-D-C
        ArrayList<Town> routes3 = new ArrayList<Town>();
        routes3.add(a);
        routes3.add(d);
        routes3.add(c);

        try{
            System.out.println("Output#3  " + graph.findDistanceBetweenTowns(routes3));
        } catch (Routes.NoSuchRouteException noSuchExc){
            System.out.println(noSuchExc.getMessage());
        }

        //4. Distance of route A-E-B-C-D
        ArrayList<Town> routes4 = new ArrayList<Town>();
        routes4.add(a);
        routes4.add(e);
        routes4.add(b);
        routes4.add(c);
        routes4.add(d);

        try{
            System.out.println("Output#4  " + graph.findDistanceBetweenTowns(routes4));
        } catch (Routes.NoSuchRouteException noSuchExc){
            System.out.println(noSuchExc.getMessage());
        }


        //5. Distance of route A-E-D
        ArrayList<Town> routes5 = new ArrayList<Town>();
        routes5.add(a);
        routes5.add(e);
        routes5.add(d);
        try{
            System.out.println("Output#5  " + graph.findDistanceBetweenTowns(routes5));
        } catch (Routes.NoSuchRouteException noSuchExc){
            System.out.println(noSuchExc.getMessage());
        }


        //6. Number of trips starting at C,ending at C with 3 stops max
        System.out.println("Output#6  " + graph.numberOfStopsBetweenTowns(c,c,3));

        //7. Number of trips starting at A,ending at C with exactly 4 stops
        System.out.println("Output#7  " + graph.numberOfRoutsBetweenTownsExactStopCount(a,c,4));

        //8.The length of the shortest route from A to C.
        System.out.println("Output#8  " + graph.shortestRouteBetweenTowns(a,c));

        //9.The length of the shortest route from B to B.

        System.out.println("Output#9  " + graph.shortestRouteBetweenTowns(b,b));

        //10.The number of different routes from C to C with a distance of less than 30
        System.out.println("Output#10  " + graph.numRoutesWithin(c,c,30));

//        //Extension 1.
//
//
//
//
//        System.out.println("Output#11   " + graph.calculateTotalTimeForRoute(routes1));
//
//        System.out.println("Output#14   " + graph.calculateTotalTimeForRoute(routes4));
//
//        System.out.println("Output#15   " + graph.calculateTotalTimeForRoute(routes5));

    }
}
