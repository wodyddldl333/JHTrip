package com.ssafy.jhtrip.plan.service;

import com.ssafy.jhtrip.plan.dao.PlanMapper;
import com.ssafy.jhtrip.plan.dto.PlanDto;
import com.ssafy.jhtrip.plan.dto.PlanItem;
import com.ssafy.jhtrip.plan.SearchCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {

    private final PlanMapper planMapper;

    public PlanServiceImpl(PlanMapper planMapper) {
        this.planMapper = planMapper;
    }

    @Transactional
    @Override
    public int createPlan(PlanDto planDto) {
        planMapper.insertPlan(planDto);
        int result = planMapper.getPlanId();
        planDto.getRoutePlans().forEach(routePlan -> routePlan.setPlanId(planDto.getId()));
        planMapper.insertRoutePlans(planDto.getRoutePlans());
        return result;
    }

    @Override
    public List<PlanItem> getPlan(Long id) {
        return planMapper.selectPlanItems(id);
    }

    @Override
    public List<PlanDto> getPlans(String userId) {
        return planMapper.selectPlan(userId);
    }

    @Override
    public void deletePlan(Long id) {
        planMapper.deletePlan(id);
    }

    @Override
    @Transactional
    public PlanDto getDetailPlan(Long id) {
        planMapper.updateHit(id);
        return planMapper.getDetailPlan(id);
    }

    @Override
    public List<PlanItem> getAttractionsByIds(List<Integer> contentIds) {
        return planMapper.getAttractionsByIds(contentIds);
    }

    @Override
    public int isLike(PlanDto planDto) {
        return planMapper.isLike(planDto);
    }

    @Override
    public int getLikes(Long id) {
        return planMapper.getLikes(id);
    }

    @Override
    public void like(PlanDto planDto) {
        planMapper.like(planDto);
    }

    @Override
    public void unlike(PlanDto planDto) {
        planMapper.unLike(planDto);
    }

    @Override
    public List<PlanDto> getLikePlans(String id) {
        return planMapper.getLikePlans(id);
    }

    @Override
    public int countheart(String id) {
        return planMapper.countheart(id);
    }

    @Override
    public List<PlanDto> getPlanALL(SearchCondition condition) {
        return planMapper.getPlanListALL(condition);
    }

    @Override
    public int getCountList() {
        return planMapper.getCountList();
    }

    @Override
    public int share(Long id) {
        return planMapper.share(id);
    }

    @Override
    public int unshare(Long id) {
        return planMapper.unshare(id);
    }
}
