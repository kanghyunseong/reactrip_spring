package com.kh.reactrip.admin.community.diary.model.service;

import com.kh.reactrip.admin.community.diary.model.dto.AdminDiaryDTO;
import com.kh.reactrip.common.PageResponseDTO;

public interface AdminDiaryService {

	PageResponseDTO<AdminDiaryDTO> findAllDiary(int page);

	AdminDiaryDTO findByDiaryNo(Long diaryNo);

	AdminDiaryDTO deleteStatus(Long diaryNo, AdminDiaryDTO dto);

	PageResponseDTO<AdminDiaryDTO> findByDiarySearch(String keyword, int page);


}
