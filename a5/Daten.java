import java.util.ArrayList;

public class Daten
{
    public ArrayList<Integer> links=new ArrayList<Integer>();
    public ArrayList<Integer> rechts=new ArrayList<Integer>();
    public long abstand;
    public int gewicht;

    public Daten(int input)
    {
        abstand=-1;
        gewicht=input;
    }
}
