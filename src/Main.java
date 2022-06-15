
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author w1360994 Mohammed Dirir
 */

public class Main
{
    
    private ArrayList<Point> final_path;
    private int dist = 0;
    private JFrame window;
    private Grid grid;
    private JLabel[][] buttons;
    private int locx = 0;
    private int locy = 0;
    private JButton start;
    private JButton clear;
    private JLabel distance;
    private JLabel message;
    private JLabel dis;
    private JRadioButton euclidean;
    private JRadioButton manhattan;
    private JRadioButton chebyshev;
    
    private ButtonGroup group;
    public static void main(String[]args)
    {
        Main main = new Main();
        main.initializeData(main.readFile());
        
        main.drawGrid();
        
    }
    public void initializeData(Grid grid)
    {
        this.grid = grid;
        window = new JFrame("Grid");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(600,80);
        window.setLayout(null);
        window.setSize(723, 50+(grid.getHeight()*45));
        window.setVisible(true);
        this.buttons = grid.getLabels();
    }
    
    public void drawGrid()
    {
        for(int i=0;i<grid.getHeight();i++)
        {
            for(int j=0;j<grid.getWidth();j++)
            {
                buttons[i][j].setLocation(locx, locy);
                locx += 35;
                window.add(buttons[i][j]);
            }
            locx = 0;
            locy += 35;
        }
        euclidean = new JRadioButton("Euclidean Path");
        manhattan = new JRadioButton("Manhattan Path");
        chebyshev = new JRadioButton("Chebyshev Path");
        start = new JButton("Start");
        start.setBounds(220, locy+65, 100, 40);
        clear = new JButton("Clear");
        clear.setBounds(340, locy+65, 100, 40);
        distance = new JLabel("(Cost): ");
        distance.setBounds(220, locy+20, 100, 30);
        message = new JLabel("Select the two points on the Grid by clicking on them.");
        message.setBounds(20, locy+120,500,30);
        dis = new JLabel();
        dis.setBounds(280, locy+20, 100, 30);
        euclidean.setSelected(true);
        group = new ButtonGroup();
        group.add(euclidean);
        group.add(manhattan);
        group.add(chebyshev);
        euclidean.setBounds(50, locy+20, 150, 30);
        manhattan.setBounds(50, locy+50, 150, 30);
        chebyshev.setBounds(50, locy+80, 150, 30);
        window.add(euclidean);
        window.add(manhattan);
        window.add(chebyshev);
        window.add(start);
        window.add(clear);
        window.add(distance);
        window.add(message);
        window.add(dis);
        window.repaint();
        start.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(chebyshev.isSelected())
                {
                    Point start = grid.getStart();
                    Point end = grid.getEnd();
                    if(start!=null&&end!=null)
                    {
                        findChebyshev();
                    }
                }
                else if(euclidean.isSelected())
                {
                    Point start = grid.getStart();
                    Point end = grid.getEnd();
                    if(start!=null&&end!=null)
                    {
                        findEuclidean();
                    }
                }
                else if(manhattan.isSelected())
                {
                    Point start = grid.getStart();
                    Point end = grid.getEnd();
                    if(start!=null&&end!=null)
                    {
                        findManhattan();
                    }
                }
            }
        });
        clear.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.nullTraversal();
                grid.reset();
                dis.setText("");
            }
        });
        
    }
    public void lightUpFinal()
    {
        if(final_path!=null)
        {
            for(Point p:final_path)
            {
                grid.setBlack(grid.findButton(p.x, p.y));
            }
            dis.setText(dist+"");
            window.repaint();
        }
    }
    public void lightUpPath(ArrayList<Point> path)
    {
        if(path!=null)
        {
            for(Point p:path)
            {
                grid.setBlack(grid.findButton(p.x, p.y));
            }
            dis.setText(dist+"");
            window.repaint();
        }
    }
    
    //chebyshev pathfinding algorithm
    public void findChebyshev()
    {
        DirectedGraph returnDir = new DirectedGraph();
        int k = 0,l=0;
        // mapping nodes in matrix accordingly to our grid
        for(int y = 0;y<grid.getHeight();y++)
        {
            for(int x = 0;x<grid.getWidth();x++)
            {
                
                if(grid.getAt(y, x)!=0)
                {
                    if((x-1!=-1&&y-1!=-1))
                    {
                        if(grid.getAt(y-1, x-1)!=0)
                        {
                            
                            returnDir.addEdge(k+"", (grid.getWidth()*y+x)-grid.getWidth()-1+"",grid.getAt(y-1, x-1));
                        }
                    }
                    if(y-1!=-1)
                    {
                        if(grid.getAt(y-1, x)!=0)
                        {
                            
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)-grid.getWidth())+"", grid.getAt(y-1, x));
                        }
                    }
                    if((y-1!=-1&&x+1<grid.getWidth()))
                    {
                        if(grid.getAt(y-1, x+1)!=0)
                        {
                            
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)-grid.getWidth()+1)+"", grid.getAt(y-1, x+1));
                        }
                    }
                    if(x-1!=-1)
                    {
                        if(grid.getAt(y, x-1)!=0)
                        {
                            
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)-1)+"", grid.getAt(y, x-1));
                        }
                    }
                    if(x+1<grid.getWidth())
                    {
                        if(grid.getAt(y, x+1)!=0)
                        {
                            
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)+1)+"", grid.getAt(y, x+1));
                        }
                    }
                    if((x-1!=-1&&y+1<grid.getHeight()))
                    {
                        if(grid.getAt(y+1, x-1)!=0)
                        {
                            
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)+grid.getWidth()-1)+"", grid.getAt(y+1, x-1));
                        }
                    }
                    if(y+1<grid.getHeight())
                    {
                        if(grid.getAt(y+1, x)!=0)
                        {
                            
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)+grid.getWidth())+"", grid.getAt(y+1, x));
                        }
                    }
                    if((x+1<grid.getWidth()&&y+1<grid.getHeight()))
                    {
                        if(grid.getAt(y+1, x+1)!=0)
                        {
                            
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)+grid.getWidth()+1)+"", grid.getAt(y+1, x+1));
                        }
                    }
                    returnDir.addVertex(k+"");
                }
                k++;
            }
            System.out.println("");
        }
        List<Vertex> list = returnDir.getVertices();
        Collections.sort(list);
        Stopwatch timer = new Stopwatch();
        String start = (grid.getStart().x*grid.getWidth()+grid.getStart().y)+"";
        Vertex str = null;
        for(Vertex v:list)
        {
            if(v.getLabel().equals(start))
            {
                str = v;
                break;
            }
        }
        for(Vertex v:list)
        {
            
        }
        
        
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(returnDir);
        dijkstra.run(str);
        
       
       // The time elapsed is printed in the console 
        StdOut.println("Total time Taken: "+timer.elapsedTime());
        Vertex v = new Vertex((grid.getEnd().x*grid.getWidth()+grid.getEnd().y)+"");
        LinkedList<Vertex> path = dijkstra.getPath(v);
        String pth = "";
        if(path==null)
        {
            pth = "NO PATH";
            System.out.println("shortest path to "+v.getLabel()+": "+pth);
        }
        else
        {
            int cost = 0;
            for(Vertex vertex : path)
            {
                pth += vertex.getLabel()+" ";
                for(int x = 0;x<grid.getHeight();x++)
                {
                    for(int y = 0;y<grid.getWidth();y++)
                    {
                        if(((x*grid.getWidth()+y)+"").equals(vertex.getLabel()))
                        {
                            grid.setRed(buttons[x][y]);
                            cost += grid.getAt(x, y);
                        }
                    }
                }
            }
            if(path.size()>1||!path.get(0).getLabel().equals(v.getLabel()))
            {
                pth += v.getLabel();
                for(int x = 0;x<grid.getHeight();x++)
                {
                    for(int y = 0;y<grid.getWidth();y++)
                    {
                        if(((x*grid.getWidth()+y)+"").equals(v.getLabel()))
                        {
                            grid.setRed(buttons[x][y]);
                            cost += grid.getAt(x, y);
                        }
                    }
                }
            }
            window.repaint();
            System.out.println("shortest path to "+v.getLabel()+": "+pth+": cost: " +Math.max(Math.abs((grid.getStart().x - grid.getEnd().x)*2) ,Math.abs((grid.getStart().y-grid.getEnd().y)*2)));
            dis.setText(Math.max(Math.abs((grid.getStart().x - grid.getEnd().x)*2) ,Math.abs((grid.getStart().y-grid.getEnd().y)*2))+"");
            
        }
    }
    
    //Euclidean pathfinding algorithm
    public void findEuclidean()
    {
        DirectedGraph returnDir = new DirectedGraph();
        int k = 0,l=0;
        // mapping nodes in matrix accordingly to our grid
        for(int y = 0;y<grid.getHeight();y++)
        {
            for(int x = 0;x<grid.getWidth();x++)
            {
                if(grid.getAt(y, x)!=0)
                {
                    if((x-1!=-1&&y-1!=-1))
                    {
                        if(grid.getAt(y-1, x-1)!=0)
                        {
                           
                            returnDir.addEdge(k+"", (grid.getWidth()*y+x)-grid.getWidth()-1+"",grid.getAt(y-1, x-1));
                        }
                    }
                    if(y-1!=-1)
                    {
                        if(grid.getAt(y-1, x)!=0)
                        {
                            
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)-grid.getWidth())+"", grid.getAt(y-1, x));
                        }
                    }
                    if((y-1!=-1&&x+1<grid.getWidth()))
                    {
                        if(grid.getAt(y-1, x+1)!=0)
                        {
                            
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)-grid.getWidth()+1)+"", grid.getAt(y-1, x+1));
                        }
                    }
                    if(x-1!=-1)
                    {
                        if(grid.getAt(y, x-1)!=0)
                        {
                            
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)-1)+"", grid.getAt(y, x-1));
                        }
                    }
                    if(x+1<grid.getWidth())
                    {
                        if(grid.getAt(y, x+1)!=0)
                        {
                            
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)+1)+"", grid.getAt(y, x+1));
                        }
                    }
                    if((x-1!=-1&&y+1<grid.getHeight()))
                    {
                        if(grid.getAt(y+1, x-1)!=0)
                        {
                            
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)+grid.getWidth()-1)+"", grid.getAt(y+1, x-1));
                        }
                    }
                    if(y+1<grid.getHeight())
                    {
                        if(grid.getAt(y+1, x)!=0)
                        {
                           
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)+grid.getWidth())+"", grid.getAt(y+1, x));
                        }
                    }
                    if((x+1<grid.getWidth()&&y+1<grid.getHeight()))
                    {
                        if(grid.getAt(y+1, x+1)!=0)
                        {
                            
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)+grid.getWidth()+1)+"", grid.getAt(y+1, x+1));
                        }
                    }
                    returnDir.addVertex(k+"");
                }
                k++;
            }
        }
        List<Vertex> list = returnDir.getVertices();
        Collections.sort(list);
        Scanner sc = new Scanner(System.in);
        Stopwatch timer = new Stopwatch();
        String start = (grid.getStart().x*grid.getWidth()+grid.getStart().y)+"";
        Vertex str = null;
        for(Vertex v:list)
        {
            if(v.getLabel().equals(start))
            {
                str = v;
                break;
            }
        }
        
        
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(returnDir);
        dijkstra.run(str);
        
        
         // The time elapsed is printed in the console 
        StdOut.println("Total time Taken: "+timer.elapsedTime());
        Vertex v = new Vertex((grid.getEnd().x*grid.getWidth()+grid.getEnd().y)+"");
        LinkedList<Vertex> path = dijkstra.getPath(v);
        String pth = "";
        if(path==null)
        {
            pth = "NO PATH";
            System.out.println("shortest path to "+v.getLabel()+": "+pth);
        }
        else
        {
            int cost = 0;
            for(Vertex vertex : path)
            {
                pth += vertex.getLabel()+" ";
                for(int x = 0;x<grid.getHeight();x++)
                {
                    for(int y = 0;y<grid.getWidth();y++)
                    {
                        if(((x*grid.getWidth()+y)+"").equals(vertex.getLabel()))
                        {
                            grid.setRed(buttons[x][y]);
                            cost += grid.getAt(x, y);
                        }
                    }
                }
            }
            if(path.size()>1||!path.get(0).getLabel().equals(v.getLabel()))
            {
                pth += v.getLabel();
                for(int x = 0;x<grid.getHeight();x++)
                {
                    for(int y = 0;y<grid.getWidth();y++)
                    {
                        if(((x*grid.getWidth()+y)+"").equals(v.getLabel()))
                        {
                            grid.setRed(buttons[x][y]);
                            cost += grid.getAt(x, y);
                        }
                    }
                }
            }
            window.repaint();
            System.out.println("shortest path to "+v.getLabel()+": "+pth+": cost: "+Math.sqrt(Math.pow((grid.getStart().x-grid.getEnd().x),2)+Math.pow((grid.getStart().y-grid.getEnd().y),2)));
            dis.setText(Math.sqrt(Math.pow((grid.getStart().x-grid.getEnd().x),2)+Math.pow((grid.getStart().y-grid.getEnd().y),2))+"");
            
        }
    }
    
    //Manhattan pathfinding algorithm
    public void findManhattan()
    {
        DirectedGraph returnDir = new DirectedGraph();
        int k = 0,l=0;
        // mapping nodes in matrix accordingly to our grid
        for(int y = 0;y<grid.getHeight();y++)
        {
            for(int x = 0;x<grid.getWidth();x++)
            {
                if(grid.getAt(y, x)!=0)
                {
                    if((x-1!=-1&&y-1!=-1))
                    {
                        if(grid.getAt(y-1, x-1)!=0)
                        {
                            
                            returnDir.addEdge(k+"", (grid.getWidth()*y+x)-grid.getWidth()-1+"",grid.getAt(y-1, x-1));
                        }
                    }
                    if(y-1!=-1)
                    {
                        if(grid.getAt(y-1, x)!=0)
                        {
                            
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)-grid.getWidth())+"", grid.getAt(y-1, x));
                        }
                    }
                    if((y-1!=-1&&x+1<grid.getWidth()))
                    {
                        if(grid.getAt(y-1, x+1)!=0)
                        {
                            
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)-grid.getWidth()+1)+"", grid.getAt(y-1, x+1));
                        }
                    }
                    if(x-1!=-1)
                    {
                        if(grid.getAt(y, x-1)!=0)
                        {
                            
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)-1)+"", grid.getAt(y, x-1));
                        }
                    }
                    if(x+1<grid.getWidth())
                    {
                        if(grid.getAt(y, x+1)!=0)
                        {
                            
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)+1)+"", grid.getAt(y, x+1));
                        }
                    }
                    if((x-1!=-1&&y+1<grid.getHeight()))
                    {
                        if(grid.getAt(y+1, x-1)!=0)
                        {
                           
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)+grid.getWidth()-1)+"", grid.getAt(y+1, x-1));
                        }
                    }
                    if(y+1<grid.getHeight())
                    {
                        if(grid.getAt(y+1, x)!=0)
                        {
                            
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)+grid.getWidth())+"", grid.getAt(y+1, x));
                        }
                    }
                    if((x+1<grid.getWidth()&&y+1<grid.getHeight()))
                    {
                        if(grid.getAt(y+1, x+1)!=0)
                        {
                            
                            returnDir.addEdge(k+"", ((grid.getWidth()*y+x)+grid.getWidth()+1)+"", grid.getAt(y+1, x+1));
                        }
                    }
                    returnDir.addVertex(k+"");
                }
                k++;
            }
        }
        List<Vertex> list = returnDir.getVertices();
        Collections.sort(list);
        Scanner sc = new Scanner(System.in);
        Stopwatch timer = new Stopwatch();
        String start = (grid.getStart().x*grid.getWidth()+grid.getStart().y)+"";
        Vertex str = null;
        for(Vertex v:list)
        {
            if(v.getLabel().equals(start))
            {
                str = v;
                break;
            }
        }
        
        
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(returnDir);
        dijkstra.run(str);
        
        
         // The time elapsed is printed in the console 
        StdOut.println("Total time Taken: "+timer.elapsedTime());
        Vertex v = new Vertex((grid.getEnd().x*grid.getWidth()+grid.getEnd().y)+"");
        
        LinkedList<Vertex> path = dijkstra.getPath(v);
        String pth = "";
        if(path==null)
        {
            pth = "NO PATH";
            
            System.out.println("shortest path to "+v.getLabel()+": "+pth);
            
        }
        else
        {
            int cost = 0;
            for(Vertex vertex : path)
            {
                pth += vertex.getLabel()+" ";
                for(int x = 0;x<grid.getHeight();x++)
                {
                    for(int y = 0;y<grid.getWidth();y++)
                    {
                        if(((x*grid.getWidth()+y)+"").equals(vertex.getLabel()))
                        {
                            grid.setRed(buttons[x][y]);
                            cost += grid.getAt(x, y);
                        }
                    }
                }
            }
            if(path.size()>1||!path.get(0).getLabel().equals(v.getLabel()))
            {
                pth += v.getLabel();
                for(int x = 0;x<grid.getHeight();x++)
                {
                    for(int y = 0;y<grid.getWidth();y++)
                    {
                        if(((x*grid.getWidth()+y)+"").equals(v.getLabel()))
                        {
                            grid.setRed(buttons[x][y]);
                            cost += grid.getAt(x, y);
                        }
                    }
                }
            }
            window.repaint();
            System.out.println("shortest path to "+v.getLabel()+": "+pth+": cost: "+cost);
            dis.setText(cost+"");
        }
    }
    public void printPath()
    {
        for(Point p:final_path)
        {
            System.out.println(p);
        }
    }
    public void printPath(ArrayList<Point> path)
    {
        for(Point p:path)
        {
            System.out.println(p);
        }
    }
    public Grid readFile()
    {
        
        Scanner sc = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<String>();
        System.out.print("Enter File name to read: ");
        String file = sc.nextLine();
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(file));
            for (String s = in.readLine(); s != null; s = in.readLine())
            {
                lines.add(s);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found: " + file);
            System.exit(1);
        }
        catch (IOException e)
        {
            System.exit(2);
        }
        int[][] grid = new int[lines.size()][lines.get(0).length()];
        int i = 0;
        for(String line: lines)
        {
            for(int j = 0;j<line.length();j++)
            {
                grid[i][j] = Character.getNumericValue(line.charAt(j));
            }
            i++;
        }
        return new Grid(lines.get(0).length(),lines.size(),grid);
    }
}
