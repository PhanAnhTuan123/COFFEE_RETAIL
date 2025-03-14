package com.example.coffeeretail.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Request implements Serializable {
    private String action;    // ví dụ: "GET_PRODUCTS"
    private String payload;   // JSON string nếu cần dữ liệu

}


