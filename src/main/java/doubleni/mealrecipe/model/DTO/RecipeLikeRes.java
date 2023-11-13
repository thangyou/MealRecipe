package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.BoardLike;
import doubleni.mealrecipe.model.RecipeLike;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecipeLikeRes {
    private Long likeId;
    private Long id;
    private String nickname;
    private Long rcp_id;
    private String rcp_nm;
    private String attFileNoMain; // 이미지 경로(소)
    private String attFileNoMk; // 이미지 경로(대)
    private Long checkLike;

    public RecipeLikeRes(RecipeLike recipeLike) {
        this.likeId = recipeLike.getLikeId();
        this.id = recipeLike.getUser().getId();
        this.nickname = recipeLike.getUser().getNickname();
        this.rcp_id = recipeLike.getRecipe().getRcpId();
        this.rcp_nm = recipeLike.getRecipe().getRcpNm();
        this.attFileNoMain = recipeLike.getRecipe().getAttFileNoMain();
        this.attFileNoMk = recipeLike.getRecipe().getAttFileNoMk();
        this.checkLike = recipeLike.getCheckLike();
    }
}
