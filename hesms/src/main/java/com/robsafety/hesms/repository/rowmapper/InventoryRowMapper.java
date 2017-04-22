package com.robsafety.hesms.repository.rowmapper;

import com.robsafety.hesms.domain.Inventory;
import com.robsafety.hesms.domain.InventoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Jijeesh on 4/10/2017.
 */

public class InventoryRowMapper implements RowMapper<Inventory>{

    @Override
    public Inventory mapRow(ResultSet rs, int rowNum) throws SQLException {
        Inventory inv = new Inventory();
        inv.setUid(rs.getString("uid"));
        inv.setStockId(rs.getString("stock_id"));
        inv.setInvoiceNumber(rs.getString("invoice_number"));
        inv.setInventoryType(setInventoryType(rs));

        return inv;
    }

    private InventoryType setInventoryType(ResultSet rs) throws SQLException {
        InventoryType it = new InventoryType();

        it.setCode(rs.getString("type_code"));
        it.setName(rs.getString("type_name"));
        it.setDescription(rs.getString("type_description"));


        return it;
    }

}
