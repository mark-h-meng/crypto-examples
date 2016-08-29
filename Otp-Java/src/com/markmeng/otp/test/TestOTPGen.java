/**
 * Copyright 2012 Kamran Zafar 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 * 
 */

package com.markmeng.otp.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.markmeng.otp.OTP;

@RunWith(JUnit4.class)
public class TestOTPGen {
	@Test
	public void testTOTP() {
		String key = "12345678";

		for (int i = 0; i < 10; i++) {
			System.out.println(OTP.generate("" + key, "" + System.currentTimeMillis(), 6, "totp"));
		}
	}

	@Test
	public void testHOTP() {
		String secret = "helloworld";

		try {
			for (int i = 0; i < 10; i++) {
				System.out.println(OTP.generate(secret, "" + 2, 6, "hotp"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
