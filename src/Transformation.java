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

}
