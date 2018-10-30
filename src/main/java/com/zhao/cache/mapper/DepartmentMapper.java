package com.zhao.cache.mapper;

import com.zhao.cache.bean.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DepartmentMapper {

	@Select("SELECT * FROM department WHERE id = #{id}")
	Department getDeptById(Integer id);
}
