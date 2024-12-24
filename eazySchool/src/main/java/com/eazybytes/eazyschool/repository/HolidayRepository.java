package com.eazybytes.eazyschool.repository;

import com.eazybytes.eazyschool.controller.HolidaysController;
import com.eazybytes.eazyschool.model.Holiday;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public class HolidayRepository {

    private JdbcTemplate jdbcTemplate;

    public HolidayRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate =jdbcTemplate;
    }

    public List<Holiday> findAllHolidays(){
        String sql = "SELECT * FROM holidays";
        var hoildayMapper = BeanPropertyRowMapper.newInstance(Holiday.class);
        return jdbcTemplate.query(sql,hoildayMapper);
    }
}
