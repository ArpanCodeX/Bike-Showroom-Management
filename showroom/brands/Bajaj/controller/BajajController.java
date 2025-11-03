package showroom.brands.Bajaj.controller;

import java.util.ArrayList;
import java.util.List;
import showroom.brands.Bajaj.model.BajajBike;
import showroom.brands.Bajaj.dao.BajajDAO;

public class BajajController {
    private List<BajajBike> bajajBikes;
    private BajajDAO bajajDAO;

    public BajajController() {
        this.bajajDAO = new BajajDAO();
        initializeBikes();
    }

    private void initializeBikes() {
        // First try to fetch from database
        bajajBikes = bajajDAO.getAllBikes();
        
        // If database is empty, populate with default data
        if (bajajBikes.isEmpty()) {
            bajajBikes = new ArrayList<>();
            BajajBike[] defaultBikes = {
                new BajajBike("Bajaj Pulsar 150", 85000, "150cc", "Petrol", 60, "Black"),
                new BajajBike("Bajaj Pulsar NS200", 115000, "200cc", "Petrol", 45, "Red"),
                new BajajBike("Bajaj Dominar 400", 185000, "400cc", "Petrol", 32, "Silver"),
                new BajajBike("Bajaj Avenger Cruise 220", 98000, "220cc", "Petrol", 50, "Blue"),
                new BajajBike("Bajaj Platina 100", 56000, "100cc", "Petrol", 80, "White"),
                new BajajBike("Bajaj CT100", 52000, "100cc", "Petrol", 85, "Green")
            };
            
            // Add default bikes to database and list
            for (BajajBike bike : defaultBikes) {
                bajajDAO.addBike(bike);
                bajajBikes.add(bike);
            }
        }
    }

    public List<BajajBike> getAllBikes() {
        return new ArrayList<>(bajajBikes);
    }

    public BajajBike getBikeByName(String modelName) {
        return bajajDAO.getBikeByName(modelName);
    }

    public void addBike(BajajBike bike) {
        if (bajajDAO.addBike(bike)) {
            bajajBikes.add(bike);
        }
    }

    public void removeBike(String modelName) {
        if (bajajDAO.deleteBike(modelName)) {
            bajajBikes.removeIf(bike -> bike.getModelName().equals(modelName));
        }
    }

    public void updateBike(String modelName, BajajBike bike) {
        if (bajajDAO.updateBike(modelName, bike)) {
            // Update in-memory list
            for (int i = 0; i < bajajBikes.size(); i++) {
                if (bajajBikes.get(i).getModelName().equals(modelName)) {
                    bajajBikes.set(i, bike);
                    break;
                }
            }
        }
    }
}
