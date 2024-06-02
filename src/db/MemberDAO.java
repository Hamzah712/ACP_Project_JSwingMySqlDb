package db;

import model.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    public void addMember(Member member) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO members_tb (first_name, last_name, age, gender, membership_date) VALUES (?, ?, ?, ?, ?)")) {

            pstmt.setString(1, member.getFirstName());
            pstmt.setString(2, member.getLastName());
            pstmt.setInt(3, member.getAge());
            pstmt.setString(4, member.getGender());
            pstmt.setString(5, member.getMembershipDate());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMember(Member member) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE members_tb SET first_name = ?, last_name = ?, age = ?, gender = ?, membership_date = ? WHERE id = ?")) {

            pstmt.setString(1, member.getFirstName());
            pstmt.setString(2, member.getLastName());
            pstmt.setInt(3, member.getAge());
            pstmt.setString(4, member.getGender());
            pstmt.setString(5, member.getMembershipDate());
            pstmt.setInt(6, member.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMember(int id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM members_tb WHERE id = ?")) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM members_tb")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                String membershipDate = rs.getString("membership_date");

                Member member = new Member(id, firstName, lastName, age, gender, membershipDate);
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
}
