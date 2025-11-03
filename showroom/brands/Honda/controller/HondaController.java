package showroom.brands.Honda.controller;

import java.util.ArrayList;
import java.util.List;
import showroom.brands.Honda.model.HondaBike;
import showroom.brands.Honda.dao.HondaDAO;

public class HondaController {
    private List<HondaBike> hondaBikes;
    private HondaDAO hondaDAO;

    public HondaController() {
        this.hondaDAO = new HondaDAO();
        initializeBikes();
    }

    private void initializeBikes() {
        // First try to fetch from database
        hondaBikes = hondaDAO.getAllBikes();
        
        // If database is empty, populate with default data
        if (hondaBikes.isEmpty()) {
            hondaBikes = new ArrayList<>();
            HondaBike[] defaultBikes = {
                new HondaBike("Honda CB Shine", 75000, "125cc", "Petrol", 70, "Black"),
                new HondaBike("Honda CB Unicorn 160", 95000, "160cc", "Petrol", 55, "Red"),
                new HondaBike("Honda X-Blade", 102000, "160cc", "Petrol", 50, "Blue"),
                new HondaBike("Honda CB 300R", 245000, "300cc", "Petrol", 32, "Silver"),
                new HondaBike("Honda Hornet 2.0", 122000, "184cc", "Petrol", 45, "Orange"),
                new HondaBike("Honda Activa 6G", 65000, "110cc", "Petrol", 60, "White")
            };
            
            // Add default bikes to database and list
            for (HondaBike bike : defaultBikes) {
                hondaDAO.addBike(bike);
                hondaBikes.add(bike);
            }
        }
    }

    public List<HondaBike> getAllBikes() {
        return new ArrayList<>(hondaBikes);
    }

    public HondaBike getBikeByName(String modelName) {
        return hondaDAO.getBikeByName(modelName);
    }

    public void addBike(HondaBike bike) {
        if (hondaDAO.addBike(bike)) {
            hondaBikes.add(bike);
        }
    }

    public void removeBike(String modelName) {
        if (hondaDAO.deleteBike(modelName)) {
            hondaBikes.removeIf(bike -> bike.getModelName().equals(modelName));
        }
    }

    public void updateBike(String modelName, HondaBike bike) {
        if (hondaDAO.updateBike(modelName, bike)) {
            // Update in-memory list
            for (int i = 0; i < hondaBikes.size(); i++) {
                if (hondaBikes.get(i).getModelName().equals(modelName)) {
                    hondaBikes.set(i, bike);
                    break;
                }
            }
        }
    }
}
