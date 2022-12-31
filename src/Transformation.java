
import static java.lang.Math.*;

public class Transformation {
    public static float consoleWidth = 120;
    public static float consoleHeight = 28;
    public static float consoleLength = 60;
    public static float current_x = 0;
    public static float current_y = 0;
    public static float current_z = 0;
    private static final double degreesToRadians = PI/180;
    public static double currentRotation_x = 0;
    public static double currentRotation_y = 0;
    public static double currentRotation_z = 0;
    private static float center_x;
    public static float center_y;
    public static float center_z;
    public static final float padding_y = consoleHeight / 4;
    private static char[][] pixels;
    private static char[][][] shape;
    private static char[][][] movedShape;
    private static char[][][] finalShape;
    public static void update(char[][][] Shape){
        shape = Shape;
        pixels = new char[(int) consoleWidth][(int) consoleHeight];
        movedShape = new char[(int) consoleWidth][(int) consoleHeight][(int) consoleLength];
        finalShape = new char[(int) consoleWidth][(int) consoleHeight][(int) consoleLength];
        center_x = (int) (consoleWidth/2);
        center_y = (int) (consoleHeight/2);
        center_z = (int) (consoleLength/2);
    }
    public static char[][] transformTo2D(){
        fillEmpty();
        moveObject();
        rotateObject();
        render();
        return pixels;
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
                        Point point = new Point(x, y, z);

                        point = rotateOnX(point);
                        point = rotateOnY(point);
                        point = rotateOnZ(point);

                        int final_x = (int) point.x;
                        int final_y = (int) (point.y + padding_y);
                        int final_z = (int) point.z;
                        finalShape[(final_x)][final_y][(final_z)] = movedShape[x][y][z];
                    }
                }
            }
        }
    }
    private static Point rotateOnX(Point point){
        double theta = currentRotation_x*degreesToRadians;
        double final_x = point.x;
        double final_y = (point.y-center_y) * cos(theta) - (point.z-center_z) * sin(theta);
        double final_z = (point.y-center_y) * sin(theta) + (point.z-center_z) * cos(theta);
        return new Point(final_x, final_y+center_y, final_z + center_z);
    }
    private static Point rotateOnY(Point point){
        double theta = currentRotation_y*degreesToRadians;
        double final_x = (point.x - center_x) * cos(theta) - (point.z - center_z) * sin(theta);
        double final_y = point.y;
        double final_z = (point.x - center_x) * sin(theta) + (point.z - center_z) * cos(theta);
        return new Point(final_x + center_x, final_y, final_z + center_z);
    }
    private static Point rotateOnZ(Point point){
        double theta = currentRotation_z*degreesToRadians;
        double final_x = (point.x - center_x) * Math.cos(theta) - (point.y-center_y) * Shapes.ratio * Math.sin(theta);
        double final_y = (point.x - center_x) * Math.sin(theta) + (point.y-center_y) * Shapes.ratio * Math.cos(theta);
        double final_z = point.z;
        return new Point(final_x + center_x, (final_y + center_y) / Shapes.ratio, final_z);
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

    public static void moveLeft(float speed){
        if (Math.abs(current_x) < Shapes.widthLimitFactor) {
            current_x -= speed / Main.FPS;
        }
    }
    public static void moveRight(float speed){
        if (Math.abs(current_x) < Shapes.widthLimitFactor) {
            current_x += speed / Main.FPS;
        }
    }
    public static void moveUp(float speed){
        if (Math.abs(current_y) < Shapes.heightLimitFactor) {
            current_y -= speed / Main.FPS;
        }
    }
    public static void moveDown(float speed){
        if (Math.abs(current_y) < Shapes.heightLimitFactor) {
            current_y += speed / Main.FPS;
        }
    }
    public static void moveForward(float speed){
        if (Math.abs(current_z) < Shapes.depthLimitFactor) {
            current_z -= speed / Main.FPS;
        }
    }
    public static void moveBackward(float speed){
        if (Math.abs(current_z) < Shapes.depthLimitFactor) {
            current_z += speed / Main.FPS;
        }
    }
    public static void rotateOnX(float speed){
        currentRotation_x +=speed;
    }
    public static void rotateOnY(float speed){
        currentRotation_y +=speed;
    }
    public static void rotateOnZ(float speed){
        currentRotation_z +=speed;
    }
}

class Point{
    final double x;
    final double y;
    final double z;
    Point(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
