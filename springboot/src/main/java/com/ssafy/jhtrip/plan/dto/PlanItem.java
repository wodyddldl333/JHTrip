package com.ssafy.jhtrip.plan.dto;

import lombok.Data;

@Data
public class PlanItem {
    private int sequence;
    private int contentId;
    private String title;
    private String addr1;
    private String firstImage;
    private float latitude;
    private float longitude;
}
