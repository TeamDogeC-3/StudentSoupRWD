package ProjectDoge.StudentSoup.dto.board;

import lombok.Data;

@Data
public class BoardCategoryDto {

    private String categoryKey;
    private String categoryValue;

    public BoardCategoryDto(String categoryKey, String categoryValue){
        this.categoryKey = categoryKey;
        this.categoryValue = categoryValue;
    }
}
