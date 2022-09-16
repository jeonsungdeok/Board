package com.util.sqlMap;

import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class SqlMapConfig {
	
	private static final SqlMapClient sqlMap; //xml���� �о�� �������� ��ü�� ���� ����
	
	
	static {  // ��� �ʱ�ȭ ó�� 
		
		try {
			
			String resource = "com/util/sqlMap/sqlMapConfig.xml";
			Reader reader = Resources.getResourceAsReader(resource); //�� �� ���� �־�� xml ������ �о�� �� ���� 
			
			sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);  // �� �����͸� ��ü ������
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("sqlMap Error : " + e);
		}
	}
	
	public static SqlMapClient getSqlMapInstance() {
		return sqlMap;
	}

}

