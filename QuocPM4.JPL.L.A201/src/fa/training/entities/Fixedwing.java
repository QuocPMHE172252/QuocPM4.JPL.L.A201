package fa.training.entities;

public class Fixedwing extends Airplane {
    private String planeType;
    private double minNeededRunwaySize;

    public Fixedwing() {
        super();
    }

    public Fixedwing(String id, String model, double cruiseSpeed, double emptyWeight, 
                    double maxTakeoffWeight, String planeType, double minNeededRunwaySize) {
        super(id, model, cruiseSpeed, emptyWeight, maxTakeoffWeight);
        this.planeType = planeType;
        this.minNeededRunwaySize = minNeededRunwaySize;
    }

    public String getPlaneType() {
        return planeType;
    }

    public void setPlaneType(String planeType) {
        this.planeType = planeType;
    }

    public double getMinNeededRunwaySize() {
        return minNeededRunwaySize;
    }

    public void setMinNeededRunwaySize(double minNeededRunwaySize) {
        this.minNeededRunwaySize = minNeededRunwaySize;
    }

    @Override
    public String fly() {
        return "fixed wing";
    }

    @Override
    public String toString() {
        return super.toString() + ", Plane Type: " + planeType + 
               ", Min Needed Runway Size: " + minNeededRunwaySize + ", Fly Method: " + fly();
    }
} 