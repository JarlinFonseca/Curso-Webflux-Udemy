package com.debuggeandoideas.eats_hub_catalog.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationResponse {

    private String restaurantId;
    private String customerName;
    private String dateTime; // example 2025-06-16,15:30
    private Integer partySize;
}