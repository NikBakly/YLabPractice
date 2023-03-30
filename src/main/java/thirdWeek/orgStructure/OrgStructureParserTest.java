package thirdWeek.orgStructure;

import java.io.File;
import java.io.IOException;

public class OrgStructureParserTest {
    public static void main(String[] args) throws IOException {
        File file = new File("src/thirdWeek/orgStructure/resource/info.csv");
        OrgStructureParser parser = new OrgStructureParserImpl();
        Employee employeeBoss = parser.parseStructure(file); // boss: Иван Иванович
        System.out.println();
    }
}
