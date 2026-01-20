package com.kh.reactrip.admin.travel.model.service;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.reactrip.admin.travel.model.dto.AdminTravelDTO;
import com.kh.reactrip.admin.travel.model.mapper.AdminTravelMapper;
import com.kh.reactrip.admin.travel.model.vo.AdminTravelVO;
import com.kh.reactrip.common.PageResponseDTO;
import com.kh.reactrip.file.service.FileService;
import com.kh.reactrip.util.PageInfo;
import com.kh.reactrip.util.Pagenation;
import com.kh.reactrip.util.Validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminTravelServiceImpl implements AdminTravelService {

    private final AdminTravelMapper adminTravelMapper;
    private final Pagenation pagenation;
    private final FileService fileService;

    @Value("${api.tour.base-url}")
    private String tourBaseUrl;

    @Value("${api.tour.service-key}")
    private String tourServiceKey;

    @Override
    public PageResponseDTO<AdminTravelDTO> findAllTravel(int page) {
        int totalCount = adminTravelMapper.getTotalCount();
        PageInfo pi = pagenation.getPageInfo(totalCount, page);
        Validator.validatePage(page, pi.getMaxPage());
        List<AdminTravelVO> voList = adminTravelMapper.findAllTravel(pagenation.createRowBounds(pi));
        return new PageResponseDTO<>(pi, voList.stream().map(AdminTravelDTO::new).toList());
    }

    @Override
    @Transactional
    public AdminTravelDTO updateTravelStatus(Long travelNo, String status) {
        Validator.validateNo(travelNo, "잘못된 여행지 번호입니다.");
        AdminTravelVO vo = AdminTravelVO.builder().travelNo(travelNo).travelStatus(status).build();
        if (adminTravelMapper.updateTravelStatus(vo) > 0) {
            return new AdminTravelDTO(adminTravelMapper.findByTravelNo(travelNo));
        }
        return null;
    }

    @Override
    @Transactional
    public void insertTravel(AdminTravelDTO adminTravelDTO, MultipartFile file) {
        if (file != null && !file.isEmpty()) adminTravelDTO.setTravelImage(fileService.store(file));
        adminTravelMapper.insertTravel(new AdminTravelVO(adminTravelDTO));
    }

    @Override
    @Transactional
    public void updateTravel(Long travelNo, MultipartFile file, AdminTravelDTO adminTravelDTO) {
        AdminTravelDTO origin = adminTravelMapper.selectTravelDetail(travelNo);
        adminTravelDTO.setTravelNo(travelNo);
        adminTravelDTO.setTravelImage(fileService.updateFile(file, origin.getTravelImage()));
        adminTravelMapper.updateTravel(adminTravelDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminTravelDTO> getOrSyncNearbyTravels(double mapX, double mapY) {
        double range = 0.03;
        Map<String, Double> params = new HashMap<>();
        params.put("minX", mapX - range); params.put("maxX", mapX + range);
        params.put("minY", mapY - range); params.put("maxY", mapY + range);

        List<AdminTravelVO> list = adminTravelMapper.findNearbyTravels(params);
        log.info("[사용자 조회] {}, {} 주변 {}건 발견", mapX, mapY, list.size());
        
        return list.stream().map(AdminTravelDTO::new).toList();
    }

    @Override
    @Transactional
    public void fetchAndSaveApiData() {
        log.info("📢 [Admin] 전국 주요 거점 데이터 일괄 동기화 프로세스 시작");

        double[][] locations = {
            {126.9816, 37.5684}, // 서울
            {129.0393, 35.1154}, // 부산
            {128.6014, 35.8714}, // 대구
            {126.7052, 37.4563}, // 인천
            {126.8514, 35.1595}, // 광주
            {127.3845, 36.3504}, // 대전
            {129.3114, 35.5384}, // 울산
            {127.2892, 36.4801}, // 세종
            {127.0096, 37.2636}, // 경기(수원)
            {127.7302, 37.8813}, // 강원(춘천)
            {128.8761, 37.7519}, // 강원(강릉)
            {127.1472, 36.8151}, // 충남(천안)
            {127.4892, 36.6372}, // 충북(청주)
            {127.1480, 35.8242}, // 전북(전주)
            {126.4630, 34.8118}, // 전남(목포)
            {128.6011, 36.5760}, // 경북(안동)
            {128.6922, 35.2280}, // 경남(창원)
            {126.5312, 33.4996}  // 제주
        };

        for (double[] loc : locations) {
            log.info("거점 수집 중: [{}, {}]", loc[0], loc[1]);
            fetchApiAndSave(loc[0], loc[1], 100, "A", 100000);
        }
        
        log.info("전국 주요 거점 데이터 동기화 완료!");
    }

    private void fetchApiAndSave(double x, double y, int rows, String arrange, int radius) {
        String baseUrl = tourBaseUrl.endsWith("/") ? tourBaseUrl.substring(0, tourBaseUrl.length() - 1) : tourBaseUrl;
        
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/locationBasedList2")
                .queryParam("serviceKey", tourServiceKey).queryParam("numOfRows", rows)
                .queryParam("pageNo", 1).queryParam("MobileOS", "ETC").queryParam("MobileApp", "Reactrip")
                .queryParam("_type", "json").queryParam("arrange", arrange)
                .queryParam("mapX", x).queryParam("mapY", y).queryParam("radius", radius).queryParam("contentTypeId", "12");

        try {
            RestTemplate rt = new RestTemplate();
            DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
            factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
            rt.setUriTemplateHandler(factory);

            Map<String, Object> response = rt.getForObject(builder.build().toUri(), Map.class);
            Map<String, Object> body = (Map<String, Object>) ((Map<String, Object>) response.get("response")).get("body");
            if (body == null || body.get("items") == null || "".equals(body.get("items"))) return;

            List<Map<String, Object>> itemList = extractItemList(body.get("items"));
            if (itemList == null) return;

            int savedCount = 0;
            for (Map<String, Object> item : itemList) {
                String title = String.valueOf(item.get("title"));
                if (adminTravelMapper.existsByTitle(title) > 0) continue; 

                String overview = fetchOverview(String.valueOf(item.get("contentid")));

                AdminTravelVO vo = AdminTravelVO.builder()
                        .travelName(title).travelAddress(String.valueOf(item.get("addr1")))
                        .mapX(parseSafeDouble(item.get("mapx"))).mapY(parseSafeDouble(item.get("mapy")))
                        .travelImage(item.get("firstimage") != null ? String.valueOf(item.get("firstimage")) : "")
                        .travelContent(overview).travelStatus("N").count(0)
                        .regionNo(getRegionNoFromApiCode(String.valueOf(item.get("areacode")))).build();

                adminTravelMapper.insertTravel(vo);
                savedCount++;
            }
            log.info("해당 거점 신규 데이터 {}건 저장 완료", savedCount);
        } catch (Exception e) { log.error("동기화 중 오류: {}", e.getMessage()); }
    }

    private String fetchOverview(String contentId) {
        try {
            String url = "https://apis.data.go.kr/B551011/KorService2/detailCommon2?serviceKey=" + tourServiceKey + 
                         "&MobileOS=ETC&MobileApp=Reactrip&_type=json&contentId=" + contentId;
            
            RestTemplate rt = new RestTemplate();
            Map<String, Object> map = rt.getForObject(url, Map.class);
            Map<String, Object> response = (Map<String, Object>) map.get("response");
            Map<String, Object> body = (Map<String, Object>) response.get("body");
            List<Map<String, Object>> items = extractItemList(body.get("items"));
            
            if (items != null && !items.isEmpty()) {
                String overview = String.valueOf(items.get(0).get("overview"));
                return overview.replaceAll("<[^>]*>", "").strip(); // 태그 제거
            }
        } catch (Exception e) { 
            log.warn("ID {} 상세 수집 실패", contentId); 
        }
        return "상세 정보가 준비 중인 여행지입니다.";
    }

    private List<Map<String, Object>> extractItemList(Object itemsObj) {
        if (itemsObj instanceof Map) {
            Object itemObj = ((Map<String, Object>) itemsObj).get("item");
            if (itemObj instanceof List) return (List<Map<String, Object>>) itemObj;
            if (itemObj instanceof Map) return List.of((Map<String, Object>) itemObj);
        }
        return null;
    }

    private double parseSafeDouble(Object value) {
        try { return Double.parseDouble(String.valueOf(value)); } catch (Exception e) { return 0.0; }
    }

    private Long getRegionNoFromApiCode(String areaCode) {
        if (areaCode == null || "null".equals(areaCode)) return 54L;
        String code = areaCode.contains(".") ? areaCode.split("\\.")[0] : areaCode;
        return switch (code) {
            case "1" -> 54L; case "2" -> 57L; case "3" -> 59L; case "4" -> 56L;
            case "5" -> 58L; case "6" -> 55L; case "7" -> 60L; case "8" -> 61L;
            case "31" -> 62L; case "32" -> 63L; case "33" -> 64L; case "34" -> 65L;
            case "35" -> 68L; case "36" -> 69L; case "37" -> 66L; case "38" -> 67L;
            case "39" -> 70L; default -> 54L;
        };
    }
}