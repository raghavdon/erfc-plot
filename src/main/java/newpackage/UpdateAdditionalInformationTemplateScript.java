
package newpackage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UpdateAdditionalInformationTemplateScript {

    public static Connection conn;
    public static String url = "jdbc:postgresql://54.173.205.140:5432/thitedev";
    public static String username = "postgres";
    public static String password = "thite";
    public static String FilePath = "/home/raghav/Desktop/additional information.csv";

    public static void main(String[] args) throws Exception {
        UpdateAdditionalInformationTemplateScript obj = new UpdateAdditionalInformationTemplateScript();
        CreateConnection();
        List<String> banks = getBankNamesFromFile();
      InsertNewBankTemplate(banks);

        InsertNewTagsIntoTemplateTag(getTagsFromFile());
       List<AdditionalTempalteAttributes> attributes = getAdditionalTempalteAttributesFromFile();
       InsertNewAdditionalTempalte(attributes);
        CreateAdditionInformationBankTemplateLink(attributes);

    }

    public static List<Integer> getBankTemplateIds(String bankName) throws Exception {
        List<Integer> ids = new ArrayList<>();
        PreparedStatement st = conn.prepareStatement("SELECT id FROM bank_template WHERE bank_name = ?");
        st.setString(1, bankName.toUpperCase());
        ResultSet rs = st.executeQuery();
        int id = 0;
        while (rs.next()) {
            id = rs.getInt("id");
            ids.add(id);
        }
        rs.close();
        st.close();

        return ids;
    }

    public static int getTemplateTagId(String tagName) throws Exception {
        int id = 0;
        PreparedStatement st = conn.prepareStatement("SELECT id FROM template_tag WHERE tag_name = ?");
        st.setString(1, tagName);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            id = rs.getInt("id");

        }
        rs.close();
        st.close();

        return id;
    }

    public static int getAdditionalInformationId(int tagId) throws Exception {

        int addtionalTemplateId = 0;
        if (tagId != 0) {
            PreparedStatement st2 = conn.prepareStatement("SELECT id FROM addition_information_template WHERE template_tag_id = ?");
            st2.setInt(1, tagId);
            ResultSet rs2 = st2.executeQuery();

            while (rs2.next()) {
                addtionalTemplateId = rs2.getInt("id");

            }
            rs2.close();
            st2.close();
        }

        return addtionalTemplateId;
    }

    public static void CreateAdditionInformationBankTemplateLink(List<AdditionalTempalteAttributes> attrs) throws Exception {

        for (AdditionalTempalteAttributes attr : attrs) {
//                    System.out.println(bank);
            int additionalTemplateId = getAdditionalInformationId(attr.tag_id);

            if (additionalTemplateId != 0) {
                if (attr.banks != null && attr.banks.size() > 0) {
                    for (String bank : attr.banks) {
                        List<Integer> bankTemplateIds = getBankTemplateIds(bank);
                        if (bankTemplateIds != null && bankTemplateIds.size() > 0) {
                            for (int id : bankTemplateIds) {
                                if (id != 0) {

                                    PreparedStatement st = conn.prepareStatement("SELECT * FROM addition_information_bank_template_link WHERE addition_information_template_id = ? AND bank_template_id = ?");
                                    st.setInt(1, additionalTemplateId);
                                    st.setInt(2, id);
                                    ResultSet rs = st.executeQuery();
                                    int bankaddtionTemplateLinkId = 0;
                                    while (rs.next()) {
                                        bankaddtionTemplateLinkId = rs.getInt("id");
                                    }
                                    rs.close();
                                    st.close();
                                    if (bankaddtionTemplateLinkId == 0) {
                                        Statement stm = conn.createStatement();
                                        String query2 = "INSERT INTO addition_information_bank_template_link (addition_information_template_id,bank_template_id,created_at,updated_at,is_deleted) VALUES ( " + additionalTemplateId + ", " + id + ", '2016-12-29 16:10:32.833+05:30', '2016-12-29 16:10:32.833+05:30',false);";

                                        int count = stm.executeUpdate(query2);
                                        System.out.println(count);
                                        stm.close();
                                    }
                                }
                            }
                        }
                    }
                }
            }else{
                System.out.println(attr.toString());
            }

        }

    }

    public static void InsertNewAdditionalTempalte(List<AdditionalTempalteAttributes> attrs) throws Exception {

        for (AdditionalTempalteAttributes attr : attrs) {
//                    System.out.println(bank);

            PreparedStatement st = conn.prepareStatement("SELECT * FROM addition_information_template WHERE template_tag_id 	 = ?");
            st.setInt(1, attr.tag_id);
            ResultSet rs = st.executeQuery();
            int id = 0;
            while (rs.next()) {
                id = rs.getInt("id");
            }
            rs.close();
            st.close();

            if (id == 0) {
                System.out.println(attr.fieldName + ":" + attr.fieldType + ":" + attr.tag_id);
                Statement stm = conn.createStatement();
                String query2 = "INSERT INTO addition_information_template (field_name,field_type,template_tag_id,created_at,updated_at,is_deleted) VALUES ( '" + attr.fieldName + "', '" + attr.fieldType + "', " + attr.tag_id + ", '2016-12-29 16:10:32.833+05:30', '2016-12-29 16:10:32.833+05:30',false);";

                int count = stm.executeUpdate(query2);
               

            }

        }

    }

    public static void InsertNewBankTemplate(List<String> banks) throws Exception {

        for (String bank : banks) {
//                    System.out.println(bank);
            if (bank != null && bank.length() > 2) {

                PreparedStatement st = conn.prepareStatement("SELECT * FROM bank_template WHERE bank_name = ?");
                st.setString(1, bank.toUpperCase());
                
                ResultSet rs = st.executeQuery();
               
                int id = 0;
                while (rs.next()) {
                    id = rs.getInt("id");
                    //System.out.println(rs.getString("name"));
                }
                rs.close();
                st.close();

                if (id == 0) {
                    Statement stm = conn.createStatement();
                    String query2 = "INSERT INTO bank_template (name,bank_name,pages,created_at,updated_at,is_deleted) VALUES ( '" + bank + "-Flat', '" + bank.toUpperCase() + "', 0, '2016-12-29 16:10:32.833+05:30', '2016-12-29 16:10:32.833+05:30',false);";
                    int count = stm.executeUpdate(query2);

                    String query3 = "INSERT INTO bank_template (name,bank_name,pages,created_at,updated_at,is_deleted) VALUES ( '" + bank + "-Land', '" + bank.toUpperCase() + "', 0, '2016-12-29 16:10:32.833+05:30', '2016-12-29 16:10:32.833+05:30',false);";

                    int count2 = stm.executeUpdate(query3);

                }

            }
        }

    }

    public static void InsertNewTagsIntoTemplateTag(List<String> tags) throws Exception {

        for (String tag : tags) {
            if (tag != null && tag.length() > 2) {

                PreparedStatement st = conn.prepareStatement("SELECT * FROM template_tag WHERE tag_name = ?");
                st.setString(1, tag);
                ResultSet rs = st.executeQuery();
                int id = 0;
                while (rs.next()) {
                    id = rs.getInt("id");
                    //System.out.println(rs.getString("tag_name"));
                }
                rs.close();
                st.close();

                tag = tag.replaceAll("[^\\w_]+", "");
                tag = tag.replaceAll("_tag_", "_tag");

                if (id == 0) {

                    PreparedStatement st2 = conn.prepareStatement("SELECT * FROM template_tag WHERE tag_name = ?");
                    st2.setString(1, tag);
                    ResultSet rs2 = st2.executeQuery();

                    while (rs2.next()) {
                        id = rs2.getInt("id");
                        //System.out.println(rs.getString("tag_name"));
                    }
                    rs2.close();
                    st2.close();
                }
                if (id == 0) {
                    Statement stm = conn.createStatement();

                    System.out.println(tag);
                    String query2 = "INSERT INTO template_tag (tag_name,created_at,updated_at,is_deleted) VALUES ( '" + tag + "', '2016-12-29 16:10:32.833+05:30', '2016-12-29 16:10:32.833+05:30',false);";

                    int count = stm.executeUpdate(query2);

                }

            }
        }

    }

    public static List<String> getBankNamesFromFile() throws Exception {

        List<String> bankList = new ArrayList<>();

        FileReader in = new FileReader(FilePath);
        BufferedReader br = new BufferedReader(in);
int i=0;
Str
        while (line= br.readLine() != null || i<5) {
            
            if(br.readLine()==null){
                i++;
            }
            try{
            System.out.println("line-"+br.readLine());
            String line=br.readLine().replaceAll("/", ",");
            
            String[] content = line.split("\t");

            if (content[0] != null) {
                String[] banks = content[0].replaceAll(" ", "").split(",");

                for (String bank : banks) {
//                    System.out.println(bank);
                    if (bank != null && bank.length() > 2) {
                        String bankName=(bank.charAt(0)+"").toUpperCase()+bank.substring(1);
                        bankList.add(bankName);

                    }
                }

            }

        }catch (Exception e){
            
        }
        }
        in.close();
        return bankList;
    }

    public static List<String> getTagsFromFile() throws Exception {

        List<String> tags = new ArrayList<>();

        FileReader in = new FileReader(FilePath);
        BufferedReader br = new BufferedReader(in);

        int i=0;
        while (br.readLine() != null || i<5) {
            
            if(br.readLine()==null){
                i++;
            }
            try{
          
            String[] content = br.readLine().split("\t");

            // insert Bank Template
            if (content.length >= 3) {
                if (content[2].contains("_tag_")) {
                    String tag = content[2];

                    tags.add(content[2]);

                }else{
                System.out.println(br.readLine());
            }

            }else{
                System.out.println(br.readLine());
            }
        }catch (Exception e){
                
        }
        }
        in.close();
        return tags;
    }

    public static List<AdditionalTempalteAttributes> getAdditionalTempalteAttributesFromFile() throws Exception {

        List<AdditionalTempalteAttributes> attributes = new ArrayList<>();

        FileReader in = new FileReader(FilePath);
        BufferedReader br = new BufferedReader(in);

        int i=0;
        while (br.readLine() != null || i<5) {
            
            if(br.readLine()==null){
                i++;
            }
            //System.out.println("U--"+ br.readLine());
            try{
            String line=br.readLine().replaceAll("/", ",");
            
            String[] content = line.split("\t");

            // insert Bank Template
            if (content.length >= 4) {
                AdditionalTempalteAttributes attr = new AdditionalTempalteAttributes();
                attr.banks = new ArrayList<>();

                String[] banks = content[0].replaceAll(" ", "").split(",");

                for (String bank : banks) {
//                    System.out.println(bank);
                    if (bank != null && bank.length() > 2) {

                        attr.banks.add(bank);

                    }
                }
                attr.fieldName = content[1];
                attr.fieldType = content[3];

                int tag_id = getTemplateTagId(content[2]);
                if (tag_id == 0) {
                    String tag = content[2].replaceAll("[^\\w_]+", "");
                    tag = tag.replaceAll("_tag_", "_tag");
                    tag_id = getTemplateTagId(tag);
                }
                attr.tag_id = tag_id;

                if (attr.tag_id != 0 && attr.fieldName != null && !"".equals(attr.fieldName) && !"".equals(attr.fieldType) && attr.fieldType != null) {
                    attributes.add(attr);
                }
            }else{
                System.out.println(br.readLine());
            }
            }catch (Exception e){
                System.err.println(br.readLine());
            }
        }
        in.close();
        return attributes;
    }

    public static boolean CreateConnection() {
        try {

            Class.forName("org.postgresql.Driver");
            System.out.println("class.forname");

            conn = DriverManager.getConnection(url, username, password);
            System.out.println("conn created");

            Statement stmt = conn.createStatement();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
