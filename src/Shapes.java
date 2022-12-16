
/** A Utility class to describe the shapes and check if there is a pixels in it, it's better not to touch it**/

class Shapes{
    public static final float consoleWidth = Main.consoleWidth;
    public static final float consoleHeight = Main.consoleHeight;
    public static final float consoleLength = Main.consoleLength;
    public static final float ratio = Main.symbolRatio;
    public static final int R = 10;
    public static float widthLimitFactor;
    public static float heightLimitFactor;
    public static float depthLimitFactor;
    private static float center_x = (int) (consoleWidth/2);
    private static float center_y = (int) (consoleHeight*ratio/2);
    private static float center_z = (int) (consoleLength/2);
    public static boolean inSomeShape(Main.allShapes shape, float x, float y, float z){
        switch (shape){
            case Square -> {
                return inCube(x, y);
            }
            case Circle -> {
                return inCircle(x, y);
            }
            case Annulus -> {
                return inDonut2D(x, y);
            }
            case Sphere -> {
                return inSphere(x, y, z);
            }
            case Torus -> {
                return inTorus(x, y, z);
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

        return x > consoleWidth/4  && x < (consoleWidth - consoleWidth/4) && y > consoleH/4 && y < consoleH - consoleH/4;
    }
    private static boolean inCircle(float x, float y){
        y = y *ratio;
        float consoleH = consoleHeight*ratio;

        widthLimitFactor = (consoleWidth/2 - R);
        heightLimitFactor = (consoleH/2 - R);

        double distance = Math.sqrt(Math.pow(x - center_x , 2) + Math.pow(y - center_y , 2));
        return distance < R;
    }
    private static boolean inDonut2D(float x, float y){
        y = y *ratio;
        float consoleH = consoleHeight*ratio;

        widthLimitFactor = (consoleWidth/2 - R);
        heightLimitFactor = (consoleH/2 - R);

        float r = R/2f;
        double distance = Math.sqrt(Math.pow(R + r - Math.sqrt(Math.pow(x - center_x, 2) + Math.pow(y - center_y, 2)), 2));
        return distance < R;
    }

    private static boolean inSphere(float x, float y, float z){
        y = y *ratio;
        float consoleH = consoleHeight*ratio;

        widthLimitFactor = (consoleWidth/2 - R);
        heightLimitFactor = (consoleH/2 - R);
        depthLimitFactor = (consoleLength - R);

        double distance = Math.sqrt(Math.pow(x - center_x, 2) + Math.pow(y - center_y, 2) + Math.pow(z - center_z, 2));
        return (int)distance == R;
    }

    private static boolean inTorus(float x, float y, float z){
        y = y *ratio;

        widthLimitFactor = (consoleWidth/2 - R*3);
        heightLimitFactor = (consoleHeight*ratio/2 - R*3);
        depthLimitFactor = (consoleLength - R);

        float r = R/1.5f;

        double distance = Math.sqrt(Math.pow(R + r - Math.sqrt(Math.pow(x - center_x, 2) + Math.pow(y - center_y, 2)), 2) + Math.pow(z - center_z, 1.9));
        return (int) distance == R;
    }
}