package showroom.brands.KTM.dao;

import shadow.org.bson.Document;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import showroom.brands.KTM.model.KTMBike;
import java.util.ArrayList;
import java.util.List;

public class KTMDAO {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "Bikeshowroom";
    private static final String COLLECTION_NAME = "ktm_bikes";

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> ktmCollection;

    public KTMDAO() {
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
            ktmCollection = database.getCollection(COLLECTION_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addBike(KTMBike bike) {
        try {
            Document doc = new Document("modelName", bike.getModelName())
                    .append("price", bike.getPrice())
                    .append("engineCapacity", bike.getEngineCapacity())
                    .append("fuelType", bike.getFuelType())
                    .append("mileage", bike.getMileage())
                    .append("color", bike.getColor());

            ktmCollection.insertOne(doc);
            return true;
        } catch (MongoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<KTMBike> getAllBikes() {
        List<KTMBike> bikes = new ArrayList<>();
        try {
            for (Document doc : ktmCollection.find()) {
                KTMBike bike = new KTMBike(
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

    public KTMBike getBikeByName(String modelName) {
        try {
            Document query = new Document("modelName", modelName);
            Document result = ktmCollection.find(query).first();

            if (result != null) {
                return new KTMBike(
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

    public boolean updateBike(String modelName, KTMBike bike) {
        try {
            Document query = new Document("modelName", modelName);
            Document update = new Document("$set", new Document("modelName", bike.getModelName())
                    .append("price", bike.getPrice())
                    .append("engineCapacity", bike.getEngineCapacity())
                    .append("fuelType", bike.getFuelType())
                    .append("mileage", bike.getMileage())
                    .append("color", bike.getColor()));

            ktmCollection.updateOne(query, update);
            return true;
        } catch (MongoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBike(String modelName) {
        try {
            Document query = new Document("modelName", modelName);
            ktmCollection.deleteOne(query);
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
