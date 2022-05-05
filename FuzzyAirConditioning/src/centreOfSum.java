public class centreOfSum {
    protected static double trapezoidArea(double botRight, double topRight, double botLeft, double topLeft, double height){
        if (height == 0)
            return 0;
        //check for triangle
        if (topLeft == topRight){
            return (botRight - botLeft)*height/2;
        }
        double baseSum = botRight-botLeft + topRight - topLeft;
        return baseSum*height/2;
    }

    protected static double centreOfArea(double botRight, double topRight, double botLeft, double topLeft){
        double sum = botLeft+botRight+topLeft+topRight;
        return sum/4;
    }
}
