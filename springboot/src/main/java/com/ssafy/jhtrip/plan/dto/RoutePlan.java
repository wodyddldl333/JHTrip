package com.ssafy.jhtrip.plan.dto;

import lombok.Data;

@Data
public class RoutePlan {
    private Long id;
    private Long planId;
    private int contentId;
    private int sequence;
}
