package Login;

import java.sql.*;
import java.util.ArrayList;
//import java.util.Scanner;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;

@Service
public class Login {
    private ArrayList<Object> userList;
    public Login(){}
    public Login(ArrayList<Object> userList){
        this.userList=userList;
    }
    //* Adding a user to the database.
    public void addUser(User u)throws SQLException{
        String uname="root";
        String password="2kihjFFnn@2003";
        String url="jdbc:mysql://localhost:3306/sql_workbench";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con=DriverManager.getConnection(url,uname,password);

            PreparedStatement preparedStatement=con.prepareStatement("insert into sql_workbench.users (UserName, UserPassword, Mail) values(?, ?, ?)");

            preparedStatement.setString(1,u.getName());
            preparedStatement.setString(2,u.getPassword());
            preparedStatement.setString(3,u.getEmail());

            preparedStatement.execute();
            con.close();

        }
        catch(ClassNotFoundException e){
            System.out.println("Error: "+e);
        }
    }

    //* Deleting a user from the database.
    public void deleteUser(User u) throws SQLException {
        String uname = "root";
        String password = "2kihjFFnn@2003";
        String url = "jdbc:mysql://localhost:3306/sql_workbench";

        try {
            try (Connection con = DriverManager.getConnection(url, uname, password)) {
                String deleteSql = "DELETE FROM sql_workbench.users WHERE idUsers = ?";
                try (PreparedStatement preparedStatement = con.prepareStatement(deleteSql)) {
                    preparedStatement.setInt(1, u.getId());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("Eroare la ștergere: " + e.getMessage());
        }

    }

    //* Rearranging the ids of the users from the database.
    public void rearrangeIds() {
        String uname = "root";
        String password = "2kihjFFnn@2003";
        String url = "jdbc:mysql://localhost:3306/sql_workbench";

        try (Connection con = DriverManager.getConnection(url, uname, password)) {
            String updateSql = "UPDATE sql_workbench.users SET idUsers = ? WHERE idUsers = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(updateSql)) {
                // Obțineți toate înregistrările din tabel și actualizați valorile id-urilor într-o buclă
                ResultSet rs = con.createStatement().executeQuery("SELECT idUsers FROM sql_workbench.users ORDER BY idUsers");
                int newId = 1;

                while (rs.next()) {
                    preparedStatement.setInt(1, newId);
                    preparedStatement.setInt(2, rs.getInt(1));
                    preparedStatement.executeUpdate();
                    newId++;
                }
            }
        } catch (SQLException e) {
            System.out.println("Eroare la rearanjarea id-urilor: " + e.getMessage());
        }
    }

    //* Displaying the users from the database.
    public void displayUsers(){
        System.out.println("ID | Name | Password | Email");
        String uname="root";
        String password="2kihjFFnn@2003";
        String url="jdbc:mysql://localhost:3306/sql_workbench";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con=DriverManager.getConnection(url,uname,password);

            Statement st=con.createStatement();

            ResultSet rs=st.executeQuery("select * from sql_workbench.users");

            while(rs.next()){
                System.out.println(rs.getInt(1)+" | "+rs.getString(2)+" | "+rs.getString(3)+" |" +rs.getString(4));
            }
            con.close();
        }
        catch(ClassNotFoundException | SQLException e){
            System.out.println("Error: "+e);
        }
    }
    public ArrayList<Object> getAllUsers() throws SQLException {
        return userList;
    }
}
