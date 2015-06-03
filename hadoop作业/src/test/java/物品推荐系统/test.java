package 物品推荐系统;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class test {
	public static void main(String[] args) throws ParseException {
		String time = "20140116000000";
		String[] i = "3946|20140114|01|tmall|TBSETTLE20140114551001.0051.CMCC.20140116040015|TBSETTLE20140114551001.0051.CMCC|/opt/mcb/upaysys/data/20140116/incoming/file_interface/0051/tbpay/TBSETTLE20140114551001.0051.CMCC.20140116040015|0051|1|00|20140116|20140116040015|天猫缴费对账文件| |20140116040015|||".split("\\|");
		System.out.println(i.length);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Long a=null;
		 a=sdf.parse(time).getTime();
		System.out.println(a);

	}
}
