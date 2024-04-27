package com.gsoftcode.servicebankingsystem.services.company;

import com.gsoftcode.servicebankingsystem.dto.AdDTO;

import java.io.IOException;

public interface CompanyService {
    boolean postAd(Long userId, AdDTO adDTO) throws IOException;
}
