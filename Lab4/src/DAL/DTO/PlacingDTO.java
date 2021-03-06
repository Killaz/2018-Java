package DAL.DTO;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import DAL.CSVFile.CSVFile;
import DAL.CSVFile.CSVException;

public class PlacingDTO implements DTO {
	int productID, shopID, quantity, price;
	boolean isComplete;
	public /* ? */ PlacingDTO(int shopID, int productID, int quantity, int price) throws SQLException {
		if (price <= 0 || quantity < 0 || shopID < 0 || productID < 0)
			throw new SQLException("price <= 0 || quantity < 0 || shopID < 0 || productID < 0");
		this.shopID = shopID;
		this.productID = productID;
		this.quantity = quantity;
		this.price = price;
		this.isComplete = true;
	}
	public PlacingDTO(int quantity, int price) throws SQLException {
		if (price <= 0 || quantity < 0)
			throw new SQLException("Price must be >0, quantity must be >= 0");
		this.productID = -1;
		this.shopID = -1;
		this.price = price;
		this.quantity = quantity;
		this.isComplete = false;
	}
	public PlacingDTO(ResultSet rs) throws SQLException {
		try {
			rs.getString("ShopID");
			rs.getString("ProductID");
			rs.getString("Quantity");
			rs.getString("Price");
		} catch (Exception ex) {
			if (ex.getMessage().equals("ResultSet closed"))
				throw ex;
			throw new SQLException("Error at PlacingDTO constructor from ResultSet\n", ex);
		}
		shopID = new Integer(rs.getString("ShopID"));
		productID = new Integer(rs.getString("ProductID"));
		quantity = new Integer(rs.getString("Quantity"));
		price = new Integer(rs.getString("Price"));
	}
	public PlacingDTO(CSVFile csv) throws CSVException {
		if (csv.getColumns() != 4)
			throw new CSVException("Error at PlacingDTO constructor from CSVParser: number of columns != 4");
		try {
			shopID = new Integer(csv.getString(1));
			productID = new Integer(csv.getString(2));
			quantity = new Integer(csv.getString(3));
			price = new Integer(csv.getString(4));
		} catch (NumberFormatException ex) {
			throw new CSVException("Bad CSV file: one of the columns (shopID, productID, quantity, price) cannot be cast to Integer");
		}
	}
	public int getShopID() {
		return shopID;
	}
	public int getProductID() {
		return productID;
	}
	public int getQuantity() {
		return quantity;
	}
	public int getPrice() {
		return price;
	}
	public boolean isComplete() {
		return this.isComplete;
	}
	public PlacingDTO setQuantity(int newQuantity) {
		quantity = newQuantity;
		return this;
	}
	public PlacingDTO setPrice(int newPrice) {
		quantity = newPrice;
		return this;
	}
	@Override
	public String toString() {
		return String.format("(Placing DTO) Product %d at place %d, %d pieces with price %d", productID, shopID, quantity, price);
	}
}
