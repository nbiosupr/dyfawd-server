package site.deepsleep.dyfawd.web.dto.rgc;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class RGCRequestDto {
    private String request;
    private String coords;
    private String sourcecrs;
    private String targetcrs;
    private String orders;
    private String output;
    private String callback;

    @Builder
    public RGCRequestDto(
            String request,
            String coords,
            String sourcecrs,
            String targetcrs,
            String orders,
            String output,
            String callback) {
        this.request = request;
        this.coords = coords;
        this.sourcecrs = sourcecrs;
        this.targetcrs = targetcrs;
        this.orders = orders;
        this.output = output;
        this.callback = callback;
    }

    public RGCRequestDto(
            double longitude,
            double latitude) {
        this.coords = longitude + "," + latitude;
        this.output = "json";
    }

    //TODO: StringBuilder 와 StringBuffer 중 결정하기.
    public String toQueryString(){
        StringBuffer sb = new StringBuffer();
        sb.append("coords=").append(this.coords);

        if(this.request!=null){
            sb.append("&request=").append(this.request);
        }
        if(this.sourcecrs!=null){
            sb.append("&sourcecrs=").append(this.sourcecrs);
        }
        if(this.targetcrs!=null){
            sb.append("&targetcrs=").append(targetcrs);
        }
        if(this.orders!=null){
            sb.append("&orders=").append(orders);
        }
        if(this.callback!=null){
            sb.append("&callback=").append(callback);
        }
        if(this.orders!=null){
            sb.append("&orders=").append(orders);
        }
        if(this.output!=null){
            sb.append("&output=").append(output);
        }

        return sb.toString();
    }
}
