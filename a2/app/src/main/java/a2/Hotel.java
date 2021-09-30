package a2;

public class Hotel implements Comparable<Hotel>{
    public int distance;
    public float rating;

    public Hotel (int distance,float rating){
        this.distance = distance;
        this.rating = rating;
    }

    @Override
    public int compareTo(Hotel h){
        Integer Hotel1 = (Integer)this.distance;
        return Hotel1.compareTo(h.distance);
    }
}