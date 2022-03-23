public class StatLib {
    // Simple Average:
    public static float avg(float[] x) {
        int sum = 0,i;
        for(i = 0;i<x.length;i++) {sum += x[i];}
        return sum / i;
    }
    // Variance between X,Y:
    public static float var(float[] x) {






        return 0;
    }
    // Covariance of X and Y:
    public static float cov(float[] x, float[] y) {return 0;}
    // Pearson correlation coefficient of X and Y:
    public static float pearson(float[] x, float[] y) {return 0;}
    // performs a linear regression and returns the line equation
    public static Line linear_reg(Point[] points) {
        Line Line = null;
        return Line;}
    // returns the deviation between point p and the line equation of the points
    public static float dev(Point p,Point[] points);
    // returns the deviation between point p and the line
    public static float dev(Point p,Line l);


}
