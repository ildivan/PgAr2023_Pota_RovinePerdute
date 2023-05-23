public class Metztli implements EdgeCalculator {

    @Override
    public double calculateEdgeDistance(City first, City second) {
        double fuel = second.getH() - first.getH();
        return Math.abs(fuel);
    }
}
