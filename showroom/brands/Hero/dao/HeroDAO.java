package showroom.brands.Hero.dao;

import shadow.org.bson.Document;
import showroom.core.DatabaseConfig;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import showroom.brands.Hero.model.HeroBike;
import java.util.ArrayList;
import java.util.List;

public class HeroDAO {
    private static final String CONNECTION_STRING = DatabaseConfig.CONNECTION_STRING;
    private static final String DATABASE_NAME = DatabaseConfig.DATABASE_NAME;
    private static final String COLLECTION_NAME = DatabaseConfig.HERO_COLLECTION;

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> heroCollection;

    public HeroDAO() {
        try {
            ServerApi serverApi = ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build();

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                    .serverApi(serverApi)
                    .build();

            mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase(DATABASE_NAME);
            heroCollection = database.getCollection(COLLECTION_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addBike(HeroBike bike) {
        try {
            Document doc = new Document("modelName", bike.getModelName())
                    .append("price", bike.getPrice())
                    .append("engineCapacity", bike.getEngineCapacity())
                    .append("fuelType", bike.getFuelType())
                    .append("mileage", bike.getMileage())
                    .append("color", bike.getColor());

            heroCollection.insertOne(doc);
            return true;
        } catch (MongoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<HeroBike> getAllBikes() {
        List<HeroBike> bikes = new ArrayList<>();
        try {
            for (Document doc : heroCollection.find()) {
                HeroBike bike = new HeroBike(
                        doc.getString("modelName"),
                        doc.getDouble("price"),
                        doc.getString("engineCapacity"),
                        doc.getString("fuelType"),
                        doc.getDouble("mileage"),
                        doc.getString("color")
                );
                bikes.add(bike);
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return bikes;
    }

    public HeroBike getBikeByName(String modelName) {
        try {
            Document query = new Document("modelName", modelName);
            Document result = heroCollection.find(query).first();

            if (result != null) {
                return new HeroBike(
                        result.getString("modelName"),
                        result.getDouble("price"),
                        result.getString("engineCapacity"),
                        result.getString("fuelType"),
                        result.getDouble("mileage"),
                        result.getString("color")
                );
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateBike(String modelName, HeroBike bike) {
        try {
            Document query = new Document("modelName", modelName);
            Document update = new Document("$set", new Document("modelName", bike.getModelName())
                    .append("price", bike.getPrice())
                    .append("engineCapacity", bike.getEngineCapacity())
                    .append("fuelType", bike.getFuelType())
                    .append("mileage", bike.getMileage())
                    .append("color", bike.getColor()));

            heroCollection.updateOne(query, update);
            return true;
        } catch (MongoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBike(String modelName) {
        try {
            Document query = new Document("modelName", modelName);
            heroCollection.deleteOne(query);
            return true;
        } catch (MongoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
