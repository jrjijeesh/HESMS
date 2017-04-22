package com.robsafety.hesms.repository;

import com.robsafety.hesms.domain.Inventory;
import com.robsafety.hesms.domain.InventoryType;
import com.robsafety.hesms.repository.rowmapper.InventoryRowMapper;
import com.robsafety.hesms.repository.rowmapper.InventoryTypeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jijeesh on 4/10/2017.
 */

@Repository
public class InventoryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("#{QueryPropertyMapper.map('${hesms.queries}')}")
    Map<String, String> queriesMap;

    public Inventory getInventory(){
        Inventory inv = new Inventory();

        //inv.setStockNumber("S0001");

        return  inv;
    }

    @Transactional(readOnly=true)
    public List<Inventory> findAll(Integer limit,Integer offset) {
        if(limit != null && offset !=null) {
            /*return jdbcTemplate.query(queriesMap.get("inventory_get_all_paginated"), new Integer[]{limit,offset},new InventoryRowMapper());*/
            return namedParameterJdbcTemplate.query(queriesMap.get("inventory_get_all_paginated"),
                    new MapSqlParameterSource().addValue("limit",limit).addValue("offset",offset) ,
                    new InventoryRowMapper());
        }else{
            return jdbcTemplate.query(queriesMap.get("inventory_get_all"), new InventoryRowMapper());
        }
    }

    @Transactional(readOnly=true)
    public List<Inventory> findAll() {

        System.out.println(queriesMap);

        return jdbcTemplate.query(queriesMap.get("inventory_get_all"), new InventoryRowMapper());
    }

    public InventoryType saveInventoryType(InventoryType inventoryType) {
        MapSqlParameterSource paramMap =new MapSqlParameterSource()
                .addValue("code",inventoryType.getCode())
                .addValue("name",inventoryType.getName())
                .addValue("description",inventoryType.getDescription());

        namedParameterJdbcTemplate.update(queriesMap.get("inventory_type_create"),paramMap);

        return inventoryType;

    }

    public List<InventoryType> getInventoryType() {
        return jdbcTemplate.query(queriesMap.get("inventory_type_get_all"), new InventoryTypeRowMapper());
    }

    public Inventory saveInventory(Inventory inventory) {
        MapSqlParameterSource paramMap =new MapSqlParameterSource()
                .addValue("uid",inventory.getUid())
                .addValue("stock_id",inventory.getStockId())
                .addValue("type_code",(inventory.getInventoryType()!=null)?inventory.getInventoryType().getCode():null)
                .addValue("invoice_number",inventory.getInvoiceNumber());

        namedParameterJdbcTemplate.update(queriesMap.get("inventory_create"),paramMap);

        return inventory;
    }

    /*@Transactional(readOnly=true)
    public User findUserById(int id) {
        return jdbcTemplate.queryForObject("select * from users where id=?", new Object[]{id}, new UserRowMapper());
    }

    public User create(final User user) {
        final String sql = "insert into users(name,email) values(?,?)";

        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getName());
                ps.setString(2, user.getEmail());
                return ps;
            }
        }, holder);

        int newUserId = holder.getKey().intValue();
        user.setId(newUserId);
        return user;
    }*/
}
