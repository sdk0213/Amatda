/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.turtle.amatda.presentation.utilities

/**
 * Constants used throughout the app.
 */
const val DATABASE_NAME = "amatda-db"
const val CHECKLIST_DATA_FILENAME = "checkList.json"

// 알림 채널 생성 ID / NAME
const val notificationChannelIdOfDefault = "notificationChannelIdOfDefault"
const val notificationChannelNameOfDefault = "여행 정보 알림"
const val notificationChannelIdOfGeofence = "notificationChannelIdOfGeofence"
const val notificationChannelNameOfGeofence = "위치 정보 관련 알림"

// 알림 채널 노티 알림 ID
const val notificationIdOfDefault = 1000
const val notificationIdOfGeofence = 1001

// 포어그라운드 서비스 관련
const val thisServiceIsForeGroundService = "thisServiceIsForeGroundService"

// 공공 데이터 포털 Open API 에러 코드
// https://www.data.go.kr/iim/api/selectAPIAcountView.do (기상청41_단기예보 조회서비스_오픈API활용가이드_최종.docx)
const val NORMAL_SERVICE = 0 // 정상
const val APPLICATION_ERROR = 1// 어플리케이션 에러
const val DB_ERROR = 2    // 데이터베이스 에러
const val NODATA_ERROR = 3 // 데이터없음 에러
const val HTTP_ERROR = 4    // HTTP 에러
const val SERVICETIME_OUT = 5    // 서비스 연결실패 에러
const val INVALID_REQUEST_PARAMETER_ERROR = 10    // 잘못된 요청 파라메터 에러
const val NO_MANDATORY_REQUEST_PARAMETERS_ERROR = 11 // 필수요청 파라메터가 없음
const val NO_OPENAPI_SERVICE_ERROR = 12    // 해당 오픈API서비스가 없거나 폐기됨
const val SERVICE_ACCESS_DENIED_ERROR = 20    // 서비스 접근거부
const val TEMPORARILY_DISABLE_THE_SERVICEKEY_ERROR = 21    // 일시적으로 사용할 수 없는 서비스 키
const val LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS_ERROR = 22    // 서비스 요청제한횟수 초과에러
const val SERVICE_KEY_IS_NOT_REGISTERED_ERROR = 30    // 등록되지 않은 서비스키
const val DEADLINE_HAS_EXPIRED_ERROR = 31    // 기한만료된 서비스키
const val UNREGISTERED_IP_ERROR = 32  // 등록되지 않은 IP
const val UNSIGNED_CALL_ERROR = 33    // 서명되지 않은 호출
const val UNKNOWN_ERROR = 99   // 기타에러

// 지오펜싱
const val GEOFENCE_RADIUS_IN_METERS = 200f // 200M
const val GEOFENCE_LIOTERE_IN_MILLISECONDS = 60000 // 60초
private const val GEOFENCE_EXPIRATION_IN_DAYS: Long = 90
const val GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_DAYS * 24 * 60 * 60 * 1000 // 90일동안 유지시킴
