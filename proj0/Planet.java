public class Planet {

    public double xxPos, yyPos, xxVel, yyVel, mass;
    public String imgFileName;

    public static final double GRAVITY_CONSTANT = 6.67e-11;

    /**
     * Construct an planet from six parameters.
     * @param xP the x position of the planet.
     * @param yP the y position of the planet.
     * @param xV the x velocity of the planet.
     * @param yV the y velocity of the planet.
     * @param m the mass of the planet.
     * @param img the image file name of the corresponded planet.
     */
    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    /**
     * Construct a planet from an existed planet.
     * @param p is an exsited planet.
     */
    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    /**
     * Calculate the distance from this planet to the Planet p.
     * @param p is a planet.
     * @return the distance from this planet to the p.
     */
    public double calcDistance(Planet p) {
        double xxDistance = p.xxPos - this.xxPos;
        double yyDistance = p.yyPos - this.yyPos;
        double squareDistance = xxDistance * xxDistance + yyDistance * yyDistance;
        double distance = Math.sqrt(squareDistance);
        return distance;
    }

    public double calcForceExertedBy(Planet p) {
        double distance = this.calcDistance(p);
        double force = GRAVITY_CONSTANT * this.mass * p.mass / distance / distance;
        return force;
    }

    public double calcForceExertedByX(Planet p) {
        double totalDistance = this.calcDistance(p);
        double xxDistance = p.xxPos - this.xxPos;
        double cos = xxDistance / totalDistance;
        double totalForce = this.calcForceExertedBy(p);
        double xxForce = totalForce * cos;
        return xxForce;
    }

    /**
     * Calculate the force in the y axis p exerted to this plqnet.
     * @param p is a planet which exerted force to this planet.
     * @return force in the y axis which p exerted to this planet.
     */
    public double calcForceExertedByY(Planet p) {
        double totalDistance = this.calcDistance(p);
        double yyDistance = p.yyPos - this.yyPos;
        double sin = yyDistance / totalDistance;
        double totalForce = this.calcForceExertedBy(p);
        double yyForce = totalForce * sin;
        return yyForce;
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        int planetNums = planets.length;
        double xxForce = 0;
        for(int i = 0; i < planetNums; ++i) {
            if (this.equals(planets[i]) == false) {
                xxForce = xxForce + this.calcForceExertedByX(planets[i]);
            }
        }
        return xxForce;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        int planetNums = planets.length;
        double yyForce = 0;
        for(int i = 0; i < planetNums; ++i) {
            if (this.equals(planets[i]) == false) {
                yyForce = yyForce + this.calcForceExertedByY(planets[i]);
            }
        }
        return yyForce;
    }

    /**
     * Update the position, velocity, acceleration of the planet with time advanced.
     * @param timeInterval is the time advanced interval.
     * @param xxForce force in the x axis.
     * @param yyForce force in the y axis.
     */
    public void update(double timeInterval, double xxForce, double yyForce) {
        double xxAcceleration = xxForce / this.mass;
        double yyAcceleration = yyForce / this.mass;
        this.xxVel = this.xxVel + xxAcceleration * timeInterval;
        this.yyVel = this.yyVel + yyAcceleration * timeInterval;
        this.xxPos = this.xxPos + this.xxVel * timeInterval;
        this.yyPos = this.yyPos + this.yyVel * timeInterval;
    }

    public void draw() {
        String picturePath = "images/" + this.imgFileName;
        StdDraw.picture(this.xxPos, this.yyPos, picturePath);
    }

}
