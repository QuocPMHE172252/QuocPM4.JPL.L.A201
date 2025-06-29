package fa.training.entities;

import java.util.ArrayList;
import java.util.List;

public class Airport {
    private String id;
    private String name;
    private double runwaySize;
    private int maxFixedwingParking;
    private List<String> fixedwingIds;
    private int maxHelicopterParking;
    private List<String> helicopterIds;

    public Airport() {
        this.fixedwingIds = new ArrayList<>();
        this.helicopterIds = new ArrayList<>();
    }

    public Airport(String id, String name, double runwaySize, int maxFixedwingParking, int maxHelicopterParking) {
        this.id = id;
        this.name = name;
        this.runwaySize = runwaySize;
        this.maxFixedwingParking = maxFixedwingParking;
        this.maxHelicopterParking = maxHelicopterParking;
        this.fixedwingIds = new ArrayList<>();
        this.helicopterIds = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRunwaySize() {
        return runwaySize;
    }

    public void setRunwaySize(double runwaySize) {
        this.runwaySize = runwaySize;
    }

    public int getMaxFixedwingParking() {
        return maxFixedwingParking;
    }

    public void setMaxFixedwingParking(int maxFixedwingParking) {
        this.maxFixedwingParking = maxFixedwingParking;
    }

    public List<String> getFixedwingIds() {
        return fixedwingIds;
    }

    public void setFixedwingIds(List<String> fixedwingIds) {
        this.fixedwingIds = fixedwingIds;
    }

    public int getMaxHelicopterParking() {
        return maxHelicopterParking;
    }

    public void setMaxHelicopterParking(int maxHelicopterParking) {
        this.maxHelicopterParking = maxHelicopterParking;
    }

    public List<String> getHelicopterIds() {
        return helicopterIds;
    }

    public void setHelicopterIds(List<String> helicopterIds) {
        this.helicopterIds = helicopterIds;
    }

    public boolean addFixedwing(String fixedwingId) {
        if (fixedwingIds.size() < maxFixedwingParking) {
            fixedwingIds.add(fixedwingId);
            return true;
        }
        return false;
    }

    public boolean removeFixedwing(String fixedwingId) {
        return fixedwingIds.remove(fixedwingId);
    }

    public boolean addHelicopter(String helicopterId) {
        if (helicopterIds.size() < maxHelicopterParking) {
            helicopterIds.add(helicopterId);
            return true;
        }
        return false;
    }

    public boolean removeHelicopter(String helicopterId) {
        return helicopterIds.remove(helicopterId);
    }

    @Override
    public String toString() {
        return "Airport ID: " + id + ", Name: " + name + ", Runway Size: " + runwaySize + 
               ", Max Fixedwing Parking: " + maxFixedwingParking + ", Fixedwing IDs: " + fixedwingIds +
               ", Max Helicopter Parking: " + maxHelicopterParking + ", Helicopter IDs: " + helicopterIds;
    }
} 