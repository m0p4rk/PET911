package com.m0p4rk.pet911.typehandler;

import com.m0p4rk.pet911.enums.QueryCategory;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryCategoryTypeHandler extends BaseTypeHandler<QueryCategory> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, QueryCategory parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public QueryCategory getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : QueryCategory.valueOf(value);
    }

    @Override
    public QueryCategory getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : QueryCategory.valueOf(value);
    }

    @Override
    public QueryCategory getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : QueryCategory.valueOf(value);
    }
}