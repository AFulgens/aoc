package xyz.kovacs.aoc2022.day04;

import org.apache.logging.log4j.Logger;

import xyz.kovacs.util.TimeAspect.TrackTime;

import static xyz.kovacs.util.AocUtils.getLogger;

import java.util.List;

import static xyz.kovacs.util.AocUtils.getAllLines;

public class Day04 {
	
	public static void main(String[] args) {
		Day04 day = new Day04();
		day.doPuzzle("sample");
		day.doPuzzle("input");
	}

	private final Logger log = getLogger(u -> u);

	public static final boolean theyContainEachOther(int a, int b, int c, int d) {
		return (  (a == c && b == d)
		       || (a <= c && b >= d)
		       || (a >= c && b <= d));
	}

	public static final boolean theyOverlap(int a, int b, int c, int d) {
		return (  (a <= c && b >= c)
		       || (a >= c && a <= d));
	}

	@TrackTime
	public void doPuzzle(String inputFile) {
		List<String> allLines = getAllLines(() -> inputFile);

		int contained = 0;
		int overlap = 0;

		for (String line : allLines) {
			int a = Integer.valueOf(line.split(",")[0].split("-")[0]);
			int b = Integer.valueOf(line.split(",")[0].split("-")[1]);
			int c = Integer.valueOf(line.split(",")[1].split("-")[0]);
			int d = Integer.valueOf(line.split(",")[1].split("-")[1]);

			if (theyContainEachOther(a, b, c, d)) {
				++contained;
				++overlap;
			} else if (theyOverlap(a, b, c, d)) {
				++overlap;
			}
		}

		log.info("contained {}", contained);
		log.info("overlap {}", overlap);
	}
}
