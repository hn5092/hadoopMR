package com.x.math.dichotomy;

import java.util.Scanner;

public class 会员分组 {
	public static void main(String[] args) {
		while (true) {
			Scanner scanner = new Scanner(System.in);
			String input = scanner.next();
			String ind = input.substring(0, 1);
			int index = Integer.parseInt(ind);
			int len = input.length();
			// vip
			int vip = 0;
			int baseVip = 0;
			int score = Integer.parseInt(input);
			if (len < 3) {

			}
			if (len > 8) {
				vip = 19;
			}else if (score % 10 == 0 && len > 3) {
				vip = (len - 4) * 3 + 7;
			}else if (len > 3) {
				baseVip = (len - 4)*3 + 7;
				vip = index < 2 ? 1 : (index < 5 ? 2 : 3);
				vip = baseVip + vip;
			}
			System.out.println(vip);
		}
	}
}
