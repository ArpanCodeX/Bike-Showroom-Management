package showroom.brands.KTM.controller;

import java.util.ArrayList;
import java.util.List;
import showroom.brands.KTM.model.KTMBike;
import showroom.brands.KTM.dao.KTMDAO;

public class KTMController {
    private List<KTMBike> ktmBikes;
    private KTMDAO ktmDAO;

    public KTMController() {
        this.ktmDAO = new KTMDAO();
        initializeBikes();
    }

    private void initializeBikes() {
        // First try to fetch from database
        ktmBikes = ktmDAO.getAllBikes();
        
        // If database is empty, populate with default data
        if (ktmBikes.isEmpty()) {
            ktmBikes = new ArrayList<>();
            KTMBike[] defaultBikes = {
                new KTMBike("KTM 125 Duke", 148000, "125cc", "Petrol", 40, "Orange"),
                new KTMBike("KTM 200 Duke", 168000, "200cc", "Petrol", 35, "Orange"),
                new KTMBike("KTM 390 Duke", 248000, "390cc", "Petrol", 28, "Orange"),
                new KTMBike("KTM RC 125", 155000, "125cc", "Petrol", 38, "Orange"),
                new KTMBike("KTM RC 200", 185000, "200cc", "Petrol", 33, "Orange"),
                new KTMBike("KTM 250 SX-F", 450000, "250cc", "Petrol", 25, "Orange")
            };
            
            // Add default bikes to database and list
            for (KTMBike bike : defaultBikes) {
                ktmDAO.addBike(bike);
                ktmBikes.add(bike);
            }
        }
    }

    public List<KTMBike> getAllBikes() {
        return new ArrayList<>(ktmBikes);
    }

    public KTMBike getBikeByName(String modelName) {
        return ktmDAO.getBikeByName(modelName);
    }

    public void addBike(KTMBike bike) {
        if (ktmDAO.addBike(bike)) {
            ktmBikes.add(bike);
        }
    }

    public void removeBike(String modelName) {
        if (ktmDAO.deleteBike(modelName)) {
            ktmBikes.removeIf(bike -> bike.getModelName().equals(modelName));
        }
    }

    public void updateBike(String modelName, KTMBike bike) {
        if (ktmDAO.updateBike(modelName, bike)) {
            // Update in-memory list
            for (int i = 0; i < ktmBikes.size(); i++) {
                if (ktmBikes.get(i).getModelName().equals(modelName)) {
                    ktmBikes.set(i, bike);
                    break;
                }
            }
        }
    }
}
