
import java.util.*;
/**
 *
 * @author w1360994 Mohammed Dirir
 */

public class DijkstraAlgorithm
{
    private Vertex start;
    private final List<Edge> edges;
    private final List<Vertex> vertices;
    private ArrayList<Vertex> settled;
    private ArrayList<Vertex> unSettled;
    private Map<String, String> previous;
    private ArrayList<Edge> distance;

    public DijkstraAlgorithm(DirectedGraph graph) 
    {
        this.edges = graph.getEdges();
        this.vertices = graph.getVertices();
    }
    public void run(Vertex start) 
    {
        this.start = start; 
        settled = new ArrayList<Vertex>();
        unSettled = new ArrayList<Vertex>();
        distance = new ArrayList<Edge>();
        previous = new HashMap<String, String>();
        distance.add(new Edge(start,start, 0));
        unSettled.add(start);
        while(unSettled.size()>0)
        {
            Collections.sort(distance);
            
            Vertex node = null;
            for(Edge edge : distance)
            {
                if(!settled.contains(edge.getTo())) 
                {
                    node = edge.getTo();
                    break;
                }
            }
            settled.add(node);
            unSettled.remove(node);
            List<Vertex> neighbours = new ArrayList<Vertex>();
            for (Edge edge : edges) 
            {
                if(edge.getFrom().equals(node)) 
                {
                    neighbours.add(edge.getTo());
                }
            }
            for (Vertex to : neighbours) 
            {
                int weight = 0;
                for (Edge edge : edges) 
                {
                    if (edge.getFrom().equals(node)&& edge.getTo().equals(to)) 
                    {
                        weight = edge.getWeight();
                    }
                }
                if (getShortestDistance(to) > (getShortestDistance(node) + weight)||getShortestDistance(to)==-1) 
                {
                    distance.add(new Edge(start, to, (getShortestDistance(node) + weight)));
                    previous.put(to.getLabel(), node.getLabel());
                    if(!unSettled.contains(to))
                    {
                        unSettled.add(to);
                    }
                }
            }
        }
    }
    public void printEdges()
    {
        for(Edge e:edges)
        {
            System.out.println(e.toString());
        }
    }
    public int getShortestDistance(Vertex to) 
    {
        Integer dist = null;
        for(Edge edge:distance)
        {
            if(edge.getTo().equals(to))
            {
                dist = edge.getWeight();
                return dist;
            }
        }
        return -1; 
    }
    public LinkedList<Vertex> getPath(Vertex to) 
    {    
        LinkedList<Vertex> path = new LinkedList<Vertex>();
        if(this.start.equals(to))
        {
            path.add(start);
            return path;
        }
        Vertex str = null;
        String temp = null;
        
        temp = previous.get(to.getLabel());
        for(Vertex ver:vertices)
        {
            if(ver.getLabel().equals(temp))
            {
                str = ver;
            }
        }
        if(str==null)
        {
            return null;
        }
        path.add(str);
        while (previous.get(str.getLabel()) != null) 
        {
           
            temp = previous.get(str.getLabel());
            for(Vertex ver:vertices)
            {
                if(ver.getLabel().equals(temp))
                {
                    str = ver;
                }
            }
            path.add(str);
        }
        Collections.reverse(path);
        return path;
    }
}
