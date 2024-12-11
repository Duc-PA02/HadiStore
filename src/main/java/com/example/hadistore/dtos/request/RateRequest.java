package com.example.hadistore.dtos.request;

import lombok.Getter;

import java.util.Date;

@Getter
public class RateRequest {
    private Long id;
    private Double rating;
    private String comment;
    private Date rateDate;
    private Long userId;
    private Long productId;
    private Long orderDetailId;
}
