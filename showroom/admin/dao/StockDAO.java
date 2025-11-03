package showroom.admin.dao;

import shadow.org.bson.Document;
import showroom.core.DatabaseConfig;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import showroom.admin.model.Stock;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {
    private static final String CONNECTION_STRING = DatabaseConfig.CONNECTION_STRING;
    private static final String DATABASE_NAME = DatabaseConfig.DATABASE_NAME;
    private static final String COLLECTION_NAME = "stock";

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> stockCollection;

    public StockDAO() {
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
            stockCollection = database.getCollection(COLLECTION_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addStock(Stock stock) {
        try {
            Document doc = new Document("brand", stock.getBrand())
                    .append("modelName", stock.getModelName())
                    .append("totalQuantity", stock.getTotalQuantity())
                    .append("availableQuantity", stock.getAvailableQuantity())
                    .append("soldQuantity", stock.getSoldQuantity())
                    .append("lastRestockDate", stock.getLastRestockDate())
                    .append("minStockLevel", stock.getMinStockLevel())
                    .append("status", stock.getStatus());

            stockCollection.insertOne(doc);
            return true;
        } catch (MongoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Stock> getAllStock() {
        List<Stock> stockList = new ArrayList<>();
        try {
            for (Document doc : stockCollection.find()) {
                Stock stock = new Stock(
                        doc.getObjectId("_id").toString(),
                        doc.getString("brand"),
                        doc.getString("modelName"),
                        doc.getInteger("totalQuantity"),
                        doc.getInteger("availableQuantity"),
                        doc.getInteger("soldQuantity"),
                        doc.getString("lastRestockDate"),
                        doc.getString("minStockLevel"),
                        doc.getString("status")
                );
                stockList.add(stock);
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return stockList;
    }

    public Stock getStockByModel(String brand, String modelName) {
        try {
            Document query = new Document("brand", brand).append("modelName", modelName);
            Document result = stockCollection.find(query).first();

            if (result != null) {
                return new Stock(
                        result.getObjectId("_id").toString(),
                        result.getString("brand"),
                        result.getString("modelName"),
                        result.getInteger("totalQuantity"),
                        result.getInteger("availableQuantity"),
                        result.getInteger("soldQuantity"),
                        result.getString("lastRestockDate"),
                        result.getString("minStockLevel"),
                        result.getString("status")
                );
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateStock(String brand, String modelName, Stock stock) {
        try {
            Document query = new Document("brand", brand).append("modelName", modelName);
            Document update = new Document("$set", new Document("totalQuantity", stock.getTotalQuantity())
                    .append("availableQuantity", stock.getAvailableQuantity())
                    .append("soldQuantity", stock.getSoldQuantity())
                    .append("lastRestockDate", stock.getLastRestockDate())
                    .append("minStockLevel", stock.getMinStockLevel())
                    .append("status", stock.getStatus()));

            stockCollection.updateOne(query, update);
            return true;
        } catch (MongoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStock(String brand, String modelName) {
        try {
            Document query = new Document("brand", brand).append("modelName", modelName);
            stockCollection.deleteOne(query);
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
