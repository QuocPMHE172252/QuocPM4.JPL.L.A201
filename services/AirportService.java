package fa.training.services;

import fa.training.entities.Airport;
import fa.training.utils.ValidationUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AirportService {
    private static final String AIRPORT_FILE = "airports.txt";
    private List<Airport> airports;
    
    public AirportService() {
        this.airports = new ArrayList<>();
        loadAirportsFromFile();
    }
    
    public boolean createAirport(String id, String name, double runwaySize, 
                                int maxFixedwingParking, int maxHelicopterParking) {
        // Validate inputs
        if (!ValidationUtils.isValidAirportId(id)) {
            System.out.println("Error: Invalid airport ID format. Must be AP followed by 5 digits.");
            return false;
        }
        
        if (!ValidationUtils.isValidModel(name)) {
            System.out.println("Error: Invalid airport name. Must not be empty and maximum 40 characters.");
            return false;
        }
        
        if (!ValidationUtils.isPositiveNumber(runwaySize)) {
            System.out.println("Error: Runway size must be positive.");
            return false;
        }
        
        if (!ValidationUtils.isPositiveInteger(maxFixedwingParking) || 
            !ValidationUtils.isPositiveInteger(maxHelicopterParking)) {
            System.out.println("Error: Parking capacities must be positive integers.");
            return false;
        }
        
        // Check if ID is unique
        List<String> existingIds = new ArrayList<>();
        for (Airport airport : airports) {
            existingIds.add(airport.getId());
        }
        
        if (!ValidationUtils.isIdUnique(id, existingIds)) {
            System.out.println("Error: Airport ID already exists.");
            return false;
        }
        
        // Create new airport
        Airport airport = new Airport(id, name, runwaySize, maxFixedwingParking, maxHelicopterParking);
        airports.add(airport);
        saveAirportsToFile();
        System.out.println("Airport created successfully: " + airport.getName());
        return true;
    }
    
    public List<Airport> getAllAirports() {
        Collections.sort(airports, (a1, a2) -> a1.getId().compareTo(a2.getId()));
        return airports;
    }
    
    public Airport getAirportById(String id) {
        for (Airport airport : airports) {
            if (airport.getId().equals(id)) {
                return airport;
            }
        }
        return null;
    }
    
    public boolean addFixedwingToAirport(String airportId, String fixedwingId) {
        Airport airport = getAirportById(airportId);
        if (airport == null) {
            System.out.println("Error: Airport not found.");
            return false;
        }
        
        if (airport.addFixedwing(fixedwingId)) {
            saveAirportsToFile();
            return true;
        } else {
            System.out.println("Error: Airport parking capacity exceeded for fixed wing airplanes.");
            return false;
        }
    }
    
    public boolean removeFixedwingFromAirport(String airportId, String fixedwingId) {
        Airport airport = getAirportById(airportId);
        if (airport == null) {
            System.out.println("Error: Airport not found.");
            return false;
        }
        
        if (airport.removeFixedwing(fixedwingId)) {
            saveAirportsToFile();
            return true;
        } else {
            System.out.println("Error: Fixed wing airplane not found in this airport.");
            return false;
        }
    }
    
    public boolean addHelicopterToAirport(String airportId, String helicopterId) {
        Airport airport = getAirportById(airportId);
        if (airport == null) {
            System.out.println("Error: Airport not found.");
            return false;
        }
        
        if (airport.addHelicopter(helicopterId)) {
            saveAirportsToFile();
            return true;
        } else {
            System.out.println("Error: Airport parking capacity exceeded for helicopters.");
            return false;
        }
    }
    
    public boolean removeHelicopterFromAirport(String airportId, String helicopterId) {
        Airport airport = getAirportById(airportId);
        if (airport == null) {
            System.out.println("Error: Airport not found.");
            return false;
        }
        
        if (airport.removeHelicopter(helicopterId)) {
            saveAirportsToFile();
            return true;
        } else {
            System.out.println("Error: Helicopter not found in this airport.");
            return false;
        }
    }
    
    private void loadAirportsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(AIRPORT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    double runwaySize = Double.parseDouble(parts[2].trim());
                    int maxFixedwingParking = Integer.parseInt(parts[3].trim());
                    int maxHelicopterParking = Integer.parseInt(parts[4].trim());
                    
                    Airport airport = new Airport(id, name, runwaySize, maxFixedwingParking, maxHelicopterParking);
                    
                    // Load fixedwing IDs if available
                    if (parts.length > 5 && !parts[5].trim().isEmpty()) {
                        String[] fixedwingIds = parts[5].trim().split(";");
                        for (String fwId : fixedwingIds) {
                            if (!fwId.trim().isEmpty()) {
                                airport.addFixedwing(fwId.trim());
                            }
                        }
                    }
                    
                    // Load helicopter IDs if available
                    if (parts.length > 6 && !parts[6].trim().isEmpty()) {
                        String[] helicopterIds = parts[6].trim().split(";");
                        for (String heliId : helicopterIds) {
                            if (!heliId.trim().isEmpty()) {
                                airport.addHelicopter(heliId.trim());
                            }
                        }
                    }
                    
                    airports.add(airport);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, that's okay
        } catch (IOException e) {
            System.out.println("Error loading airports from file: " + e.getMessage());
        }
    }
    
    private void saveAirportsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(AIRPORT_FILE))) {
            for (Airport airport : airports) {
                StringBuilder line = new StringBuilder();
                line.append(airport.getId()).append(",");
                line.append(airport.getName()).append(",");
                line.append(airport.getRunwaySize()).append(",");
                line.append(airport.getMaxFixedwingParking()).append(",");
                line.append(airport.getMaxHelicopterParking()).append(",");
                
                // Add fixedwing IDs
                if (!airport.getFixedwingIds().isEmpty()) {
                    line.append(String.join(";", airport.getFixedwingIds()));
                }
                line.append(",");
                
                // Add helicopter IDs
                if (!airport.getHelicopterIds().isEmpty()) {
                    line.append(String.join(";", airport.getHelicopterIds()));
                }
                
                writer.println(line.toString());
            }
        } catch (IOException e) {
            System.out.println("Error saving airports to file: " + e.getMessage());
        }
    }
} 