package com.spring.mvc.chap06.jdbc;

import com.spring.mvc.chap06.entity.Person;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcRepository {

    // DB연결 설정
    private String url = "jdbc:mariadb://localhost:3307/spring"; // 데이터베이스 URL
    private String username = "root";
    private String password = "mariadb";

    // JDBC 드라이버 로딩
    public JdbcRepository() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 데이터베이서 커넥션 얻기
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    // INSERT 기능
    public void save(Person person) {
        Connection conn = null;

        // 1. DB연결 이후 연결 정보를 얻어와야 한다.
        try {
            conn = DriverManager.getConnection(url, username, password);
            // 2. 트랜잭션을 시작
            conn.setAutoCommit(false);
            // 3. SQL 생성
            String sql = "INSERT INTO person (id, person_name, person_age) VALUES (?, ?, ?)";
            // 4. SQL을 실행 시켜주는 객체를 얻어와야 한다.
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // 5. ? 파라미터 세팅
            pstmt.setString(1, person.getId());
            pstmt.setString(2, person.getPersonName());
            pstmt.setInt(3, person.getPersonAge());
            // 6. SQL 실행 명령
            // excuteUpdate - 갱신, excuteQuery - 조회
            int result = pstmt.executeUpdate();// 리턴값 = 성공한 쿼리의 갯수
            // 7. 트랜젝션 처리
            if (result == 1) conn.commit();
            else conn.rollback();
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        } finally {
            // close
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    // UPDAET기능
    public void update(Person person) {
        Connection conn = null;

        // 1. DB연결 이후 연결 정보를 얻어와야 한다.
        try {
            conn = DriverManager.getConnection(url, username, password);
            // 2. 트랜잭션을 시작
            conn.setAutoCommit(false);
            // 3. SQL 생성
            String sql = "UPDATE person SET person_name = ?, person_age = ? where id = ?";
            // 4. SQL을 실행 시켜주는 객체를 얻어와야 한다.
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // 5. ? 파라미터 세팅
            pstmt.setString(1, person.getPersonName());
            pstmt.setInt(2, person.getPersonAge());
            pstmt.setString(3, person.getId());
            // 6. SQL 실행 명령
            // excuteUpdate - 갱신, excuteQuery - 조회
            int result = pstmt.executeUpdate();// 리턴값 = 성공한 쿼리의 갯수
            // 7. 트랜젝션 처리
            if (result == 1) conn.commit();
            else conn.rollback();
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        } finally {
            // close
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    // Delete 기능
    public void delete(String id) {
        Connection conn = null;

        // 1. DB연결 이후 연결 정보를 얻어와야 한다.
        try {
            conn = DriverManager.getConnection(url, username, password);
            // 2. 트랜잭션을 시작
            conn.setAutoCommit(false);
            // 3. SQL 생성
            String sql = "DELETE FROM person WHERE id = ?";
            // 4. SQL을 실행 시켜주는 객체를 얻어와야 한다.
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // 5. ? 파라미터 세팅
            pstmt.setString(1, id);
            // 6. SQL 실행 명령
            // excuteUpdate - 갱신, excuteQuery - 조회
            int result = pstmt.executeUpdate();// 리턴값 = 성공한 쿼리의 갯수
            // 7. 트랜젝션 처리
            if (result == 1) conn.commit();
            else conn.rollback();
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        } finally {
            // close
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    // 전체 회원 조회
    public List<Person> findALl() {
        List<Person> people = new ArrayList<>();
        try {
            // 1. DB에 연결해서 정보 획득
            Connection conn = DriverManager.getConnection(url, username, password);

            // 2. SQL 생성
            String sql = "SELECT * FROM person";

            // 3. SQL 실행을 위한 객체 획득
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 4. ? 파라미터 채우기
            // 5. SQL 실행 명령
            ResultSet rs = pstmt.executeQuery();

            // 6. 결과집합 조작하기
            while (rs.next()) {

                String id = rs.getString("id");
                String personName = rs.getString("person_name");
                int personAge = rs.getInt("person_age");

                Person person = new Person(id, personName, personAge);
                people.add(person);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return people;
    }

    // 단일 조회 (ID)
    public Person findOne(String id) {
        try {
            // 1. DB에 연결해서 정보 획득
            Connection conn = DriverManager.getConnection(url, username, password);

            // 2. SQL 생성
            String sql = "SELECT * FROM person WHERE id = ?";

            // 3. SQL 실행을 위한 객체 획득
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 4. ? 파라미터 채우기
            pstmt.setString(1, id);
            // 5. SQL 실행 명령
            ResultSet rs = pstmt.executeQuery();

            // 6. 결과집합 조작하기
            while (rs.next()) {
                String pid = rs.getString("id");
                String personName = rs.getString("person_name");
                int personAge = rs.getInt("person_age");
                return new Person(pid, personName, personAge);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
