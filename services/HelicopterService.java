package fa.training.services;

import fa.training.entities.Helicopter;
import fa.training.utils.ValidationUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HelicopterService {
    private static final String HELICOPTER_FILE = "helicopters.txt";
    private List<Helicopter> helicopters;
    
    public HelicopterService() {
        this.helicopters = new ArrayList<>();
        loadHelicoptersFromFile();
    }
    
    public boolean createHelicopter(String id, String model, double cruiseSpeed, double emptyWeight,
                                   double maxTakeoffWeight, double range) {
        // Validate inputs
        if (!ValidationUtils.isValidHelicopterId(id)) {
            System.out.println("Error: Invalid helicopter ID format. Must be RW followed by 5 digits.");
            return false;
        }
        
        if (!ValidationUtils.isValidModel(model)) {
            System.out.println("Error: Invalid model. Must not be empty and maximum 40 characters.");
            return false;
        }
        
        if (!ValidationUtils.isPositiveNumber(cruiseSpeed) || 
            !ValidationUtils.isPositiveNumber(emptyWeight) || 
            !ValidationUtils.isPositiveNumber(maxTakeoffWeight) || 
            !ValidationUtils.isPositiveNumber(range)) {
            System.out.println("Error: All numeric values must be positive.");
            return false;
        }
        
        if (!ValidationUtils.isValidHelicopterWeight(emptyWeight, maxTakeoffWeight)) {
            System.out.println("Error: Max takeoff weight cannot exceed 1.5 times empty weight.");
            return false;
        }
        
        // Check if ID is unique
        List<String> existingIds = new ArrayList<>();
        for (Helicopter helicopter : helicopters) {
            existingIds.add(helicopter.getId());
        }
        
        if (!ValidationUtils.isIdUnique(id, existingIds)) {
            System.out.println("Error: Helicopter ID already exists.");
            return false;
        }
        
        // Create new helicopter
        Helicopter helicopter = new Helicopter(id, model, cruiseSpeed, emptyWeight, maxTakeoffWeight, range);
        helicopters.add(helicopter);
        saveHelicoptersToFile();
        System.out.println("Helicopter created successfully: " + helicopter.getModel());
        return true;
    }
    
    public List<Helicopter> getAllHelicopters() {
        return helicopters;
    }
    
    public Helicopter getHelicopterById(String id) {
        for (Helicopter helicopter : helicopters) {
            if (helicopter.getId().equals(id)) {
                return helicopter;
            }
        }
        return null;
    }
    
    public List<Helicopter> getAvailableHelicopters() {
        List<Helicopter> available = new ArrayList<>();
        for (Helicopter helicopter : helicopters) {
            if (helicopter.getParkingAirportId() == null || helicopter.getParkingAirportId().isEmpty()) {
                available.add(helicopter);
            }
        }
        return available;
    }
    
    public boolean setParkingAirport(String helicopterId, String airportId) {
        Helicopter helicopter = getHelicopterById(helicopterId);
        if (helicopter == null) {
            System.out.println("Error: Helicopter not found.");
            return false;
        }
        
        helicopter.setParkingAirportId(airportId);
        saveHelicoptersToFile();
        return true;
    }
    
    private void loadHelicoptersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HELICOPTER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String id = parts[0].trim();
                    String model = parts[1].trim();
                    double cruiseSpeed = Double.parseDouble(parts[2].trim());
                    double emptyWeight = Double.parseDouble(parts[3].trim());
                    double maxTakeoffWeight = Double.parseDouble(parts[4].trim());
                    double range = Double.parseDouble(parts[5].trim());
                    
                    Helicopter helicopter = new Helicopter(id, model, cruiseSpeed, emptyWeight, maxTakeoffWeight, range);
                    
                    // Load parking airport ID if available
                    if (parts.length > 6 && !parts[6].trim().isEmpty()) {
                        helicopter.setParkingAirportId(parts[6].trim());
                    }
                    
                    helicopters.add(helicopter);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, that's okay
        } catch (IOException e) {
            System.out.println("Error loading helicopters from file: " + e.getMessage());
        }
    }
    
    private void saveHelicoptersToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HELICOPTER_FILE))) {
            for (Helicopter helicopter : helicopters) {
                StringBuilder line = new StringBuilder();
                line.append(helicopter.getId()).append(",");
                line.append(helicopter.getModel()).append(",");
                line.append(helicopter.getCruiseSpeed()).append(",");
                line.append(helicopter.getEmptyWeight()).append(",");
                line.append(helicopter.getMaxTakeoffWeight()).append(",");
                line.append(helicopter.getRange()).append(",");
                
                if (helicopter.getParkingAirportId() != null) {
                    line.append(helicopter.getParkingAirportId());
                }
                
                writer.println(line.toString());
            }
        } catch (IOException e) {
            System.out.println("Error saving helicopters to file: " + e.getMessage());
        }
    }
} 