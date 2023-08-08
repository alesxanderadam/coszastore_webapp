package alticshaw.com.coszastore.payload.response;

import alticshaw.com.coszastore.entity.TagEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagResponse {
    private int id;
    private String name;

    public TagResponse mapTagEntityToTagResponse(TagEntity tagEntity) {
        return new TagResponse(tagEntity.getId(), tagEntity.getName());
    }
}
