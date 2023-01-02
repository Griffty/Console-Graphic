class Shapes{
    public static float consoleWidth = 120;
    public static float consoleHeight = 28;
    public static float consoleLength = 60;
    public static float ratio = 18f/9f;
    public static int R = 4;
    public static float widthLimitFactor;
    public static float heightLimitFactor;
    public static float depthLimitFactor;
    public static float center_x;
    public static float center_y;
    public static float center_z;
    public static void update(){
        center_x = (int) (consoleWidth/2);
        center_y = (int) (consoleHeight*ratio/2);
        center_z = (int) (consoleLength/2);
    }
    public static boolean inSomeShape(Main.allShapes shape, float x, float y, float z){
        switch (shape){
            case Rectangle -> {
                return inCube(x, y, z);
            }
            case Circle -> {
                return inCircle(x, y, z);
            }
            case Annulus -> {
                return inDonut2D(x, y, z);
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
    private static boolean inCube(float x, float y , float z) {
        if (z == center_z) {
            y = y * ratio;
            float consoleH = consoleHeight * ratio;

            widthLimitFactor = consoleWidth / 4;
            heightLimitFactor = consoleH / 4;

            return x > consoleWidth / 4 && x < (consoleWidth - consoleWidth / 4) && y > consoleH / 4 && y < consoleH - consoleH / 4;
        }
        return false;
    }
    private static boolean inCircle(float x, float y, float z){
        if (z == center_z) {
            y = y * ratio;
            float consoleH = consoleHeight * ratio;

            widthLimitFactor = (consoleWidth / 2 - R);
            heightLimitFactor = (consoleH / 2 - R);

            double distance = Math.sqrt(Math.pow(x - center_x, 2) + Math.pow(y - center_y, 2));
            return distance < R;
        }
        return false;
    }
    private static boolean inDonut2D(float x, float y, float z){
        if (z==center_z) {
            y = y * ratio;
            float consoleH = consoleHeight * ratio;

            widthLimitFactor = (consoleWidth / 2 - R);
            heightLimitFactor = (consoleH / 2 - R);

            float r = R / 2f;
            double distance = Math.sqrt(Math.pow(R + r - Math.sqrt(Math.pow(x - center_x, 2) + Math.pow(y - center_y, 2)), 2));
            return distance < R;
        }
        return false;
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
        double distance = Math.sqrt(Math.pow(R + r - Math.sqrt(Math.pow(x - center_x, 2) + Math.pow(y - center_y, 2)), 2) + Math.pow(z - center_z, 2));
        return (int) distance == R;
    }
}