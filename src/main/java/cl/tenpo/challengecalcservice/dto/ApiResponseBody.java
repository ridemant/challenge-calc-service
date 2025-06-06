package cl.tenpo.challengecalcservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResponseBody<T> {
    private boolean success;
    private int code;
    private LocalDateTime timestamp;
    private T data;

    public ApiResponseBody(boolean success, int code, T data) {
        this.success = success;
        this.code = code;
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }

    public ApiResponseBody(T data) {
        this.success = true;
        this.code = 200;
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }

    public static <T> ApiResponseBody<T> success(T data) {
        return new ApiResponseBody<>(data);
    }
}