package fa.training.utils;

import java.util.List;
import java.util.regex.Pattern;

public class ValidationUtils {
    
    // ID validation patterns
    private static final Pattern FIXEDWING_ID_PATTERN = Pattern.compile("^FW\\d{5}$");
    private static final Pattern HELICOPTER_ID_PATTERN = Pattern.compile("^RW\\d{5}$");
    private static final Pattern AIRPORT_ID_PATTERN = Pattern.compile("^AP\\d{5}$");
    
    // Fixed wing airplane types
    public static final String[] FIXEDWING_TYPES = {"CAG", "LGR", "PRV"};
    
    // Maximum model size
    public static final int MAX_MODEL_SIZE = 40;
    
    // Helicopter weight ratio
    public static final double HELICOPTER_WEIGHT_RATIO = 1.5;
    
    public static boolean isValidFixedwingId(String id) {
        return id != null && FIXEDWING_ID_PATTERN.matcher(id).matches();
    }
    
    public static boolean isValidHelicopterId(String id) {
        return id != null && HELICOPTER_ID_PATTERN.matcher(id).matches();
    }
    
    public static boolean isValidAirportId(String id) {
        return id != null && AIRPORT_ID_PATTERN.matcher(id).matches();
    }
    
    public static boolean isValidModel(String model) {
        return model != null && model.length() <= MAX_MODEL_SIZE && !model.trim().isEmpty();
    }
    
    public static boolean isValidFixedwingType(String type) {
        if (type == null) return false;
        for (String validType : FIXEDWING_TYPES) {
            if (validType.equals(type)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isValidRunwaySize(double minNeeded, double airportRunway) {
        return minNeeded <= airportRunway;
    }
    
    public static boolean isValidHelicopterWeight(double emptyWeight, double maxTakeoffWeight) {
        return maxTakeoffWeight <= emptyWeight * HELICOPTER_WEIGHT_RATIO;
    }
    
    public static boolean isIdUnique(String id, List<String> existingIds) {
        return !existingIds.contains(id);
    }
    
    public static boolean isPositiveNumber(double number) {
        return number > 0;
    }
    
    public static boolean isPositiveInteger(int number) {
        return number > 0;
    }
} 