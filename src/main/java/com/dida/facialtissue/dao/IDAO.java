package com.dida.facialtissue.dao;

import java.util.List;

public interface IDAO<T, Texample> {
	long countByExample(Texample example);

    int deleteByExample(Texample example);

    int deleteByPrimaryKey(Long id);

    List<T> selectByExample(Texample example);

    int updateByExample(T record, Texample example);

    int updateByPrimaryKey(T record);
    
    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Long id);

    int updateByExampleSelective(T record, Texample example);

    int updateByPrimaryKeySelective(T record);
}
