
public class Complex{
    private float re, im, width=2250, height=1500;
    private Point p;

    public Complex(float re, float im) {
        this.re = re;
        this.im = im;
        this.p = new Point(oldX(re),oldY(im));
    }
    public Complex(float re, float im, float w, float h) {
        width = w;
        height = h;
        this.re = newX(re);
        this.im = newY(im);
        this.p = new Point(re,im);
    }
    public Complex(Point p){
        this.p = p;
        this.re = newX(p.x);
        this.im = newY(p.y);
    }

//    public void elli(float c, float d) {
//        ellipse(oldX(re), oldY(im), c, d);
//    }
    public void update(Point p){
        re = newX(p.x);
        im = newY(p.y);
    }
    public Point toPoint(){
        return new Point(oldX(re),oldY(im));
    }
    public Complex sq() {
        return new Complex(re * re - im * im,2 * re * im);
    }

    public Complex add(Complex c) {
        return new Complex(re + c.re(),im + c.im());
    }

    public Complex iterate(Complex c) { return sq().add(c); }

    public double radius() { return Math.sqrt(re * re + im * im); }

    public double dist(Complex c) {
        return Math.sqrt(
                Math.abs(re-c.re())*Math.abs(re-c.re()) +
                Math.abs(im-c.im())*Math.abs(im-c.im()));
    }

    public float getX(){ return oldX(re); }
    public float getY() { return oldY(im); }
    private float newX(float x) { return 3 * (x - 2 * width / 3) / width; }

    private float oldX(float x) { return (x * width) / 3 + (2 * width / 3); }

    private float newY(float y) { return ((height / 2) - y) / height * 2; }

    private float oldY(float y) { return (height / 2) - (height * y) / 2; }


    public String toString() {
        return "(" + re + ", " + im + "i)";
    }
    public void setRe(float re) {
        this.re = re;
    }
    public void setIm(float im) { this.im = im; }

    public float re() { return re; }

    public float im() { return im; }
}
