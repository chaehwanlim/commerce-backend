package chlim.commercebackend.util;

import java.security.SecureRandom;
import java.util.Random;

public final class RandomGenerator {

	private static final Random random = new SecureRandom();

	public static String generateNumber(int length) {
		int maxCode = Double.valueOf(Math.pow(10, length)).intValue() - 1;
		String codeFormat = "%0" + length + "d";

		return String.format(codeFormat, random.nextInt(maxCode));
	}
}
