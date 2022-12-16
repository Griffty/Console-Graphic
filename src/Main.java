import java.util.Scanner;

public class Main {
private static final Scanner scanner = new Scanner(System.in);

public static final float consoleWidth = 120;
public static final float consoleHeight = 28;
public static final float consoleLength = 28;

private static final char[] symbols = new char[]{'`','~','"','^','*','?','№','$','#','&','@'};
private static char[][] pixels = new char[(int) consoleWidth][(int) consoleHeight];

public static final float screenRatio = 18f/9f;
public static final float symbolRatio = consoleLength / symbols.length;


public static final boolean moving = true;
public static final int FPS = 24;
public static final allShapes shape = allShapes.Cube;

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
                        if (Shapes.inSomeShape2D(shape, x, y)) {
                            int symbol = (int)(z / symbolRatio);
                            pixels[x][y] = symbols[symbol];
                        }
                    }
                }
            }
            if (moving){
                Transformation.moveUp(10);
                Transformation.moveRight(20);
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