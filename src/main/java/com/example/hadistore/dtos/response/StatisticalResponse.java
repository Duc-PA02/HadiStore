package com.example.hadistore.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class StatisticalResponse {
    private int month;
    private Date date;
    private Double amount;
    private int count;
}
