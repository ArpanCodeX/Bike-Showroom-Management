package showroom.admin.model;

/**
 * Stock model class representing inventory/stock of bikes
 */
public class Stock {
    private String stockId;
    private String brand; // e.g., "Bajaj", "Hero", "Honda", "KTM", "RoyalEnfield"
    private String modelName;
    private int totalQuantity;
    private int availableQuantity;
    private int soldQuantity;
    private String lastRestockDate;
    private String minStockLevel;
    private String status; // e.g., "In Stock", "Low Stock", "Out of Stock"

    public Stock() {
    }

    public Stock(String brand, String modelName, int totalQuantity, int availableQuantity) {
        this.brand = brand;
        this.modelName = modelName;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
        this.soldQuantity = totalQuantity - availableQuantity;
        updateStatus();
    }

    public Stock(String stockId, String brand, String modelName, int totalQuantity, int availableQuantity, int soldQuantity, String lastRestockDate, String minStockLevel, String status) {
        this.stockId = stockId;
        this.brand = brand;
        this.modelName = modelName;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
        this.soldQuantity = soldQuantity;
        this.lastRestockDate = lastRestockDate;
        this.minStockLevel = minStockLevel;
        this.status = status;
    }

    private void updateStatus() {
        if (availableQuantity == 0) {
            this.status = "Out of Stock";
        } else if (availableQuantity < Integer.parseInt(minStockLevel != null ? minStockLevel : "5")) {
            this.status = "Low Stock";
        } else {
            this.status = "In Stock";
        }
    }

    // Getters
    public String getStockId() {
        return stockId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModelName() {
        return modelName;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public String getLastRestockDate() {
        return lastRestockDate;
    }

    public String getMinStockLevel() {
        return minStockLevel;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
        this.soldQuantity = this.totalQuantity - availableQuantity;
        updateStatus();
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public void setLastRestockDate(String lastRestockDate) {
        this.lastRestockDate = lastRestockDate;
    }

    public void setMinStockLevel(String minStockLevel) {
        this.minStockLevel = minStockLevel;
        updateStatus();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockId='" + stockId + '\'' +
                ", brand='" + brand + '\'' +
                ", modelName='" + modelName + '\'' +
                ", totalQuantity=" + totalQuantity +
                ", availableQuantity=" + availableQuantity +
                ", soldQuantity=" + soldQuantity +
                ", lastRestockDate='" + lastRestockDate + '\'' +
                ", minStockLevel='" + minStockLevel + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
