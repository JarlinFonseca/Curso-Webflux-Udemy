package com.debuggeandoideas.customer_manager.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationRequest {

    private String restaurantId;
    private String customerId;
    private String customerName;
    private String customerEmail;
    private String dateTime;
    private Integer partySize;
    private String comment;
}