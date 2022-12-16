import java.util.HashMap;
import java.util.Scanner;

/** If you don't know what you're doing, it's better to change only those parameters that contain comments*/

public class Main {
    /** Enter the number of rows and columns in your terminal,
     * Length is depth, so since terminal literally has no depth, it can be any number.
     * Right now it is absolutely useless*/
    public static final float consoleWidth = 120;
    public static final float consoleHeight = 28;
    public static final float consoleLength = 30;

    /** Your terminal symbols height and width ratio*/
    public static final float symbolRatio = 18f/9f;
    /** refresh time, you can put 0 to disable restriction*/
    public static final int FPS = 25;
    /** Shape of Object you want to get, IF YOU ARE CHANGING THE SHAPE, CHECK IF IT'S 2D OR 3D, and change some values after*/
    public static final allShapes shape = allShapes.Torus;

private static final char[] symbols = new char[]{'.',',','-','~','"',':',';','=','!','*','№','%','#','$','&','@',};

private static final char[][][] shapePixels = new char[(int) consoleWidth][(int) consoleHeight][(int) consoleLength];

private static final Scanner scanner = new Scanner(System.in);
public static final float depthRatio = consoleLength / symbols.length;
private static final boolean debug = false;

private static HashMap<allMovements, Float> movement = new HashMap<>();


public enum allShapes{
    Square, //2d
    Circle, //2d
    Annulus, //2d
    Sphere, //3d
    Torus, //3d
}
public enum allMovements{
    Left, //2d
    Right, //2d
    Up, //2d
    Down, //2d
    Forward, //3d
    Backward, //3d
}


    Main(){
        MakeShape();
        while (true) {
            clear();
            move();
            draw();
            refresh();
        }
    }
    private void MakeShape(){
        for (int z = 0; z < consoleLength; z++) {
            for (int y = 0; y < consoleHeight; y++) {
                for (int x = 0; x < consoleWidth; x++) {
                    if (Shapes.inSomeShape(shape, x, y, z)) {
                        int symbol = (int)(z / depthRatio);
                        shapePixels[x][y][z] = symbols[symbol];
                    }else {
                        shapePixels[x][y][z] = ' ';
                    }
                }
            }
        }
    }
    private static void clear() {
        try {
            movement = new HashMap<>();
            System.out.print("\u001b[H");
            System.out.flush();
        } catch (final Exception e) {
            System.out.print("clearConsole() error");
            scanner.next();
        }
    }

    private static void move(){

    }

    private static void draw(){
        if (debug){
            System.out.print("x:" + Transformation.current_x+" y:" + Transformation.current_y+" z:" + Transformation.current_z);
        }
        char[][] pixels = Transformation.transformTo2D(shapePixels, movement);
        for (int y = 0; y < consoleHeight; y++) {
            for (int x = 0; x < consoleWidth; x++) {
                System.out.print(pixels[x][y]);
            }
            System.out.print("\n");
        }
    }

    private static void refresh(){
        try {
            Thread.sleep(1000 / FPS);
        } catch (Exception ignored) {
            System.out.print("Thread.sleep error");
            scanner.next();
        }
    }
    public static void main(String[] args) {
        new Main();
    }
}