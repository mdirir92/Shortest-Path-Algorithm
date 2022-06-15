
/**
 *
 * @author w1360994 Mohammed Dirir
 */
public class Vertex implements Comparable<Vertex> 
{    
    private String label;    
 
    public Vertex(String label)
    {
        this.label = label;   
    }
    public String getLabel() 
    {
        return label;
    }
    @Override
    public boolean equals(Object obj) 
    {
        if (obj == null) 
        {
            return false;
        }
        if (getClass() != obj.getClass()) 
        {
            return false;
        }
        final Vertex other = (Vertex) obj;
        if (!this.label.equals(other.label)) 
        {
            return false;
        }
        return true;
    }
    @Override
    public int compareTo(Vertex t) 
    {
        return Integer.compare(Integer.parseInt(this.label), Integer.parseInt(t.label));
    }
    @Override
    public String toString() 
    {
        return this.label;
    }
}
