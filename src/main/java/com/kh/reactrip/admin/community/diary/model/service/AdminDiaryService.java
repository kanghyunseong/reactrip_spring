package com.kh.reactrip.admin.community.diary.model.service;

import com.kh.reactrip.admin.community.diary.model.dto.AdminDiaryDTO;
import com.kh.reactrip.admin.community.diary.model.dto.AdminDiaryDetailDTO;
import com.kh.reactrip.common.PageResponseDTO;

public interface AdminDiaryService {

	PageResponseDTO<AdminDiaryDTO> findAllDiary(int page);

	AdminDiaryDetailDTO findByDiaryNo(Long diaryNo);

	AdminDiaryDTO updateDiaryStatus(Long diaryNo, AdminDiaryDTO dto);


}
