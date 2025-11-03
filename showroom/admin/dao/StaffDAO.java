package showroom.admin.dao;

import shadow.org.bson.Document;
import showroom.core.DatabaseConfig;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import showroom.admin.model.Staff;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {
    private static final String CONNECTION_STRING = DatabaseConfig.CONNECTION_STRING;
    private static final String DATABASE_NAME = DatabaseConfig.DATABASE_NAME;
    private static final String COLLECTION_NAME = "staff";

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> staffCollection;

    public StaffDAO() {
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
            staffCollection = database.getCollection(COLLECTION_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addStaff(Staff staff) {
        try {
            Document doc = new Document("name", staff.getName())
                    .append("email", staff.getEmail())
                    .append("phone", staff.getPhone())
                    .append("position", staff.getPosition())
                    .append("department", staff.getDepartment())
                    .append("salary", staff.getSalary())
                    .append("joiningDate", staff.getJoiningDate())
                    .append("status", staff.getStatus());

            staffCollection.insertOne(doc);
            return true;
        } catch (MongoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        try {
            for (Document doc : staffCollection.find()) {
                Staff staff = new Staff(
                        doc.getObjectId("_id").toString(),
                        doc.getString("name"),
                        doc.getString("email"),
                        doc.getString("phone"),
                        doc.getString("position"),
                        doc.getString("department"),
                        doc.getString("salary"),
                        doc.getString("joiningDate"),
                        doc.getString("status")
                );
                staffList.add(staff);
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public Staff getStaffByEmail(String email) {
        try {
            Document query = new Document("email", email);
            Document result = staffCollection.find(query).first();

            if (result != null) {
                return new Staff(
                        result.getObjectId("_id").toString(),
                        result.getString("name"),
                        result.getString("email"),
                        result.getString("phone"),
                        result.getString("position"),
                        result.getString("department"),
                        result.getString("salary"),
                        result.getString("joiningDate"),
                        result.getString("status")
                );
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateStaff(String email, Staff staff) {
        try {
            Document query = new Document("email", email);
            Document update = new Document("$set", new Document("name", staff.getName())
                    .append("phone", staff.getPhone())
                    .append("position", staff.getPosition())
                    .append("department", staff.getDepartment())
                    .append("salary", staff.getSalary())
                    .append("status", staff.getStatus()));

            staffCollection.updateOne(query, update);
            return true;
        } catch (MongoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStaff(String email) {
        try {
            Document query = new Document("email", email);
            staffCollection.deleteOne(query);
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
