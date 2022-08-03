package africa.semicolon.goodreads.controllers.requestsAndResponse;

import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponse implements Serializable {
    private String status;
    private String message;
    private int result;
    private Object data;
}
