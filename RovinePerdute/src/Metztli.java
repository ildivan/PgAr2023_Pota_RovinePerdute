public class Metztli implements EdgeCalculator {

    @Override
    public int calculateEdgeDistance(City first, City second) {
        int fuel = second.getH() - first.getH();
        return Math.abs(fuel);
    }
}
