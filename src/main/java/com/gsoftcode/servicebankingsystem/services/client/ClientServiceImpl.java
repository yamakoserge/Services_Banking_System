package com.gsoftcode.servicebankingsystem.services.client;

import com.gsoftcode.servicebankingsystem.dto.AdDTO;
import com.gsoftcode.servicebankingsystem.dto.AdDetailsForClientDTO;
import com.gsoftcode.servicebankingsystem.dto.ReservationDTO;
import com.gsoftcode.servicebankingsystem.dto.ReviewDTO;
import com.gsoftcode.servicebankingsystem.entity.Ad;
import com.gsoftcode.servicebankingsystem.entity.Reservation;
import com.gsoftcode.servicebankingsystem.entity.Review;
import com.gsoftcode.servicebankingsystem.entity.User;
import com.gsoftcode.servicebankingsystem.enums.ReservationStatus;
import com.gsoftcode.servicebankingsystem.enums.ReviewStatus;
import com.gsoftcode.servicebankingsystem.repository.AdRepository;
import com.gsoftcode.servicebankingsystem.repository.ReservationRepository;
import com.gsoftcode.servicebankingsystem.repository.ReviewRepository;
import com.gsoftcode.servicebankingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public List<AdDTO> getAllAds(){
        return adRepository.findAll().stream().map(Ad::getAdDto).collect(Collectors.toList());
    }


    public List<AdDTO> searchAdByName(String name){
        return  adRepository.findAllByServiceNameContaining(name).stream().map(Ad::getAdDto).collect(Collectors.toList());
    }

    public boolean bookService(ReservationDTO reservationDTO){
        Optional<Ad> optionalAd = adRepository.findById(reservationDTO.getAdId());
        Optional<User> optionalUser = userRepository.findById(reservationDTO.getUserId());

        if (optionalAd.isPresent()&& optionalUser.isPresent()){

            Reservation reservation = new Reservation();

            reservation.setBookDate(reservationDTO.getBookDate());
            reservation.setReservationStatus(ReservationStatus.PENDING);
            reservation.setUser(optionalUser.get());

            reservation.setAd(optionalAd.get());
            reservation.setCompany(optionalAd.get().getUser());
            reservation.setReviewStatus(ReviewStatus.FALSE);

            reservationRepository.save(reservation);

            return true;
        }
        return false;
    }

    public AdDetailsForClientDTO getAdDetailsForClientDTO(Long adId){
        Optional<Ad> optionalAd = adRepository.findById(adId);
        AdDetailsForClientDTO adDetailsForClientDTO = new AdDetailsForClientDTO();
        if (optionalAd.isPresent()){
            adDetailsForClientDTO.setAdDTO(optionalAd.get().getAdDto());

            List<Review> reviewList = reviewRepository.findAllByAdId(adId);
            adDetailsForClientDTO.setReviewDTOList(reviewList.stream().map(Review::getDto).collect(Collectors.toList()));

        }
        return adDetailsForClientDTO;
    }

    public List<ReservationDTO> getAllBookingsByUserId(Long userId){
        return reservationRepository.findAllByUserId(userId).stream()
                .map(Reservation::getReservationDto)
                .collect(Collectors.toList());
    }

    public Boolean giveReview(ReviewDTO reviewDTO){
        Optional<User> optionalUser = userRepository.findById(reviewDTO.getUserId());
        Optional<Reservation> optionalBooking = reservationRepository.findById(reviewDTO.getBookId());

        if (optionalUser.isPresent() && optionalBooking.isPresent()){
            Review review = new Review();

            review.setReviewDate(new Date());
            review.setReview(reviewDTO.getReview());
            review.setRating(reviewDTO.getRating());

            review.setUser(optionalUser.get());
            review.setAd(optionalBooking.get().getAd());

            reviewRepository.save(review);

            Reservation booking = optionalBooking.get();
            booking.setReviewStatus(ReviewStatus.TRUE);

            reservationRepository.save(booking);

            return true;
        }
        return false;
    }
}
