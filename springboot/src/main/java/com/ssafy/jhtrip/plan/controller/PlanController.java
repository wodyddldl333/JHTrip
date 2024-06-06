package com.ssafy.jhtrip.plan.controller;

import com.ssafy.jhtrip.plan.dto.PlanDto;
import com.ssafy.jhtrip.plan.dto.PlanItem;
import com.ssafy.jhtrip.plan.SearchCondition;
import com.ssafy.jhtrip.plan.service.PlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plan")
@Slf4j
public class PlanController {
    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping("/write")
    public ResponseEntity<?> createPlan(@RequestBody PlanDto planDto) {
        log.info("Create plan: {}", planDto);
        int id = planService.createPlan(planDto);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<?> viewPlan(@PathVariable Long id) {
        log.info("View plan: {}", id);
        List<PlanItem> planItems = planService.getPlan(id);
        return ResponseEntity.ok(planItems);
    }

    @GetMapping("/planlist")
    public ResponseEntity<?> getPlanListAll(@ModelAttribute SearchCondition condition) {
        System.out.println(condition);
        log.info("get Plan List ALL");
        List<PlanDto> items = planService.getPlanALL(condition);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detailPlan(@PathVariable Long id) {

        log.info("Detail plan: {}", id);
        PlanDto plan = planService.getDetailPlan(id);
        return ResponseEntity.ok(plan);
    }

    @GetMapping("/views/{userId}")
    public ResponseEntity<?> viewPlanByUserId(@PathVariable String userId) {
        log.info("View plan: {}", userId);
        List<PlanDto> plans = planService.getPlans(userId);
        return ResponseEntity.ok(plans);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePlan(@PathVariable Long id) {
        log.info("Delete plan: {}", id);
        planService.deletePlan(id);
        return ResponseEntity.ok("Plan deleted");
    }

    @PostMapping("/aiplan")
    public ResponseEntity<?> getAiPlan(@RequestBody List<Integer> contentIds) {
        List<PlanItem> planItems = planService.getAttractionsByIds(contentIds);
        return ResponseEntity.ok(planItems);
    }

    @PostMapping("/islike")
    public ResponseEntity<?> isLike(@RequestBody PlanDto planDto) {
        int result = planService.isLike(planDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getlikes/{id}")
    public ResponseEntity<?> getLikes(@PathVariable Long id) {
        log.info("Get likes plan: {}", id);
        int likes = planService.getLikes(id);
        return ResponseEntity.ok(likes);
    }

    @PostMapping("/like")
    public void like(@RequestBody PlanDto planDto) {
        log.info("Like plan: {}", planDto);
        planService.like(planDto);
    }

    @PostMapping("/unlike")
    public void unlike(@RequestBody PlanDto planDto) {
        log.info("Unlike plan: {}", planDto);
        planService.unlike(planDto);
    }

    @GetMapping("/getlikeplans/{id}")
    public ResponseEntity<?> getLikePlans(@PathVariable String id) {
        log.info("Get likes plan: {}", id);
        List<PlanDto> planDtos = planService.getLikePlans(id);
        return ResponseEntity.ok(planDtos);
    }

    @GetMapping("/countheart/{id}")
    public ResponseEntity<?> getCountheartPlans(@PathVariable String id) {
        log.info("Get countheart plan: {}", id);
        int myHeart = planService.countheart(id);
        return ResponseEntity.ok(myHeart);
    }

    @GetMapping("/countlist")
    public ResponseEntity<?> getCountList() {
        log.info("count List");
        return ResponseEntity.ok(planService.getCountList());
    }

    @GetMapping("/share/{id}")
    public ResponseEntity<?> share(@PathVariable Long id) {
        return ResponseEntity.ok(planService.share(id));
    }

    @GetMapping("/unshare/{id}")
    public ResponseEntity<?> unshare(@PathVariable Long id) {
        return ResponseEntity.ok(planService.unshare(id));
    }

}
