package com.hellorin.stickyMoss.user.usertypes;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StringNVarcharType;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hellorin on 22.10.17.
 */
public class SimpleGrantedAuthorityType implements UserType {
    @Override
    public int[] sqlTypes() {
        return new int[] {
            StringType.INSTANCE.sqlType()
        };
    }

    @Override
    public Class returnedClass() {
        return SimpleGrantedAuthority.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        if (o == o1) {
            return true;
        } else if (o == null || o1 == null) {
            return false;
        } else {
            return o.equals(o1);
        }
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SessionImplementor sessionImplementor, Object o) throws HibernateException, SQLException {
        if (strings.length != 1) {
            throw new HibernateException("Role cannot be empty in database !");
        }
        String role = (String) StringType.INSTANCE.get(resultSet, strings[0], sessionImplementor);

        return role == null ? null : new SimpleGrantedAuthority(role);
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        if (o == null) {
            throw new HibernateException("Role cannot be empty in database !");
        } else {
            final SimpleGrantedAuthority authority = (SimpleGrantedAuthority) o;
            StringNVarcharType.INSTANCE.set(preparedStatement, authority.getAuthority(), i, sessionImplementor);
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        throw new UnsupportedOperationException();
    }
}
