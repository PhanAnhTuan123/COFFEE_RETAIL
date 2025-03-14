package socket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Request implements Serializable {
    private String action;   // ví dụ: "GET_PRODUCTS"
    private String payload;  // JSON string nếu cần dữ liệu
}
