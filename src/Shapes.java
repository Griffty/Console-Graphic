
/** A Utility class to describe the shapes and check if there is a pixels in it, it's better not to touch it**/

class Shapes{
    public static final float consoleWidth = Main.consoleWidth;
    public static final float consoleHeight = Main.consoleHeight;
    public static final float consoleLength = Main.consoleLength;
    public static final float ratio = Main.symbolRatio;
    public static final int R = 12;
    public static float current_x = 0;
    public static float current_y = 0;
    public static float current_z = 0;
    public static float widthLimitFactor;
    public static float heightLimitFactor;
    public static float depthLimitFactor;

    public static boolean inSomeShape2D(Main.allShapes shape, float x, float y){
        switch (shape){
            case Cube -> {
                return inCube(x, y);
            }
            case Circle -> {
                return inCircle(x, y);
            }
            case Donut2d -> {
                return inDonut2D(x, y);
            }
            default -> {
                return false;
            }
        }
    }
    public static boolean inSomeShape3D(Main.allShapes shape, float x, float y, int z){
        switch (shape){
            case Sphere -> {
                return inSphere(x, y, z);
            }
            default -> {
                return false;
            }
        }
    }

    private static boolean inCube(float x, float y){
        y = y *ratio;
        float consoleH = consoleHeight*ratio;

        widthLimitFactor = consoleWidth/4;
        heightLimitFactor = consoleH/4;

        return x > consoleWidth/4 + current_x && x < (consoleWidth - consoleWidth/4) + current_x && y > consoleH/4 + current_y && y < consoleH - consoleH/4 + current_y;
    }
    private static boolean inCircle(float x, float y){
        y = y *ratio;
        float consoleH = consoleHeight*ratio;

        widthLimitFactor = (consoleWidth/2 - R);
        heightLimitFactor = (consoleH/2 - R);

        float center_x = (int) (consoleWidth/2);
        float center_y = (int) (consoleH/2);
        double distance = Math.sqrt(Math.pow(x - (center_x + current_x), 2) + Math.pow(y - (center_y - current_y), 2));
        return distance < R;
    }
    private static boolean inDonut2D(float x, float y){
        y = y *ratio;
        float consoleH = consoleHeight*ratio;

        widthLimitFactor = (consoleWidth/2 - R);
        heightLimitFactor = (consoleH/2 - R);

        float center_x = (int) (consoleWidth/2);
        float center_y = (int) (consoleH/2);
        float r = R/2.5f;
        double distance = Math.sqrt(Math.pow(x - (center_x + current_x), 2) + Math.pow(y - (center_y - current_y), 2));
        return distance < R && distance > r;
    }

    private static boolean inSphere(float x, float y, float z){
        y = y *ratio;
        float consoleH = consoleHeight*ratio;

        widthLimitFactor = (consoleWidth/2 - R);
        heightLimitFactor = (consoleH/2 - R);
        depthLimitFactor = (consoleLength/2 - R);

        float center_x = (int) (consoleWidth/2);
        float center_y = (int) (consoleH/2);
        float center_z = (int) (consoleLength/2);
        double distance = Math.sqrt(Math.pow(x - (center_x + current_x), 2) + Math.pow(y - (center_y - current_y), 2) + Math.pow(z - (center_z - current_z), 2));
        return distance < R;
    }

}