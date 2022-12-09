package xyz.kovacs.aoc2022.day01;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import xyz.kovacs.util.TimeAspect.TrackTime;

import static xyz.kovacs.util.AocUtils.getLogger;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static xyz.kovacs.util.AocUtils.getAllLines;

public class Day01 {
	
	public static void main(String[] args) {
		Day01 day01 = new Day01();
		day01.doPuzzle("sample", 1);
		day01.doPuzzle("input", 1);
		day01.doPuzzle("sample", 3);
		day01.doPuzzle("input", 3);
	}

	private final Logger log = getLogger(u -> u);

	@TrackTime
	public void doPuzzle(String inputFile, int top) {
		List<String> allLines = getAllLines(() -> inputFile);

		Set<Long> sums = new TreeSet<>();
		long currentSum = 0;
		for (String line : allLines) {
			if (StringUtils.isBlank(line)) {
				log.debug("sum ready {}", currentSum);
				sums.add(currentSum);
				currentSum = 0;
			} else {
				currentSum += Long.valueOf(line);
			}
		}

		Deque<Long> sumsDeque = new LinkedList<>(sums);
		log.debug("all sums sorted are {}", sumsDeque);
		long sumsum = 0;
		for (int i = 0; i < top; ++i) {
			sumsum += sumsDeque.removeLast();
		}
		log.info("solution for {} (top {}) is {}", inputFile, top, sumsum);
	}
}
