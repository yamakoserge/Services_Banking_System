package com.gsoftcode.servicebankingsystem.dto;

import com.gsoftcode.servicebankingsystem.enums.ReservationStatus;
import com.gsoftcode.servicebankingsystem.enums.ReviewStatus;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationDTO {

    private Long id;

    private Date bookDate;

    private ReservationStatus reservationStatus;

    private ReviewStatus reviewStatus;

    private Long userId;

    private String userName;

    private Long companyId;

    private Long adId;


}
