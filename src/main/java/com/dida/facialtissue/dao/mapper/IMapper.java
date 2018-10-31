package com.dida.facialtissue.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface IMapper <T, Texample> {
	long countByExample(Texample example);

    int deleteByExample(Texample example);

    int deleteByPrimaryKey(Long id);

    List<T> selectByExample(Texample example);

    int updateByExample(@Param("record") T record, @Param("example") Texample example);

    int updateByPrimaryKey(T record);
    
    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") T record, @Param("example") Texample example);

    int updateByPrimaryKeySelective(T record);
}
