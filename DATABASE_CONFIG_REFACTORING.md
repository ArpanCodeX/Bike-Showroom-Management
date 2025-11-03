# Database Configuration Refactoring

## Overview
Centralized all MongoDB connection strings and database configuration into a single `DatabaseConfig` class to avoid code duplication and make maintenance easier.

## Changes Made

### 1. Created `showroom/core/DatabaseConfig.java`
A centralized configuration class that holds all database-related constants:

```java
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
}
```

### 2. Updated All DAO Classes
Modified all 6 DAO classes to use `DatabaseConfig` constants instead of hardcoded strings:

#### Files Updated:
- ✅ `showroom/auth/dao/UserDAO.java`
- ✅ `showroom/brands/Bajaj/dao/BajajDAO.java`
- ✅ `showroom/brands/Hero/dao/HeroDAO.java`
- ✅ `showroom/brands/Honda/dao/HondaDAO.java`
- ✅ `showroom/brands/KTM/dao/KTMDAO.java`
- ✅ `showroom/brands/RoyalEnfield/dao/RoyalEnfieldDAO.java`

#### Before (Repetitive):
```java
private static final String CONNECTION_STRING = "mongodb://localhost:27017";
private static final String DATABASE_NAME = "Bikeshowroom";
private static final String COLLECTION_NAME = "bajaj_bikes";
```

#### After (Clean & DRY):
```java
import showroom.core.DatabaseConfig;

private static final String CONNECTION_STRING = DatabaseConfig.CONNECTION_STRING;
private static final String DATABASE_NAME = DatabaseConfig.DATABASE_NAME;
private static final String COLLECTION_NAME = DatabaseConfig.BAJAJ_COLLECTION;
```

## Benefits

✅ **DRY Principle** - Don't Repeat Yourself: Configuration defined in one place  
✅ **Easy Maintenance** - Change connection string once, applies everywhere  
✅ **Professional Code** - Single source of truth for configuration  
✅ **Scalability** - Add new collections easily  
✅ **Consistency** - All DAOs use the same configuration standard  
✅ **Centralized Control** - No scattered configuration strings  

## Compilation Status
✅ All files compile successfully without errors  
✅ Application runs without issues  
✅ All 6 DAOs properly reference DatabaseConfig  

## Future Improvements
If needed, you can:
- Load configuration from a properties file
- Support different environments (dev, test, prod)
- Add connection pooling settings
- Add timeout configurations
