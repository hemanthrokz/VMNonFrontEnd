package com.VendingMachine.VendingMachine01.dao;

import com.VendingMachine.VendingMachine01.model.InitialBalanceAndPurchaseHistory;
import com.VendingMachine.VendingMachine01.model.Inventry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InitialBalanceDAOImp implements InitialBalanceDAO {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public InitialBalanceAndPurchaseHistory getChange() {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", getAllPurchaseHistory().size());

        List<InitialBalanceAndPurchaseHistory> initialBalanceAndPurchaseHistoryList = namedParameterJdbcTemplate.query("select initialBalance from purchasehistory_table where id = :id",sqlParameterSource, new BeanPropertyRowMapper<InitialBalanceAndPurchaseHistory>(InitialBalanceAndPurchaseHistory.class));
        return initialBalanceAndPurchaseHistoryList.get(0);
    }

    @Override
    public List<InitialBalanceAndPurchaseHistory> getAllPurchaseHistory() {
        return namedParameterJdbcTemplate.query("select * from purchasehistory_table ", new BeanPropertyRowMapper<InitialBalanceAndPurchaseHistory>(InitialBalanceAndPurchaseHistory.class));
    }


    //////////////////////////////////////////////////////////////////
    @Override
    public int saveTransaction(InitialBalanceAndPurchaseHistory initialBalanceAndPurchaseHistory) {

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", getAllPurchaseHistory().size() + 1)
                .addValue("productId", initialBalanceAndPurchaseHistory.getProductId())
                .addValue("product", initialBalanceAndPurchaseHistory.getProduct())
                .addValue("productPrice", initialBalanceAndPurchaseHistory.getProductPrice())
                .addValue("customerInputAmount", initialBalanceAndPurchaseHistory.getCustomerInputAmount())
                .addValue("vendingMachinebalance", initialBalanceAndPurchaseHistory.getVendingMachineBalance())
                .addValue("initialBalance", initialBalanceAndPurchaseHistory.getInitialBalance());

        return namedParameterJdbcTemplate.update("insert into purchasehistory_table (id,productId,product,productPrice,customerInputAmount,vendingMachinebalance,initialBalance) values (:id,:productId,:product,:productPrice,:customerInputAmount,:vendingMachinebalance,:initialBalance)", sqlParameterSource);
    }

}

