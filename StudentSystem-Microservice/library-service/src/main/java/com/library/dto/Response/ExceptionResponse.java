package com.library.dto.Response;

import com.library.exception.ErrorCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExceptionResponse {
      private int code;
      private String massage;

      public ExceptionResponse(int code) {
            this.code = code;
            this.massage = ErrorCode.getErrorMessage(code);
      }
}
