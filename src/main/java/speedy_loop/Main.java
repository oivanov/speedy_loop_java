package speedy_loop;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Routes graph = new Routes();

        //loading TestInputGraph.txt that has this Graph: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7

        String strFileNameToLoad = args[0];

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
            String destination = oneEdge.substring(1,2);
            int weight = Integer.parseInt(oneEdge.substring(2));
            Town townOrigin = new Town(origin.toUpperCase(Locale.ROOT));
            Town townDestination = new Town(destination.toUpperCase(Locale.ROOT));
            List<Edge> townEdges = new ArrayList<>();
            townEdges.add(new Edge(townOrigin,townDestination,weight));


            if (!mapTowns.containsKey(origin.toUpperCase(Locale.ROOT))){
                mapTowns.put(origin.toUpperCase(Locale.ROOT),townOrigin);
            }
            if (!mapTowns.containsKey(destination.toUpperCase(Locale.ROOT))){
                mapTowns.put(destination.toUpperCase(Locale.ROOT),townDestination);
            }
            // we need to peek into the next edge first, to see if it has the same origin - that's why i + 1 in the loop
            while(i + 1 < edges.size() && origin.equalsIgnoreCase(edges.get(i+1).substring(0,1))){
                i++;
                String nextEdge = edges.get(i);
                String nextDestination = nextEdge.substring(1,2);
                int nextWeight = Integer.parseInt(nextEdge.substring(2));
                Town nextTownDestination = new Town(nextDestination.toUpperCase(Locale.ROOT));
                townEdges.add(new Edge(townOrigin,nextTownDestination,nextWeight));

                if (!mapTowns.containsKey(nextDestination.toUpperCase(Locale.ROOT))){
                    mapTowns.put(nextDestination.toUpperCase(Locale.ROOT),nextTownDestination);
                }
            }
            //adding the "chained" edges starting from the end
            for ( int j = townEdges.size() - 1; j >0 ; j--){
                townEdges.get(j-1).next(townEdges.get(j));
            }
            graph.routingTable.put(townOrigin, townEdges.get(0));

        }


      /*  Adjacency List from the test input looks like this:

          A | {B,5}, {D,5}, {E,7}
          B | {C,4}
          C | {D,8}, {E,2}
          D | {C,8}, {E,6}
          E | {B,3}
     */

        //1. The distance of the route A-B-C.
        ArrayList<Town> routes1 = new ArrayList<>();
        routes1.add(mapTowns.get("A"));
        routes1.add(mapTowns.get("B"));
        routes1.add(mapTowns.get("C"));

        try{
            System.out.println("Output#1  " + graph.findDistanceBetweenTowns(routes1));
        } catch (Routes.NoSuchRouteException noSuchExc){
            System.out.println(noSuchExc.getMessage());
        }

        //2. The distance of the route A-D.
        ArrayList<Town> routes2 = new ArrayList<>();
        routes2.add(mapTowns.get("A"));
        routes2.add(mapTowns.get("D"));

        try{
            System.out.println("Output#2  " + graph.findDistanceBetweenTowns(routes2));
        } catch (Routes.NoSuchRouteException noSuchExc){
            System.out.println(noSuchExc.getMessage());
        }

        //3. The distance of the route A-D-C.
        ArrayList<Town> routes3 = new ArrayList<>();
        routes3.add(mapTowns.get("A"));
        routes3.add(mapTowns.get("D"));
        routes3.add(mapTowns.get("C"));

        try{
            System.out.println("Output#3  " + graph.findDistanceBetweenTowns(routes3));
        } catch (Routes.NoSuchRouteException noSuchExc){
            System.out.println(noSuchExc.getMessage());
        }

        //4. The distance of the route A-E-B-C-D.
        ArrayList<Town> routes4 = new ArrayList<>();
        routes4.add(mapTowns.get("A"));
        routes4.add(mapTowns.get("E"));
        routes4.add(mapTowns.get("B"));
        routes4.add(mapTowns.get("C"));
        routes4.add(mapTowns.get("D"));

        try{
            System.out.println("Output#4  " + graph.findDistanceBetweenTowns(routes4));
        } catch (Routes.NoSuchRouteException noSuchExc){
            System.out.println(noSuchExc.getMessage());
        }

        //5. The distance of the route A-E-D.
        ArrayList<Town> routes5 = new ArrayList<>();
        routes5.add(mapTowns.get("A"));
        routes5.add(mapTowns.get("E"));
        routes5.add(mapTowns.get("D"));
        try{
            System.out.println("Output#5  " + graph.findDistanceBetweenTowns(routes5));
        } catch (Routes.NoSuchRouteException noSuchExc){
            System.out.println(noSuchExc.getMessage());
        }

        //6. The number of trips starting at C and ending at C with a maximum of 3 stops.
        System.out.println("Output#6  " + graph.numberOfStopsBetweenTowns(mapTowns.get("C"),mapTowns.get("C"),3));

        //7. The number of trips starting at A and ending at C with exactly 4 stops.
        System.out.println("Output#7  " + graph.numberOfRoutsBetweenTownsExactStopCount(mapTowns.get("A"),mapTowns.get("C"),4));

        //8.The length of the shortest route (in terms of distance to travel) from A to C.
        System.out.println("Output#8  " + graph.shortestRouteBetweenTowns(mapTowns.get("A"),mapTowns.get("C")));

        //9.The length of the shortest route (in terms of distance to travel) from B to B.
        System.out.println("Output#9  " + graph.shortestRouteBetweenTowns(mapTowns.get("B"),mapTowns.get("B")));

        //10.The number of different routes from C to C with a distance of less than 30.
        System.out.println("Output#10  " + graph.numRoutesWithin(mapTowns.get("C"),mapTowns.get("C"),30));

    }
}
