package com.gsoftcode.servicebankingsystem.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewDTO {

    private Long id;

    private Date reviewDate;

    private String review;

    private Long Rating;

    private Long userId;

    private Long adId;

    private String clientName;

    private String serviceName;

    private Long bookId;


}
