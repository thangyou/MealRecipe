package doubleni.mealrecipe.model.vo;

import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ReadJsonFile {

    public static void main(String[] args)throws IOException, ParseException {

        JSONParser parser = new JSONParser();

        // JSON 파일 읽기
        Reader reader = new FileReader("C:\\Users\\dbwjd\\tmpFile\\output.json");
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        String rcp_seq = (String) jsonObject.get("rcp_seq");
        String rcp_nm = (String) jsonObject.get("rcp_nm");
        String rcp_way2 = (String) jsonObject.get("rcp_way2");
        System.out.println("rcp_seq : " + rcp_seq);
        System.out.println("rcp_nm : " + rcp_nm);
        System.out.println("rcp_way2 : " + rcp_way2);
    }
}

