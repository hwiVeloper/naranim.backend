# 스케쥴러 정보

> 현재는 2019년 04월 03일까지의 투표정보만을 저장하지만, 2020년부터는 다가오는 투표/선거일 정보만 저장할 예정.

- MemberSchedule
  - 각종 의원 정보에 관한 API 호출 후 DB에 저장한다.
  - 매일 00:05:00 => getMemberCurrStateList (국회의원 현황 조회)
  - 매일 00:10:00 => getMemberDetailInfoList (국회의원 현황 상세 조회)
- CodeSchedule
  - 각종 코드성 정보에 관한 API 호출 후 DB에 저장한다.
  - 매일 00:30:00 => getPolySearch (정당 검색)
  - 매일 00:01:00 => getLocalSearch (지역 검색)
  - 매일 00:35:00 => getCommonSgCodeList (선거코드)
  - 매일 00:40:00 => getCommonGusigunCodeList (구시군코드)
  - 매일 00:50:00 => getCommonSggCodeList (선거구코드)
  - 매일 01:00:00 => getCommonPartyCodeList (정당코드)
  - 매일 01:05:00 => getCommonJobCodeList (직업코드)
  - 매일 01:10:00 => getCommonEduBckgrdCodeList (학력코드)
- PollPlaceSchedule
  - 투표소 정보 조회 후 DB에 저장한다.
  - 매일 01:20:00 => getPrePolplcOtlnmapTrnsportInfoInqire (사전투표소 정보 조회)
  - 매일 01:50:00 => getPolplcOtlnmapTrnsportInfoInqire (선거일 투표소 정보 조회)
- CandidateSchedule
  - 후보자 정보 조회 후 DB에 저장한다.
  - 매일 0?:??:?? => getPoelpcddRegistSttusInfoInqire (예비후보자 정보 조회 / 미개발)
  - 매일 02:00:00 => getPofelcddRegistSttusInfoInqire (후보자 정보 조회)
- VoteSchedule
  - 투표와 개표 정보 조회 후 DB에 저장한다.
  - 매일 02:30:00 => getVoteSttusInfoInqire (투표 결과 조회)
  - 매일 02:45:00 => getXmntckSttusInfoInqire (개표 결과 조회)
- WinnerSchedule
  - 당선인 정보를 조회하여 DB에 저장한다.
  - 매일 03:00:00 => getWinnerInfoInqire (당선인 조회)
