
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 *
 * @author w1360994 Mohammed Dirir
 */

public class Grid
{
    private int clicks = 0;
    private int[][] grid;
    private int[][] traversal;
    private JLabel[][] grid_labels;
    private int width;
    private int height;
    private JLabel start;
    private JLabel end;
    private Point begin;
    private Point finish;
    private Color black = new Color(0,0,0);
    private Color path = Color.BLUE;
    private Color white_gray = new Color(255,255,255);
    private Color light_gray = new Color(240,240,240);
    private Color middle_gray = new Color(224,224,224);
    private Color dark_gray = new Color(208,208,208);
    private Border boundary = BorderFactory.createLineBorder(new Color(200,200,255), 5);
    public Grid(int width, int height, int[][] grid)
    {
        
        this.traversal = new int[height][width];
        nullTraversal();
        this.width = width;
        this.height = height;
        this.grid_labels = new JLabel[height][width];
        this.grid = grid;
    }
    public void nullTraversal()
    {
        for(int i=0;i<height;i++)
        {
            for(int j=0;j<width;j++)
            {
                traversal[i][j] = 0;
            }
        }
    }
    public boolean hasTraversed(int x, int y)
    {
        if(traversal[x][y] == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public void traverse(int x, int y)
    {
        traversal[x][y] = 1;
    }
    public void unTraverse(int x, int y)
    {
        traversal[x][y] = 0;
    }
    public void printGrid()
    {
        for(int i = 0;i<height;i++)
        {
            for(int j = 0;j<width;j++)
            {
                System.out.print(grid[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
    public JLabel findButton(int x, int y)
    {
        return grid_labels[x][y];
    }
    public void setBlack(JLabel label)
    {
        label.setBackground(black);
    }
    public void setRed(JLabel label)
    {
        label.setBackground(Color.RED);
    }
    public void reset()
    {
        for(int i = 0;i<height;i++)
        {
            for(int j = 0;j<width;j++)
            {
                if(grid[i][j]==1)
                {
                    grid_labels[i][j].setBackground(white_gray);
                }
                else if(grid[i][j]==2)
                {
                    grid_labels[i][j].setBackground(light_gray);
                }
                else if(grid[i][j]==3)
                {
                    grid_labels[i][j].setBackground(middle_gray);
                }
                else if(grid[i][j]==4)
                {
                    grid_labels[i][j].setBackground(dark_gray);
                }
                else if(grid[i][j]==0)
                {
                    grid_labels[i][j].setBackground(black);
                }
                start = null;
                end = null;
            }
        }
    }
    public JLabel[][] getLabels()
    {
        for(int i = 0;i<height;i++)
        {
            for(int j = 0;j<width;j++)
            {
                grid_labels[i][j] = new JLabel("");
                if(grid[i][j]==1)
                {
                    grid_labels[i][j].setBackground(white_gray);
                }
                else if(grid[i][j]==2)
                {
                    grid_labels[i][j].setBackground(light_gray);
                }
                else if(grid[i][j]==3)
                {
                    grid_labels[i][j].setBackground(middle_gray);
                }
                else if(grid[i][j]==4)
                {
                    grid_labels[i][j].setBackground(dark_gray);
                }
                else if(grid[i][j]==0)
                {
                    grid_labels[i][j].setBackground(black);
                }
                grid_labels[i][j].setBounds(new Rectangle(40,40));
                grid_labels[i][j].setBorder(boundary);
                grid_labels[i][j].setOpaque(true);
                grid_labels[i][j].addMouseListener(new Listener());
            }
        }
        return grid_labels;
    }
    public int getHeight()
    {
        return height;
    }
    public int getWidth()
    {
        return width;
    }
    public int getAt(int x, int y)
    {
        return grid[x][y];
    }
    public class Listener implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(((JLabel)e.getSource()).getBackground()!=black)
            {
                if(clicks < 2)
                {
                    for(int i=0;i<height;i++)
                    {
                        for(int j=0;j<width;j++)
                        {
                            if(((JLabel)e.getSource()) == grid_labels[i][j])
                            {
                                if(grid[i][j]==0)
                                {
                                    return;
                                }
                                if(start == null)
                                {
                                    start = ((JLabel)e.getSource());
                                    begin = new Point(i,j);
                                }
                                else
                                {
                                    end = ((JLabel)e.getSource());
                                    finish = new Point(i,j);
                                }
                            }
                        }
                    }
                    ((JLabel)e.getSource()).setBackground(Color.GREEN);
                    clicks++;
                }
            }
            else
            {
                for(int i=0;i<height;i++)
                {
                    for(int j=0;j<width;j++)
                    {
                        if(((JLabel)e.getSource()) == grid_labels[i][j])
                        {
                            if(grid[i][j]==1)
                            {
                                grid_labels[i][j].setBackground(white_gray);
                            }
                            else if(grid[i][j]==2)
                            {
                                grid_labels[i][j].setBackground(light_gray);
                            }
                            else if(grid[i][j]==3)
                            {
                                grid_labels[i][j].setBackground(middle_gray);
                            }
                            else if(grid[i][j]==4)
                            {
                                grid_labels[i][j].setBackground(dark_gray);
                            }
                            else if(grid[i][j]==0)
                            {
                                grid_labels[i][j].setBackground(black);
                            }
                            if(end == grid_labels[i][j])
                            {
                                end = null;
                            }
                            else
                            {
                                start = null;
                            }
                            clicks--;
                        }
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
        }
    }
    public Point getStart()
    {
        return begin;
    }
    public Point getEnd()
    {
        return finish;
    }
}
