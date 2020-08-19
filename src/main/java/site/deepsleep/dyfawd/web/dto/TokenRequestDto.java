package site.deepsleep.dyfawd.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TokenRequestDto {
    private String id;
    private String pw;

    @Builder
    public TokenRequestDto(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }
}
