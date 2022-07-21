package com.example.db_proj2_test1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

@SpringBootTest
class StaffRepositoryTest {
    @Autowired
    private Center1Repository centerRepository;

//    @Test
//    void findAll(){
//        System.out.println(staffRepository.findAll());
//    }
//    @Test
//    void save(){
//        Staff staff=new Staff();
//        staff.setId(100000);
//        staff.setName("sed");
//        staff.setAge(34);
//        staff.setGender("aa");
//        staff.setNumber("123");
//        staff.setSupply_center("Asia");
//        staff.setMobile_number("18028793998");
//        staff.setType("Salesman");
//        Staff staff1=staffRepository.save(staff);
//        System.out.println();
//
//
//    }
    @Test
    void delete() throws SQLException {

//        StaffHandler.deleteById(676);
        CenterHandler centerHandler=new CenterHandler();
        centerHandler.deleteById(2);
    }

    @Test
    void update() throws SQLException {
        StaffHandler.update(new Staff(2,"Steven Edwards",38,"FeMale","12211522","America","15673757797","Supply Staff"
        ));
    }

    @Test
    void getCount() throws SQLException {
//        StaffHandler.getCount();
    }

//    @Test
//    void select() throws SQLException {
//        StaffHandler.Select("id","176");
//    }

//    @Test
//    void findbyName() throws SQLException {
//        CenterHandler centerHandler=new CenterHandler();
//        centerHandler.select("Asia");
//    }
}