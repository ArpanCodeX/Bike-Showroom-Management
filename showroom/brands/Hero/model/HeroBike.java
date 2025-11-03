package showroom.brands.Hero.model;

public class HeroBike {
    private String modelName;
    private double price;
    private String engineCapacity;
    private String fuelType;
    private double mileage;
    private String color;

    public HeroBike(String modelName, double price, String engineCapacity, 
                    String fuelType, double mileage, String color) {
        this.modelName = modelName;
        this.price = price;
        this.engineCapacity = engineCapacity;
        this.fuelType = fuelType;
        this.mileage = mileage;
        this.color = color;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "HeroBike{" +
                "modelName='" + modelName + '\'' +
                ", price=" + price +
                ", engineCapacity='" + engineCapacity + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", mileage=" + mileage +
                ", color='" + color + '\'' +
                '}';
    }
}
