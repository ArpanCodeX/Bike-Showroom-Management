package showroom.core;

/**
 * Centralized database configuration for all DAOs
 * This class contains all shared MongoDB connection constants
 */
public class DatabaseConfig {
    // MongoDB Connection Settings
    public static final String CONNECTION_STRING = "mongodb://localhost:27017";
    public static final String DATABASE_NAME = "Bikeshowroom";
    
    // Collection Names
    public static final String USERS_COLLECTION = "users";
    public static final String BAJAJ_COLLECTION = "bajaj_bikes";
    public static final String HERO_COLLECTION = "hero_bikes";
    public static final String HONDA_COLLECTION = "honda_bikes";
    public static final String KTM_COLLECTION = "ktm_bikes";
    public static final String ROYAL_ENFIELD_COLLECTION = "royal_enfield_bikes";
    
    // Private constructor to prevent instantiation
    private DatabaseConfig() {
        throw new AssertionError("DatabaseConfig should not be instantiated");
    }
}
