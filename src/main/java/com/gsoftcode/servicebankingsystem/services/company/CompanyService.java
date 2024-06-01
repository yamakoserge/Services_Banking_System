package com.gsoftcode.servicebankingsystem.services.company;

import com.gsoftcode.servicebankingsystem.dto.AdDTO;
import com.gsoftcode.servicebankingsystem.dto.ReservationDTO;

import java.io.IOException;
import java.util.List;

public interface CompanyService {
    boolean postAd(Long userId, AdDTO adDTO) throws IOException;

    public List<AdDTO> getAllAds(Long userId);

    public AdDTO getAdById(Long adDTO);

    boolean updateAd(Long adId, AdDTO adDTO) throws IOException;

    boolean deleteAd(Long adId);

    List<ReservationDTO> getAllAdBookings(Long companyId);

    boolean changeBookingStatus(Long bookingId, String status);
}
