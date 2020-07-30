public class Testt {
    public static float sqrt(float f) {
        return (float) Math.sqrt(f);
    }

    public static float sqrt(double d) {
        return (float) Math.sqrt(d);
    }

    public static void main(String[] args) {
        int n = 30_000_000;
        int n2 = 30_000_000;
        int n3=n * n + n2 * n2;
        System.out.println((long)n * n + n2 * n2);
        System.out.println(n3);
        System.out.println(sqrt(n * n + n2 * n2));
    }
}
