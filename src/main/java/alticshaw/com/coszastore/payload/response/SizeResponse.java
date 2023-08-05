package alticshaw.com.coszastore.payload.response;

import alticshaw.com.coszastore.entity.SizeEntity;
import alticshaw.com.coszastore.entity.TagEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizeResponse {
    private int id;
    private String name;

    public SizeResponse mapTagEntityToSizeResponse(SizeEntity sizeEntity) {
        return new SizeResponse(sizeEntity.getId(), sizeEntity.getName());
    }
}
