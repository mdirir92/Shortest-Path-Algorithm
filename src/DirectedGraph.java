
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author w1360994 Mohammed Dirir
 */
public class DirectedGraph
{
    ArrayList<Edge> edges = new ArrayList<Edge>();
    ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    
    public void addEdge(String p, String q,int w) 
    {
        Vertex x = new Vertex(p);
        Vertex y = new Vertex(q);
        this.edges.add(new Edge(x,y,w));
    }
    public void addEdge(Edge edge) 
    {
        this.edges.add(edge);
    }    
    public List<Vertex> getVertices() 
    {
        return vertices;
    }
    public void addVertex(String g)
    {
        vertices.add(new Vertex(g));
    }
    public Vertex getVertex(String str) 
    {
        for(Vertex ver:getVertices())
        {
            if(ver.getLabel().equals(str))
            {
                return ver;
            }
        }
        return null;
    }
    public Set<String> getVerticesLabels() 
    {
        Set<String> ret = new HashSet<String>();
        for(int y=0;y<edges.size();y++)
        {
            if(!ret.contains(edges.get(y).getTo().getLabel()))
            {
                ret.add(edges.get(y).getTo().getLabel());
            }
            if(!ret.contains(edges.get(y).getFrom().getLabel()))
            {
                ret.add(edges.get(y).getFrom().getLabel());
            }
        }
        return ret;
    }
    public ArrayList<Edge> getEdges()
    {
        return edges;
    }
}
