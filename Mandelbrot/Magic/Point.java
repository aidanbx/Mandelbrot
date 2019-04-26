//public class Point<X,Y> {
//    public X x;
//    public Y y;
//
//    public Point(X x, Y y) {
//        this.x = x;
//        this.y = y;
//    }
//
//}
public class Point {
    public float x;
    public float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Point add(Point p){
        return new Point(p.x+x,p.y+y);
    }
    public Point add(float i, float j){
        return new Point(x+i,y+j);
    }
    public Point update(float x, float y){
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public String toString() {
        return "(" +
                x +
                ", " + y +
                ')';
    }
}
