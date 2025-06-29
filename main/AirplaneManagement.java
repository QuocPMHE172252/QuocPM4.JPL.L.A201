package fa.training.main;

import fa.training.entities.Airport;
import fa.training.entities.Fixedwing;
import fa.training.entities.Helicopter;
import fa.training.services.AirportService;
import fa.training.services.FixedwingService;
import fa.training.services.HelicopterService;
import fa.training.utils.ValidationUtils;

import java.util.List;
import java.util.Scanner;

public class AirplaneManagement {
    private static AirportService airportService;
    private static FixedwingService fixedwingService;
    private static HelicopterService helicopterService;
    private static Scanner scanner;

    public static void main(String[] args) {
        initializeServices();
        scanner = new Scanner(System.in);
        
        while (true) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    inputDataFromKeyboard();
                    break;
                case 2:
                    airportManagement();
                    break;
                case 3:
                    fixedwingManagement();
                    break;
                case 4:
                    helicopterManagement();
                    break;
                case 5:
                    System.out.println("Thank you for using Airport Management System!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void initializeServices() {
        airportService = new AirportService();
        fixedwingService = new FixedwingService();
        helicopterService = new HelicopterService();
    }
    
    private static void displayMainMenu() {
        System.out.println("\n=== AIRPORT MANAGEMENT SYSTEM ===");
        System.out.println("1. Input data from keyboard");
        System.out.println("2. Airport management");
        System.out.println("3. Fixed wing airplane management");
        System.out.println("4. Helicopter management");
        System.out.println("5. Close program");
        System.out.println("=================================");
    }
    
    private static void inputDataFromKeyboard() {
        System.out.println("\n=== INPUT DATA FROM KEYBOARD ===");
        System.out.println("1. Create new airport");
        System.out.println("2. Create new fixed wing airplane");
        System.out.println("3. Create new helicopter");
        System.out.println("4. Back to main menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                createNewAirport();
                break;
            case 2:
                createNewFixedwing();
                break;
            case 3:
                createNewHelicopter();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void createNewAirport() {
        System.out.println("\n--- Create New Airport ---");
        
        String id = getStringInput("Enter airport ID (AP followed by 5 digits): ");
        String name = getStringInput("Enter airport name: ");
        double runwaySize = getDoubleInput("Enter runway size: ");
        int maxFixedwingParking = getIntInput("Enter max fixed wing parking capacity: ");
        int maxHelicopterParking = getIntInput("Enter max helicopter parking capacity: ");
        
        airportService.createAirport(id, name, runwaySize, maxFixedwingParking, maxHelicopterParking);
    }
    
    private static void createNewFixedwing() {
        System.out.println("\n--- Create New Fixed Wing Airplane ---");
        
        String id = getStringInput("Enter fixed wing ID (FW followed by 5 digits): ");
        String model = getStringInput("Enter model: ");
        double cruiseSpeed = getDoubleInput("Enter cruise speed: ");
        double emptyWeight = getDoubleInput("Enter empty weight: ");
        double maxTakeoffWeight = getDoubleInput("Enter max takeoff weight: ");
        
        System.out.println("Available plane types: CAG (Cargo), LGR (Long range), PRV (Private)");
        String planeType = getStringInput("Enter plane type: ");
        double minNeededRunwaySize = getDoubleInput("Enter min needed runway size: ");
        
        fixedwingService.createFixedwing(id, model, cruiseSpeed, emptyWeight, maxTakeoffWeight, planeType, minNeededRunwaySize);
    }
    
    private static void createNewHelicopter() {
        System.out.println("\n--- Create New Helicopter ---");
        
        String id = getStringInput("Enter helicopter ID (RW followed by 5 digits): ");
        String model = getStringInput("Enter model: ");
        double cruiseSpeed = getDoubleInput("Enter cruise speed: ");
        double emptyWeight = getDoubleInput("Enter empty weight: ");
        double maxTakeoffWeight = getDoubleInput("Enter max takeoff weight: ");
        double range = getDoubleInput("Enter range: ");
        
        helicopterService.createHelicopter(id, model, cruiseSpeed, emptyWeight, maxTakeoffWeight, range);
    }
    
    private static void airportManagement() {
        System.out.println("\n=== AIRPORT MANAGEMENT ===");
        System.out.println("1. Display all airports");
        System.out.println("2. Display airport status by ID");
        System.out.println("3. Add fixed wing to airport");
        System.out.println("4. Remove fixed wing from airport");
        System.out.println("5. Add helicopter to airport");
        System.out.println("6. Remove helicopter from airport");
        System.out.println("7. Back to main menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                displayAllAirports();
                break;
            case 2:
                displayAirportStatus();
                break;
            case 3:
                addFixedwingToAirport();
                break;
            case 4:
                removeFixedwingFromAirport();
                break;
            case 5:
                addHelicopterToAirport();
                break;
            case 6:
                removeHelicopterFromAirport();
                break;
            case 7:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void displayAllAirports() {
        System.out.println("\n--- All Airports ---");
        List<Airport> airports = airportService.getAllAirports();
        if (airports.isEmpty()) {
            System.out.println("No airports found.");
        } else {
            for (Airport airport : airports) {
                System.out.println(airport);
            }
        }
    }
    
    private static void displayAirportStatus() {
        String airportId = getStringInput("Enter airport ID: ");
        Airport airport = airportService.getAirportById(airportId);
        if (airport != null) {
            System.out.println("\n--- Airport Status ---");
            System.out.println(airport);
        } else {
            System.out.println("Airport not found.");
        }
    }
    
    private static void addFixedwingToAirport() {
        String airportId = getStringInput("Enter airport ID: ");
        String fixedwingId = getStringInput("Enter fixed wing ID: ");
        
        // Check if fixed wing exists and is available
        Fixedwing fixedwing = fixedwingService.getFixedwingById(fixedwingId);
        if (fixedwing == null) {
            System.out.println("Error: Fixed wing airplane not found.");
            return;
        }
        
        if (fixedwing.getParkingAirportId() != null && !fixedwing.getParkingAirportId().isEmpty()) {
            System.out.println("Error: Fixed wing airplane is already parked at an airport.");
            return;
        }
        
        // Check runway size compatibility
        Airport airport = airportService.getAirportById(airportId);
        if (airport != null) {
            if (!ValidationUtils.isValidRunwaySize(fixedwing.getMinNeededRunwaySize(), airport.getRunwaySize())) {
                System.out.println("Error: Fixed wing airplane's min runway size exceeds airport runway size.");
                return;
            }
        }
        
        if (airportService.addFixedwingToAirport(airportId, fixedwingId)) {
            fixedwingService.setParkingAirport(fixedwingId, airportId);
            System.out.println("Fixed wing airplane added to airport successfully.");
        }
    }
    
    private static void removeFixedwingFromAirport() {
        String airportId = getStringInput("Enter airport ID: ");
        String fixedwingId = getStringInput("Enter fixed wing ID: ");
        
        if (airportService.removeFixedwingFromAirport(airportId, fixedwingId)) {
            fixedwingService.setParkingAirport(fixedwingId, null);
            System.out.println("Fixed wing airplane removed from airport successfully.");
        }
    }
    
    private static void addHelicopterToAirport() {
        String airportId = getStringInput("Enter airport ID: ");
        String helicopterId = getStringInput("Enter helicopter ID: ");
        
        // Check if helicopter exists and is available
        Helicopter helicopter = helicopterService.getHelicopterById(helicopterId);
        if (helicopter == null) {
            System.out.println("Error: Helicopter not found.");
            return;
        }
        
        if (helicopter.getParkingAirportId() != null && !helicopter.getParkingAirportId().isEmpty()) {
            System.out.println("Error: Helicopter is already parked at an airport.");
            return;
        }
        
        if (airportService.addHelicopterToAirport(airportId, helicopterId)) {
            helicopterService.setParkingAirport(helicopterId, airportId);
            System.out.println("Helicopter added to airport successfully.");
        }
    }
    
    private static void removeHelicopterFromAirport() {
        String airportId = getStringInput("Enter airport ID: ");
        String helicopterId = getStringInput("Enter helicopter ID: ");
        
        if (airportService.removeHelicopterFromAirport(airportId, helicopterId)) {
            helicopterService.setParkingAirport(helicopterId, null);
            System.out.println("Helicopter removed from airport successfully.");
        }
    }
    
    private static void fixedwingManagement() {
        System.out.println("\n=== FIXED WING AIRPLANE MANAGEMENT ===");
        System.out.println("1. Display all fixed wing airplanes");
        System.out.println("2. Update fixed wing airplane");
        System.out.println("3. Back to main menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                displayAllFixedwings();
                break;
            case 2:
                updateFixedwing();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void displayAllFixedwings() {
        System.out.println("\n--- All Fixed Wing Airplanes ---");
        List<Fixedwing> fixedwings = fixedwingService.getAllFixedwings();
        if (fixedwings.isEmpty()) {
            System.out.println("No fixed wing airplanes found.");
        } else {
            for (Fixedwing fixedwing : fixedwings) {
                String airportName = "Not parked";
                if (fixedwing.getParkingAirportId() != null && !fixedwing.getParkingAirportId().isEmpty()) {
                    Airport airport = airportService.getAirportById(fixedwing.getParkingAirportId());
                    if (airport != null) {
                        airportName = airport.getName();
                    }
                }
                System.out.println(fixedwing + " | Parking Airport: " + airportName);
            }
        }
    }
    
    private static void updateFixedwing() {
        String id = getStringInput("Enter fixed wing ID to update: ");
        System.out.println("Available plane types: CAG (Cargo), LGR (Long range), PRV (Private)");
        String newPlaneType = getStringInput("Enter new plane type: ");
        double newMinNeededRunwaySize = getDoubleInput("Enter new min needed runway size: ");
        
        fixedwingService.updateFixedwing(id, newPlaneType, newMinNeededRunwaySize);
    }
    
    private static void helicopterManagement() {
        System.out.println("\n=== HELICOPTER MANAGEMENT ===");
        System.out.println("1. Display all helicopters");
        System.out.println("2. Back to main menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                displayAllHelicopters();
                break;
            case 2:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void displayAllHelicopters() {
        System.out.println("\n--- All Helicopters ---");
        List<Helicopter> helicopters = helicopterService.getAllHelicopters();
        if (helicopters.isEmpty()) {
            System.out.println("No helicopters found.");
        } else {
            for (Helicopter helicopter : helicopters) {
                String airportName = "Not parked";
                if (helicopter.getParkingAirportId() != null && !helicopter.getParkingAirportId().isEmpty()) {
                    Airport airport = airportService.getAirportById(helicopter.getParkingAirportId());
                    if (airport != null) {
                        airportName = airport.getName();
                    }
                }
                System.out.println(helicopter + " | Parking Airport: " + airportName);
            }
        }
    }
    
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
} 