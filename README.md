# Amatda
* 해당 프로젝트는 다음 기술들을 공부하고 실습하기위한 개인 프로젝트입니다.
* 기술
  * 디자인, 아키텍쳐 및 디자인 패턴
    * mvvm 
    * data - domain - presentation 패키지 구분
    * material Design
  * 언어
    * kotlin
    * rxjava2 
    * ~~coroutine~~ (적용되지 않음)
  * 종속성 주입
    * dagger2
  * AAC
    * room
    * viewModel
    * view/data binding
    * livedata
    * navigation component
    * workmanager
  * 라이브러리
    * glide
    * okhttp
    * retrofit
    * gson
    * tedPermission
      * rxjava 권한
    * tikxml
      * xml 파싱
    * keyboardvisibilityevent 
      * 키보드 이벤트 감지
    * materialsearchview
      * db 검색
  * API
    * 공공데이터 포털 날씨 검색
    * 공공데이터 포털 지역 코드 검색
    * 공공데이터 포털 주변 관광지 검색
    * Google Map
    * Google Place
    * Google Geofence
    * FireBase 인증 (적용 필요)
    * 카카오톡 이메일 로그인 (적용 필요)
  * 테스트 
    * ~~mockito~~ (적용되지 않음)
  * FireBase
    * FCM
    * 서버
  * 구글 앱 등록

---
아마따(Amatda) 여행 비서 어플리케이션
---
### 기능
---
##### 홈
* 현재 주변의 3일간의 날씨를 안내합니다.
* 현재 주변의 맛집과 관광지를 소개합니다.
##### 나의 가방
* 여행 도중 자신의 가방속 물건을 관리합니다. 자신의 가방을 생성하고 안에 자신의 물건들을 관리합니다. 물건의 위치를 직관적으로 가방 UI 에 생성하고 이를 쉽게 검색할수있습니다.
##### 나의 여행
* 여행지역을 추가하며 해당 지역에 필요한 메모를 설정할수있습니다. 이런 메모는 여행지에 도착하였을때 알림을 통해서 여행자에게 중요한 메모를 상기시켜줍니다.
* 예를들어서 내가 방문할 관광지에 도착하였을때 후에 방문할 식당에 전화를 걸어 당일 예약을 반드시 해야할경우 이를 메모하였다가 후에 관광지에 도착하였을때 해당 메모를 알림을 통해 여행자에게 알려줍니다.
##### 마이 페이지
* (개발진행중)
---
### 개발 현황
---
<details>
<summary>21/07/29 (펼치기/접기)</summary>
<div markdown="1">
* 가방 추가
  * 아이템 추가
  * 아이템 수정 툴팁
    * 삭제
    * 사이즈
    * 개수
    * 색깔
  * 아이템 터치로 이동
* ![중간기획](https://user-images.githubusercontent.com/51182964/127456396-e77f8dc8-6718-4980-8f4f-ae06b984ebcc.png)
 
</div>
</details>

<details>
<summary>21/08/10 (펼치기/접기)</summary>
<div markdown="1">
* 32 등분 설명표시
* 탭 레이아웃으로 가방 위치에 따른 보기
* DrawLayout 추가
* App bar 추가
* 주머니와 물건 층개념 도입
* ![구현결과](https://user-images.githubusercontent.com/51182964/128870399-61a1a26f-bb72-40ed-afb3-b18d8d66858e.png)
* 검색기능 개발에 시간이 많이 소요될것으로 예상되어 디자인과 ui는 Material SearchView 라이브러리를 사용하기로 결정
* 검색 기능 예상 UI
  * <img src="https://user-images.githubusercontent.com/51182964/128870443-06fe76d7-ee38-4ffc-ab68-bcd70fe21b69.png" width="40%" height="40%"> 
</div>
</details>

---
##### 릴리즈

![amatda_graphics_image_1024_500](https://user-images.githubusercontent.com/51182964/144740598-85896050-efbf-46d8-8908-81e607b95ee0.png)

<img src="https://user-images.githubusercontent.com/51182964/144740534-5f8d4fcc-e707-4705-8704-944a8f2385c6.png" width="30%" height="30%"> <img src="https://user-images.githubusercontent.com/51182964/144740537-f4ece8cb-d83f-47fe-a92c-6f14b6532bb1.png" width="30%" height="30%"> <img src="https://user-images.githubusercontent.com/51182964/144740536-80782ca9-4164-48d8-be5f-cea1c0650ebb.png" width="30%" height="30%"> 

<img src="https://user-images.githubusercontent.com/51182964/144740528-c90a1d53-4b22-4276-a160-bd4ef06d792c.png" width="30%" height="30%"> <img src="https://user-images.githubusercontent.com/51182964/144740529-62357e1f-46cf-41cd-8def-e195e6a90daf.png" width="30%" height="30%"> <img src="https://user-images.githubusercontent.com/51182964/144740531-184d9f1e-fef2-4eef-b8f9-5479fc2c19ad.png" width="30%" height="30%"> 
