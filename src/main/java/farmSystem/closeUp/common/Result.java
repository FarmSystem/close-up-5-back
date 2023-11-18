package farmSystem.closeUp.common;

import lombok.Getter;

@Getter
public enum Result {



    // Common
    OK(200, "성공"),
    FAIL(-1,"실패"),
    INVALID_ACCESS(400, "유효하지 않은 접근"),
    INVALID_INPUT_VALUE(400, " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405,  " Invalid Input Value"),
    ENTITY_NOT_FOUND(400,  " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "Server Error"),
    INVALID_TYPE_VALUE(400, " Invalid Type Value"),
    ACCESS_TOKEN_EXPIRED(400,"access 토큰 만료"),
    UNAUTHORIZED(401, "권한이 없습니다."),
    FORBIDDEN_REQUEST(403, "잘못된 권한 요청입니다."),
    INVALID_TOKEN(400, "유효하지 않은 토큰입니다."),

    // 유저
    HANDLE_ACCESS_DENIED(403, "로그인이 필요합니다."),
    INVALID_INPUT_USERNAME(400, "닉네임을 3자 이상 입력하세요"),
    NOTEQUAL_INPUT_PASSWORD(400,  "비밀번호가 일치하지 않습니다"),
    INVALID_PASSWORD(400,  "비밀번호를 4자 이상 입력하세요"),
    INVALID_USERNAME(400,  "알파벳 대소문자와 숫자로만 입력하세요"),
    NOT_AUTHORIZED(403, "작성자만 수정 및 삭제를 할 수 있습니다."),
    USERNAME_DUPLICATION(400, "이미 등록된 닉네임입니다."),
    LOGIN_INPUT_INVALID(400, "로그인 정보를 다시 확인해주세요."),
    NOTFOUND_USER(404,  "유저를 찾을 수 없습니다."),
    UNAUTHORIZED_TOKEN(401, "유효한 토큰이 없습니다."),


    // 게시글
    NOTFOUND_POST(404, "해당 게시글이 존재하지 않습니다."),
    CONVERTING_FAILED(400, "파일 변환에 실패했습니다."),

    // 래플
    NOTFOUND_RAFFLE(404, "해당 래플이 존재하지 않습니다."),
    RAFFLE_END(404, "래플 응모가 마감됐습니다."),
    NOT_ENOUGH_POINT(404, "응모할 포인트가 부족합니다"),

    // 포인트
    LESS_THAN_MINIMUM_POINT(400, "5000원 이상부터 충전이 가능합니다."),
    NOT_EQUAL_AMOUNT(400, "결제 금액이 일치하지 않습니다.");

    private final String message;
    private final int status;

    Result(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
    public int getStatus() {
        return status;
    }
}