import processing.core.PApplet;

public class Mandelbulb extends PApplet {
    public static void main(String[] args) {
        PApplet.main("Mandelbulb", args);
    }

    public void settings() {
        size(1000, 1000, P3D);
    }


    private float z = 0;

    public void setup() {
        background(0);
        stroke(255);
        noFill();
    }

    public void draw() {
        background(0);
        for(int x=0;x<width;x+=50){
            for (int z = 0; z > -width; z -= 50) {
                squareZ(x,height,z,50);
                //line(0,height,z,width,height,z);
                //line(x,height,0,x,height,-width);
            }
        }

        line(width / 3, height/3, z, width/3, height, z);
    }
    public void squareZ(double x, double y, double z, double h){
        rectMode(CENTER);
        pushMatrix();
        translate((float)x,(float)y,(float)z);
        rotateX(PI/2);
        rect(0,0,(float)h,(float)h);
        popMatrix();
    }
    @Override
    public void keyPressed() {
        switch (key) {
            case ('q'):
                z += 10;
                println(z);
                break;
            case ('a'):
                z -= 10;
                println(z);
                break;
            default:
                break;

        }
    }
}
