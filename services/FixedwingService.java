package fa.training.services;

import fa.training.entities.Fixedwing;
import fa.training.utils.ValidationUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FixedwingService {
    private static final String FIXEDWING_FILE = "fixedwings.txt";
    private List<Fixedwing> fixedwings;
    
    public FixedwingService() {
        this.fixedwings = new ArrayList<>();
        loadFixedwingsFromFile();
    }
    
    public boolean createFixedwing(String id, String model, double cruiseSpeed, double emptyWeight,
                                  double maxTakeoffWeight, String planeType, double minNeededRunwaySize) {
        // Validate inputs
        if (!ValidationUtils.isValidFixedwingId(id)) {
            System.out.println("Error: Invalid fixed wing ID format. Must be FW followed by 5 digits.");
            return false;
        }
        
        if (!ValidationUtils.isValidModel(model)) {
            System.out.println("Error: Invalid model. Must not be empty and maximum 40 characters.");
            return false;
        }
        
        if (!ValidationUtils.isValidFixedwingType(planeType)) {
            System.out.println("Error: Invalid plane type. Must be CAG, LGR, or PRV.");
            return false;
        }
        
        if (!ValidationUtils.isPositiveNumber(cruiseSpeed) || 
            !ValidationUtils.isPositiveNumber(emptyWeight) || 
            !ValidationUtils.isPositiveNumber(maxTakeoffWeight) || 
            !ValidationUtils.isPositiveNumber(minNeededRunwaySize)) {
            System.out.println("Error: All numeric values must be positive.");
            return false;
        }
        
        // Check if ID is unique
        List<String> existingIds = new ArrayList<>();
        for (Fixedwing fixedwing : fixedwings) {
            existingIds.add(fixedwing.getId());
        }
        
        if (!ValidationUtils.isIdUnique(id, existingIds)) {
            System.out.println("Error: Fixed wing ID already exists.");
            return false;
        }
        
        // Create new fixed wing
        Fixedwing fixedwing = new Fixedwing(id, model, cruiseSpeed, emptyWeight, maxTakeoffWeight, planeType, minNeededRunwaySize);
        fixedwings.add(fixedwing);
        saveFixedwingsToFile();
        System.out.println("Fixed wing airplane created successfully: " + fixedwing.getModel());
        return true;
    }
    
    public List<Fixedwing> getAllFixedwings() {
        return fixedwings;
    }
    
    public Fixedwing getFixedwingById(String id) {
        for (Fixedwing fixedwing : fixedwings) {
            if (fixedwing.getId().equals(id)) {
                return fixedwing;
            }
        }
        return null;
    }
    
    public boolean updateFixedwing(String id, String newPlaneType, double newMinNeededRunwaySize) {
        Fixedwing fixedwing = getFixedwingById(id);
        if (fixedwing == null) {
            System.out.println("Error: Fixed wing airplane not found.");
            return false;
        }
        
        if (!ValidationUtils.isValidFixedwingType(newPlaneType)) {
            System.out.println("Error: Invalid plane type. Must be CAG, LGR, or PRV.");
            return false;
        }
        
        if (!ValidationUtils.isPositiveNumber(newMinNeededRunwaySize)) {
            System.out.println("Error: Min needed runway size must be positive.");
            return false;
        }
        
        fixedwing.setPlaneType(newPlaneType);
        fixedwing.setMinNeededRunwaySize(newMinNeededRunwaySize);
        saveFixedwingsToFile();
        System.out.println("Fixed wing airplane updated successfully.");
        return true;
    }
    
    public List<Fixedwing> getAvailableFixedwings() {
        List<Fixedwing> available = new ArrayList<>();
        for (Fixedwing fixedwing : fixedwings) {
            if (fixedwing.getParkingAirportId() == null || fixedwing.getParkingAirportId().isEmpty()) {
                available.add(fixedwing);
            }
        }
        return available;
    }
    
    public boolean setParkingAirport(String fixedwingId, String airportId) {
        Fixedwing fixedwing = getFixedwingById(fixedwingId);
        if (fixedwing == null) {
            System.out.println("Error: Fixed wing airplane not found.");
            return false;
        }
        
        fixedwing.setParkingAirportId(airportId);
        saveFixedwingsToFile();
        return true;
    }
    
    private void loadFixedwingsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FIXEDWING_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    String id = parts[0].trim();
                    String model = parts[1].trim();
                    double cruiseSpeed = Double.parseDouble(parts[2].trim());
                    double emptyWeight = Double.parseDouble(parts[3].trim());
                    double maxTakeoffWeight = Double.parseDouble(parts[4].trim());
                    String planeType = parts[5].trim();
                    double minNeededRunwaySize = Double.parseDouble(parts[6].trim());
                    
                    Fixedwing fixedwing = new Fixedwing(id, model, cruiseSpeed, emptyWeight, maxTakeoffWeight, planeType, minNeededRunwaySize);
                    
                    // Load parking airport ID if available
                    if (parts.length > 7 && !parts[7].trim().isEmpty()) {
                        fixedwing.setParkingAirportId(parts[7].trim());
                    }
                    
                    fixedwings.add(fixedwing);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, that's okay
        } catch (IOException e) {
            System.out.println("Error loading fixed wings from file: " + e.getMessage());
        }
    }
    
    private void saveFixedwingsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FIXEDWING_FILE))) {
            for (Fixedwing fixedwing : fixedwings) {
                StringBuilder line = new StringBuilder();
                line.append(fixedwing.getId()).append(",");
                line.append(fixedwing.getModel()).append(",");
                line.append(fixedwing.getCruiseSpeed()).append(",");
                line.append(fixedwing.getEmptyWeight()).append(",");
                line.append(fixedwing.getMaxTakeoffWeight()).append(",");
                line.append(fixedwing.getPlaneType()).append(",");
                line.append(fixedwing.getMinNeededRunwaySize()).append(",");
                
                if (fixedwing.getParkingAirportId() != null) {
                    line.append(fixedwing.getParkingAirportId());
                }
                
                writer.println(line.toString());
            }
        } catch (IOException e) {
            System.out.println("Error saving fixed wings to file: " + e.getMessage());
        }
    }
} 