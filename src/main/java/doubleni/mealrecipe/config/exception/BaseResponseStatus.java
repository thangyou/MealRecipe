package doubleni.mealrecipe.config.exception;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    //1000 : 요청 성공
    SUCCESS(true,1000,"요청에 성공"),

    //2000 : 요청 오류
    REQUEST_ERROR(false,2000,"입력값을 확인 필요"),

    //3000 : 응답 오류
    RESPONSE_ERROR(false,3000,"값을 불러오는데 실패"),
    REDIRECT_ERROR(false,3001,"리다이렉트 실패"),

    //4000 : 데이터베이스, 서버 오류
    DATABASE_ERROR(false,4000,"데이터베이스 연결에 실패"),
    SERVER_ERROR(false,4001,"서버 연결 실패");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    //열거 상수에 대한 생성자로, 응답 상태값을 초기화함
    private BaseResponseStatus(boolean isSuccess, int code, String message){
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
