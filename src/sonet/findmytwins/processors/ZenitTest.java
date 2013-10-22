package sonet.findmytwins.processors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ContentHandler;
import java.net.ContentHandlerFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.CharBuffer;

public class ZenitTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		sendToBank();
		//copyFile();
		
	}
	
	private static URL SERVICE_URL;
	private static URL SERVICE_URL_CALC;
	private static URL SERVICE_URL_NEWS;
	private static URL SERVICE_URL_TEST;
	static {
		try {
			SERVICE_URL = new URL("https://q3.zenit.ru/wzr_forms/");
			SERVICE_URL_CALC = new URL("https://q3.zenit.ru/wzr_calc/");
			SERVICE_URL_TEST = new URL("http://localhost:8180/wzrdepo/");
		//	SERVICE_URL = new URL("https://ib.tcsbank.ru/Logon.aspx");
				SERVICE_URL_NEWS = new URL("https://q3.zenit.ru/wzr_depo/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	
	public static String sendToBank() {
		String result = "";
		BufferedReader rd = null;
		OutputStreamWriter wr = null;
		System.out.println("connect");
		try {
			// Construct data
			// String data = URLEncoder.encode("key1", "UTF-8") + "=" +
			// URLEncoder.encode("value1", "UTF-8");
//			String data = URLEncoder.encode(answersXml.toString(), "UTF8");

//			String dataEmitents="<?xml version=\"1.0\" encoding=\"utf-8\" ?>" +
//					"<request>" +
//					"<search>а</search>" +
//					"</request>";

//			String dataEmitents="<?xml version=\"1.0\" encoding=\"utf-8\" ?>" +
//			"<request>" +
//			"<issuer>327</issuer>" +
//			"</request>";

//			String dataEmitents="<?xml version=\"1.0\" encoding=\"utf-8\" ?>" +
//			"<request>" +
//			"<secuno>BON-ZENT-1</secuno>" +
//			"</request>";

			String dataEmitents="<?xml version=\"1.0\" encoding=\"utf-8\" ?>" +
			"<request>" +
			"<command>getAList</command>" +
			"</request>";

			String dataNews="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
			"<request type='getNews'>" +
			"<news>search</news>" +
			"<what>годового</what>" +
			"<skip>0</skip>" +
			"</request>";

//			String dataNews="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
//			"<request type='getNews'>" +
//			"<news>article</news>" +
//			"<id>3260199</id>" +
//			"<skip>0</skip>" +
//			"</request>";

//			String dataNews="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
//			"<request type='getNews'>" +
//			"<news>list</news>" +
//			"<from>2011-01-29</from>" +
//			"<till>2011-04-04</till>" +
//			"<skip>0</skip>" +
//			"</request>";

			String dataCalc2=" <?xml version=\"1.0\" encoding=\"utf-8\" ?> <request>" +
					"<calculatorId>depositCalculator</calculatorId>" +
					"<currency>usd</currency>" +
					"<fullSumm>10000</fullSumm>" +
					"<percentType>atExpense</percentType>" +
					"<percentPeriod></percentPeriod>" +
					"<prolongation>true</prolongation>" +
					"<additional>true</additional>" +
					"<partition></partition>" +
					"<thirdParty></thirdParty>" +
					"<duration>100</duration>" +
					"<earlyTermination></earlyTermination>" +
					"</request>";
			String dataCalc=" <?xml version=\"1.0\" encoding=\"utf-8\" ?> <request>" +
			"<calculatorId>depositCalculator</calculatorId>" +
			"<currency>rur</currency>" +
			"<duration>199</duration>" +
			"<fullSumm>10000</fullSumm>" +
			"<percentType>atExpense</percentType>" +
			"<percentPeriod></percentPeriod>" +
			"<prolongation></prolongation>" +
			"<additional></additional>" +
			"<partition></partition>" +
			"<thirdParty></thirdParty>" +
			"<earlyTermination></earlyTermination>" +
			"</request>";
	String data=" <?xml version=\"1.0\" encoding=\"utf-8\" ?> <formPostPequest>" +
	"<ftype>onlineCredit</ftype><ffield><pname>city</pname><pvalue>newq</pvalue></ffield>" +
	"<ffield><pname>currency</pname><pvalue>rur</pvalue></ffield>" +
	"<ffield><pname>sum</pname><pvalue>1000</pvalue></ffield>" +
	"<ffield><pname>mounth</pname><pvalue>36</pvalue></ffield>" +
	"<ffield><pname>creditPurpose</pname><pvalue>test</pvalue></ffield>" +
	"<ffield><pname>fixedAssets</pname><pvalue>auto</pvalue></ffield>" +
	"<ffield><pname>currentAssets</pname><pvalue>100</pvalue></ffield>" +
	"<ffield><pname>receivables</pname><pvalue>1000</pvalue></ffield>" +
	"<ffield><pname>equity</pname><pvalue>1000</pvalue></ffield>" +
	"<ffield><pname>payables</pname><pvalue>0</pvalue></ffield>" +
	"<ffield><pname>shortTermDebt</pname><pvalue>0</pvalue></ffield>" +
	"<ffield><pname>longTermDebt</pname><pvalue>0</pvalue></ffield>" +
	"<ffield><pname>turnoverPerMonth</pname><pvalue>1000</pvalue></ffield>" +
	"<ffield><pname>profitPerMonth</pname><pvalue>100</pvalue></ffield>" +
	"<ffield><pname>typeOfActivity</pname><pvalue>vpk</pvalue></ffield>" +
	"<ffield><pname>explanationOfActivity</pname><pvalue>test</pvalue></ffield>" +
	"<ffield><pname>fullOrgName</pname><pvalue>test</pvalue></ffield>" +
	"<ffield><pname>postAddress</pname><pvalue>test</pvalue></ffield>" +
	"<ffield><pname>contactPerson</pname><pvalue>test</pvalue></ffield>" +
	"<ffield><pname>phoneNumber</pname><pvalue>(100)1000000</pvalue></ffield>" +
	"<ffield><pname>email</pname><pvalue>test@test.test</pvalue></ffield>" +
	"<ffield><pname>siteUrl</pname><pvalue>test</pvalue></ffield>" +
	"<ffield><pname>contactPersonPost</pname><pvalue>test</pvalue></ffield></formPostPequest>" ;
	// Send data
//			URLConnection conn = SERVICE_URL.openConnection();
		//	URLConnection conn = SERVICE_URL_CALC.openConnection();
			URLConnection conn = SERVICE_URL_NEWS.openConnection();
		//	URLConnection conn = SERVICE_URL_TEST.openConnection();
			conn.setDoOutput(true);
			//conn.setDoInput(true);
			conn.setRequestProperty("CONTENT-TYPE", "text/xml");
					//	wr = new OutputStreamWriter(conn.getOutputStream(),"utf-8");
			OutputStream os = conn.getOutputStream();
			//os.write(dataNews.getBytes("utf-8"));
			//wr.write(data);
			//wr.write(dataCalc2)
			//wr.write(dataNews);
			os.write(dataEmitents.getBytes("utf-8"));
		//	wr.flush();
			os.flush();
			os.close();
			// Get the response
			rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			StringBuffer bf = new StringBuffer();
			;
			System.out.println(rd.ready());
			while ((line = rd.readLine()) != null) {
				bf.append(line);
		//		System.out.println("Server line:"+line);
			}
			result = bf.toString();
			System.out.println("Server answer:"+result);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (wr != null)
					wr.close();
				if (rd != null)
					rd.close();
			} catch (IOException e) {
			}

		}
		return result;
	}
	
	private static void copyFile() {

		File tmpFile = new File( "c://test.tmp");
		File dstFile = new File("c://test.txt");
		InputStreamReader in = null;
		OutputStreamWriter out = null;
		FileInputStream fis;
		FileOutputStream fos;
		try {
			fis = new FileInputStream(tmpFile);
			fos = new FileOutputStream(dstFile);
			String ENCODING_SRC="utf-8";
			if (ENCODING_SRC != null) {
				in = new InputStreamReader(fis, ENCODING_SRC);
			} else {
				in = new InputStreamReader(fis);
			}

			String ENCODING_DEST="cp1251";
			if (ENCODING_DEST != null) {
				out = new OutputStreamWriter(fos, ENCODING_DEST);
			} else {
				out = new OutputStreamWriter(fos);
			}
			// CharBuffer target=new CharBuffer();
			while (in.ready()) {
				out.write(in.read());
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
