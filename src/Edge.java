/**
 *
 * @author w1360994
 */
public class Edge implements Comparable<Edge>
{    
    private Vertex to;
    private Vertex from;
    private int weight;
    public Edge(Vertex from,Vertex to,int weight)
    {
        this.to = to;
        this.from = from;
        this.weight = weight;
    }
    public Vertex getTo() 
    {
        return to;
    }
    public Vertex getFrom() 
    {
        return from;
    }
    public int getWeight() 
    {
        return weight;
    }
    @Override
    public boolean equals(Object o)
    {
        return this.weight == ((Edge)o).getWeight()&&this.to.equals(((Edge)o).getTo())&&this.from.equals(((Edge)o).getFrom());
    }

    @Override
    public int compareTo(Edge o) 
    {
        return Integer.compare(this.weight, o.getWeight());
    }

    @Override
    public String toString() {
        return "from: "+ from + "  to: " + to +"  weight: " + weight;
    }
    
}
