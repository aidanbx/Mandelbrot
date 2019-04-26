import processing.core.PApplet;

public class Mandelbrot extends PApplet {
    private float scale = 1, res = 5,dScale=10;
    private int acc = 500;
    private Point renderPoint = new Point(0, 0);
    boolean zoom = true;

    public static void main(String[] args) {
        PApplet.main("Mandelbrot", args);
    }

    public void settings() {
        size(2250, 1500);
    }

    public void setup() {
        background(0);
        alpha(255);
        noStroke();
        rectMode(CENTER);
        render(renderPoint, scale, res);
//        stroke(255);
//        line(0, height / 2, width, height / 2);
//        line(2 * width / 3, 0, 2 * width / 3, height);
        println("done");
    }

    public void draw() {
    }

    public void mouseClicked() {
        background(0);
        println("-----------");
//        if(scale>acc*acc*acc){
//            acc=acc*2;
//        }
        float newW = width / scale, newH = height / scale;
        float newMX = renderPoint.x + (((float)mouseX / width) * newW);
        float newMY = renderPoint.y + (((float)mouseY / height) * newH);
        if (zoom) {
            scale = scale * dScale;
        } else {
            scale = scale / dScale;
        }
        int startTimer = millis();

        newW = width/scale;
        newH = height/scale;
        renderPoint.update(newMX-newW/2, newMY-newH/2);
        render(renderPoint, scale, res);

        println("rendered in: " + (millis() - startTimer) + "ms");
        println("scale: " + scale);
        println("renderPoint: " + renderPoint);
        println("-----------");
    }

    public void keyPressed() {
        if (key == ' ') {
            zoom = !zoom;
        }
    }


    private void render(Point startP, float scl, float rez) {
        int matrix[][] = create(startP, scl, rez);

        for (int i = 0; i < width / rez; i++) {
            for (int j = 1; j < height / rez; j++) {
                fill(10*matrix[i][j]);
                ellipse(i * rez, j * rez, rez + 1, rez + 1);
            }
        }
    }

    private int[][] create(Point startP, float scl, float rez) {
//        float w = (750/scale)*3;
//        float h = (750/scale)*2;
        int xPoints = (int) (width / rez);
        int yPoints = (int) (height / rez);
        int matrix[][] = new int[xPoints][yPoints];
        Complex c = new Complex(startP);

        for (int i = 0; i < xPoints; i++) {
            for (int j = 0; j < yPoints; j++) {
                c.update(startP.add((i / scl) * rez, (j / scl) * rez));
                matrix[i][j] = iterate(c, acc);
            }
        }
        return matrix;


    }

    public int iterate(Complex c, int n) {
        Complex z = new Complex(0, 0);
        for (int i = 0; i < n; i++) {
            if (z.dist(c) > 3) {
                return i;
            }
//            cEllipse(z,30);
//            cEllipse(z.iterate(c),30);
//            cLine(z,z.iterate(c));
            z = z.iterate(c);
        }
        return 0;
    }

    public int juliate(Complex z, Complex c, int n) {
        for (int i = 0; i < n; i++) {
            if (z.dist(c) > 2) {
                return i;
            }
//            cEllipse(z,30);
//            cEllipse(z.iterate(c),30);
//            cLine(z,z.iterate(c));
            z = z.iterate(c);
        }
        return 0;
    }

    void cLine(Complex c1, Complex c2) {
        line(c1.getX(), c1.getY(), c2.getX(), c2.getY());
    }

    void cEllipse(Complex c, float s) {
        ellipse(c.getX(), c.getY(), s, s);
    }

    void pRect(Point p) {
        rectMode(CENTER);
        rect(p.x, p.y, res, res);
    }
}
