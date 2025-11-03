package showroom.brands.RoyalEnfield.controller;

import java.util.ArrayList;
import java.util.List;
import showroom.brands.RoyalEnfield.model.RoyalEnfieldBike;
import showroom.brands.RoyalEnfield.dao.RoyalEnfieldDAO;

public class RoyalEnfieldController {
    private List<RoyalEnfieldBike> reBikes;
    private RoyalEnfieldDAO reDAO;

    public RoyalEnfieldController() {
        this.reDAO = new RoyalEnfieldDAO();
        initializeBikes();
    }

    private void initializeBikes() {
        // First try to fetch from database
        reBikes = reDAO.getAllBikes();
        
        // If database is empty, populate with default data
        if (reBikes.isEmpty()) {
            reBikes = new ArrayList<>();
            RoyalEnfieldBike[] defaultBikes = {
                new RoyalEnfieldBike("Royal Enfield Bullet 350", 145000, "350cc", "Petrol", 40, "Black"),
                new RoyalEnfieldBike("Royal Enfield Classic 350", 155000, "350cc", "Petrol", 38, "Chrome"),
                new RoyalEnfieldBike("Royal Enfield Electra 350", 140000, "350cc", "Petrol", 42, "Red"),
                new RoyalEnfieldBike("Royal Enfield Thunderbird 350", 162000, "350cc", "Petrol", 35, "Silver"),
                new RoyalEnfieldBike("Royal Enfield 650 Twin", 265000, "650cc", "Petrol", 30, "Black"),
                new RoyalEnfieldBike("Royal Enfield Himalayan 410", 245000, "410cc", "Petrol", 32, "Green")
            };
            
            // Add default bikes to database and list
            for (RoyalEnfieldBike bike : defaultBikes) {
                reDAO.addBike(bike);
                reBikes.add(bike);
            }
        }
    }

    public List<RoyalEnfieldBike> getAllBikes() {
        return new ArrayList<>(reBikes);
    }

    public RoyalEnfieldBike getBikeByName(String modelName) {
        return reDAO.getBikeByName(modelName);
    }

    public void addBike(RoyalEnfieldBike bike) {
        if (reDAO.addBike(bike)) {
            reBikes.add(bike);
        }
    }

    public void removeBike(String modelName) {
        if (reDAO.deleteBike(modelName)) {
            reBikes.removeIf(bike -> bike.getModelName().equals(modelName));
        }
    }

    public void updateBike(String modelName, RoyalEnfieldBike bike) {
        if (reDAO.updateBike(modelName, bike)) {
            // Update in-memory list
            for (int i = 0; i < reBikes.size(); i++) {
                if (reBikes.get(i).getModelName().equals(modelName)) {
                    reBikes.set(i, bike);
                    break;
                }
            }
        }
    }
}
