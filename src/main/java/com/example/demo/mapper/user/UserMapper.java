package com.example.demo.mapper.user;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class UserMapper extends SqlSessionDaoSupport{

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@PostConstruct
	void init() {
		setSqlSessionTemplate(sqlSessionTemplate);
	}

	/**
	 * User 조회(One)
	 *
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int insertUser (HashMap<String, Object> paramMap) throws Exception {
		logger.debug("UserMapper insertUser START");
		String sqlId = "User.insertUser";
		logger.debug("UserMapper insertUser END");
		return getSqlSession().insert(sqlId, paramMap);
	}

	/**
	 * User 조회(One)
	 *
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectUser(HashMap<String, Object> paramMap) throws Exception {
		logger.debug("UserMapper selectUser START");
		String sqlId = "User.selectUser";
		logger.debug("UserMapper selectUser END");
		return getSqlSession().selectList(sqlId, paramMap);
	}
}
