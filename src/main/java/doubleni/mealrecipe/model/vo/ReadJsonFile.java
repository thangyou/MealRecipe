//package doubleni.mealrecipe.model.vo;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.json.simple.JSONObject;
//
//import java.io.*;
//
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//
//@NoArgsConstructor
//@Getter
//public class ReadJsonFile {
//
//    public static void main(String[] args)throws IOException, ParseException {
//
//        JSONParser parser = new JSONParser();
//
//        // JSON 파일 읽기
//        Reader reader = new FileReader("C:\\json_data\\output.json");
//        JSONObject jsonObject = (JSONObject) parser.parse(reader);
//        for (int i = 0; i < jsonObject.size(); i++) {
//            JSONObject element = (JSONObject) jsonObject.get(i);
//            JSONObject rows = (JSONObject) element.get("rows");
//
//        }
//
//        // 값 확인
//        String rcp_seq = (String) jsonObject.get("rcp_seq");
//        String rcp_nm = (String) jsonObject.get("rcp_nm");
//        String rcp_way2 = (String) jsonObject.get("rcp_way2");
//        System.out.println("rcp_seq : " + rcp_seq);
//        System.out.println("rcp_nm : " + rcp_nm);
//        System.out.println("rcp_way2 : " + rcp_way2);
//    }
//}
