import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

public class Magic extends PApplet {
    private static double wScale = 3.840, hScale = 2.16;
    private static double viewWidth = 3840, viewHeight = 2160, scl = 1, res = 3, dscl = 2;
    //    private BigDecimal scl = new BigDecimal(1);
    private static double maxIter = 200;
    private static double rg1 = 1, rg2 = 1, rg3 = 1;
    private static double hue = 1, sat = 1, bri = 1, renderTime = 0, cxVal, cyVal;
    private static double rendPX = viewWidth / 2, rendPY = viewHeight / 2;
    private static double juX = 0, juY = 0, mnX = 0, mnY = 0, fromGif = 0, toGif = 0;
    private static boolean drawMandel = true, moveImage = false, wheelie = false;
    private static PImage output;
    private static float imgScale = 1, imgRX = 5, imgRY = 1, oldMX = 0, oldMY = 0;
    private static String eq;
    private static final DateFormat dateFormat = new SimpleDateFormat("EEE_MMM_d_h'h'mm'm'ss's'_a");

    public static void main(String[] args) {
        if (args.length > 0) {

            fromFile(args[0]);
        }
        PApplet.main("Magic", args);
    }

    public void settings() {
//        size((int) viewWidth, (int) viewHeight);
        fullScreen();
    }

    public void setup() {
        background(0);
//        colorMode(HSB, (int) maxIter);
        colorMode(HSB, 255);
        noStroke();
        if (drawMandel) {
            render(imagX(rendPX), imagY(rendPY), scl, false);
        } else {
            render(imagX(rendPX), imagY(rendPY), scl, true);
        }

    }

    public void draw() {
        noFill();
        stroke(255);
/*        if(drawMandel){
            rect(0,0,(float)viewWidth,(float)viewHeight);
        }else{
            rect(1920,0,(float)viewWidth,(float)viewHeight);
        }*/

        if (keyPressed) {
            colorMode(RGB, 255);
            fill(115, 150);
            rect(0, 0, 400, 600);
            fill(255);
            text(updateStats(), 10, 20);
            colorMode(HSB, 255);
        }
        colorMode(RGB, 255);
/*        stroke(0,255,0);
        line(0,height/2,width,height/2);
        line(width/2,0,width/2,height);
        stroke(0,0,255);
        line(0,(float)rendPY,(float)viewWidth,(float)rendPY);
        line((float)rendPX,0,(float)rendPX,(float)viewHeight);*/
/*        stroke(255,0,0);
        line(0,(float)realY(0),(float)viewWidth,(float)realY(0));
        line((float)realX(0),0,(float)realX(0),(float)viewHeight);
        noStroke();*/
    }

    public void mouseReleased() {
        oldMX = 0;
        oldMY = 0;
    }

    public void mouseWheel(MouseEvent e) {
        if (imgScale > 0) {
            imgScale += e.getCount();
            wheelie = true;
        }
    }

    public void mouseClicked() {
//        if (drawMandel) {
//            rendPX = transX(mouseX);
//            rendPY = transY(mouseY);
//            juX = imagX(rendPX);
//            juY = imagY(rendPY);
//            render(juX, juY, scl, false);
//        } else {
        if (!keyPressed) {
            rendPX = transX(mouseX);
            rendPY = transY(mouseY);
            if (!drawMandel) {

                mnX = imagX(rendPX);
                mnY = imagY(rendPY);
                render(mnX, mnY, scl, true);
            } else {
                juX = imagX(rendPX);
                juY = imagY(rendPY);
                render(juX, juY, scl, false);
            }
        }

//        }


    }

    private void render(double cx, double cy, double scale, boolean julia) {
        render(cx, cy, scale, julia, false, 0, 0);
    }

    private void render(double cx, double cy, double scale, boolean julia, boolean justSave) {
        render(cx, cy, scale, julia, justSave, 0, 0);
    }

    private void render(double cx, double cy, double scale, boolean julia, boolean justSave, double rpx, double rpy) {
        render(cx, cy, scale, julia, justSave, rpx, rpy, true);
    }

    private void render(double cx, double cy, double scale, boolean julia, boolean justSave, double rpx, double rpy, boolean stats) {
        colorMode(HSB, 255);
        char bar[], unfilled[];
        if (stats)
            println("---rendering---");
        int startTimer = millis();
        int iter;
        double nXPix = (int) (viewWidth / res), nYPix = (int) (viewHeight / res);
        output = createImage((int) (nXPix), (int) (nYPix), HSB);

        int loaded = 0;
        for (double i = -nXPix / 2; i < nXPix / 2; i++) {
            for (double j = -nYPix / 2; j < nYPix / 2; j++) {
                if (julia) {
                    iter = iterate(cx + (i / (nXPix / 2)) * (wScale / scale / 2), cy + (j / (nYPix / 2)) * (hScale / scale / 2));
                } else {
                    iter = iterateM(cx + (i / (nXPix / 2)) * (wScale / scale / 2), cy + (j / (nYPix / 2)) * (hScale / scale / 2));
                }

                if (iter > 0) {
                    output.pixels[(int) (i + nXPix / 2) + (int) ((j + nYPix / 2) * nXPix)] =
                            //color((float)((255%(iter))),(float)(150*sat),(float)(150*bri));
                            color((float) (hue * 113 * (sin(radians(150+iter * 10)) + 1)), (float) (sat * 150 * (iter / Math.abs(iter))), (float) (bri * 150 * (iter / Math.abs(iter))));
                } else {
                    output.pixels[(int) (i + nXPix / 2) + (int) ((j + nYPix / 2) * nXPix)] =
                            color(0);
                }
//                output.pixels[(int)(i+nXPix/2)+(int)((j+nYPix/2)*nXPix)] = iter;
//                rect((float) ((i + nXPix / 2) * res), (float) ((j + nYPix / 2) * res), (float) res, (float) res)
            }
            {
                loaded = (int) (20 * ((i + nXPix / 2) / nXPix));
                bar = new char[loaded];
                unfilled = new char[20 - loaded];
                Arrays.fill(bar, '=');
                Arrays.fill(unfilled, ' ');
                print("[" + new String(bar) + new String(unfilled) + "] " + 5 * loaded + "% \r");
            }
        }
        bar = new char[20];
        Arrays.fill(bar, '=');
        print("[" + new String(bar) + "] " + "100%\r");
        if (stats) {
            println();
        }
        output.updatePixels();
        if (!justSave) {
            image(output, (float) rpx, (float) rpy, (int) viewWidth, (int) viewHeight);
        }
        scl = scale;
        textSize(20);
        fill(255);
        println();
        if (stats) {
            renderTime = millis() - startTimer;
            println(updateStats());
        }
//        iterateRM(cx,cy);
    }

    private int iterateM(double cx, double cy) {
        eq = "            double zx2 = ((zx * zx) - (zy * zy));\n" +
                "            double zy2 = (2 * zx * zy);\n" +
                "            double zx3 = (zx * zx2 - zy * zy2);\n" +
                "            double zy3 = (zx * zy2)+(zy*zx2);\n" +
                "            zx=zx2+(cx);\n" +
                "            zy=zy2+(cy);";
        double zx = 0, zy = 0;
        for (int i = 1; i < maxIter; i++) {
            if (dist(cx, cy, zx, zy) > imgRX) {
                return i;
            }
           /* double zx1 = (zx * zx - zy * zy) + cx;
            double zy1 = (2 * zx * zy) + cy;
            zx = zx1;
            zy = zy1;*/
            double zx2 = ((zx * zx) - (zy * zy));
            double zy2 = (2 * zx * zy);
            double zx3 = (zx * zx2 - zy * zy2);
            double zy3 = (zx * zy2) + (zy * zx2);
            zx = rg1 * zx2 + (cx);
            zy = rg2 * zy2 + (cy);

        }
        return -1;
    }

    /*private int iterateRM(double cx, double cy) {
        double zx = 0, zy = 0;
        stroke(255);
        noFill();
        ellipse((float)realX(cx),(float)realY(cy),(float)maxIter,(float)maxIter);
        for (int i = 1; i < maxIter; i++) {
            if (dist(cx, cy, zx, zy) > 5) {
                return i;
            }
            double zx1 = (zx * zx - zy * zy) + cx;
            double zy1 = (2 * zx * zy) + cy;
            line((float)(realX(zx1)),(float)(realY(zy1)),(float)(realX(zx)),(float)(realY(zy)));
            ellipse((float)(realX(zx1)),(float)(realY(zy1)),(float)((maxIter/i)),(float)((maxIter/i)));
            zx = zx1;
            zy = zy1;
        }
        return -1;
    }*/
    /*private int iterateM(double cx, double cy) {
        double zx = 0, zy = 0;
        for (int i = 1; i < maxIter; i++) {
            if (dist(cx, cy, zx, zy) > 5) {
                return i;
            }
            double zx1 = zy + cx;
            double zy1 = zx + cy;
            zx = zx1;
            zy = zy1;
        }
        return -1;
    }*//*
    private int iterateMR(double cx, double cy) {
        double zx = 0, zy = 0;
        stroke(255);
        noFill();
        ellipse((float)cx,(float)cy,100,100);
        println("---------------------",transX(realX(cx)),realY(cy)," vs ", transX(rendPX),rendPY);
        for (int i = 1; i < maxIter; i++) {
            if (dist(cx, cy, zx, zy) > 5) {
                return i;
            }
            double zx1 = zy + cx;
            double zy1 = zx + cy;
            ellipse((float)transX(realX(cx)),realY(cy),100,100);
//            line((float)transX(realX(zx1)),(float)transY(realY(zy1)),(float)transX(realX(zx)),(float)transY(realY(zy)));
//            ellipse((float)transX(realX(zx1)),(float)transY(realY(zy1)),(float)(100+100*(i/maxIter)),(float)(100+100*(i/maxIter)));
            zx = zx1;
            zy = zy1;
        }
        noStroke();

        return -1;
    }*/
    private int iterate(double zx, double zy) {
        double cx = juX, cy = juY;
        for (int i = 1; i < maxIter; i++) {
            if (dist(cx, cy, zx, zy) > imgRX) {
                return i;
            }
            double zx2 = ((zx * zx) - (zy * zy));
            double zy2 = (2 * zx * zy);
            double zx3 = (zx * zx2 - zy * zy2);
            double zy3 = (zx * zy2) + (zy * zx2);
            zx = zx2 + (cx);
            zy = zy2 + (cy);

        }
        return -1;
        /*double cx = juX, cy = juY;
        for (int i = 1; i < maxIter; i++) {
            if (dist(cx, cy, zx, zy) > 5) {
                return i;
            }
            double zx1 = (zx * zx - zy * zy) + cx;
            double zy1 = (2 * zx * zy) + cy;
            zx = zx1;
            zy = zy1;
        }
        return -1;*/

    }

    private double dist(double cx, double cy, double zx, double zy) {
        return Math.sqrt(
                Math.abs(zx - cx) * Math.abs(zx - cx) +
                        Math.abs(zy - cy) * Math.abs(zy - cy));

    }

    private String updateStats() {
        return updateStats(false);
    }

    private String updateStats(boolean accurate) {
        DecimalFormat df;
        if (accurate) {
            df = new DecimalFormat("##################################.##########" +
                    "##################################################################");
        } else {
            df = new DecimalFormat("#####.#####");
        }
        String juul;
        int iter;
        if (drawMandel) {
            juul = "Mandelbrot";
            iter = iterateM(imagX(rendPX), imagY(rendPY));
        } else {
            juul = "Julia from: (" + df.format(juX) + " + " + df.format(juY) + "i)";
            iter = iterate(imagX(rendPX), imagY(rendPY));
        }
        return ("rendered in: " + df.format((renderTime)) + "ms" + "\n" +
                "\n" +
                "(q,a)zoom: " + scl + "\n" +
                "(e,d)change speed: " + df.format(dscl) + "\n" +
                "(r,f)pixel size: " + df.format(res) + "\n" +
                "(w,s)max iter: " + df.format(maxIter) + "\n" +
                "\n" +
                "rendering: " + juul + "\n" +
                "\n" +
                "(1,z)H: " + df.format(hue) + "\n" +
                "(2,x)S: " + df.format(sat) + "\n" +
                "(3,c)B: " + df.format(bri) + "\n" +
                "(5,b?)sz: " + df.format(imgRX) + "\n" +

                "(1,z)rg1: " + df.format(rg1) + "\n" +
                "(2,x)rg2: " + df.format(rg2) + "\n" +
                "(3,c)rg3: " + df.format(rg3) + "\n" +
                "(5,b?)rng: " + df.format(imgRX) + "\n" +
/*                "(5,b)rg1: "+ df.format(rg1)+"\n"+
                "(6,n)rg2: "+ df.format(rg2)+"\n"+
                "(7,m)rg3: "+ df.format(rg3)+"\n"+*/
                "renderPoint: (" + df.format(rendPX) + " + " + df.format(rendPY) + "i)" + "\n" +
                "chaos at RP : " + iter + "\n" +
                "-----------");
    }

    private String toFile() {
        String juul;
        if (drawMandel) {
            juul = "true\n0\n0";
        } else {
            juul = "false\n" + juX + "\n" + juY;
        }
        return (scl + "\n" +
                res + "\n" +
                maxIter + "\n" +
                juul + "\n" +
                hue + "\n" +
                sat + "\n" +
                bri + "\n" +
/*                "(5,b)rg1: "+ (rg1)+"\n"+
                "(6,n)rg2: "+ (rg2)+"\n"+
                "(7,m)rg3: "+ (rg3)+"\n"+*/
                rendPX + "\n" +
                rendPY + "\n" +
                dscl + "\n" +
                fromGif + "\n" +
                toGif + "\n" +
                imgRX + "\n" +
                eq);
    }

    private static void fromFile(String file) {
        try {
            Scanner input = new Scanner(new File(System.getProperty("user.dir") + "\\Saved\\" + file));
            int cnt = 0;
            while (input.hasNextLine()) {
                switch (cnt) {
                    case (0):
                        scl = Double.parseDouble(input.nextLine());
                        break;
                    case (1):
                        res = Double.parseDouble(input.nextLine());
                        break;
                    case (2):
                        maxIter = Double.parseDouble(input.nextLine());
                        break;
                    case (3):
                        drawMandel = Boolean.parseBoolean(input.nextLine());
                        break;
                    case (4):
                        juX = Double.parseDouble(input.nextLine());
                        break;
                    case (5):
                        juY = Double.parseDouble(input.nextLine());
                        break;
                    case (6):
                        hue = Double.parseDouble(input.nextLine());
                        break;
                    case (7):
                        sat = Double.parseDouble(input.nextLine());
                        break;
                    case (8):
                        bri = Double.parseDouble(input.nextLine());
                        break;
                    case (9):
                        rendPX = Double.parseDouble(input.nextLine());
                        break;
                    case (10):
                        rendPY = Double.parseDouble(input.nextLine());
                        break;
                    case (11):
                        dscl = Double.parseDouble(input.nextLine());
                    default:
                        input.nextLine();
                }
                cnt++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    private double newX(double x) {
        return ((rendPX + ((x - viewWidth / 2) / scl)) - viewWidth / 2) * (wScale / viewWidth);
    }

    private double newY(double y) {
        return (((rendPY + (y - viewHeight / 2) / scl)) - viewHeight / 2) * (hScale / viewHeight);
    }

    private double imagX(double x) {
        return (x - viewWidth / 2) * (wScale / viewWidth);
    }

    private double imagY(double y) {
        return (y - viewHeight / 2) * (hScale / viewHeight);
    }

    private double realX(double x) {
        return ((viewWidth / 2) + (x / (wScale / viewWidth))) - scl * (rendPX - viewWidth / 2);
    }

    private double realY(double y) {
        return ((viewHeight / 2) + (y / (hScale / viewHeight))) - scl * (rendPY - viewHeight / 2);
    }

    private double transX(double x) {
        return (rendPX + ((x - viewWidth / 2) / scl));
    }

    private double transY(double y) {
        return ((rendPY + (y - viewHeight / 2) / scl));
    }

    private boolean createGif(double minI, double maxI, double minS, double maxS, double step) {
        String date = dateFormat.format(new Date());
        String loc = "" + System.getProperty("user.dir") + "\\Saved\\" + date + "_gif\\";
        int loaded = 0;
        char bar[], unfilled[];
        maxIter = minI;
        double svScl = scl, dsclGif = Math.pow((maxS / minS), 1 / ((maxI - minI) / step));
        int fileno = 0;
        scl = minS;
        fromGif = minI;
        toGif = maxI;
        for (double i = minI; i < maxI; i += step) {
            {
                loaded = (int) (100 * ((i - minI) / step) / ((maxI - minI) / step));
                bar = new char[loaded];
                unfilled = new char[100 - loaded];
                Arrays.fill(bar, '=');
                Arrays.fill(unfilled, ' ');
                print("[" + new String(bar) + new String(unfilled) + "] " + loaded + "% \r");
            }

            maxIter += step;
            scl *= dsclGif;

            if (i == minI) {
                saveCurr(date, true);
            } else {
                saveCurr(date + "_gif\\scale_" + (100000 + fileno), false);
            }
            fileno++;
        }
        bar = new char[100];
        Arrays.fill(bar, '=');
        print("[" + new String(bar) + "] " + "completed!\nconverting to gif...");
        println();
        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "cd \"" + loc + "\" &&" +
                    " magick convert *.tiff " + date + ".gif" + " &&" +
                    " del *.tiff &&" +
                    " move *.gif " + System.getProperty("user.dir") + "\\Saved\\");
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }
            Files.delete(Paths.get(loc));
        } catch (IOException e) {
            e.printStackTrace();
        }
        println("saved " + date + ".gif!");
        scl = svScl;
        return true;
    }

    private String saveCurr(String name, boolean savetxt) {
        if (drawMandel) {
            render(imagX(rendPX), imagY(rendPY), scl, false, true, 0, 0, false);
        } else {
            render(imagX(rendPX), imagY(rendPY), scl, true, true, 0, 0, false);
        }
        output.save(System.getProperty("user.dir") + "\\Saved\\" + name + ".tiff");
        if (savetxt) {
            List<String> lines = Arrays.asList(toFile());
            Path file = Paths.get(System.getProperty("user.dir") + "\\Saved\\" + name + ".txt");
            try {
                Files.write(file, lines, Charset.forName("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return System.getProperty("user.dir") + "\\Saved\\" + name;
    }

    public void keyPressed() {
        switch (keyCode) {
            case (CONTROL):
                drawMandel = !drawMandel;
                if (drawMandel) {
                    render(imagX(rendPX), imagY(rendPY), scl, false);
                } else {
                    render(imagX(rendPX), imagY(rendPY), scl, true);
                }
                break;
            case (LEFT):
                rendPX -= viewWidth / scl * (dscl - 1);
                if (drawMandel) {
                    render(imagX(rendPX), imagY(rendPY), scl, false);
                } else {
                    render(imagX(rendPX), imagY(rendPY), scl, true);
                }
                break;
            case (RIGHT):
                rendPX += viewWidth / scl * (dscl - 1);
                if (drawMandel) {
                    render(imagX(rendPX), imagY(rendPY), scl, false);
                } else {
                    render(imagX(rendPX), imagY(rendPY), scl, true);
                }
                break;
            case (UP):
                rendPY -= viewHeight / scl * (dscl - 1);
                if (drawMandel) {
                    render(imagX(rendPX), imagY(rendPY), scl, false);
                } else {
                    render(imagX(rendPX), imagY(rendPY), scl, true);
                }
                break;
            case (DOWN):
                rendPY += viewHeight / scl * (dscl - 1);
                if (drawMandel) {
                    render(imagX(rendPX), imagY(rendPY), scl, false);
                } else {
                    render(imagX(rendPX), imagY(rendPY), scl, true);
                }
                break;


            default:
                break;
        }

        switch (key) {
            case ('g'):
                Scanner scan = new Scanner(System.in);
                println("Min Iteration: ");
                double min = Double.parseDouble(scan.next());
                println("Max Iteration: ");
                double max = Double.parseDouble(scan.next());
                println("At Rate: ");
                double step = Double.parseDouble(scan.next());
                println("Min Scale: ");
                double minS = Double.parseDouble(scan.next());
                println("Max Scale: ");
                double maxS = Double.parseDouble(scan.next());
                println("do you want to save " + (max - min) / step + " images? (y/n)");

                boolean stop = false;
                while (!stop) {
                    String ans = scan.next();
                    if (ans.equals("y")) {
                        stop = createGif(min, max, minS, maxS, step);
                    } else if (ans.equals("n")) {
                        println("wimp");
                        stop = true;
                    } else {
                        println("y/n");
                    }
                }
                break;

            case ('='):
                println("saving @res " + viewWidth / res + "x" + viewHeight / res + "........");

                String date = dateFormat.format(new Date());
                saveCurr(date, true);
                println("saved!\n" + System.getProperty("user.dir") + "\\Saved\\" + date + ".tiff/.txt");
                break;
            case ('p'):
                moveImage = !moveImage;
                break;
            case ('q'):
                if (drawMandel) {
                    render(imagX(rendPX), imagY(rendPY), scl * dscl, false);
                } else {
                    render(imagX(rendPX), imagY(rendPY), scl * dscl, true);
                }
                break;

            case ('a'):
                if (drawMandel) {
                    render(imagX(rendPX), imagY(rendPY), scl / dscl, false);
                } else {
                    render(imagX(rendPX), imagY(rendPY), scl / dscl, true);
                }
                break;


            case ('e'):
                dscl = 1 + (dscl - 1) * 2;
                break;
            case ('d'):
                dscl = 1 + (dscl - 1) / 2;
                break;


            case ('`'):
                hue = 1;
                sat = 1;
                bri = 1;
                rendPX = viewWidth / 2;
                rendPY = viewHeight / 2;
                imgRX = 5;
                imgRY = 0;
                scl = 1;
                imgScale = 1;
                moveImage = false;
                dscl = 2;
                maxIter = 200;
                drawMandel = true;
                render(imagX(rendPX), imagX(rendPY), scl, false);
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
            case ('4'):
                imgRX *= dscl;
                break;
            case ('v'):
                imgRX /= dscl;
                break;


            case ('r'):
                if (res > 1) {
                    res -= 1;
                } else {
                    res /= 2;
                }
                break;
            case ('f'):
                if (res >= 1) {
                    res += 1;
                } else {
                    res *= 2;
                }
                break;

            case (' '):
                if (drawMandel) {
                    render(imagX(rendPX), imagY(rendPY), scl, false);
                } else {
                    render(imagX(rendPX), imagY(rendPY), scl, true);
                }

                break;
            default:
                break;
        }
    }
}
