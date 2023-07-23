package alticshaw.com.coszastore.payload.response;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;
@Data
public class OrderResponse {
    private int id;
    private double total_price;
    private String state;
    private String postcode;
    private String country_name;
    private String coupon_code;
    private String user_name;

    private Set<ProductResponse> product;

}
