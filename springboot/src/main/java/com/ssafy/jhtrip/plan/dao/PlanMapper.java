package com.ssafy.jhtrip.plan.dao;

import com.ssafy.jhtrip.plan.dto.PlanDto;
import com.ssafy.jhtrip.plan.dto.PlanItem;
import com.ssafy.jhtrip.plan.dto.RoutePlan;
import com.ssafy.jhtrip.plan.SearchCondition;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PlanMapper {

    int insertPlan(PlanDto planDto);

    void insertRoutePlans(@Param("routePlans") List<RoutePlan> routePlans);

    List<PlanItem> selectPlanItems(@Param("planId") Long planId);

    List<PlanDto> selectPlan(@Param("userId") String userId);

    void deletePlan(@Param("planId") Long planId);

    PlanDto getDetailPlan(@Param("id") Long id);

    List<PlanItem> getAttractionsByIds(@Param("contentIds") List<Integer> contentIds);

    @Select("SELECT LAST_INSERT_ID()")
    int getPlanId();

    @Update("update plan set hit = hit + 1 where id = #{id}")
    void updateHit(@Param("id") Long id);

    @Select("select count(*) from likes_plan where user_id = #{userId} and plan_id = #{id}")
    int isLike(PlanDto planDto);

    @Select("select count(*) from likes_plan where plan_id = #{id}")
    int getLikes(Long id);

    @Insert("insert likes_plan values (#{userId}, #{id});")
    void like(PlanDto planDto);

    @Delete("delete from likes_plan where user_id = #{userId} and plan_id = #{id}")
    void unLike(PlanDto planDto);

    @Select("select id, a.user_id, plan_name, description, hit, created_at from plan a join likes_plan b on a.id = b.plan_id where b.user_id = #{id}")
    List<PlanDto> getLikePlans(String id);

    @Select("select count(*) from likes_plan where user_id = #{id}")
    int countheart(String id);

    List<PlanDto> getPlanListALL(SearchCondition condition);

    @Select("select count(*) from plan where share_status = 1")
    int getCountList();

    @Update("update plan set share_status = 1 where id = #{id}")
    int share(@Param("id") Long id);

    @Update("update plan set share_status = 0 where id = #{id}")
    int unshare(@Param("id") Long id);
}
