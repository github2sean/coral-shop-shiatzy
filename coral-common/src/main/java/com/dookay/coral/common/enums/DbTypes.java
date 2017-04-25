package com.dookay.coral.common.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 数据库类型
 * @since : 2016年11月8日
 * @author : kezhan
 * @version : v0.0.1
 */
public class DbTypes {
	
	/**
	 * ***************************************************************************
	 *模块名 : DbTypeEnum
	 *文件名 : DbTypesEnum.java
	 *创建时间 : 2016年10月28日
	 *实现功能 : 目前支持的数据库类型
	 *作者 : kezhan
	 *版本 : v0.0.1
	-----------------------------------------------------------------------------
	 *修改记录:
	 *日 期 版本 修改人 修改内容
	 *2016年10月28日 v0.0.1 kezhan 创建
	 ****************************************************************************
	 */
	public enum DbTypeEnum {
		ORACLE("oracle"), MYSQL("mysql");
		private String value;

		private DbTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	/**
	 * 支持的数据库类型列表,目前只支持oracle，mysql
	 */
	public static List<String> dbTypeList = new ArrayList<String>();
	static {
		dbTypeList.add(DbTypeEnum.ORACLE.getValue());
		dbTypeList.add(DbTypeEnum.MYSQL.getValue());
	}
	
	/**
	 * ====================================================================
	 *函 数 名： @param dbType
	 *函 数 名： @return
	 *功 能： 数据库是否是oracle
	----------------------------------------------------------------------
	 *修改记录 ：
	 *日 期  版本 修改人 修改内容
	 *2016年10月28日 v0.0.1 kezhan 创建
	====================================================================
	 */
	public static boolean isOracle(String dbType) {
		if (StringUtils.isNotEmpty(dbType) && DbTypeEnum.ORACLE.getValue().equals(dbType)) {
			return true;
		}
		return false;
	}

	/**
	 * ====================================================================
	 *函 数 名： @param dbType
	 *函 数 名： @return
	 *功 能： 数据库是否是mysql
	----------------------------------------------------------------------
	 *修改记录 ：
	 *日 期  版本 修改人 修改内容
	 *2016年10月28日 v0.0.1 kezhan 创建
	====================================================================
	 */
	public static boolean isMysql(String dbType) {
		if (StringUtils.isNotEmpty(dbType) && DbTypeEnum.MYSQL.getValue().equals(dbType)) {
			return true;
		}
		return false;
	}
	
}
