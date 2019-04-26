import processing.core.PApplet;

import java.math.BigDecimal;

public class Mandelbrot extends PApplet {
    public static void main(String[] args) {
        PApplet.main("Mandelbrot", args);
    }

    private double wScale = 3, hScale = 2;
    private double viewWidth = 3000, viewHeight = 2000, res = 3, dscl = 2;
    private BigDecimal scl = new BigDecimal(1);
    private double rg1 = 2, rg2 = 2.5, rg3 = 3;
    private double hue = 1, sat = 1, bri = 1, renderTime = 0, cxVal, cyVal;
    private double rendPX = viewWidth / 2, rendPY = viewHeight / 2;
    private double maxIter = 200;

    public void settings() {
        size((int) viewWidth, (int) viewHeight);
//        fullScreen();
    }

    public void setup() {
        background(0);
        colorMode(HSB, (int) maxIter);
        noStroke();
        render(imagX(rendPX), imagY(rendPY), scl);
    }

    public void draw() {
        colorMode(RGB, 255);
        fill(115, 50);
        rect(0, 0, 300, 300);
        fill(255);
        text(updateStats(), 10, 20);
        colorMode(HSB, 255);

//        if(mousePressed)
//        println(newX(mouseX));
    }

    public void mouseClicked() {
        rendPX = transX(mouseX);
        rendPY = transY(mouseY);
        render(imagX(rendPX), imagY(rendPY), scl);

    }
//    public void renderCenter(double centerX, double centerY){
//        double newW = width / scl, newH = height / scl;
//        double newMX = rendPX + ((centerX / width) * newW);
//        double newMY = rendPY + ((centerY / height) * newH);
//
//        newW = width/scl;
//        newH = height/scl;
//        rendPX = newMX-newW/2;
//        rendPY = newMY-newH/2;
//        render(rendPX,rendPY,scl,res);
//    }
//    public void renderCenter(double newScale){
//
////        double newW = width / scl, newH = height / scl;
////        double newMX = rendPX + ((((double)width/2) / width) * newW);
////        double newMY = rendPY + ((((double)height/2) / height) * newH);
//        scl = newScale;
////        newW = width/scl;
////        newH = height/scl;
////        rendPX = newMX-newW/2;
////        rendPY = newMY-newH/2;
//        render(rendPX,rendPY,newScale,res);
//    }

    //    public void render(double zX, double zY, double scale, double resolution){
//        double cX = cxVal, cY = cyVal;
//        println("-----------");
//        int startTimer = millis();
//        rectMode(CENTER);
//        for(double i = 0.5;i<width/resolution;i++){
//            for(double j = 0.5;j<height/resolution;j++){
//                fill(10*iterate(zX+((i/scale)*resolution), zY+((j/scale)*resolution),cX,cY,maxIter),255,255);
//                rect((float)(i*resolution),(float)(j*resolution),(float)(resolution),(float)(resolution));
//            }
//        }
//        textSize(30);
//        fill(255);
//
//        renderTime = millis() - startTimer;
//        updateStats();
//
//        noStroke();
//        println("rendered in: " + (millis() - startTimer) + "ms");
//        println();
//        println("scale: " + scale);
//        println("pixel size: ",res);
//        println("iteration scale: ", maxIter);
//        println();
//        println("brightness factor: ", bri);
//        println("saturation factor: ", sat);
//        println("renderPoint: (" + cX + ", " + cY + ")");
//        println("-----------");
//    }
    public void render(double cx, double cy, BigDecimal scale) {
        colorMode(HSB, (int) maxIter);
        println("---rendering---");
//
//        double cX = rendPX + (centerX-viewWidth/(2*scl));
//        double cY = rendPY + (centerY-viewHeight/(2*scl));
//        rendPX=cX;
//        rendPY=cY;
//
//        println(cX,cY);
//        cX = cX - (viewWidth/scale)/2;
//        cY = cY - (viewHeight/scale)/2;
//        println(cX,cY);
//        scl=scale;3
        int startTimer = millis();
        int iter;
        double nXPix = width / res, nYPix = height / res;
        for (double i = -nXPix / 2; i < nXPix / 2; i++) {
            for (double j = -height / res / 2; j < height / res / 2; j++) {
//                iter = iterate(cx + (i / (nXPix / 2)) * (wScale / scale / 2), cy + (j / (nYPix / 2)) * (hScale / scale / 2));
                BigDecimal smallX = new BigDecimal(i / (nXPix / 2))
                        .multiply(new BigDecimal(wScale)
                                .divide(scale).
                                        divide(new BigDecimal(2)));
                BigDecimal smallY = new BigDecimal(j / (nYPix / 2))
                        .multiply(new BigDecimal(hScale)
                                .divide(scale).
                                        divide(new BigDecimal(2)));
//                BigDecimal smallY = new BigDecimal((j / (nYPix / 2)) * (hScale / scale / 2));
                iter = iterate(new BigDecimal(cx).add(smallX), new BigDecimal(cy).add(smallY));

//                fill(255 * Math.abs(sin(iter)), 255 * Math.abs(sin(iter)), 255 * Math.abs(sin(iter)));
//                fill((float)(Math.sin(iter))*255,(float)(sat),(float)(bri));
//                colorMode(RGB,(float)255);
//                fill((float)(sin(iter)*255*hue),(float)(cos(iter)*255*sat),(float)(tan(iter)*255*bri));
                fill((iter / Math.abs(sin(iter))), 150 * iter, 150 * iter);
                rect((float) ((i + nXPix / 2) * res), (float) ((j + nYPix / 2) * res), (float) res, (float) res);
            }
        }
        scl=scale;
        textSize(20);
        fill(255);

        renderTime = millis() - startTimer;
        noStroke();
        println(updateStats());

    }

    public int iterate(BigDecimal cx, BigDecimal cy) {
//        double cx = 3 * (x - 2 * viewWidth / 3) / viewWidth;
//        double cy = ((viewHeight / 2) - y) / viewHeight * 2;
        //        double cx = newX(x);
//        double cy = newY(y);
//        int out[] = {0,0,0};
        //        if(zX != 0 && zY != 0 ){
//            zx = newX(zX);
//            zy = newY(zY);
//        }else{
//            zx = 0;
//            zy = 0;
//        }
        BigDecimal zx = new BigDecimal(0), zy = new BigDecimal(0);
        for (int i = 1; i < maxIter; i++) {
//            if (dist(cx, cy, zx, zy)>=rg1 && dist(cx, cy, zx, zy)<rg2){
//                out[0] = i;
//            }else if(dist(cx, cy, zx, zy)>=rg2 && dist(cx, cy, zx, zy)<rg3){
//                out[1]=i;
//            }else if(dist(cx, cy, zx, zy)>=rg3 ){
//                out[2]=i;
//                return out;
//            }
            if (dist(cx.doubleValue(), cy.doubleValue(), zx.doubleValue(), zy.doubleValue()) > 2) {
                return i;
            }
//            double zx1 = (zx * zx - zy * zy) + cx;
            BigDecimal zx1 = (zx.multiply(zx).subtract(zy.multiply(zy))).add(cx);
//            double zy1 = (2 * zx * zy) + cy;
            BigDecimal zy1 = (zx.multiply(zy).multiply(new BigDecimal(2))).add(cy);
//            ellipse((float)oldX(zx),(float)oldY(zy),30,30);
//            ellipse((float)oldX(zx1),(float)oldY(zy1),30,30);
//            line((float)oldX(zx),(float)oldY(zy),(float)oldX(zx1),(float)oldY(zy));
            zx = zx1;
            zy = zy1;
        }
        return -1;
    }

    //    public int iterate(double px, double py, double n) {
//        BigDecimal cx = newX(px);
//        BigDecimal cy = newY(py);
//        BigDecimal zx = new BigDecimal(0);
//        BigDecimal zy = new BigDecimal(0);
//        for (int i = 1; i < n; i++) {
//            if (dist(cx.doubleValue(), cy.doubleValue(), zx.doubleValue(), zy.doubleValue()) > 2) {
//                return i;
//            }
//            BigDecimal zx1 = (zx.multiply(zx).subtract(zy.multiply(zy))).add(cx);
//            BigDecimal zy1 = (zx.multiply(zy).multiply(new BigDecimal(2))).add(cy);
//            zx = zx1;
//            zy = zy1;
//        }
//        return 0;
//    }
    private double dist(double cx, double cy, double zx, double zy) {
        return Math.sqrt(
                Math.abs(zx - cx) * Math.abs(zx - cx) +
                        Math.abs(zy - cy) * Math.abs(zy - cy));

    }

    public String updateStats() {
        return ("rendered in: " + (renderTime) + "ms" + "\n" +
                "\n" +
                "(q,a)zoom: " + scl + "\n" +
                "(4,v)zoom speed: " + dscl + "\n" +
                "(r,f)pixel size: " + res + "\n" +
                "(w,s)max iter: " + maxIter + "\n" +
                "\n" +
//                "(1,z)H: "+ hue+"\n"+
//                "(2,x)S: "+ sat+"\n"+
//                "(3,c)B: "+ bri+"\n"+
//                "(5,b)rg1: "+ rg1+"\n"+
//                "(6,n)rg2: "+ rg2+"\n"+
//                "(7,m)rg3: "+ rg3+"\n"+
//                "renderPoint: (" + newX(rendPX) + " + i" + newY(rendPY) + ")" + "\n" +
                "-----------");
    }
//    private double newX(double x){
//        return ((rendPX+((x-viewWidth/2)/scl))-viewWidth/2)*(wScale/viewWidth);
//    }
//    private double newY(double y){
//        return (((rendPY+(y-viewHeight/2)/scl))-viewHeight/2)*(hScale/viewHeight);
//    }
    private double imagX(double x){
        return (x-viewWidth/2)*(wScale/viewWidth);
    }
    private double imagY(double y){
        return (y-viewHeight/2)*(hScale/viewHeight);
    }
    private double transX(double x) {
        return (rendPX+((x-viewWidth/2)/scl.doubleValue()));
    }

    private double transY(double y) {
        return ((rendPY+(y-viewHeight/2)/scl.doubleValue()));
    }

    public void keyPressed() {
        switch (key) {
            case ('q'):
                render(imagX(rendPX), imagY(rendPY), scl.multiply(new BigDecimal(dscl)));
                break;

            case ('a'):
                render(imagX(rendPX), imagY(rendPY), scl.divide(new BigDecimal(dscl)));
                break;


            case ('4'):
                dscl = dscl * dscl;
                break;
            case ('v'):
                dscl = Math.sqrt(dscl);
                break;


            case ('`'):
                hue = 5;
                sat = 5;
                bri = 5;
                rendPX = viewWidth / 2;
                rendPY = viewHeight / 2;
                scl = new BigDecimal(1);
                dscl = 2;
                maxIter = 255;
                render(imagX(rendPX), imagX(rendPY), scl);
                break;


            case ('s'):
                maxIter /= dscl;
                break;
            case ('w'):
                maxIter *= dscl;
                break;


            case ('2'):
                sat *= dscl;
                break;
            case ('x'):
                sat /= dscl;
                break;

            case ('3'):
                bri *= dscl;
                break;
            case ('c'):
                bri /= dscl;
                break;

            case ('1'):
                hue = hue * dscl;
                break;
            case ('z'):
                hue /= dscl;
                break;

            case ('5'):
                rg1 += +0.1;
                break;
            case ('b'):
                rg1 -= 0.1;
                break;
            case ('6'):
                rg2 += +0.1;
                break;
            case ('n'):
                rg2 -= 0.1;
                break;
            case ('7'):
                rg3 += +0.1;
                break;
            case ('m'):
                rg3 -= 0.1;
                break;


            case ('r'):
                res -= 1;
                break;
            case ('f'):
                res += 1;
                break;

            case (' '):
                render(imagX(rendPX), imagY(rendPY), scl);
                break;
            default:
                break;
        }
    }


}
//    public void mouseReleased(){
//        render(0,0,scl,res);
//    }
//


//            ellipse(oldX(zx),oldY(zy),30,30);
//            ellipse(oldX(zx1),oldY(zy1),30,30);
//            line(oldX(zx),oldY(zy),oldX(zx1),oldY(zy1));