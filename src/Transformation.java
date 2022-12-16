import java.util.HashMap;

public class Transformation {
    public static final float consoleWidth = Main.consoleWidth;
    public static final float consoleHeight = Main.consoleHeight;
    public static final float consoleLength = Main.consoleLength;
    public static float current_x = 0;
    public static float current_y = 0;
    public static float current_z = 0;
    private static final char[][] pixels = new char[(int) consoleWidth][(int) consoleHeight];
    private static char[][][] shape = new char[(int) consoleWidth][(int) consoleHeight][(int) consoleLength];
    private static final char[][][] movedShape = new char[(int) consoleWidth][(int) consoleHeight][(int) consoleLength];
    private static final char[][][] finalShape = new char[(int) consoleWidth][(int) consoleHeight][(int) consoleLength];
    private static HashMap<Main.allMovements, Float> movement = new HashMap<>();
    public static char[][] transformTo2D(char[][][] Shape, HashMap<Main.allMovements, Float> Movement){
        shape = Shape;
        movement = Movement;
        assignMovement();
        fillEmpty();
        moveObject();
        rotateObject();
        render();
        return pixels;
    }

    private static void assignMovement(){
        if (movement.get(Main.allMovements.Left)!=null){
            moveLeft(movement.get(Main.allMovements.Left));
        }
        if (movement.get(Main.allMovements.Right)!=null){
            moveRight(movement.get(Main.allMovements.Right));
        }
        if (movement.get(Main.allMovements.Up)!=null){
            moveUp(movement.get(Main.allMovements.Up));
        }
        if (movement.get(Main.allMovements.Down)!=null){
            moveDown(movement.get(Main.allMovements.Down));
        }
        if (movement.get(Main.allMovements.Forward)!=null){
            moveForward(movement.get(Main.allMovements.Forward));
        }
        if (movement.get(Main.allMovements.Backward)!=null){
            moveBackward(movement.get(Main.allMovements.Backward));
        }
    }
    private static void fillEmpty(){
        for (int x = 0; x < consoleWidth; x++) {
            for (int y = 0; y < consoleHeight; y++) {
                pixels[x][y] = ' ';
                for (int z = 0; z < consoleLength; z++){
                    movedShape[x][y][z] = ' ';
                    finalShape[x][y][z] = ' ';
                }
            }
        }
    }
    private static void moveObject(){
        for (int z = (int) consoleLength-1; z > -1; z--){
            for (int x = 0; x < consoleWidth; x++) {
                for (int y = 0; y < consoleHeight; y++) {
                    if (shape[x][y][z] != ' '){
                        movedShape[(int) (x + current_x)][(int) (y + current_y)][(int) (z + current_z)] = shape[x][y][z];
                    }
                }
            }
        }
    }
    private static void rotateObject(){
        for (int z = (int) consoleLength-1; z > -1; z--){
            for (int x = 0; x < consoleWidth; x++) {
                for (int y = 0; y < consoleHeight; y++) {
                    if (movedShape[x][y][z] != ' '){
                        finalShape[(x)][(y)][(z)] = movedShape[x][y][z];
                    }
                }
            }
        }
    }
    private static void render(){
        for (int z = (int) consoleLength-1; z > -1; z--){
            for (int x = 0; x < consoleWidth; x++) {
                for (int y = 0; y < consoleHeight; y++) {
                    if (pixels[x][y] == ' '){
                        pixels[x][y] = finalShape[x][y][z];
                    }
                }
            }
        }
    }

    private static void moveLeft(float speed){
        if (Math.abs(current_x) < Shapes.widthLimitFactor) {
            current_x -= speed / Main.FPS;
        }
    }
    private static void moveRight(float speed){
        if (Math.abs(current_x) < Shapes.widthLimitFactor) {
            current_x += speed / Main.FPS;
        }
    }
    private static void moveUp(float speed){
        if (Math.abs(current_y) < Shapes.heightLimitFactor) {
            current_y -= speed / Main.FPS;
        }
    }
    private static void moveDown(float speed){
        if (Math.abs(current_y) < Shapes.heightLimitFactor) {
            current_y += speed / Main.FPS;
        }
    }
    private static void moveForward(float speed){
        if (Math.abs(current_z) < Shapes.depthLimitFactor) {
            current_z -= speed / Main.FPS;
        }
    }
    private static void moveBackward(float speed){
        if (Math.abs(current_z) < Shapes.depthLimitFactor) {
            current_z += speed / Main.FPS;
        }
    }


}
