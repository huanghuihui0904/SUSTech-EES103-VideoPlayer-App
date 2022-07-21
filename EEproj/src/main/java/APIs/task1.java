package APIs;

import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;
import java.net.URL;

public class task1 {
    private static URL propertyURL = task1.class
            .getResource("/loader.cnf");

    private static Connection con = null;
    private static PreparedStatement stmt = null;
    private static boolean verbose = false;

    static HashMap<String,Integer> centerLength=new HashMap<>();

    private static void openDB(String host, String dbname,
                               String user, String pwd) {
        try {

            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }
        String url = "jdbc:postgresql://" + host + "/" + dbname;
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        try {
            con = DriverManager.getConnection(url, props);
            if (verbose) {
                System.out.println("Successfully connected to the database "
                        + dbname + " as " + user);
            }
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void closeDB() {
        if (con != null) {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
        }
    }


    public static java.sql.Date strToDate(String strDate) {
        if (strDate.equals("")) {
            return null;
        } else {
            String str = strDate;
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            java.util.Date d = null;
            try {

                d = format.parse(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
            java.sql.Date date = new java.sql.Date(d.getTime());
            return date;
        }

    }
    static Scanner in=new Scanner(System.in);
    public static void start() throws SQLException {

        Properties defprop = new Properties();
        defprop.put("host", "localhost");
        defprop.put("user", "postgres");
        defprop.put("password", "wsy@13650597327");
        defprop.put("database", "Project2");
        Properties prop = new Properties(defprop);



        openDB(prop.getProperty("host"), prop.getProperty("database"),
                prop.getProperty("user"), prop.getProperty("password"));

        centerLength.put("center",2);
        centerLength.put("enterprise",6);
        centerLength.put("model",5);
        centerLength.put("staff",8);
        centerLength.put("contract",6);
        centerLength.put("orders",6);
        centerLength.put("inventory",6);
//        task1();

//////////////////////////////////////////////////////////////////////////////////



    }
    public static void end() throws SQLException {
        con.commit();
        stmt.close();
        closeDB();
    }

    public static void task1() throws SQLException {
        String operation=in.nextLine();

        centerLength.put("center",2);
        centerLength.put("enterprise",6);
        centerLength.put("model",5);
        centerLength.put("staff",8);
        centerLength.put("contract",6);
        centerLength.put("orders",6);
        centerLength.put("inventory",6);


        String table=in.nextLine().toLowerCase(Locale.ROOT);
        ArrayList<String>Columns =new ArrayList<>();

        ArrayList<String> Data =new ArrayList<>();
        ArrayList<String>content=new ArrayList<>();
        ArrayList<String>parts = new ArrayList<>();
        switch (operation.toLowerCase(Locale.ROOT)) {
            case "select": {
                String selectStatement = "select ";
//                String table=in.next().toLowerCase(Locale.ROOT);
//                ArrayList<String>Columns =new ArrayList<>();
//
//                ArrayList<String> Data =new ArrayList<>();
//                ArrayList<String>content=new ArrayList<>();

                String m = in.nextLine();
                Columns.addAll(List.of(m.split("\\|")));
//                content.addAll(List.of(m.split("\\|")));
                String n = in.nextLine();
                String[] strings = n.split("\\|");
                for (int i = 0; i < strings.length - 1; i = i + 2) {
                    if (strings[i].equals(" ")) strings[i] = "null";
                    Data.add(strings[i]);
                    content.add(strings[i + 1]);
                }


                ArrayList<String[]> fin = Select(table, content, Columns, Data, selectStatement);

                break;
            }
            case "update":
            {
                String updateStatement;//后面加

//                String table=in.next().toLowerCase(Locale.ROOT);
//                ArrayList<String>Columns =new ArrayList<>();
//                ArrayList<String> Data =new ArrayList<>();
//                ArrayList<String>content=new ArrayList<>();
//                ArrayList<String>parts = new ArrayList<>();
                updateStatement = "update " + table + " set ";
//                while (in.hasNext()){
//                    String m=in.next();
//                    if (m.equals("\\"))break;
//                    Columns.add(m);
//                    String n=in.next();
//                    if (n.equals(" "))n="null";
//                    content.add(n);
//                }
//                while (in.hasNext()){
//                    String m=in.next();
//                    if (m.equals("\\"))break;
//                    Data.add(m);
//                    String n=in.next();
//                    if (n.equals(" "))n="null";
//                    content.add(n);
//                    parts.add(n);
//                }
                String m = in.nextLine();
                String[]string=m.split("\\|");
                for (int i = 0; i < string.length - 1; i = i + 2) {
                    if (string[i].equals(" ")) string[i] = "null";
                    Columns.add(string[i]);
                    content.add(string[i + 1]);
                }

                String n = in.nextLine();
                String[] strings = n.split("\\|");
                for (int i = 0; i < strings.length - 1; i = i + 2) {
                    if (strings[i].equals(" ")) strings[i] = "null";
                    Data.add(strings[i]);
                    content.add(strings[i + 1]);
                    parts.add(strings[i+1]);
                }

                Update(updateStatement, table, Columns, Data, content, parts);
                break;
            }
            case "delete": {
                String deleteStatement = "delete from ";
//                String table=in.next().toLowerCase(Locale.ROOT);
//                ArrayList<String>Columns =new ArrayList<>();
//                ArrayList<String> Data =new ArrayList<>();
//                ArrayList<String>content=new ArrayList<>();
//                while (in.hasNext()) {
//                    String m = in.next();
//                    if (m.equals("\\")) break;
//                    Data.add(m);
//                    String n = in.next();
//                    if (n.equals(" ")) n = "null";
//                    content.add(n);
//
//                }
                String n = in.nextLine();
                String[] strings = n.split("\\|");
                for (int i = 0; i < strings.length - 1; i = i + 2) {
                    if (strings[i].equals(" ")) strings[i] = "null";
                    Data.add(strings[i]);
                    content.add(strings[i + 1]);
                }
                Delete(deleteStatement, table, Columns, Data, content);
                break;
            }
            case "count":
            {
                String Column = null;
                String m = in.nextLine();
                if (m.equals("\\|")) {
                    break;
                } else {
                    Column = m;
                }

                int count = Count(table, Column);


                break;
            }
            default: {
                String Statement = in.nextLine();
                execute(Statement);
                break;
            }
        }
    }


    public static Integer Count(String table, String Column){
        String statement;
        if (Column==null) {
            statement= "select count(*) from " + table;
        }else {
            statement = "select count("+Column+") from " + table;
        }
        int count=0;
        try {
            stmt = con.prepareStatement(statement);
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }

        try {
            ResultSet rs = null;

            rs=  stmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            System.out.println(count);

        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
            } catch (Exception e2) {
            }
        }
        return count;
    }



    public static void load_Update(String statement,String content){
        try {
            stmt = con.prepareStatement(statement);
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }

        try {
            if (con != null) {
                stmt.setString(1, content);
                stmt.executeUpdate();
                con.commit();
            }

        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
            } catch (Exception e2) {
            }
        }

    }
    public static void load_insert(String insertStatement){

        try {
            stmt = con.prepareStatement(insertStatement);
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }

        try {
            if (con != null) {
                stmt.executeUpdate();
            }

        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
            } catch (Exception e2) {
            }
        }
    }


    public static void execute(String insertStatement){

        try {
            stmt = con.prepareStatement(insertStatement);
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }

        try {
            if (con != null) {
                stmt.executeUpdate();
            }

        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
            } catch (Exception e2) {
            }
        }
    }



    public static boolean Update(String updateStatement,String table,ArrayList<String>Columns,ArrayList<String> Data,ArrayList<String>content,ArrayList<String>parts) throws SQLException {
        boolean isSuccess=true;

        switch (table){

            case "orders":{
                if (Columns.contains("quantity")) {
                    ArrayList<String>part1 = new ArrayList<>();
                    part1.addAll(parts);
                    content.removeAll(parts);
                    part1.addAll(content);
                    ArrayList<String[]>part = new ArrayList<>();
                    String[]strings=new String[part1.size()];
                    for (int i = 0; i < part1.size(); i++) {
                        strings[i]=part1.get(i);
                    }
                    part.add(strings);

                    task45.UpdateOrder(part);
                }
                break;
            }
            case"center": {
                if (Columns.contains("name")) {
                    ArrayList<String[]> centers = Select("center", parts, new ArrayList<>(), Data, "select ");
                    int index = Columns.indexOf("name");
                    for (int i = 0; i < centers.size(); i++) {
                        String cen = centers.get(i)[1];
                        int id = 0;
                        String insertCenterStatement = "insert into center values(" + id + ",'" + content.get(0) + "')";
                        load_insert(insertCenterStatement);


                        String updateEnterpriseStatement = "update enterprise set supply_center=? where supply_center='" + cen + "'";
                        load_Update(updateEnterpriseStatement, content.get(index));
                        String updateStaffStatement = "update staff set supply_center=? where supply_center='" + cen + "'";
                        load_Update(updateStaffStatement, content.get(index));
                        String updateContractStatement = "update contract set supply_center=? where supply_center='" + cen + "'";
                        load_Update(updateContractStatement, content.get(index));
                        String updateInventoryStatement = "update inventory set center=? where center='" + cen + "'";
                        load_Update(updateInventoryStatement, content.get(index));
                        String deleteStatement = "delete from center where id=" + centers.get(i)[0];
                        load_delete(deleteStatement);
                        String Statement = "update center set id =" + centers.get(i)[0] + " where id=" + id;
                        execute(Statement);
                    }
                }
                break;
            }
            case "enterprise": {
                if (Columns.contains("name")) {
                    int index = Columns.indexOf("name");
                    ArrayList<String[]> enterprise = Select("enterprise", parts, new ArrayList<>(), Data, "select ");
                    for (int i = 0; i < enterprise.size(); i++) {
                        String enter = enterprise.get(i)[1];
                        int id = 0;
                        String insertEnterpriseStatement = "insert into enterprise values(" + id;

                        for (int j = 2; j < enterprise.get(i).length; j++) {
                            if (j==index){
                                insertEnterpriseStatement=insertEnterpriseStatement+",'"+content.get(index)+"'";
                                continue;
                            }
                            insertEnterpriseStatement = insertEnterpriseStatement + ",'" + enterprise.get(i)[j] + "'";
                        }
                        insertEnterpriseStatement = insertEnterpriseStatement + ")";
                        load_insert(insertEnterpriseStatement);

                        String updateContractStatement = "update contract set enterprise=? where enterprise='" + enter + "'";
                        load_Update(updateContractStatement, content.get(index));
                        String deleteStatement = "delete from enterprise where id=" + enterprise.get(i)[0];

                        load_delete(deleteStatement);
                        String Statement = "update enterprise set id =" + enterprise.get(i)[0] + " where id=" + id;
                        execute(Statement);
                    }
                }
                break;
            }
            case "model": {
                if (Columns.contains("model")) {
                    int index = Columns.indexOf("model");
                    ArrayList<String[]> model = Select("model", parts, new ArrayList<>(), Data, "select ");
                    for (int i = 0; i < model.size(); i++) {
                        String mo = model.get(i)[2];
                        int id =0;
                        String insertModelStatement = "insert into model values(" + id;
                        for (int j = 1; j < model.get(i).length; j++) {
                            if (j==2){
                                insertModelStatement=insertModelStatement+",'"+content.get(index)+"'";
                                continue;
                            }
                            if (j==4){
                                insertModelStatement = insertModelStatement + "," + model.get(i)[j];
                                continue;
                            }
                            insertModelStatement = insertModelStatement + ",'" + model.get(i)[j] + "'";

                        }
                        insertModelStatement = insertModelStatement + ")";
                        load_insert(insertModelStatement);
                        String updateOrdersStatement = "update orders set product_model=? where product_model='" + mo + "'";
                        load_Update(updateOrdersStatement, content.get(index));
                        String updateInventoryStatement = "update inventory set product_model=? where product_model='" + mo + "'";
                        load_Update(updateInventoryStatement, content.get(index));
                        String deleteStatement = "delete from model where id=" + model.get(i)[0];

                        load_delete(deleteStatement);
                        String Statement = "update model set id =" + model.get(i)[0] + " where id=" + id;
                        execute(Statement);
                    }
                }
                break;
            }
            case "staff": {

                if (Columns.contains("number")) {
                    int index = Columns.indexOf("number");
                    ArrayList<String[]> staff = Select("staff", parts, new ArrayList<>(), Data, "select ");
                    for (int i = 0; i < staff.size(); i++) {
                        String st = staff.get(i)[4];
                        int id = 0;
                        String insertStaffStatement = "insert into staff values(" + id;
                        for (int j = 1; j < staff.get(i).length; j++) {
                            if (j == 4) {
                                insertStaffStatement = insertStaffStatement + ",'" + content.get(index) + "'";
                                continue;
                            }
                            if (j == 2) {
                                insertStaffStatement = insertStaffStatement + "," + staff.get(i)[j];
                                continue;
                            }
                            insertStaffStatement = insertStaffStatement + ",'" + staff.get(i)[j] + "'";
                        }
                        insertStaffStatement = insertStaffStatement + ")";
                        load_insert(insertStaffStatement);
                        String updateContractStatement = "update contract set contract_manager=? where contract_manager='" + st + "'";
                        load_Update(updateContractStatement, content.get(index));
                        String updateOrdersStatement = "update orders set salesman_num=? where salesman_num='" + st + "'";
                        load_Update(updateOrdersStatement, content.get(index));
//
//                        String deleteStatement = "delete from staff where id=" + staff.get(i)[0];
//
//                        load_delete(deleteStatement);
//                        String Statement = "update staff set id =" + staff.get(i)[0] + " where id=" + id;
//                        execute(Statement);
                    }
                }
                break;
            }
            default:break;

        }




        ArrayList<Integer> INT =new ArrayList<>();
        ArrayList<Integer> DATE =new ArrayList<>();
        ArrayList<Integer> ColumnIndex =new ArrayList<>();
        if (Columns.size()!=0){
            for (int i = 0; i < Columns.size() ; i++) {
                if (i== Columns.size()-1){
                    updateStatement = updateStatement + Trans(i,Columns.get(i),INT,DATE)+" ";
                }else {
                    updateStatement = updateStatement + Trans(i,Columns.get(i),INT,DATE) + ",";
                }
            }


        }
        updateStatement=updateStatement+"where ";
        if (Data.size()!=0){
            for (int i = Columns.size(); i < Columns.size()+Data.size() ; i++) {
                if (i== Columns.size()+Data.size()-1){
                    updateStatement = updateStatement + Trans(i, Data.get(i-Columns.size()),INT,DATE);
                }else {
                    updateStatement = updateStatement + Trans(i, Data.get(i-Columns.size()),INT,DATE) + " and ";
                }
            }
        }

        /////////////////////////////open DB
        try {
            stmt = con.prepareStatement(updateStatement);
        } catch (SQLException e) {
            isSuccess=false;
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }

        try {
            if (con != null) {
                int countINT=0;
                int countDATE=0;

                for (int i = 0; i < content.size(); i++) {
                    if (INT.size()>countINT){
                        if (INT.get(countINT)==i){
                            stmt.setInt(i+1,Integer.parseInt(content.get(i)));
                            countINT++;
                            continue;
                        }
                    }
                    if (DATE.size()>countDATE){
                        if (DATE.get(countDATE)==i){
                            stmt.setDate(i+1,strToDate(content.get(i)));
                            countDATE++;
                            continue;
                        }
                    }
                    stmt.setString(i + 1, content.get(i));
                }


                stmt.executeUpdate();
                con.commit();
            }




        } catch (SQLException se) {
            isSuccess=false;
            System.err.println("SQL error: " + se.getMessage());

            try {
                con.rollback();
            } catch (Exception e2) {
            }

        }
        return isSuccess;
    }

    public static void Delete(String deleteStatement,String table,ArrayList<String>Columns,ArrayList<String> Data,ArrayList<String>content) throws SQLException {

        switch (table){
            case"center":

                ArrayList<String[]> centers = Select("center", content, Columns, Data, "select ");

                for (int i = 0; i < centers.size(); i++) {
                    String cen = centers.get(i)[1];
                    ArrayList<String>dData=new ArrayList<>();
                    dData.add("supply_center");
                    ArrayList<String>cContent=new ArrayList<>();
                    cContent.add(cen);
                    ArrayList<String[]> contract = Select("contract", cContent, Columns, dData, "select ");
                    ArrayList<String[]> staff = Select("staff", cContent,Columns, dData, "select ");
                    if (contract!=null) {
                        for (int j = 0; j < contract.size(); j++) {
                            String deleteOrdersStatement = "delete from orders where contract_num='" + contract.get(j)[0] + "'";
                            load_delete(deleteOrdersStatement);
                        }
                    }
                    if (staff!=null) {
                        for (int j = 0; j < staff.size(); j++) {
                            String deleteOrdersStatement = "delete from orders where salesman_num='" + staff.get(j)[4] + "'";
                            load_delete(deleteOrdersStatement);
                        }
                    }

                    String deleteContractStatement="delete from contract where supply_center='" + cen+"'";
                    load_delete(deleteContractStatement);


                    String deleteInventoryStatement="delete from inventory where center='" + cen+"'";
                    load_delete(deleteInventoryStatement);
                    String deleteEnterpriseStatement = "delete from enterprise where supply_center='" + cen+"'";
                    load_delete(deleteEnterpriseStatement);
                    String deleteStaffStatement="delete from staff where supply_center='" + cen+"'";
                    load_delete(deleteStaffStatement);
                }
                break;
            case "enterprise":
                ArrayList<String[]>enterprise= Select("enterprise",content,Columns,Data,"select ");
                for (int i = 0; i < enterprise.size(); i++) {
                    String enter=enterprise.get(i)[1];
                    ArrayList<String>dData=new ArrayList<>();
                    dData.add("enterprise");
                    ArrayList<String>cContent=new ArrayList<>();
                    cContent.add(enter);
                    ArrayList<String[]> contract = Select("contract", cContent, Columns, dData, "select ");
                    if (contract!=null) {
                        for (int j = 0; j < contract.size(); j++) {
                            String deleteOrdersStatement = "delete from orders where contract_num='" + contract.get(j)[0] + "'";
                            load_delete(deleteOrdersStatement);
                        }
                    }

                    String deleteContractStatement="delete from contract where enterprise='"+enter+"'";
                    load_delete(deleteContractStatement);
                }
                break;
            case "model":
                ArrayList<String[]>model= Select("model",content,Columns,Data,"select ");
                for (int i = 0; i < model.size(); i++) {
                    String mo=model.get(i)[2];
//                    ArrayList<String>dData=new ArrayList<>();
//                    dData.add("product_model");
//                    ArrayList<String>cContent=new ArrayList<>();
//                    cContent.add(mo);
//                    ArrayList<String[]> contract = selectTable("contract", cContent, new ArrayList<>(), dData, "select ");
//                    if (contract!=null) {
//                        for (int j = 0; j < contract.size(); j++) {
//                            String deleteOrdersStatement = "delete from orders where contract_num='" + contract.get(j)[0] + "'";
//                            load_delete(deleteOrdersStatement);
//                        }
//                    }
                    String deleteOrdersStatement="delete from orders where product_model='"+mo+"'";
                    load_delete(deleteOrdersStatement);
                    String deleteInventoryStatement="delete from inventory where product_model='"+mo+"'";
                    load_delete(deleteInventoryStatement);
                }

                break;
            case "staff":
                ArrayList<String[]> staff= Select("staff",content,Columns,Data,"select ");
                for (int i = 0; i <staff.size() ; i++) {
                    String st=staff.get(i)[4];
                    String deleteOrdersStatement="delete from orders where salesman_num='"+st+"'";
                    load_delete(deleteOrdersStatement);

                    ArrayList<String>dData=new ArrayList<>();
                    dData.add("contract_manager");
                    ArrayList<String>cContent=new ArrayList<>();
                    cContent.add(st);
                    ArrayList<String[]> contract = Select("contract", cContent, Columns, dData, "select ");
                    if (contract!=null) {
                        for (int j = 0; j < contract.size(); j++) {
                            deleteOrdersStatement = "delete from orders where contract_num='" + contract.get(j)[0] + "'";
                            load_delete(deleteOrdersStatement);
                        }
                    }

                    String deleteContractStatement="delete from contract where contract_manager='"+st+"'";
                    load_delete(deleteContractStatement);

                }

                break;
            default:break;

        }

        ArrayList<Integer> INT =new ArrayList<>();
        ArrayList<Integer> DATE =new ArrayList<>();

        deleteStatement=deleteStatement+table+" where ";
        for (int i = 0; i < Data.size(); i++) {
            if (i == 0) {
                deleteStatement = deleteStatement + Trans(i, Data.get(i),INT,DATE);
            } else {
                deleteStatement = deleteStatement + " and " + Trans(i, Data.get(i),INT,DATE);
            }
        }
        try {
//            stmt = con.prepareStatement("select * from contract_info where contract_number=? and  product_model=? and salesman_number=?");
            stmt = con.prepareStatement(deleteStatement);
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
        try {
            if (con != null) {
                int countINT=0;
                int countDATE=0;

                for (int i = 0; i < content.size(); i++) {
                    if (INT.size()>countINT){
                        if (INT.get(countINT)==i){
                            stmt.setInt(i+1,Integer.parseInt(content.get(i)));
                            countINT++;
                            continue;
                        }
                    }
                    if (DATE.size()>countDATE){
                        if (DATE.get(countDATE)==i){
                            stmt.setDate(i+1,strToDate(content.get(i)));
                            countDATE++;
                            continue;
                        }
                    }
                    stmt.setString(i + 1, content.get(i));
                }


                stmt.executeUpdate();
                con.commit();
            }



        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
            } catch (Exception e2) {
            }
        }

    }
    public static ArrayList<String[]> SelectColums(String table, ArrayList<String[]> parts, ArrayList<String>Columns, ArrayList<String>Data,String selectStatement){
//        table table = null;
        ArrayList<Integer> INTData =new ArrayList<>();
        ArrayList<Integer> INTColumn =new ArrayList<>();
        ArrayList<Integer> DATEData =new ArrayList<>();
        ArrayList<Integer> DATEColumn =new ArrayList<>();
        ArrayList<String[]>p=new ArrayList<>();
        if (Columns.size()!=0){
            for (int i = 0; i < Columns.size() ; i++) {
                if (i== Columns.size()-1){
                    selectStatement = selectStatement + Trans(i, Columns.get(i),INTColumn,DATEColumn)+" ";
                }else {
                    selectStatement = selectStatement + Trans(i, Columns.get(i),INTColumn,DATEColumn) + ",";
                }
            }


        }else {
            selectStatement = selectStatement +"* ";
            switch (table){
                case "center":INTColumn.add(0);break;
                case "enterprise":INTColumn.add(0);break;
                case "model":INTColumn.add(0);INTColumn.add(4);break;
                case "staff":INTColumn.add(0);INTColumn.add(2);break;
                case "contract":DATEColumn.add(3);break;
                case "orders":INTColumn.add(2);DATEColumn.add(4);DATEColumn.add(5);break;
                case"inventory":INTColumn.add(2);INTColumn.add(3);INTColumn.add(4);INTColumn.add(5);break;
                case "temp":INTColumn.add(2);INTColumn.add(6);break;
            }
        }


        selectStatement = selectStatement +"from "+table+" where ";
        for (int i = 0; i < Data.size(); i++) {
            if (i == 0) {
                selectStatement = selectStatement + Trans(i, Data.get(i),INTData,DATEData);
            } else {
                selectStatement = selectStatement + " and " + Trans(i, Data.get(i),INTData,DATEData);
            }
        }

        /////////////////////////////open DB
        try {
            stmt = con.prepareStatement(selectStatement);
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }

        String[]strings=new String[parts.get(0).length ];

        for (int i = 0; i < parts.size();i++ ) {
            try {


                if (parts.get(i).length >= 1) {
                    for (int j = 0; j < parts.get(0).length; j++) {
                        if (parts.get(i)[j].equals("")){
                            parts.get(i)[j]="null";
                        }
                        strings[j]= parts.get(i)[j];
                    }
                    String[]strings1=load_select(strings,Columns,INTData,DATEData,table);
                    if (strings1!=null){
                        p.add( strings1);
                    }
                    con.commit();
                }
            } catch (SQLException se) {
                System.err.println("SQL error: " + se.getMessage());
                try {
                    con.rollback();
                } catch (Exception e2) {
                }
            }
        }
        return p;
    }
    private static String[] load_select(String[] strings,ArrayList<String>Columns,ArrayList<Integer>INT,ArrayList<Integer>DATE,String table)
            throws SQLException {

//        int index=0;
        String[] fin = new String[centerLength.get(table)];
        if (con != null) {
            int countINT=0;
            int countDATE=0;
            ResultSet rs = null;
            for (int i = 0; i <strings.length ; i++) {
                if (INT.size()>countINT){
                    if (INT.get(countINT)==i){
                        stmt.setInt(i+1,Integer.parseInt(strings[i]));
                        countINT++;
                        continue;
                    }
                }
                if (DATE.size()>countDATE){
                    if (DATE.get(countDATE)==i){
                        stmt.setDate(i+1,strToDate(strings[i]));
                        countDATE++;
                        continue;
                    }
                }
                stmt.setString(i+1,strings[i]);
            }
            countINT=0;
            countDATE=0;
            rs=  stmt.executeQuery();

            if(rs.next()){
                if (Columns.size()==0) {
                    fin=new String[centerLength.get(table)];
                    for (int i = 1; i <= centerLength.get(table); i++) {
                        if (INT.size() > countINT) {
                            if (INT.get(countINT)+1 == i) {
                                String s=String.valueOf(rs.getInt(i));
                                fin[i-1]=s;

                                System.out.print(s + " ");
                                countINT++;
                                continue;
                            }
                        }
                        if (DATE.size() > countDATE) {
                            if (DATE.get(countDATE)+1 == i) {
                                String s=String.valueOf(rs.getDate(i));
                                fin[i-1]=s;

                                System.out.print(s + " ");
                                countDATE++;
                                continue;
                            }
                        }
                        String s=rs.getString(i);
                        fin[i-1]=s;
                        System.out.print(s + " ");
                    }
                    System.out.println();
                }else {
                    fin=new String[Columns.size()];
                    for (int i = 1; i <= Columns.size(); i++) {
                        if (INT.size() > countINT) {
                            if (INT.get(countINT)+1 == i) {
                                String s=String.valueOf(rs.getInt(i));
                                fin[i-1]=s;

                                System.out.print(s + " ");
                                countINT++;
                                continue;
                            }
                        }
                        if (DATE.size() > countDATE) {
                            if (DATE.get(countDATE)+1 == i) {
                                String s=String.valueOf(rs.getDate(i));
                                fin[i-1]=s;

                                System.out.print(s + " ");
                                countDATE++;
                                continue;
                            }
                        }
                        String s=rs.getString(i);
                        fin[i-1]=s;
                        System.out.print(s + " ");
                    }
                    System.out.println();
                }
            }
        }
        boolean isNull=true;
        for (int i = 0; i < fin.length; i++) {
            if (fin[i]!=null){
                isNull=false;
                break;
            }
        }
        return isNull?null:fin;
    }



    public  static void UpdateColumns(String table, ArrayList<String[]> parts, ArrayList<String>Columns, ArrayList<String>Data,String updateStatement){
        ArrayList<Integer> INT =new ArrayList<>();
        ArrayList<Integer> DATE =new ArrayList<>();
        ArrayList<Integer> ColumnIndex =new ArrayList<>();
        if (Data.size()!=0){
            for (int i = 0; i < Data.size() ; i++) {
                if (i== Data.size()-1){
                    updateStatement = updateStatement + Trans(i,Data.get(i),INT,DATE)+" ";
                }else {
                    updateStatement = updateStatement + Trans(i,Data.get(i),INT,DATE) + ",";
                }
            }


        }
        updateStatement=updateStatement+"where ";
        if (Columns.size()!=0){
            for (int i = Data.size(); i < Columns.size()+Data.size() ; i++) {
                if (i== Columns.size()+Data.size()-1){
                    updateStatement = updateStatement + Trans(i, Columns.get(i-Data.size()),INT,DATE);
                }else {
                    updateStatement = updateStatement + Trans(i, Columns.get(i-Data.size()),INT,DATE) + " and ";
                }
            }
        }

        /////////////////////////////open DB
        try {
            stmt = con.prepareStatement(updateStatement);
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }

        String[]strings=new String[parts.get(0).length ];

        for (int i = 1; i < parts.size(); i++) {
            try {


                if (parts.get(i).length > 1) {
                    for (int j = 0; j < parts.get(0).length; j++) {
                        if (parts.get(i)[j].equals("")){
                            parts.get(i)[j]="null";
                        }
                        strings[j]= parts.get(i)[j];
                    }
                    load_update(strings,INT,DATE);
                    con.commit();
                }


            } catch (SQLException se) {
                System.err.println("SQL error: " + se.getMessage());
                try {
                    con.rollback();
                } catch (Exception e2) {
                }
            }
        }
//

//
//        ChooseTable(table,parts);
    }
    public static void load_update(String[] strings,ArrayList<Integer>INT,ArrayList<Integer>DATE) throws SQLException {
        if (con != null) {
            int countINT=0;
            int countDATE=0;
            for (int i = 0; i < strings.length; i++) {
                if (INT.size()>countINT){
                    if (INT.get(countINT)==i){
                        stmt.setInt(i+1,Integer.parseInt(strings[i]));
                        countINT++;
                        continue;
                    }
                }
                if (DATE.size()>countDATE){
                    if (DATE.get(countDATE)==i){
                        stmt.setDate(i+1,strToDate(strings[i]));
                        countDATE++;
                        continue;
                    }
                }
                stmt.setString(i + 1, strings[i]);
            }


            stmt.executeUpdate();
        }
    }

    public static ArrayList<String[]> Select(String table, List<String>parts , List<String>Columns, List<String>Data, String selectStatement){
//        table table = null;
        ArrayList<Integer> INT =new ArrayList<>();
        ArrayList<Integer> DATE =new ArrayList<>();
        ArrayList<Integer> ColumnINT =new ArrayList<>();
        ArrayList<Integer> ColumnDATE=new ArrayList<>();
        ArrayList<String[]>p=new ArrayList<>();
        if (Columns.size()!=0){
            for (int i = 0; i < Columns.size() ; i++) {
                if (i== Columns.size()-1){
                    selectStatement = selectStatement + TransColumns(i,Columns.get(i),ColumnINT,ColumnDATE)+" ";
                }else {
                    selectStatement = selectStatement + TransColumns(i,Columns.get(i),ColumnINT,ColumnDATE) + ",";
                }
            }


        }else {
            selectStatement = selectStatement +"* ";
            switch (table){
                case "center":ColumnINT.add(0);break;
                case "enterprise":ColumnINT.add(0);break;
                case"model":ColumnINT.add(0);ColumnINT.add(4);break;
                case "staff":ColumnINT.add(0);ColumnINT.add(2);break;
                case "contract":ColumnDATE.add(3);break;
                case "orders":ColumnINT.add(2);ColumnDATE.add(4);ColumnDATE.add(5);break;
                case "inventory":ColumnINT.add(2);ColumnINT.add(3);ColumnINT.add(4);ColumnINT.add(5);break;
                default:break;

            }
        }


        selectStatement = selectStatement +"from "+table+" where ";
        for (int i = 0; i < Data.size(); i++) {
            if (i == 0) {
                selectStatement = selectStatement + Trans(i, Data.get(i),INT,DATE);
            } else {
                selectStatement = selectStatement + " and " + Trans(i, Data.get(i),INT,DATE);
            }
        }

        /////////////////////////////open DB
        try {
            stmt = con.prepareStatement(selectStatement);
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }

        String[]strings=new String[parts.size()];
        for (int i = 0; i <strings.length ; i++) {
            strings[i]=parts.get(i);
        }



        try {


            p=load_select(strings,Columns,INT,DATE,ColumnINT,ColumnDATE,table);


            con.commit();

        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
            } catch (Exception e2) {
            }
        }

        return p;
    }


    private static void load_delete(String statement) throws SQLException {
        try {
            stmt = con.prepareStatement(statement);
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }

        try {

            if (con != null) {
//                        stmt.setString(1, content);
                stmt.executeUpdate();

                con.commit();
            }


        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
            } catch (Exception e2) {
            }
        }


    }

    private static ArrayList<String[]> load_select(String[] strings,List<String>Columns,ArrayList<Integer>INT,ArrayList<Integer>DATE,ArrayList<Integer>ColumnsINT,ArrayList<Integer>ColumnsDATE,String table)
            throws SQLException {

//        int index=0;
        String[] fin = new String[centerLength.get(table)];
        ArrayList<String[]>p=new ArrayList<>();
        if (con != null) {
            int countINT=0;
            int countDATE=0;
            ResultSet rs = null;
            for (int i = 0; i <strings.length ; i++) {
                if (INT.size()>countINT){
                    if (INT.get(countINT)==i){
                        stmt.setInt(i+1,Integer.parseInt(strings[i]));
                        countINT++;
                        continue;
                    }
                }
                if (DATE.size()>countDATE){
                    if (DATE.get(countDATE)==i){
                        stmt.setDate(i+1,strToDate(strings[i]));
                        countDATE++;
                        continue;
                    }
                }
                stmt.setString(i+1,strings[i]);
            }
            countINT=0;
            countDATE=0;
            rs=  stmt.executeQuery();

            while (rs.next()){
                if (Columns.size()==0) {
                    fin=new String[centerLength.get(table)];
                    for (int i = 1; i <= centerLength.get(table); i++) {
                        if (ColumnsINT.size() > countINT) {
                            if (ColumnsINT.get(countINT)+1 == i) {
                                String s=String.valueOf(rs.getInt(i));
                                fin[i-1]=s;

                                System.out.print(s + " ");
                                countINT++;
                                continue;
                            }
                        }
                        if (ColumnsDATE.size() > countDATE) {
                            if (ColumnsDATE.get(countDATE)+1 == i) {
                                String s=String.valueOf(rs.getDate(i));
                                fin[i-1]=s;

                                System.out.print(s + " ");
                                countDATE++;
                                continue;
                            }
                        }
                        String s=rs.getString(i);
                        fin[i-1]=s;
                        System.out.print(s + " ");
                    }
                    p.add(fin);
                    System.out.println();
                }else {
                    fin=new String[Columns.size()];
                    for (int i = 1; i <= Columns.size(); i++) {
                        if (ColumnsINT.size() > countINT) {
                            if (ColumnsINT.get(countINT)+1 == i) {
                                String s=String.valueOf(rs.getInt(i));
                                fin[i-1]=s;

                                System.out.print(s + " ");
                                countINT++;
                                continue;
                            }
                        }
                        if (ColumnsDATE.size() > countDATE) {
                            if (ColumnsDATE.get(countDATE)+1 == i) {
                                String s=String.valueOf(rs.getDate(i));
                                fin[i-1]=s;

                                System.out.print(s + " ");
                                countDATE++;
                                continue;
                            }
                        }
                        String s=rs.getString(i);
                        fin[i-1]=s;
                        System.out.print(s + " ");
                    }
                    p.add(fin);
                    System.out.println();
                }
            }
        }
        boolean isNull=true;
        for (int i = 0; i < p.size(); i++) {
            if (p.get(i)!=null){
                isNull=false;
                break;
            }
        }
        return isNull?null:p;
    }

    public static String Trans(int i,String s,ArrayList<Integer>INT,ArrayList<Integer>DATE){
        if (s.toLowerCase().contains("id")||s.toLowerCase().contains("age")||s.toLowerCase().contains("unit_price")||
                s.toLowerCase().contains("quantity")||s.toLowerCase().contains("money")||s.toLowerCase().contains("profits")){
            INT.add(i);

        }
        if (s.toLowerCase(Locale.ROOT).contains("date")){
            DATE.add(i);

        }
        return s+"=? ";

    }

    public static String TransColumns(int i,String s,ArrayList<Integer>INT,ArrayList<Integer>DATE){
        if (s.toLowerCase(Locale.ROOT).equals("id")||s.toLowerCase(Locale.ROOT).equals("age")||s.toLowerCase(Locale.ROOT).equals("unit_price")||
                s.toLowerCase(Locale.ROOT).contains("quantity")||s.toLowerCase(Locale.ROOT).contains("money")||s.toLowerCase(Locale.ROOT).contains("profits")){
            INT.add(i);

        }
        if (s.toLowerCase(Locale.ROOT).contains("date")){
            DATE.add(i);

        }
        return s;

    }


}

