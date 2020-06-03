import processing.core.PApplet;

public class Tree extends PApplet {
    private int length = 100, maxiter = 1,maxC=7;

    public static void main(String[] args) {
        PApplet.main("Tree", args);
    }

    public void settings() {
        size(500, 500);
    }

    public void setup() {
        background(255);
        stroke(0);

        drawSerp(width / 2, height - height/8, height - height/8, 1, 1);
//        drawSerp(width/2,height*((float)Math.sqrt(3)/2),height/2,(float)0.5);
        //drawTree(width/2,height/2,100,1);
    }

    public void draw() {
        //background(255);

        //drawTree(width/2,height/2,360*(mouseX/(float)width),1);
        //length=mouseY;
    }

    public void drawSerp(float x, float y, float size, int iter, int maxIter) {
        colorMode(HSB,maxC);
        if (iter > maxIter) return;
//        noStroke();
        strokeWeight(size / 200);
        fill(maxC-iter,maxC/(float)1.5,maxC);
        equiT(x, y, size / 2);
        drawSerp(x - size / 4, y, size / 2, iter + 1, maxIter);
        drawSerp(x + size / 4, y, size / 2, iter + 1, maxIter);
        drawSerp(x, y - (size / 2) * ((float) Math.sqrt(3) / 2), size / 2, iter + 1, maxIter);

    }

    public void keyPressed() {
        colorMode(RGB,255);
        switch (key) {
            case('c'):
                maxiter=1;
                background(255);
                drawSerp(width / 2, height - height/8, height, 1, maxiter);
                break;
            case('h'):
                maxC++;
                background(255);
                drawSerp(width / 2, height - height/8, height, 1, maxiter);
                break;
            case('n'):
                maxC--;
                background(255);
                drawSerp(width / 2, height - height/8, height, 1, maxiter);
                break;
            case (' '):
                maxiter++;
                background(255);
                drawSerp(width / 2, height - height/8, height, 1, maxiter);
                break;
            case ('='):
                background(255);
                drawSerp(width / 2, height - height/8, height, 1, maxiter);
                saveFrame("serp" + maxiter);
                break;
        }
    }

    public void equiT(float x, float y, float size) {
        triangle(x - size / 2, y - size * ((float) Math.sqrt(3) / 2), x + size / 2, y - size * ((float) Math.sqrt(3) / 2), x, y);
    }

    public void drawTree(float x, float y, float rot, float scale) {
        if (scale > 0.2) {
            translate(x, y);
            pushMatrix();
            rotate(radians(rot));
            line(0, 0, 0, -length * scale);
            translate(0, -length * scale);
            drawTree(0, 0, rot, scale * (float) 0.7);
            popMatrix();
            pushMatrix();
            rotate(radians(-rot));
            line(0, 0, 0, -length * scale);
            translate(0, -length * scale);
            drawTree(0, 0, rot, scale * (float) 0.7);
            popMatrix();

        }
        return;
    }
}
