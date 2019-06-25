# API List

| Controller          | URL                  | Method | Description               |
| :------------------ | :------------------- | :----: | :------------------------ |
| CandidateController | -                    |   -    | 후보자                    |
|                     | /candidates/         |  GET   | 후보자 정보 리스트 조회   |
|                     | /candidates/{huboId} |  GET   | 후보자 정보 상세 조회     |
| ElectionController  | -                    |        |                           |
|                     | /elections/          |  GET   | 선거 정보 리스트 조회     |
|                     | /elections/{sgId}    |  GET   | 선거 정보 상세 조회       |
| MemberController    | -                    |   -    | 국회의원                  |
|                     | /members/            |  GET   | 국회의원 정보 리스트 조회 |
|                     | /members/{deptCd}    |  GET   | 국회의원 정보 상세 조회   |
| PolPlaceController  | -                    |   -    | 투표소                    |
|                     | /polplaces/          |  GET   | 투표소 정보 리스트 조회   |
|                     | /polplaces/{sgId}    |  GET   | 투표소 정보 상세 조회     |
| SungeoguController  | -                    |   -    | 선거구                    |
|                     | /sungeogus/          |  GET   | 선거구 정보 리스트 조회   |
|                     | /sungeogus/{sgId}    |  GET   | 선거구 정보 상세 조회     |
| VoteController      | -                    |   -    | 투표                      |
|                     | /votes/              |  GET   | 투표 정보 리스트 조회     |
|                     | /votes/{sgId}        |  GET   | 투표 정보 상세 조회       |
| WinnerController    | -                    |   -    | 당선인                    |
|                     | /winners/            |  GET   | 당선인 정보 리스트 조회   |
|                     | /winners/{sgId}      |  GET   | 당선인 정보 상세 조회     |