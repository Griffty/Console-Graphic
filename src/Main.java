import java.util.Scanner;

public class Main {
public static float consoleWidth = 120;
public static float consoleHeight = 28;
public static float consoleLength = 60;
public static float symbolRatio = 18f/9f;
public static final int FPS = 25;
public static allShapes shape = allShapes.Torus;
public static final char[] symbols = new char[]{'.',',','-','~','"',':',';','=','!','*','№','%','#','$','&','@'};
private static final Scanner scanner = new Scanner(System.in);
public static float depthRatio;
private static final boolean debug = false;
public enum allShapes{
    Rectangle, //2d
    Circle, //2d
    Annulus, //2d
    Sphere, //3d
    Torus, //3d
}

    Main(){
        updateSettings();
        MakeShape();
        while (true) {
            clear();
            move();
            draw();
            refresh();
        }
    }
    private void updateSettings(){
        Settings.updateData();
        Shapes.update();
    }

    private void MakeShape(){
        depthRatio = consoleLength / symbols.length;
        char[][][] shapePixels = new char[(int) consoleWidth][(int) consoleHeight][(int) consoleLength];
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
        Transformation.update(shapePixels);
    }
    private static void clear() {
        try {
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
            System.out.print("x:" + Transformation.current_x+" y:" + Transformation.current_y+" z:" + Transformation.current_z+ " Rx: " + Transformation.currentRotation_x + " Ry: " + Transformation.currentRotation_y + " Rz: " + Transformation.currentRotation_z);
        }
        char[][] pixels = Transformation.transformTo2D();
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