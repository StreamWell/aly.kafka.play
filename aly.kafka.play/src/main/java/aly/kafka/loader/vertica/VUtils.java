package aly.kafka.loader.vertica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import aly.kafka.loader.IStroreUtil;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import aly.kafka.tools.ConfPlay;
import aly.kafka.tools.MyLogger;
import aly.kafka.tools.StreamChannelExeption;

/** KNOW-HOW
 * Vertica JDBC driver not to be found in maven rpository.
 * So, you can add it to repository (local) like this:
  
 	mvn install:install-file \
	-DgroupId=vertica -DartifactId=vertica-jdbc -Dversion=4.0.19 \
	-Dpackaging=jar -Dfile=/Users/ayakubo/git/aly.kafka.play/aly.kafka.play/lib/vertica-jdbc-7.1.1-0.jar -DgeneratePom=true
 
 *
 * Also be aware: vsql util lives in everyVertica nodes in /opt/vertica/bin 
 */

/**
 *  CLass in the method addRecord(List<Object> values) inserts ONE row into specified V table
 */
public class VUtils extends IStroreUtil
{
	static final String ERR_MSG_PREF = "VUtils.getConnection():  failed: ";
	
	static final String DROP_STMT = "DROP TABLE IF EXISTS dwhd_gold.ALY_PRICE_DET1 CASCADE";
	static final String CREATE_STMT = "CREATE TABLE dwhd_gold.ALY_PRICE_DET1 (fld1 CHAR(20), fld2 INT, fld3 FLOAT)";   // REAL
	static final String CLEAN_STMT = "DELETE FROM dwhd_gold.ALY_PRICE_DET1";
	static final String INSERT_STMT = "INSERT INTO dwhd_gold.ALY_PRICE_DET1 (fld1, fld2, fld3) VALUES(?,?,?)";
		
//	static Logger logger = MyLogger.createMyLoggerDefPath("VUtils", Level.DEBUG);
	
	public static void main(String[] args) throws StreamChannelExeption, SQLException
	{
		VUtils vUtil = new VUtils(3);
		vUtil.prepare();
		
//		test.dropMyTable();
//		test.createMyTable();
//		test.cleanMyTable();
		vUtil.insertTestVals();
		
		vUtil.close();
	}

//	static public VUtils create(int batchSize)
//	{
//		return new VUtils(batchSize);
//	}
	
	public VUtils()
	{
		super(3);
	}
	
	public VUtils(int batchSize)
	{
		super(batchSize);
	}
	
	public void close() throws SQLException
	{
		if(m_conn != null)
			m_conn.close();
		
		m_conn = null;	
	}

	private Connection m_conn;
	private Statement m_stmt;
	private PreparedStatement m_pstmt;
	private int batchCount;
//	private int batchSize = 3;  
	private long maxFleshWaitTimeSec = 10;  
	private long lastFlash = 0;  
	
	public void prepare() throws StreamChannelExeption
	{
		logger.debug("VUtils.prepare() on entry");
		m_conn = getConnection();
		try
		{
			m_stmt = m_conn.createStatement();
			m_conn.setAutoCommit(false);   //?
		}
		catch (SQLException e)
		{
			String errMsg = ERR_MSG_PREF + e;
			logger.error(errMsg);
			throw new StreamChannelExeption(errMsg);
		}
		logger.debug("VUtils.prepare() on exit, seems OK");
	}
	
	public void dropMyTable() throws SQLException
	{
		m_stmt.execute(DROP_STMT);
		m_conn.commit();
		logger.debug("VUtils.: table dropped");
	}
	
	public void createMyTable() throws SQLException
	{
		m_stmt.execute(CREATE_STMT);
		m_conn.commit();
		logger.debug("VUtils.table created");
	}
	
	public void insertTestVals() throws SQLException, StreamChannelExeption
	{
//		addRecord("Aaaa1", 1, 1.1);
//		addRecord("Aaaa2", 2, 2.2);
//		addRecord("Aaaa3", 3, 3.3);
//		addRecord("Aaaa4", 4, 4.4);
//		addRecord("Aaaa5", 5, 5.5);
//		addRecord("Aaaa6", 6, 6.6);
//		addRecord("Aaaa7", 7, 7.7);
		addRecord("Homer_Sim", 10, 0.01);
		flash();
	}
	
	public void cleanMyTable() throws SQLException
	{
		m_stmt.execute(CLEAN_STMT);
		m_conn.commit();
		logger.debug("table cleaned");
	}
	
	public Connection getConnection() throws StreamChannelExeption
	{
		try
		{
			Class.forName("com.vertica.jdbc.Driver");
		}
		catch (ClassNotFoundException e)
		{
			String sErrMsg = ERR_MSG_PREF + e;
			logger.error(sErrMsg);
//			e.printStackTrace();
			throw new StreamChannelExeption(sErrMsg);
		}
		
		Properties myProp = new Properties();
		myProp.put("user", "ayakubo");
		myProp.put("password", "ayakubo15");
		try
		{
			m_conn = DriverManager.getConnection(ConfPlay.VERTICA_CONN_STR, myProp);
			// Set AutoCommit to false to allow Vertica to reuse the same
			// COPY statement
			m_conn.setAutoCommit(false);
		}
		catch (Exception e)
		{
			logger.error(e);
			throw new StreamChannelExeption(e);
		}
		return m_conn;
	}

	// to the table VTBL_PRICES_DET_1
	public void addRecord(String v1, int v2, double v3) throws StreamChannelExeption
	{
		logger.debug("addRecord() on entry");
		
		try
		{
			if(m_pstmt == null)
				m_pstmt = m_conn.prepareStatement(INSERT_STMT);
			
			m_pstmt.setString(1, v1);
			m_pstmt.setInt(2, v2);
			m_pstmt.setDouble(3, v3);
			m_pstmt.addBatch();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		batchCount++;
		logger.debug("addding to batch: batchCount: " + batchCount);
		
		if(batchCount >= batchSize)
		{
			try
			{
				m_pstmt.executeBatch();
				m_conn.commit();
				lastFlash = new Date().getTime();
				logger.debug("EXECUTING BATCH INSERT");
			}
			catch(SQLException sqlEx)
			{
				logger.error("VUtils.addRecord():  " + sqlEx.getMessage());
                try
				{
					m_conn.close();
				}
				catch (SQLException e)
				{ ; }	
                m_conn = null;
                throw new StreamChannelExeption("VUtils.addRecord():  " + sqlEx.getMessage());
            }
			batchCount = 0;
		}
//		logger.debug("addRecord() on exit");
	}
	
	public void addRecord(List<Object> values) throws StreamChannelExeption
	{
		String f1Val = (String) values.get(0);
		Integer f2Val = (Integer) values.get(1);
		Double f3Val = (Double) values.get(2);
		addRecord(f1Val, f2Val, f3Val);
	}

	
	public void flash() throws SQLException, StreamChannelExeption
	{
		if(batchCount > 0)
		{
			try
			{
				logger.debug("flash(): FLASHING");
				m_pstmt.executeBatch();
				m_conn.commit();
				lastFlash = new Date().getTime();
			}
			catch(SQLException sqlEx)
			{
				logger.error("VUtils.addRecord():  " + sqlEx.getMessage());
                m_conn.close();	
                m_conn = null;
                throw new StreamChannelExeption("VUtils.addRecord():  " + sqlEx.getMessage());
            }
			finally
			{
				batchCount = 0;
			}
		}
	}
}
