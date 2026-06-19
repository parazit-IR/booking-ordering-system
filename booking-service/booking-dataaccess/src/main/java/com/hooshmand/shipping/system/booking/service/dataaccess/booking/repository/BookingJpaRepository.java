package com.hooshmand.shipping.system.booking.service.dataaccess.booking.repository;


import com.hooshmand.shipping.system.booking.service.dataaccess.booking.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingJpaRepository extends JpaRepository<BookingEntity, UUID> {
}
