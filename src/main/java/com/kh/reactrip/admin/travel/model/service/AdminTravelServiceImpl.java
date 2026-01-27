package com.kh.reactrip.admin.travel.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

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
@Primary
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
		if (file != null && !file.isEmpty()) {
			adminTravelDTO.setTravelImage(fileService.store(file));
		}
		
		adminTravelMapper.insertTravel(new AdminTravelVO(adminTravelDTO));
	}

	@Override
	@Transactional
	public void updateTravel(Long travelNo, MultipartFile file, AdminTravelDTO adminTravelDTO) {
		AdminTravelDTO origin = adminTravelMapper.selectTravelDetail(travelNo);
		adminTravelDTO.setTravelNo(travelNo);
		
		if(file != null && !file.isEmpty()) {
			adminTravelDTO.setTravelImage(fileService.updateFile(file, origin.getTravelImage()));
		} else {
			if(origin.getTravelImage() != null && !origin.getTravelImage().isEmpty()) {
				fileService.delete(origin.getTravelImage());
			}
			adminTravelDTO.setTravelImage("");
		}
		
		adminTravelMapper.updateTravel(adminTravelDTO);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AdminTravelDTO> getOrSyncNearbyTravels(double mapX, double mapY) {
		double range = 0.03;
		Map<String, Double> params = new HashMap<>();
		params.put("minX", mapX - range);
		params.put("maxX", mapX + range);
		params.put("minY", mapY - range);
		params.put("maxY", mapY + range);

		List<AdminTravelVO> list = adminTravelMapper.findNearbyTravels(params);
		log.info("[사용자 조회] {}, {} 주변 {}건 발견", mapX, mapY, list.size());

		return list.stream().map(AdminTravelDTO::new).toList();
	}

	@Override
	@Transactional
	public void fetchAndSaveApiData() {
		log.info("[Admin] 전국 주요 거점 데이터 일괄 동기화 프로세스 시작");

		double[][] locations = { { 126.9816, 37.5684 }, { 129.0393, 35.1154 }, { 128.6014, 35.8714 },
				{ 126.7052, 37.4563 }, { 126.8514, 35.1595 }, { 127.3845, 36.3504 }, { 129.3114, 35.5384 },
				{ 127.2892, 36.4801 }, { 127.0096, 37.2636 }, { 127.7302, 37.8813 }, { 128.8761, 37.7519 },
				{ 127.1472, 36.8151 }, { 127.4892, 36.6372 }, { 127.1480, 35.8242 }, { 126.4630, 34.8118 },
				{ 128.6011, 36.5760 }, { 128.6922, 35.2280 }, { 126.5312, 33.4996 } };

		for (double[] loc : locations) {
			log.info("거점 수집 중: [{}, {}]", loc[0], loc[1]);
			fetchApiAndSave(loc[0], loc[1], 100, "A", 100000);
		}

		log.info("전국 주요 거점 데이터 동기화 완료!");
	}

	private void fetchApiAndSave(double x, double y, int rows, String arrange, int radius) {
		String baseUrl = tourBaseUrl.endsWith("/") ? tourBaseUrl.substring(0, tourBaseUrl.length() - 1) : tourBaseUrl;

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/locationBasedList2")
				.queryParam("serviceKey", tourServiceKey).queryParam("numOfRows", rows).queryParam("pageNo", 1)
				.queryParam("MobileOS", "ETC").queryParam("MobileApp", "Reactrip").queryParam("_type", "json")
				.queryParam("arrange", arrange).queryParam("mapX", x).queryParam("mapY", y).queryParam("radius", radius)
				.queryParam("contentTypeId", "12"); // 필요시 39(맛집) 등 추가 수집 로직 필요

		try {
			RestTemplate rt = new RestTemplate();
			DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
			factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
			rt.setUriTemplateHandler(factory);

			Map<String, Object> response = rt.getForObject(builder.build().toUri(), Map.class);
			Map<String, Object> body = (Map<String, Object>) ((Map<String, Object>) response.get("response"))
					.get("body");
			if (body == null || body.get("items") == null || "".equals(body.get("items")))
				return;

			List<Map<String, Object>> itemList = extractItemList(body.get("items"));
			if (itemList == null)
				return;

			int savedCount = 0;
			int updatedCount = 0;
			for (Map<String, Object> item : itemList) {
				String title = String.valueOf(item.get("title"));
				String overview = fetchOverview(String.valueOf(item.get("contentid")));
				String apiImage = item.get("firstimage") != null ? String.valueOf(item.get("firstimage")) : "";
				
				// 기존 여행지 확인
				AdminTravelVO existing = adminTravelMapper.findByTitle(title);
				
				if (existing != null) {
					// 이미지가 없거나 비어있거나, 소프트 삭제된 경우 업데이트
					boolean needsUpdate = (existing.getTravelImage() == null || existing.getTravelImage().isEmpty() || existing.getTravelImage().trim().equals(""))
						|| "Y".equals(existing.getTravelStatus());
					
					if (needsUpdate) {
						AdminTravelDTO updateDto = new AdminTravelDTO();
						updateDto.setTravelNo(existing.getTravelNo());
						updateDto.setTravelName(title);
						updateDto.setTravelAddress(String.valueOf(item.get("addr1")));
						updateDto.setMapX(parseSafeDouble(item.get("mapx")));
						updateDto.setMapY(parseSafeDouble(item.get("mapy")));
						updateDto.setTravelImage(apiImage);
						updateDto.setTravelContent(overview);
						updateDto.setTravelStatus("N"); // 활성 상태로 복구
						updateDto.setRegionNo(getRegionNoFromApiCode(String.valueOf(item.get("areacode"))));
						
						adminTravelMapper.updateTravel(updateDto);
						updatedCount++;
						continue;
					} else {
						// 이미 존재하고 이미지도 있고 활성 상태면 건너뛰기
						continue;
					}
				}
				
				// 신규 여행지 추가
				AdminTravelVO vo = AdminTravelVO.builder().travelName(title)
						.travelAddress(String.valueOf(item.get("addr1"))).mapX(parseSafeDouble(item.get("mapx")))
						.mapY(parseSafeDouble(item.get("mapy")))
						.travelImage(apiImage)
						.travelContent(overview).travelStatus("N").count(0)
						.regionNo(getRegionNoFromApiCode(String.valueOf(item.get("areacode")))).build();

				adminTravelMapper.insertTravel(vo);

				// 2. 테마 정보 추출 및 매핑 테이블 저장
				String cat1 = String.valueOf(item.get("cat1"));
				String cat2 = String.valueOf(item.get("cat2"));
				String cat3 = String.valueOf(item.get("cat3"));

				Long themeNo = themeMappingString(cat1, cat2, cat3);

				if (themeNo != null) {
					Map<String, Object> themeMap = new HashMap<>();
					themeMap.put("travelNo", vo.getTravelNo());
					themeMap.put("themeNo", themeNo);
					adminTravelMapper.insertTravelTheme(themeMap);
				}

				savedCount++;
			}
			log.info("해당 거점 신규 데이터 {}건 저장, {}건 업데이트 완료", savedCount, updatedCount);
			log.info("해당 거점 신규 데이터 {}건 저장 완료", savedCount);
			Thread.sleep(300);
		} catch (Exception e) {
			log.error("동기화 중 오류: {}", e.getMessage());
		}
	}

	private Long themeMappingString (String cat1, String cat2, String cat3) {
		// 37. 액티비티 (A03: 레포츠)
		if ("A03".equals(cat1))
			return 37L;

		// 34. 식도락/맛집 (A05: 음식)
		if ("A05".equals(cat1))
			return 34L;

		// 32. 바다/해변 (A0101: 대분류 자연 - 중분류 해수욕장/섬 등)
		if ("A0101".equals(cat2))
			return 32L;

		// 36. 전통 문화 (A0201: 역사/문화유적)
		if ("A0201".equals(cat2))
			return 36L;

		// 38. 전시/공연 (A0206: 문화시설)
		if ("A0206".equals(cat2))
			return 38L;

		// 31. 자연/풍경 (A01: 전체 자연 중 바다 제외)
		if ("A01".equals(cat1))
			return 31L;

		// 40. 기타
		return 40L;
	}

	private String fetchOverview(String contentId) {
		try {
			String url = "https://apis.data.go.kr/B551011/KorService2/detailCommon2?serviceKey=" + tourServiceKey
					+ "&MobileOS=ETC&MobileApp=Reactrip&_type=json&contentId=" + contentId;

			RestTemplate rt = new RestTemplate();
			Map<String, Object> map = rt.getForObject(url, Map.class);
			Map<String, Object> response = (Map<String, Object>) map.get("response");
			Map<String, Object> body = (Map<String, Object>) response.get("body");
			List<Map<String, Object>> items = extractItemList(body.get("items"));

			if (items != null && !items.isEmpty()) {
				String overview = String.valueOf(items.get(0).get("overview"));
				return overview.replaceAll("<[^>]*>", "").strip();
			}
		} catch (Exception e) {
			log.warn("ID {} 상세 수집 실패", contentId);
		}
		return "상세 정보가 준비 중인 여행지입니다.";
	}

	private List<Map<String, Object>> extractItemList(Object itemsObj) {
		if (itemsObj instanceof Map) {
			Object itemObj = ((Map<String, Object>) itemsObj).get("item");
			if (itemObj instanceof List)
				return (List<Map<String, Object>>) itemObj;
			if (itemObj instanceof Map)
				return List.of((Map<String, Object>) itemObj);
		}
		return null;
	}

	private double parseSafeDouble(Object value) {
		try {
			return Double.parseDouble(String.valueOf(value));
		} catch (Exception e) {
			return 0.0;
		}
	}

	private Long getRegionNoFromApiCode(String areaCode) {
		if (areaCode == null || "null".equals(areaCode))
			return 54L;
		String code = areaCode.contains(".") ? areaCode.split("\\.")[0] : areaCode;
		return switch (code) {
		case "1" -> 54L; // 서울
		case "2" -> 57L; // 인천
		case "3" -> 59L; // 대전
		case "4" -> 56L; // 대구
		case "5" -> 58L; // 광주
		case "6" -> 55L; // 부산
		case "7" -> 60L; // 울산
		case "8" -> 61L; // 세종
		case "31" -> 62L; // 경기
		case "32" -> 63L; // 강원
		case "33" -> 64L; // 충북
		case "34" -> 65L; // 충남
		case "35" -> 68L; // 경북
		case "36" -> 69L; // 경남
		case "37" -> 66L; // 전북
		case "38" -> 67L; // 전남
		case "39" -> 70L; // 제주
		default -> 54L;
		};
	}

	@Override
	public PageResponseDTO<AdminTravelDTO> findBySearch(String keyword, int page) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return new PageResponseDTO<>(new PageInfo(), new ArrayList<>());
		}
		int totalCount = adminTravelMapper.getSearchCount(keyword);
		PageInfo pi = pagenation.getPageInfo(totalCount, page);
		List<AdminTravelDTO> list = adminTravelMapper.findBySearch(keyword, pagenation.createRowBounds(pi));
		return new PageResponseDTO<>(pi, list);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findAllRegions() {
		return adminTravelMapper.findAllRegions();
	}
}