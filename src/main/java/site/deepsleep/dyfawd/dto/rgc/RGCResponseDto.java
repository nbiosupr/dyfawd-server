package site.deepsleep.dyfawd.dto.rgc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RGCResponseDto {
    private List<Results> results;
    private Status status;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Results {
        private Region region;
        private Code code;
        private String name;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Region {
        private Area area4;
        private Area area3;
        private Area area2;
        private Area area1;
        private Area area0;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Area {
        private Coords coords;
        private String name;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Coords {
        private Center center;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Center {
        private int y;
        private int x;
        private String crs;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Code {
        private String mappingid;
        private String type;
        private String id;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Status {
        private String message;
        private String name;
        private int code;
    }
}