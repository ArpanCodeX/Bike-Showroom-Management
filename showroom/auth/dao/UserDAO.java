package showroom.auth.dao;
import org.bson.Document;
import showroom.auth.model.User;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;

public class UserDAO {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "Bikeshowroom";
    private static final String COLLECTION_NAME = "users";

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> usersCollection;

    public UserDAO() {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                .serverApi(serverApi)
                .build();

        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase(DATABASE_NAME);
        usersCollection = database.getCollection(COLLECTION_NAME);
    }

    public boolean registerUser(User user) {
        try {
            // Check if email already exists
            if (isEmailRegistered(user.getEmail())) {
                return false;
            }

            Document doc = new Document("name", user.getName())
                    .append("phone", user.getPhone())
                    .append("address", user.getAddress())
                    .append("email", user.getEmail())
                    .append("password", user.getPassword());

            usersCollection.insertOne(doc);
            return true;
        } catch (MongoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User authenticateUser(String email, String password) {
        try {
            Document query = new Document("email", email).append("password", password);
            Document result = usersCollection.find(query).first();

            if (result != null) {
                User user = new User();
                user.setName(result.getString("name"));
                user.setPhone(result.getString("phone"));
                user.setAddress(result.getString("address"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("password"));
                return user;
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isEmailRegistered(String email) {
        try {
            Document query = new Document("email", email);
            long count = usersCollection.countDocuments(query);
            return count > 0;
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