package showroom.brands.Hero.controller;

import java.util.ArrayList;
import java.util.List;
import showroom.brands.Hero.model.HeroBike;
import showroom.brands.Hero.dao.HeroDAO;

public class HeroController {
    private List<HeroBike> heroBikes;
    private HeroDAO heroDAO;

    public HeroController() {
        this.heroDAO = new HeroDAO();
        initializeBikes();
    }

    private void initializeBikes() {
        // First try to fetch from database
        heroBikes = heroDAO.getAllBikes();
        
        // If database is empty, populate with default data
        if (heroBikes.isEmpty()) {
            heroBikes = new ArrayList<>();
            HeroBike[] defaultBikes = {
                new HeroBike("Hero HF Deluxe", 58000, "100cc", "Petrol", 80, "Black"),
                new HeroBike("Hero Splendor Plus", 63000, "100cc", "Petrol", 75, "Red"),
                new HeroBike("Hero Passion Pro", 68000, "110cc", "Petrol", 70, "Blue"),
                new HeroBike("Hero MotoCorp Xtreme 200S", 100000, "200cc", "Petrol", 45, "Silver"),
                new HeroBike("Hero MotoCorp Xpulse 200", 95000, "200cc", "Petrol", 48, "Orange"),
                new HeroBike("Hero MotoCorp Destini 125", 67000, "125cc", "Petrol", 55, "White")
            };
            
            // Add default bikes to database and list
            for (HeroBike bike : defaultBikes) {
                heroDAO.addBike(bike);
                heroBikes.add(bike);
            }
        }
    }

    public List<HeroBike> getAllBikes() {
        return new ArrayList<>(heroBikes);
    }

    public HeroBike getBikeByName(String modelName) {
        return heroDAO.getBikeByName(modelName);
    }

    public void addBike(HeroBike bike) {
        if (heroDAO.addBike(bike)) {
            heroBikes.add(bike);
        }
    }

    public void removeBike(String modelName) {
        if (heroDAO.deleteBike(modelName)) {
            heroBikes.removeIf(bike -> bike.getModelName().equals(modelName));
        }
    }

    public void updateBike(String modelName, HeroBike bike) {
        if (heroDAO.updateBike(modelName, bike)) {
            // Update in-memory list
            for (int i = 0; i < heroBikes.size(); i++) {
                if (heroBikes.get(i).getModelName().equals(modelName)) {
                    heroBikes.set(i, bike);
                    break;
                }
            }
        }
    }
}
