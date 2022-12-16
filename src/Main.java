import java.util.Scanner;

/** If you don't know what you're doing, it's better to change only those parameters that contain comments*/

public class Main {
    /** Enter the number of rows and columns in your terminal,
     * Length is depth, so since terminal literally has no depth, it can be any number.
     * Right now it is absolutely useless*/
    public static final float consoleWidth = 120;
    public static final float consoleHeight = 28;
    public static final float consoleLength = 28;

    /** Your terminal symbols height and width ratio*/
    public static final float symbolRatio = 18f/9f;
    /** refresh time, you can put 0 to disable restriction*/
    public static final int FPS = 24;
    /** Shape of Object you want to get, IF YOU ARE CHANGING THE SHAPE, CHECK IF IT'S 2D OR 3D, and change some values after*/
    public static final allShapes shape = allShapes.Sphere;

private static final char[] symbols = new char[]{'`','~','"','^','*','?','№','$','#','&','@'};
private static final char[][] pixels = new char[(int) consoleWidth][(int) consoleHeight];

private static final Scanner scanner = new Scanner(System.in);
public static final float depthRatio = consoleLength / symbols.length;
public static final boolean moving = true;

private static final boolean debug = true;


public enum allShapes{
    Cube, //2d
    Circle, //2d
    Donut2d, //2d
    Sphere, //3d
}


    Main(){
        print();
    }

    private void print(){
        while (true) {
            clearConsole();
            for (int z = 0; z < consoleLength; z++) {
                for (int y = 0; y < consoleHeight; y++) {
                    for (int x = 0; x < consoleWidth; x++) {
                        if (Shapes.inSomeShape3D(shape, x, y, z)) { /** If you are using 3d Objects you need to change inSomeShape3D to inSomeShape3D and add third z parameter*/
                            int symbol = (int)(z / depthRatio);
                            pixels[x][y] = symbols[symbol];
                        }
                    }
                }
            }

            // Transformation
            if (moving){
                /** Here you need to put all moving functions(You can see them in Transformation class)*/
                Transformation.moveBackward(5);
            }

            draw();
            try {
                Thread.sleep(1000 / FPS);
            } catch (Exception ignored) {
                System.out.print("Thread.sleep error");
                scanner.next();
            }
        }
    }
    private static void clearConsole() {
        try {
            System.out.print("\u001b[H");
            System.out.flush();
            for (int y = 0; y < consoleHeight; y++) {
                for (int x = 0; x < consoleWidth; x++) {
                    pixels[x][y] = ' ';
                }
            }
        } catch (final Exception e) {
            System.out.print("clearConsole() error");
            scanner.next();
        }
    }
    private static void draw(){
        if (debug){
            System.out.print("x:" + Shapes.current_x+" y:" + Shapes.current_y+" z:" + Shapes.current_z);
        }
        for (int y = 0; y < consoleHeight; y++) {
            for (int x = 0; x < consoleWidth; x++) {
                if (pixels[x][y] == ' '){
                    System.out.print(' ');
                }else {
                    System.out.print(pixels[x][y]);
                }
            }
            System.out.print("\n");
        }
    }
    public static void main(String[] args) {
        new Main();
    }
}