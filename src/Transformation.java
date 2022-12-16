
/** Function for moving.
 * Put them only in Transformation block in Main class */
public class Transformation {
    public static void moveLeft(float speed){
        if (Math.abs(Shapes.current_x) < Shapes.widthLimitFactor) {
            Shapes.current_x -= speed / Main.FPS;
        }
    }
    public static void moveRight(float speed){
        if (Math.abs(Shapes.current_x) < Shapes.widthLimitFactor) {
            Shapes.current_x += speed / Main.FPS;
        }
    }
    public static void moveUp(float speed){
        if (Math.abs(Shapes.current_y) < Shapes.heightLimitFactor) {
            Shapes.current_y -= speed / Main.FPS;
        }
    }
    public static void moveDown(float speed){
        if (Math.abs(Shapes.current_y) < Shapes.heightLimitFactor) {
            Shapes.current_y += speed / Main.FPS;
        }
    }
    /** Use only with 3d objects*/
    public static void moveForward(float speed){
        if (Math.abs(Shapes.current_z) < Shapes.depthLimitFactor) {
            Shapes.current_z -= speed / Main.FPS;
        }
    }
    public static void moveBackward(float speed){
        if (Math.abs(Shapes.current_z) < Shapes.depthLimitFactor) {
            Shapes.current_z += speed / Main.FPS;
        }
    }


}
