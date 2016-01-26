package cakeifm.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Methods {
	
	////////////////////////////////
		
	// constants
		
	////////////////////////////////
	static String fname_Log = "log.D-20.txt";
	
	static String fpath_Log = "/Users/mac/Desktop/works/WS/WS_Eclipse/JavaFor_CakeIFM11/data/log";
	

	////////////////////////////////

	// methods

	////////////////////////////////
	public static boolean 
//	public static void 
	change_FileName_2_TimeLabel(File f) {
		
		String fname_Old = f.getName();
		
//		File f_2 = new File(dpath, fname_1);

		long lastModified = f.lastModified();
//		long lastModified = new File(dpath, fname_1).lastModified();
		
		String fname_Trunk = 
					Methods.conv_MillSec_to_TimeLabel(lastModified, "file name");
		
//		//debug
//		// report
//		String msg;
//		msg = String.format(Locale.JAPAN, 
//				"[%s : %d] f.getPath() => %s",
//				Thread.currentThread().getStackTrace()[1].getFileName(),
//				Thread.currentThread().getStackTrace()[1].getLineNumber()
//				, f.getPath()
//				);
//		
//		System.out.println(msg);

		
		File f_2 = new File(f.getParentFile().getAbsolutePath(), 
//				File f_2 = new File(f.getPath(), 
//				File f_2 = new File(f.getParentFile(), 
//				File f_2 = new File(dpath,
				fname_Trunk
//						Methods.conv_MillSec_to_TimeLabel(lastModified, "file name")
						+ ".jpg");
		
		// report
//		String msg;
//		msg = String.format(Locale.JAPAN, 
//				"[%s : %d] file old = %s / new = %s", 
//				Thread
//				.currentThread().getStackTrace()[1].getFileName(), Thread
//				.currentThread().getStackTrace()[1].getLineNumber(), 
//				fname_Old, f_2.getName()
//				);
////				msg = String.format(
////				Locale.JAPAN,
////				"file old = %s / new = %s", fname_Old, f_2.getName()
////				);
//		System.out.println(msg);

		////////////////////////////////////////////////////////////////

		// new file name => exists in db?

		////////////////////////////////////////////////////////////////
		int val = Methods.change_FileName_2_TimeLabel__ExistsInDB(fname_Trunk);

		////////////////////////////////

		// if exists => rename accordingly

		////////////////////////////////
		if (val > 0) {
			
			fname_Trunk = Methods.update_FileName_Trunk(fname_Trunk, val);
			
			f_2 = new File(f.getParentFile().getAbsolutePath(), 
//					f_2 = new File(f.getPath(), 
//					File f_2 = new File(dpath,
				fname_Trunk
//							Methods.conv_MillSec_to_TimeLabel(lastModified, "file name")
						+ ".jpg");
			
//			// report
////			String msg;
//			msg = String.format(Locale.JAPAN, 
//					"[%s : %d] f_2 => updated: %s",
//					Thread.currentThread().getStackTrace()[1].getFileName(),
//					Thread.currentThread().getStackTrace()[1].getLineNumber()
//					, f_2.getName()
//					);
//			
//			System.out.println(msg);

			
////			String msg;
//			msg = String.format(
//					Locale.JAPAN,
//					"f_2 => updated: %s", f_2.getName()
//					);
//			
//			System.out.println(msg);
			
		}//if (val > 0)
		
		////////////////////////////////////////////////////////////////

		// new file name => exists in directory ?

		////////////////////////////////////////////////////////////////
		String tmp_s = f_2.getName();
		
//		// report
//		String msg;
//		msg = String.format(Locale.JAPAN, 
//				"[%s : %d] calling get_FileName_No_Duplicates__V2 "
//				+ "(f_2.getParent() = %s || f_2.name = %s)",
//				Thread.currentThread().getStackTrace()[1].getFileName(),
//				Thread.currentThread().getStackTrace()[1].getLineNumber()
//				, f_2.getParent(), f_2.getName()
////				, f_2.getPath(), f_2.getName()
//				);
//
//		System.out.println(msg);

		//		[Methods.java : 133] calling get_FileName_No_Duplicates__V2 (f_2.path = /Users/mac/Desktop/works/storage/images/from_iphone/tmp/IMG_0009.JPG/2016-01-11_16-17-06_000.jpg || f_2.name = 2016-01-11_16-17-06_000.jpg)
		
		String fname_Modified = 
				Methods.get_FileName_No_Duplicates__V2(
							CONS.Paths.dpath_ImageFiles__Renamed, 
//							f_2.getParent(), 
//							f_2.getPath(), 
							f_2.getName());

		// report
////		String msg;
//		msg = String.format(Locale.JAPAN, 
//				"[%s : %d] file old = %s / modified = %s",
//				Thread.currentThread().getStackTrace()[1].getFileName(),
//				Thread.currentThread().getStackTrace()[1].getLineNumber()
//				, tmp_s, fname_Modified
//				);
//		
//		System.out.println(msg);

////		String msg;
//		msg = String.format(
//				Locale.JAPAN,
//				"file old = %s / modified = %s", tmp_s, fname_Modified
//				);
//		System.out.println(msg);

		//report
		// report
		String msg;
		msg = String.format(Locale.JAPAN, 
				"[%s : %d] file original = %s / modified = %s",
				Thread.currentThread().getStackTrace()[1].getFileName(),
				Thread.currentThread().getStackTrace()[1].getLineNumber()
				, f.getName(), fname_Modified
				);
		
		System.out.println(msg);

		////////////////////////////////////////////////////////////////

		// exec => rename

		////////////////////////////////////////////////////////////////
		boolean res_b = Methods.change_FileName_2_TimeLabel__ChangeName(
					f.toPath(), 
					new File(
							CONS.Paths.dpath_ImageFiles__Renamed, 
//							CONS.Paths.dpath_ImageFiles__Tmp, 
							fname_Modified).toPath()
		);
		
		// report
//		String msg;
		msg = String.format(Locale.JAPAN, 
				"[%s : %d] rename => %s",
				Thread.currentThread().getStackTrace()[1].getFileName(),
				Thread.currentThread().getStackTrace()[1].getLineNumber()
				, res_b
				);
		
		System.out.println(msg);

		////////////////////////////////

		// return

		////////////////////////////////
		return res_b;
		
//		try {
//			
//			Files.move(
//					f.toPath(), 
//					new File(
//							CONS.Paths.dpath_ImageFiles, 
//							fname_Modified).toPath());
////			Files.move(elem.toPath(), new File(dpath, fname_Modified).toPath());
////			Files.move(f_1.toPath(), f_2.toPath());
//
//			msg = String.format(Locale.JAPAN, 
//					"[%s : %d] file => moved",
//					Thread.currentThread().getStackTrace()[1].getFileName(),
//					Thread.currentThread().getStackTrace()[1].getLineNumber()
//					
//					);
//			
//			System.out.println(msg);
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			msg = String.format(Locale.JAPAN, 
//					"[%s : %d] file => NOT moved",
//					Thread.currentThread().getStackTrace()[1].getFileName(),
//					Thread.currentThread().getStackTrace()[1].getLineNumber()
//					
//					);
//			
//			System.out.println(msg);
//
//			e.printStackTrace();
//		}

		
		
	}//change_FileName_2_TimeLabel(File f)
	
	public static boolean
	change_FileName_2_TimeLabel__ChangeName
	(Path fpath_Src, Path fpath_Dst) {
		
		String msg;
		
		try {
			
			Files.move(fpath_Src, fpath_Dst);
//			Files.move(elem.toPath(), new File(dpath, fname_Modified).toPath());
//			Files.move(f_1.toPath(), f_2.toPath());

			msg = String.format(Locale.JAPAN, 
					"[%s : %d] file => moved",
					Thread.currentThread().getStackTrace()[1].getFileName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber()
					
					);
			
			System.out.println(msg);

			return true;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			msg = String.format(Locale.JAPAN, 
					"[%s : %d] file => NOT moved",
					Thread.currentThread().getStackTrace()[1].getFileName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber()
					
					);
			
			System.out.println(msg);

			e.printStackTrace();
			
			return false;
			
		}
		
	}//change_FileName_2_TimeLabel__ChangeName
	
	public static int
	change_FileName_2_TimeLabel__ExistsInDB
	(String fname_Trunk) {
	
		String fname_Trunk_Core = Methods.extract_TimeLabel_NoMillSec(fname_Trunk);
		
		String q = String.format(
				Locale.JAPAN,
				"SELECT count(*) FROM ifm11 "
						+ "WHERE file_name LIKE '%s%%' "
//						+ "WHERE file_name LIKE '2016-01-11_19-32-08%' "
						+ "ORDER BY file_name DESC;", 
						fname_Trunk_Core
//						fname_Trunk
//				fname_Old, f_2.getName()
				);
		
//		//debug
//		String msg;
//		msg = String.format(Locale.JAPAN, 
//				"[%s : %d] fname_Trunk_Core => %s", 
//				Thread
//				.currentThread().getStackTrace()[1].getFileName(), Thread
//				.currentThread().getStackTrace()[1].getLineNumber(), 
//				fname_Trunk_Core
//				);
////		msg = String.format(
////				Locale.JAPAN,
////				"fname_Trunk_Core => %s", fname_Trunk_Core
//////				"fname_Trunk => %s", fname_Trunk
////				);
//		System.out.println(msg);
		
		
//		String q = "SELECT count(*) FROM ifm11 "
//				+ "WHERE file_name LIKE '2016-01-11_19-32-08%' "
////					+ "WHERE file_name LIKE '2016-01-11_19-32-01%' "
////					+ "WHERE file_name LIKE '2016-01-11_19-32-09%' "
////					+ "WHERE file_name LIKE '2016-01-12_24-32-02%' "
//				+ "ORDER BY file_name DESC;";

		String db_Path = "/Users/mac/Desktop/works/WS/Cake_IFM11/app/Lib/data/";
		
		String db_Name = "ifm11_backup_20160110_080900.bk";
//		String db_Name = "ifm11_backup_20160110_080900";
		
		// validate: exists
		if (! new File(db_Path, db_Name).exists()) {
			
			String msg;
			msg = String.format(Locale.JAPAN, 
					"[%s : %d] db file => NOT exists: %s", 
					Thread
					.currentThread().getStackTrace()[1].getFileName(), Thread
					.currentThread().getStackTrace()[1].getLineNumber(), 
					new File(db_Path, db_Name).getAbsolutePath()
					);
//			msg = String(
//					Locale.JAPAN,
//					"db file => NOT exists: %s", new File(db_Path, db_Name).getAbsolutePath()
//					);
//			System.out.println(msg);
			
			return -1;
			
//		} else {
//			
////			String msg;
//			msg = String.format(
//					Locale.JAPAN,
//					"db file => exists: %s", new File(db_Path, db_Name).getAbsolutePath()
//					);
//			System.out.println(msg);
			
		}
		
		String db_Directive = String.format(
				Locale.JAPAN,
				"jdbc:sqlite:%s/%s", 
						db_Path, db_Name
//				fname_Old, f_2.getName()
				);
///				+ "C:\\WORKS\\WS\\Eclipse_Luna\\Cake_IFM11\\app\\Lib\\data"
//				+ "\\ifm11_backup_20160110_080900.bk";
//		+ "C:\WORKS\WS\Eclipse_Luna\Cake_IFM11\app\Lib\data"

		int val = is_InDB_FileName(db_Directive, q);
//			int val = get_NumOf_Entries_InDB(stmt, q);
//			int val = rs.getInt("count(*)");
//			int val = rs.getInt(0);

//		if (val > 0) {
//			
//			String msg;
//				msg = String.format(Locale.JAPAN, 
//						"[%s : %d] is_InDB_FileName => %d", 
//						Thread
//						.currentThread().getStackTrace()[1].getFileName(), Thread
//						.currentThread().getStackTrace()[1].getLineNumber(), 
//						val
//						);
//	//				msg = String.format(
//	//				Locale.JAPAN,
//	//				"is_InDB_FileName => %d", val
//	//				);
//			System.out.println(msg);
//				
//		}
		
		////////////////////////////////

		// return

		////////////////////////////////
		return val;
		
	}//change_FileName_2_TimeLabel__ExistsInDB(String fname_Trunk)
	
	public static String
	update_FileName_Trunk(String fname_Trunk, int val) {
		
		String regex = "(\\d\\d\\d)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(fname_Trunk);
		
		int serial_num;
		
		if (matcher.find() == true) {
			
			// update serial number
			serial_num = Integer.parseInt(matcher.group(1));
		
			serial_num += val;
//			serial_num += 1;
			
			fname_Trunk = fname_Trunk.replaceAll(
						regex, 
						String.format("%03d", serial_num)
//						String.format("%03d.jpg", serial_num)
			);
			
		} 
			
		return fname_Trunk;

		
	}//update_FileName_Trunk(String fname_Trunk, int val)
	
	/*******************************
	 * format type<br>
	 * 		"time label"	yyyy/MM/dd kk:mm:ss.SSS
	 * 		"file name"		yyyy-MM-dd_hh-mm-ss_SSS
	 *******************************/
	public static String
	conv_MillSec_to_TimeLabel(long millSec, String formatType) {
		//REF http://stackoverflow.com/questions/7953725/how-to-convert-milliseconds-to-date-format-in-android answered Oct 31 '11 at 12:59
		String dateFormat = null;
		
		if (formatType.equals("time label")) {
			
			dateFormat = "yyyy/MM/dd kk:mm:ss.SSS";
			
		} else if (formatType.equals("file name")) {
				
			dateFormat = "yyyy-MM-dd_kk-mm-ss_SSS";
				
		} else {//if (formatType.equals("time label"))
			
			dateFormat = "yyyy/MM/dd kk:mm:ss.SSS";
			
		}//if (formatType.equals("time label"))
		
		
//		String dateFormat = "yyyy/MM/dd kk:mm:ss.SSS";
//		String dateFormat = "yyyy/MM/dd hh:mm:ss.SSS";
//		String dateFormat = CONS.Admin.format_Date;
//		String dateFormat = "yyyy/MM/dd hh:mm:ss.SSS";
		
		DateFormat formatter = new SimpleDateFormat(dateFormat, Locale.JAPAN);
//		DateFormat formatter = new SimpleDateFormat(dateFormat);

		// Create a calendar object that will convert the date and time value in milliseconds to date. 
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTimeInMillis(millSec);
		
		return formatter.format(calendar.getTime());
		
	}//conv_MillSec_to_TimeLabel(long millSec)

	public static int
	is_InDB_FileName(String db_Directive, String query) {
		
		///////////////////////////////////
		//
		// db
		//
		///////////////////////////////////
		//ref http://www.tutorialspoint.com/sqlite/sqlite_java.htm
		Connection c = null;
		
		Statement stmt = null;
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			//ref java.sql.DriverManager
			
			c = DriverManager.getConnection(db_Directive);
			
			c.setAutoCommit(false);
			
			//ref http://stackoverflow.com/questions/7886462/how-to-get-row-count-using-resultset-in-java answered Jun 26 '12 at 5:34
			stmt = c.createStatement( 
					ResultSet.TYPE_FORWARD_ONLY, 
//					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY );
			
			ResultSet rs = stmt.executeQuery( query );
			
			///////////////////////////////////
			//
			// get count
			//
			///////////////////////////////////
			int val = get_NumOf_Entries_InDB(stmt, query);
			
			rs.close();
			stmt.close();
			c.close();
			
			return val;
			
		} catch ( Exception e ) {
			
			String msg;
			String tmp_s = e.getClass().getName() + ": " + e.getMessage();
//			String msg;
			msg = String.format(Locale.JAPAN, "[%s : %d] Exception => %s", Thread
					.currentThread().getStackTrace()[1].getFileName(), Thread
					.currentThread().getStackTrace()[1].getLineNumber(), tmp_s);
			
			System.out.println(msg);
			
			return -1;
			
		}
		
	}//is_InDB_FileName

	/*******************************
	 * @return
	 * -1	=> query exception
	 *******************************/
	public static int
	get_NumOf_Entries_InDB(Statement s, String query) {
	
		ResultSet rs = null;
		
		try {
			
			rs = s.executeQuery( query );
			
			return rs.getInt("count(*)");
			
		} catch ( Exception e ) {
			
			String msg;
			String tmp_s = e.getClass().getName() + ": " + e.getMessage();
//			String msg;
			msg = String.format(Locale.JAPAN, "[%s : %d] Exception => %s", Thread
					.currentThread().getStackTrace()[1].getFileName(), Thread
					.currentThread().getStackTrace()[1].getLineNumber(), tmp_s);

			System.out.println(msg);
			
//			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//			System.exit(0);
			
			return -1;
			
		}
		
//		return rs.getInt("count(*)");
//		int val = rs.getInt("count(*)");
		
	}//get_NumOf_Entries_InDB

	/******************************
		convert time label<br>
		"2016-01-11_19-01-16_000" => "2016-01-11_19-01-16"
	******************************/
	public static String
	extract_TimeLabel_NoMillSec(String fname_Trunk) {
		
		String[] tokens = fname_Trunk.split("_");
		
		// validate: 1 token only
		if (tokens.length == 1) {
			
			return fname_Trunk;
			
		}
		
		// more than 1 token
		return tokens[0] + "_" + tokens[1];
	}//extract_TimeLabel_NoMillSec(String fname_Trunk)

	/*******************************
	 * @return
	 * null	=> file doesn't exist
	 *******************************/
	public static String 
//	public static void 
	get_FileName_No_Duplicates
	(String dpath, String fname) {
		
//		String dpath = "C:\\WORKS\\Storage\\images\\iphone\\tmp";
//		String dpath = "C:\WORKS\Storage\images\iphone\tmp";
		
//		String fname = "2016-01-11_16-17-05_000.jpg";
		
		File f = new File(dpath, fname);
		
		// get the list of all files in the directory
		String[] aryOf_FileNames = new File(dpath).list();
		
		if (f.exists()) {
			
//			String msg;
//			
//			//ref http://stackoverflow.com/questions/47045/sprintf-equivalent-in-java answered Sep 5 '08 at 23:06
//			msg = String.format(Locale.JAPAN, "[%s : %d] file exists => %s (total = %d files)", 
//					Thread.currentThread().getStackTrace()[1].getFileName(), Thread
//					.currentThread().getStackTrace()[1].getLineNumber(), 
//					f.getName(),
//					aryOf_FileNames.length);
//			
////			System.out.println(msg);
//
//			write_Log(msg, 
//					Thread.currentThread().getStackTrace()[1].getFileName(), Thread
//					.currentThread().getStackTrace()[1].getLineNumber());

			
		} else {//if (f.exists())
			
			String msg;
			msg = String.format(Locale.JAPAN, "[%s : %d] file => not exist: %s", Thread
					.currentThread().getStackTrace()[1].getFileName(), Thread
					.currentThread().getStackTrace()[1].getLineNumber(), f.getName());

//			System.out.println(msg);

			msg = String.format(Locale.JAPAN, "file => not exist: %s", f.getName());

			write_Log(msg, 
					Thread.currentThread().getStackTrace()[1].getFileName(), Thread
					.currentThread().getStackTrace()[1].getLineNumber());
			
			return null;
			
		}//if (f.exists())
		
		///////////////////////////////////
		//
		// change file name
		//
		///////////////////////////////////
		//ref http://stackoverflow.com/questions/1128723/how-can-i-test-if-an-array-contains-a-certain-value answered Jul 15 '09 at 0:04
		List<String> listOf_FileNames = Arrays.asList(aryOf_FileNames);
		
		boolean res_b;
		
		res_b = listOf_FileNames.contains(fname);
		
		String regex = "(\\d\\d\\d)\\.jpg";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(fname);
		
		int serial_num;
		
		while(res_b == true) {
			
			String msg;
			msg = String.format(Locale.JAPAN, "[%s : %d] file %s => in the dir --> %s", Thread
					.currentThread().getStackTrace()[1].getFileName(), Thread
					.currentThread().getStackTrace()[1].getLineNumber(), fname, res_b);
			
//			System.out.println(msg);
			
			if (matcher.find() == true) {
				
				// update serial number
				serial_num = Integer.parseInt(matcher.group(1));
				
				serial_num += 1;
				
				msg = String.format(Locale.JAPAN, "[%s : %d] serial num (after) => %d", 
						Thread.currentThread().getStackTrace()[1].getFileName(), Thread
						.currentThread().getStackTrace()[1].getLineNumber(), serial_num);
				
//				System.out.println(msg);

				msg = String.format(Locale.JAPAN, 
						"serial num (after) => %d",serial_num);

				write_Log(msg, 
						Thread.currentThread().getStackTrace()[1].getFileName(), Thread
						.currentThread().getStackTrace()[1].getLineNumber());

				fname = fname.replaceAll(regex, String.format("%03d.jpg", serial_num));
				
//				String msg;
				msg = String.format(Locale.JAPAN, "[%s : %d] fname replaced => '%s'", Thread
						.currentThread().getStackTrace()[1].getFileName(),
						Thread.currentThread().getStackTrace()[1]
								.getLineNumber(), fname);
				
//				System.out.println(msg);

				msg = String.format(Locale.JAPAN, "fname replaced => '%s'", fname);

				write_Log(msg, 
						Thread.currentThread().getStackTrace()[1].getFileName(), Thread
						.currentThread().getStackTrace()[1].getLineNumber());

			} else {//if (matcher.find() == true)
				
//				String msg;
				msg = String.format(Locale.JAPAN, "[%s : %d] no match", Thread
						.currentThread().getStackTrace()[1].getFileName(),
						Thread.currentThread().getStackTrace()[1]
								.getLineNumber());
				
//				System.out.println(msg);

				msg = String.format(Locale.JAPAN, "no match");

				write_Log(msg, 
						Thread.currentThread().getStackTrace()[1].getFileName(), Thread
						.currentThread().getStackTrace()[1].getLineNumber());
				
				return null;
				
			}//if (matcher.find() == true)
			
			// update
			res_b = listOf_FileNames.contains(fname);
			
			matcher = pattern.matcher(fname);
			
		}//while(res_b == true)
		
		String msg;
		msg = String.format(Locale.JAPAN, "[%s : %d] fname is now => '%s'", Thread
				.currentThread().getStackTrace()[1].getFileName(), Thread
				.currentThread().getStackTrace()[1].getLineNumber(), fname);
		
//		System.out.println(msg);
		
		///////////////////////////////////
		//
		// return
		//
		///////////////////////////////////
		return fname;
		
	}//get_FileName_No_Duplicates

	public static void 
	write_Log
	(String message, String fileName, int lineNumber) {
		
		////////////////////////////////

		// file

		////////////////////////////////
		String fname = fname_Log;
//		String fname = "log.D-20.txt";
		
		File f_Log = new File(fpath_Log, fname);
//		File fpath_Log = new File("C:\\WORKS\\WS\\Eclipse_Luna\\Cake_IFM11", fname);
		
		////////////////////////////////

		// file exists?

		////////////////////////////////
		if (!f_Log.exists()) {
			
			try {
				
				f_Log.createNewFile();
				
				String msg;
				msg = String.format(Locale.JAPAN, "[%s : %d] log file => created: %s", Thread
						.currentThread().getStackTrace()[1].getFileName(),
						Thread.currentThread().getStackTrace()[1]
								.getLineNumber(), f_Log.getAbsolutePath());

				System.out.println(msg);
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
				String msg = "Can't create a log file";
//				Methods_dlg.dlg_ShowMessage_Duration(actv, msg, R.color.gold2);
				msg = String.format(Locale.JAPAN, "[%s : %d] log file => created: %s", Thread
						.currentThread().getStackTrace()[1].getFileName(),
						Thread.currentThread().getStackTrace()[1]
								.getLineNumber(), f_Log.getAbsolutePath());

				System.out.println(msg);
				
				return;
				
			}
			
		} else {
			
//			String msg;
//			
//			msg = String.format(Locale.JAPAN, "[%s : %d] log file => created: %s", Thread
//					.currentThread().getStackTrace()[1].getFileName(),
//					Thread.currentThread().getStackTrace()[1]
//							.getLineNumber(), fpath_Log.getAbsolutePath());
//
//			System.out.println(msg);

		}

		////////////////////////////////

		// write

		////////////////////////////////
		try {

			String text = String.format(Locale.JAPAN,
					"[%s] [%s : %d] %s\n", 
					conv_MillSec_to_TimeLabel(
							getMillSeconds_now(), "time label"),
					fileName, lineNumber,
					message);

			//ref [append] http://alvinalexander.com/java/edu/qanda/pjqa00009.shtml
			BufferedWriter bw = new BufferedWriter(new FileWriter(f_Log, true));
			
			//ref http://www.mkyong.com/java/how-to-write-to-file-in-java-bufferedwriter-example/
			bw.write(text);
			
			bw.close();
			
//			//REF append http://stackoverflow.com/questions/8544771/how-to-write-data-with-fileoutputstream-without-losing-old-data answered Dec 17 '11 at 12:37
//			FileOutputStream fos = new FileOutputStream(fpath_Log, true);
////			FileOutputStream fos = new FileOutputStream(fpath_Log);
//			
//			String text = String.format(Locale.JAPAN,
//							"[%s] [%s : %d] %s\n", 
//							conv_MillSec_to_TimeLabel(
//									getMillSeconds_now(), "time label"),
//							fileName, lineNumber,
//							message
////							Methods.conv_MillSec_to_TimeLabel(
////									STD.getMillSeconds_now()),
////									fileName, lineNumber,
////									message
//						);
//			
//			//REF getBytes() http://www.adakoda.com/android/000240.html
//			fos.write(text);
////			fos.write(text.getBytes());
////			fos.write(message.getBytes());
//			
////			fos.write("\n".getBytes());
//			
//			fos.close();
			
//			// Log
//			String msg;
//			msg = String.format(Locale.JAPAN, "[%s : %d] log => written", Thread
//					.currentThread().getStackTrace()[1].getFileName(), Thread
//					.currentThread().getStackTrace()[1].getLineNumber());
//
//			System.out.println(msg);
			
			
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[1].getLineNumber()
//					+ "]", msg_Log);
//			FileChannel oChannel = new FileOutputStream(fpath_Log).getChannel();
//			
//			oChannel.wri
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}//write_Log

	public static long getMillSeconds_now() {
		
		Calendar cal = Calendar.getInstance();
		
		return cal.getTime().getTime();
		
	}//private long getMillSeconds_now(int year, int month, int date)

	/*******************************
	 * @return
	 * file doesn't exist	=> return the same file name<br> 
	 * file exists	=> "2016-01-12_13-34-03_001.jpg" --> "2016-01-12_13-34-03_002.jpg"<br> 
	 *******************************/
	public static String 
//	public static void 
	get_FileName_No_Duplicates__V2
	(String dpath, String fname) {
		
//		String dpath = "C:\\WORKS\\Storage\\images\\iphone\\tmp";
//		String dpath = "C:\WORKS\Storage\images\iphone\tmp";
		
//		String fname = "2016-01-11_16-17-05_000.jpg";
		
		File f = new File(dpath, fname);
		
		// get the list of all files in the directory
		String[] aryOf_FileNames = new File(dpath).list();
		
		if (f.exists()) {
			
//			String msg;
//			
//			//ref http://stackoverflow.com/questions/47045/sprintf-equivalent-in-java answered Sep 5 '08 at 23:06
//			msg = String.format(Locale.JAPAN, "[%s : %d] file exists => %s (total = %d files)", 
//					Thread.currentThread().getStackTrace()[1].getFileName(), Thread
//					.currentThread().getStackTrace()[1].getLineNumber(), 
//					f.getName(),
//					aryOf_FileNames.length);
//			
////			System.out.println(msg);
//
//			write_Log(msg, 
//					Thread.currentThread().getStackTrace()[1].getFileName(), Thread
//					.currentThread().getStackTrace()[1].getLineNumber());
			
			
		} else {//if (f.exists())
			
			String msg;
			msg = String.format(Locale.JAPAN, "[%s : %d] file => not exist: %s", Thread
					.currentThread().getStackTrace()[1].getFileName(), Thread
					.currentThread().getStackTrace()[1].getLineNumber(), f.getName());
			
//			System.out.println(msg);

			msg = String.format(Locale.JAPAN, "file => not exist: %s", f.getName());

			write_Log(msg, 
					Thread.currentThread().getStackTrace()[1].getFileName(), Thread
					.currentThread().getStackTrace()[1].getLineNumber());

			
			return fname;
//			return null;
			
		}//if (f.exists())
		
		///////////////////////////////////
		//
		// change file name
		//
		///////////////////////////////////
		// same name in the db?
		int serial_num = 0;
		
//		int numOf_SameFileName = 
//		
		//ref http://stackoverflow.com/questions/1128723/how-can-i-test-if-an-array-contains-a-certain-value answered Jul 15 '09 at 0:04
		List<String> listOf_FileNames = Arrays.asList(aryOf_FileNames);
		
		boolean res_b;
		
		res_b = listOf_FileNames.contains(fname);
		
		String regex = "(\\d\\d\\d)\\.jpg";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(fname);
		
//		int serial_num;
		
		while(res_b == true) {
			
			String msg;
			msg = String.format(Locale.JAPAN, "[%s : %d] file %s => in the dir --> %s", Thread
					.currentThread().getStackTrace()[1].getFileName(), Thread
					.currentThread().getStackTrace()[1].getLineNumber(), fname, res_b);
			
//			System.out.println(msg);
			
			if (matcher.find() == true) {
				
				// update serial number
				serial_num = Integer.parseInt(matcher.group(1));
				
				serial_num += 1;
				
				msg = String.format(Locale.JAPAN, "[%s : %d] serial num (after) => %d", 
						Thread.currentThread().getStackTrace()[1].getFileName(), Thread
						.currentThread().getStackTrace()[1].getLineNumber(), serial_num);
				
//				System.out.println(msg);
				
				msg = String.format(Locale.JAPAN, 
						"serial num (after) => %d",serial_num);
				
				write_Log(msg, 
						Thread.currentThread().getStackTrace()[1].getFileName(), Thread
						.currentThread().getStackTrace()[1].getLineNumber());
				
				fname = fname.replaceAll(regex, String.format("%03d.jpg", serial_num));
				
//				String msg;
				msg = String.format(Locale.JAPAN, "[%s : %d] fname replaced => '%s'", Thread
						.currentThread().getStackTrace()[1].getFileName(),
						Thread.currentThread().getStackTrace()[1]
								.getLineNumber(), fname);
				
//				System.out.println(msg);
				
				msg = String.format(Locale.JAPAN, "fname replaced => '%s'", fname);
				
				write_Log(msg, 
						Thread.currentThread().getStackTrace()[1].getFileName(), Thread
						.currentThread().getStackTrace()[1].getLineNumber());
				
			} else {//if (matcher.find() == true)
				
//				String msg;
				msg = String.format(Locale.JAPAN, "[%s : %d] no match", Thread
						.currentThread().getStackTrace()[1].getFileName(),
						Thread.currentThread().getStackTrace()[1]
								.getLineNumber());
				
//				System.out.println(msg);
				
				msg = String.format(Locale.JAPAN, "no match");
				
				write_Log(msg, 
						Thread.currentThread().getStackTrace()[1].getFileName(), Thread
						.currentThread().getStackTrace()[1].getLineNumber());
				
				return null;
				
			}//if (matcher.find() == true)
			
			// update
			res_b = listOf_FileNames.contains(fname);
			
			matcher = pattern.matcher(fname);
			
		}//while(res_b == true)
		
		String msg;
		msg = String.format(Locale.JAPAN, "[%s : %d] fname is now => '%s'", Thread
				.currentThread().getStackTrace()[1].getFileName(), Thread
				.currentThread().getStackTrace()[1].getLineNumber(), fname);
		
//		System.out.println(msg);
		
		///////////////////////////////////
		//
		// return
		//
		///////////////////////////////////
		return fname;
		
	}//get_FileName_No_Duplicates

	public static File[]
	get_AryOf_ImageFiles(String dpath) {
		
		File dir = new File(dpath);
		
		// validate: exists
		if (dir.exists()) {
			
			String msg;
			msg = String.format(
					Locale.JAPAN,
					"Dir exists => %s", dir.getAbsolutePath()
					);
			System.out.println(msg);
			
			
		} else {//if (dir.exists())
			
			String msg;
			msg = String.format(
					Locale.JAPAN,
					"Dir NOT exists => %s", dir.getAbsolutePath()
					);
			System.out.println(msg);
			
			return null;
			
		}//if (dir.exists())
		
		return dir.listFiles(new FileFilter(){
//			File[] aryOf_ImageFiles = dir.listFiles(new FileFilter(){
//			File[] list_Files = dpath.listFiles(new FileFilter(){
			
			@Override
			public boolean accept(File f) {
				
				return f.exists() 
						&& f.getName().startsWith("IMG")
						&& f.getName().endsWith("JPG");
//				return f.exists() && f.getName().startsWith("DSC");
//				return f.exists() && f.getPath().startsWith("DSC");
				
			}
			
		});

//		// report
//		String msg;
//		msg = String.format(
//				Locale.JAPAN,
//				"aryOf_ImageFiles => %d", aryOf_ImageFiles.length
//				);
//		System.out.println(msg);

		
	}//get_AryOf_ImageFiles(String dpath)
	
}
