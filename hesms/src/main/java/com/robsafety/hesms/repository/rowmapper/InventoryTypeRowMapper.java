package com.robsafety.hesms.repository.rowmapper;

import com.robsafety.hesms.domain.Inventory;
import com.robsafety.hesms.domain.InventoryType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Jijeesh on 4/10/2017.
 */

public class InventoryTypeRowMapper implements RowMapper<InventoryType>{

    @Override
    public InventoryType mapRow(ResultSet rs, int rowNum) throws SQLException {
        InventoryType it = new InventoryType();
        it.setCode(rs.getString("code"));
        it.setName(rs.getString("name"));
        it.setDescription(rs.getString("description"));
        return it;
    }

}
