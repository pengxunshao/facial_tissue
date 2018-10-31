package com.dida.facialtissue.dao.impl;

import java.util.List;

import com.dida.facialtissue.dao.IDAO;
import com.dida.facialtissue.dao.mapper.IMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class DAOImpl<T, Texample> implements IDAO<T, Texample> {
	@Autowired
	public IMapper<T, Texample> mapper;
	
	public int insert(T record) {
		return mapper.insert(record);
	}

	public int insertSelective(T record) {
		return mapper.insertSelective(record);
	}
	
	public int deleteByExample(Texample example) {
		return mapper.deleteByExample(example);
	}

	public int deleteByPrimaryKey(Long id) {
		return mapper.deleteByPrimaryKey(id);
	}

	public int updateByExample(T record, Texample example) {
		return mapper.updateByExample(record, example);
	}

	public int updateByPrimaryKey(T record) {
		return mapper.updateByPrimaryKey(record);
	}
	
	public int updateByExampleSelective(T record, Texample example) {
		return mapper.updateByExampleSelective(record, example);
	}

	public int updateByPrimaryKeySelective(T record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	public List<T> selectByExample(Texample example) {
		return mapper.selectByExample(example);
	}

	public long countByExample(Texample example) {
		return mapper.countByExample(example);
	}

	public T selectByPrimaryKey(Long id) {
		return mapper.selectByPrimaryKey(id);
	}
	
}
