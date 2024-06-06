package com.ssafy.jhtrip.plan.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlanDto {
    private Long id;
    private String userId;
    private String planName;
    private String description;
    private int hit;
    private int like;
    private String createdAt;
    private List<RoutePlan> routePlans;
}
