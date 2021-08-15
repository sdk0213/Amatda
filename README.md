# Amatda
여행 비서 어플리케이션
---
여행 도중 신경쓸 거리는 한두가지가 아닙니다.
그러다보면 우리는 "아 맞다...! 그럴걸.." "아 그거 까먹었네" "아.. 그 물건 안 챙겼네.." 라는 말을 자주합니다.

신경쓸 거리가 많지만 특히나 신경써야 하는것은 가방속 물건관리와

숙소나 여행지를 도착하고 떠날때 생각해야할 (보조)배터리 충전, 빨래, 물병관리, 날씨에 따른 옷차림, 썬크림과 같이 체크리스트입니다.

아마따는 위 두가지를 관리해줍니다.

1. 짐 정리
    - 자신의 짐을 가방속 위치에 맞게 작성하면 직관적으로 짐 상태를 파악할수있습니다.

2. 알림
    - 사용자가 정한 숙소나 여행지를 사용자의 위치를 기반으로 하여 도착하거나 떠날시에 이를 알림으로 체크 항목들을 상기시켜줍니다.


---
### 중간 기획 (21_07_29)
* 생각보다 가방 관리 기능 추가에 소요되는 시간이 장기화되고 디자인이 되어있지 않아서 기획이라도 하고 방향을 잡아야 할것같다. 그래서 그림판이지만 일다 가방관련 디자인을 기획함
* 가장 복잡한 아이템 수정과 툴팁이 완료되었으니 나머지는 생각보다 일찍끝날것으로 보임
* 구현된 기능
  * 가방 추가
      * 아이테 추가
      * 아이테 수정 툴팁
        * 삭제
        * 사이즈
        * 개수
        * 색깔
      * 아이템 터치로 이동
* 구현 필요
  * 32 등분 설명표시
  * 탭 레이아웃으로 가방 위치에 따른 보기
  * DrawLayout 추가
  * App bar 추가
 
* ![중간기획](https://user-images.githubusercontent.com/51182964/127456396-e77f8dc8-6718-4980-8f4f-ae06b984ebcc.png)
#### 디자인 기획을 하고나니까 기능과 방향성이 더욱 구체적으로 눈 앞에 보여 아주 좋다. 기획이 얼만큼 중요한것인지 몸소 깨닫는다.
---
### 개발_현황 (21_08_10)
* 주머니와 물건 층개념 도입에 생각보다 시간이 오래걸렸다.
* 2버 검색이동은 시간관계상 추후에 개발 우선은 정보 쿼리만 가능하도록만 개발
* ![구현결과](https://user-images.githubusercontent.com/51182964/128870399-61a1a26f-bb72-40ed-afb3-b18d8d66858e.png)

* 검색기능 개발에 시간이 많이 소요될것으로 예상되어 디자인과 ui는 Material SearchView 라이브러리를 사용하기로 결정
##### 검색 기능 예상 UI
* <img src="https://user-images.githubusercontent.com/51182964/128870443-06fe76d7-ee38-4ffc-ab68-bcd70fe21b69.png" width="40%" height="40%">

