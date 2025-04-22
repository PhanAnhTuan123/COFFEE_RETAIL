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
    private String resource;  // ví dụ: "account", "product"
    private String action;    // ví dụ: "get", "getAll", "save"
    private String data;      // chứa JSON (payload)

    public Request(String getCustomers, String s) {
    }
}

