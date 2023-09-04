package com.example.demo.mapper.login;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class LoginMapper extends SqlSessionDaoSupport{

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@PostConstruct
	void init() {
		setSqlSessionTemplate(sqlSessionTemplate);
	}

	/**
	 * login
	 *
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> login(HashMap<String, Object> paramMap) throws Exception {
		logger.debug("LoginMapper login START");
		String sqlId = "Login.login";
		logger.debug("LoginMapper login END");
		return getSqlSession().selectList(sqlId, paramMap);
	}
}
