package cn.edu.nju.candleflame.rss_spider.util;


import cn.edu.nju.candleflame.rss_spider.aop.RunningLog;
import cn.edu.nju.candleflame.rss_spider.config.JDBCConfig;
import cn.edu.nju.candleflame.rss_spider.schedule.FlushFeedExecutor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class JDBCUtil {

	private static final RunningLog LOGGER = RunningLog.getLog(JDBCUtil.class);

	private final JDBCConfig jdbcConfig;

	public JDBCUtil(JDBCConfig jdbcConfig) throws Exception {
		this.jdbcConfig = jdbcConfig;
		if(jdbcConfig == null||
				StringUtils.isEmpty(jdbcConfig.getDriver())||
			StringUtils.isEmpty(jdbcConfig.getPassword())||
			StringUtils.isEmpty(jdbcConfig.getUserName())||
			StringUtils.isEmpty(jdbcConfig.getUrl())){
			throw new Exception("jdbc config error");
		}

		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch (ClassNotFoundException e){
			LOGGER.error("jdbc class not found " , e.getMessage());
		}
	}

	/**
	 * 获得数据库连接Connection
	 * @return Connection 数据库连接
	 */
	private Connection getConnection(){

		Connection connection = null;
		try {
			connection =  DriverManager.getConnection(
					jdbcConfig.getUrl(),
					jdbcConfig.getUserName(),
					jdbcConfig.getPassword());
		} catch (SQLException e) {
			LOGGER.error("cannot get connection " , e.getMessage());
		}
		return connection;
	}


	/**
	 * 设置参数
	 * @param preparedStatement Statement对象
	 * @param param 参数列表
	 * @return
	 * @throws SQLException
	 */
	private boolean settingParams(PreparedStatement preparedStatement, Object[] param) throws SQLException {

		if (param != null && param.length > 0){
			ParameterMetaData parameterMetaData = preparedStatement.getParameterMetaData();
			int paramCount = parameterMetaData.getParameterCount();

			if (paramCount != param.length){
				return false;
			}

			for (int i = 0; i < paramCount; i++){
				preparedStatement.setObject(i+1, param[i]);
			}
		}
		return true;
	}


	/**
	 * 更新操作
	 * @param sql 执行的SQL语句
	 * @param param 对应的参数列表
	 * @return
	 */
	public boolean update(String sql, Object[] param){

		PreparedStatement preparedStatement = null;
		Connection connection = getConnection();
		try {
			preparedStatement = connection.prepareStatement(sql);

			if (settingParams(preparedStatement, param) == false){
				return false;
			}

			int result = preparedStatement.executeUpdate();
			if (result > 0){
				return true;
			}
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(connection, preparedStatement);
		}
		return false;
	}

	/**
	 * 获取单个Bean
	 * @param sql 执行SQL语句
	 * @param param 对应的参数列表
	 * @param clazz 所要获取的对象的类型
	 * @param <T>  对象的类型
	 * @return bean
	 */
	public <T> T queryForBean(String sql, Object[] param, Class<T> clazz){

		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			preparedStatement = connection.prepareStatement(sql);

			if (settingParams(preparedStatement, param) == false){
				return null;
			}

			resultSet = preparedStatement.executeQuery();
			if (resultSet == null){
				return null;
			}

			if (resultSet.next()){

				T data = clazz.newInstance();
				ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
				int columnCount = resultSetMetaData.getColumnCount();
				for (int i = 0; i < columnCount; i++){
					String name = resultSetMetaData.getColumnName(i + 1);
					Object rData = resultSet.getObject(name);
					BeanUtils.copyProperty(data, name, rData);
				}
				return data;

			}else {
				return null;
			}

		} catch (InstantiationException | InvocationTargetException | SQLException | IllegalAccessException e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			close(connection, preparedStatement, resultSet);
		}
		return null;
	}

	/**
	 * 获取Bean并且封装成List
	 * @param sql 执行SQL语句
	 * @param param 对应的参数列表
	 * @param clazz 所要获取的对象的类型
	 * @param <T>  对象的类型
	 * @return list
	 */
	public <T> List<T> queryForList(String sql, Object[] param, Class<T> clazz){

		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			preparedStatement = connection.prepareStatement(sql);

			if (!settingParams(preparedStatement, param)){
				return null;
			}

			resultSet = preparedStatement.executeQuery();
			if (resultSet == null){
				return null;
			}
			List<T> results = new ArrayList<>();

			while (resultSet.next()){

				T data = clazz.newInstance();
				ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
				int columnCount = resultSetMetaData.getColumnCount();
				for (int i = 0; i < columnCount; i++){
					String name = resultSetMetaData.getColumnName(i + 1);
					Object rData = resultSet.getObject(name);
					BeanUtils.copyProperty(data, name, rData);
				}
				results.add(data);

			}
			return results;

		} catch (InstantiationException | IllegalAccessException | SQLException | InvocationTargetException e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			close(connection, preparedStatement, resultSet);
		}
		return null;
	}

	/**
	 * 关闭connection
	 * @param connection 连接池对象
	 */
	private void close(Connection connection){
		if (connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭Statement
	 * @param statement
	 */
	private static void close(Statement statement){
		if (statement != null){
			try {
				statement.close();
			} catch (SQLException e) {
				LOGGER.error(e.getLocalizedMessage());
			}
		}
	}

	/**
	 * 关闭ResultSet
	 * @param resultSet
	 */
	private void close(ResultSet resultSet){
		if (resultSet != null){
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getLocalizedMessage());
			}
		}
	}

	/**
	 * 关闭Connection 以及Statement
	 * @param connection
	 * @param statement
	 */
	private void close(Connection connection, Statement statement){
		close(connection);
		close(statement);
	}

	/**
	 * 关闭Connection，Statement以及ResultSet
	 * @param connection
	 * @param statement
	 * @param resultSet
	 */
	private void close(Connection connection, Statement statement, ResultSet resultSet){
		close(connection, statement);
		close(resultSet);
	}
}
