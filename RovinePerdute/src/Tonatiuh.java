public class Tonatiuh implements EdgeCalculator {

    @Override
    public double calculateEdgeDistance(City first, City second) {
        double deltaX =  Math.pow(second.getX() - first.getX(), 2);
        double deltaY =  Math.pow(second.getY() - first.getY(), 2);
        return  Math.sqrt(deltaY + deltaX);
    }

}
