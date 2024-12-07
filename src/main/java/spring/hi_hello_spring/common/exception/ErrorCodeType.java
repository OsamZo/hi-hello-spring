package spring.hi_hello_spring.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCodeType {

    // 스프링 시큐리티 관련 오류
    SECURITY_ACCESS_ERROR(HttpStatus.BAD_REQUEST, "SECURITY_ERROR_001", "접근 권한이 없습니다."),
    SECURITY_TOKEN_ERROR(HttpStatus.BAD_REQUEST, "SECURITY_ERROR_002", "로그인 후 다시 시도해 주세요."),
    SECURITY_LOGIN_ERROR(HttpStatus.BAD_REQUEST, "SECURITY_ERROR_003", "로그인 실패하였습니다. 관리자에게 문의해 주세요."),

    // user 관련 오류
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_ERROR_001", "사용자를 찾을 수 없습니다."),
    USER_LOGIN_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_ERROR_003", "아이디 또는 비밀번호가 일치하지 않습니다."),
    USER_PWD_INCORRECT(HttpStatus.UNAUTHORIZED, "USER_ERROR_004", "비밀번호가 일치하지 않습니다."),
    NEW_PWD_MISMATCH(HttpStatus.BAD_REQUEST, "USER_ERROR_005", "새 비밀번호 확인 시 일치하지 않습니다."),
    PWD_SAME_AS_OLD(HttpStatus.BAD_REQUEST, "USER_ERROR_006", "기존 비밀번호와 새 비밀번호가 동일합니다."),

    // 공통 오류
    COMMON_ERROR(HttpStatus.BAD_REQUEST, "COMMON_ERROR", "오류가 발생하였습니다. 관리자에게 문의 바랍니다."),
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON_ERROR_002", "데이터가 존재하지 않습니다."),
    INVALID_VALUE(HttpStatus.BAD_REQUEST, "COMMON_ERROR_003", "유효하지 않은 값입니다."),
    DUPLICATE_DATA(HttpStatus.CONFLICT, "COMMON_ERROR_004", "중복된 값입니다.")
    ;
  
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
