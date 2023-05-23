public class Tonatiuh implements EdgeCalculator {

    @Override
    public int calculateEdgeDistance(City first, City second) {
        int deltaX = (int) Math.pow(second.getX() - first.getX(), 2);
        int deltaY = (int) Math.pow(second.getY() - first.getY(), 2);
        return (int) Math.sqrt(deltaY + deltaX);
    }

}
