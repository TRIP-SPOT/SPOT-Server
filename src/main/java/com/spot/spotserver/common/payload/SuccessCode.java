package com.spot.spotserver.common.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    // 200 OK

    // USER
    LOGIN_SUCCESS(OK, "로그인에 성공하였습니다"),
    REISSUE_TOKEN_SUCCESS(OK, "토큰 재발급에 성공하였습니다."),
    REGISTER_NICKNAME_SUCCESS(OK, "닉네임이 정상적으로 설정되었습니다."),
    UPDATE_NICKNAME_SUCCESS(OK, "닉네임이 정상적으로 변경되었습니다."),
    GET_NICKNAME_SUCCESS(OK, "닉네임이 정상적으로 조회되었습니다."),
    REGISTER_PROFILE_SUCCESS(OK, "프로필 이미지가 정상적으로 설정되었습니다."),
    UPDATE_PROFILE_SUCCESS(OK, "프로필 이미지가 정상적으로 변경되었습니다."),
    REGISTER_COLOR_SUCCESS(OK, "배경색이 정상적으로 설정되었습니다."),
    UPDATE_COLOR_SUCCESS(OK, "배경색이 정상적으로 변경되었습니다."),
    GET_PROFILE_SUCCESS(OK,"프로필이 정상적으로 조회되었습니다."),
    GET_LIKED_SPOT_SUCCESS(OK, "좋아요한 스팟이 정상적으로 조회되었습니다."),
    GET_BADGE_SUCCESS(OK, "뱃지가 정상적으로 조회되었습니다."),
    GET_LEVEL_SUCCESS(OK, "등급이 정상적으로 조회되었습니다."),

    // SPOT
    GET_SPOT_SUCCESS(OK, "촬영지 조회에 성공하였습니다."),
    GET_SPOT_DETAIL_SUCCESS(OK, "스팟 상세 정보가 정상적으로 조회되었습니다."),
    GET_SPOT_AROUND_SUCCESS(OK, "스팟 주변 장소 리스트가 정상적으로 조회되었습니다."),
    GET_AROUND_DETAIL_SUCCESS(OK, "주변 장소 상세 정보가 정상적으로 조회되었습니다."),
    GET_WITHIN_RADIUS_SPOT_LIST_SUCCESS(OK, "반경 20km 이내의 스팟 목록이 정상적으로 조회되었습니다."),
    LIKE_SPOT_SUCCESS(OK, "스팟에 대한 좋아요가 정상적으로 등록되었습니다."),
    UNLIKE_SPOT_SUCCESS(OK, "스팟에 대한 좋아요가 정상적으로 삭제되었습니다."),
    GET_TOP_LIKE_SPOT_SUCCESS(OK, "좋아요 상위 5개 촬영지가 정상적으로 조회되었습니다."),
    GET_SEARCH_SPOT_SUCCESS(OK, "작품명에 대한 촬영지 목록이 정상적으로 조회되었습니다."),

    // RECORD
    CREATE_RECORD_SUCCESS(OK, "여행 기록이 정상적으로 생성되었습니다."),
    GET_REGIONAL_RECORDS_SUCCESS(OK, "지역별 기록이 정상적으로 조회되었습니다."),
    GET_RECORD_SUCCESS(OK, "여행 기록이 정상적으로 조회되었습니다."),
    UPDATE_RECORD_SUCCESS(OK, "여행 기록이 정상적으로 수정되었습니다."),
    DELETE_RECORD_SUCCESS(OK, "여행 기록이 정상적으로 삭제되었습니다."),
    CREATE_REPRESENTATIVE_IMAGE_SUCCESS(OK, "대표 이미지가 정상적으로 생성되었습니다."),
    GET_REPRESENTATIVE_IMAGE_SUCCESS(OK, "대표 이미지가 정상적으로 조회되었습니다."),
    UPDATE_REPRESENTATIVE_IMAGE_SUCCESS(OK, "대표 이미지가 정상적으로 수정되었습니다."),

    // QUIZ
    GET_QUIZ_SUCCESS(OK, "퀴즈가 정상적으로 조회되었습니다."),
    GET_QUIZ_RESULT_SUCCESS(OK, "퀴즈 정답 확인 결과가 정상적으로 조회되었습니다."),

    // SCHEDULE
    CREATE_SCHEDULE_SUCCESS(OK, "일정이 정상적으로 생성되었습니다."),
    GET_SCHEDULE_LIST_SUCCESS(OK, "일정 목록이 정상적으로 조회되었습니다."),
    GET_SCHEDULE_SUCCESS(OK, "상세 일정이 정상적으로 조회되었습니다."),
    DELETE_SCHEDULE_SUCCESS(OK, "일정이 정상적으로 삭제되었습니다."),
    UPDATE_SCHEDULE_DURATION_SUCCESS(OK, "여행 기간이 정상적으로 수정되었습니다."),
    UPDATE_SCHEDULE_IMAGE_SUCCESS(OK, "일정 이미지가 정상적으로 수정되었습니다."),
    ADD_LOCATION_SUCCESS(OK, "장소가 정상적으로 추가되었습니다."),
    DELETE_LOCATIONS_SUCCESS(OK, "장소들이 정상적으로 삭제되었습니다."),
    GET_SELECTED_SPOT_SUCCESS(OK, "해당 일정의 담은 장소들이 정상적으로 조회되었습니다."),
    SELECT_SPOT_SUCCESS(OK, "장소들이 일정에 정상적으로 담겼습니다."),
    CREATE_BADGE_SUCCESS(OK, "뱃지가 정상적으로 생성되었습니다."),
    UPDATE_LOCATION_POSITION_SUCCESS(OK, "장소의 위치가 정상적으로 변경되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHTTPStatusCode() {
        return httpStatus.value();
    }
}
