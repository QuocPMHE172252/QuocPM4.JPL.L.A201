package fa.training.entities;

public abstract class Airplane {
    private String id;
    private String model;
    private double cruiseSpeed;
    private double emptyWeight;
    private double maxTakeoffWeight;
    private String parkingAirportId;

    public Airplane() {
    }

    public Airplane(String id, String model, double cruiseSpeed, double emptyWeight, double maxTakeoffWeight) {
        this.id = id;
        this.model = model;
        this.cruiseSpeed = cruiseSpeed;
        this.emptyWeight = emptyWeight;
        this.maxTakeoffWeight = maxTakeoffWeight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getCruiseSpeed() {
        return cruiseSpeed;
    }

    public void setCruiseSpeed(double cruiseSpeed) {
        this.cruiseSpeed = cruiseSpeed;
    }

    public double getEmptyWeight() {
        return emptyWeight;
    }

    public void setEmptyWeight(double emptyWeight) {
        this.emptyWeight = emptyWeight;
    }

    public double getMaxTakeoffWeight() {
        return maxTakeoffWeight;
    }

    public void setMaxTakeoffWeight(double maxTakeoffWeight) {
        this.maxTakeoffWeight = maxTakeoffWeight;
    }

    public String getParkingAirportId() {
        return parkingAirportId;
    }

    public void setParkingAirportId(String parkingAirportId) {
        this.parkingAirportId = parkingAirportId;
    }

    public abstract String fly();

    @Override
    public String toString() {
        return "ID: " + id + ", Model: " + model + ", Cruise Speed: " + cruiseSpeed + 
               ", Empty Weight: " + emptyWeight + ", Max Takeoff Weight: " + maxTakeoffWeight;
    }
} 