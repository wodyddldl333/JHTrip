package com.ssafy.jhtrip.plan.service;

import com.ssafy.jhtrip.plan.dto.PlanDto;
import com.ssafy.jhtrip.plan.dto.PlanItem;
import com.ssafy.jhtrip.plan.SearchCondition;

import java.util.List;

public interface PlanService {
    int createPlan(PlanDto planDto);

    List<PlanItem> getPlan(Long id);

    List<PlanDto> getPlans(String userId);

    void deletePlan(Long id);

    PlanDto getDetailPlan(Long id);

    List<PlanItem> getAttractionsByIds(List<Integer> contentIds);

    int isLike(PlanDto planDto);

    int getLikes(Long id);

    void like(PlanDto planDto);

    void unlike(PlanDto planDto);

    List<PlanDto> getLikePlans(String id);

    int countheart(String id);

    List<PlanDto> getPlanALL(SearchCondition condition);

    int getCountList();

    int share(Long id);

    int unshare(Long id);
}
