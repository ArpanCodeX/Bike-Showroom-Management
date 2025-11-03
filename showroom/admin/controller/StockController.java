package showroom.admin.controller;

import showroom.admin.dao.StockDAO;
import showroom.admin.model.Stock;
import java.util.List;

public class StockController {
    private StockDAO stockDAO;

    public StockController() {
        this.stockDAO = new StockDAO();
    }

    public boolean addStock(Stock stock) {
        if (stock == null || stock.getBrand() == null || stock.getModelName() == null) {
            return false;
        }
        return stockDAO.addStock(stock);
    }

    public List<Stock> getAllStock() {
        return stockDAO.getAllStock();
    }

    public Stock getStockByModel(String brand, String modelName) {
        if (brand == null || modelName == null || brand.trim().isEmpty() || modelName.trim().isEmpty()) {
            return null;
        }
        return stockDAO.getStockByModel(brand, modelName);
    }

    public boolean updateStock(String brand, String modelName, Stock stock) {
        if (brand == null || modelName == null || stock == null) {
            return false;
        }
        return stockDAO.updateStock(brand, modelName, stock);
    }

    public boolean deleteStock(String brand, String modelName) {
        if (brand == null || modelName == null) {
            return false;
        }
        return stockDAO.deleteStock(brand, modelName);
    }

    public void closeConnection() {
        stockDAO.closeConnection();
    }
}
