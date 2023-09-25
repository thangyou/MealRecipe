package doubleni.mealrecipe.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class RecipeViewResponse {

    private Long id;

    private String rcp_seq;
    private String rcp_nm;
    private String rcp_way2;
    private String rcp_pat2;
    /*private String info_wgt;
    private String info_eng;
    private String info_car;
    private String info_pro;
    private String info_fat;
    private String info_na;
    private String hash_tag;
    private String att_file_no_main;
    private String att_file_no_mk;
    private String rcp_parts_dtls;*/
    private String manual01;
    private String manual_img01;

    public RecipeViewResponse(Recipe recipe) {
        this.id = recipe.getId();
        this.rcp_seq = recipe.getRcp_seq();
        this.rcp_nm = recipe.getRcp_nm();
        this.rcp_way2 = recipe.getRcp_way2();
        this.rcp_pat2 = recipe.getRcp_pat2();
//        this.info_wgt = recipe.getInfo_wgt();
//        this.info_eng = recipe.getInfo_eng();
//        this.info_car = recipe.getInfo_car();
//        this.info_pro = recipe.getInfo_pro();
//        this.info_fat = recipe.getInfo_fat();
//        this.info_na = recipe.getInfo_na();
//        this.hash_tag = recipe.getHash_tag();
//        this.att_file_no_main = recipe.getAtt_file_no_main();
//        this.att_file_no_mk = recipe.getAtt_file_no_mk();
//        this.rcp_parts_dtls = recipe.getRcp_parts_dtls();
        this.manual01 = recipe.getManual01();
        this.manual_img01 = recipe.getManual_img01();

    }
}
