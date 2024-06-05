package com.gsoftcode.servicebankingsystem.services.client;


import com.gsoftcode.servicebankingsystem.dto.AdDTO;
import com.gsoftcode.servicebankingsystem.dto.AdDetailsForClientDTO;
import com.gsoftcode.servicebankingsystem.dto.ReservationDTO;
import com.gsoftcode.servicebankingsystem.dto.ReviewDTO;

import java.util.List;

public interface ClientService {

    List<AdDTO> getAllAds();

    List<AdDTO> searchAdByName(String name);

    boolean bookService(ReservationDTO reservationDTO);

    AdDetailsForClientDTO getAdDetailsForClientDTO(Long adId);
    List<ReservationDTO> getAllBookingsByUserId(Long userId);
    Boolean giveReview(ReviewDTO reviewDTO);
}
