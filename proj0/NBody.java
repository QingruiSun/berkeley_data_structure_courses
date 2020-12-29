public class NBody {

    public static double readRadius(String fileName) {
        In in = new In(fileName);
        Integer planetNum = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    /**
     * Construct an array of planets from the file.
     * @param fileName is name of the file.
     * @return an array of planets.
     */
    public static Planet[] readPlanets(String fileName) {
        In in  = new In(fileName);
        Integer planetNum = in.readInt();
        double radius = in.readDouble();
        Planet[] planets = new Planet[planetNum];
        for (int i = 0; i < planetNum; ++i) {
            double xxPosition = in.readDouble();
            double yyPosition = in.readDouble();
            double xxVelocity = in.readDouble();
            double yyVelocity = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            Planet planet = new Planet(xxPosition, yyPosition, xxVelocity, yyVelocity, mass, imgFileName);
            planets[i] = planet;
        }
        return planets;
    }

    public static void main(String[] args) {
        double endTime = Double.parseDouble(args[0]);
        double advancedTime = Double.parseDouble(args[1]);
        String fileName = args[2];
        double radius = readRadius(fileName);
        Planet[] planets = readPlanets(fileName);
        int planetNum = planets.length;
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.enableDoubleBuffering();
        double time = 0;
        while (time <= endTime) {
            double[] xForces = new double[planetNum];
            double[] yForces = new double[planetNum];
            for (int i = 0; i < planetNum; ++i) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < planetNum; ++i) {
                planets[i].update(advancedTime, xForces[i], yForces[i]);
            }
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (int i = 0; i < planetNum; ++i) {
                planets[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time = time + advancedTime;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
