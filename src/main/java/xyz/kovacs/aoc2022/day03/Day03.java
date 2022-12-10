package xyz.kovacs.aoc2022.day03;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.codehaus.plexus.util.CollectionUtils;

import xyz.kovacs.util.TimeAspect.TrackTime;

import static xyz.kovacs.util.AocUtils.getLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

import static xyz.kovacs.util.AocUtils.getAllLines;
import static java.util.stream.Collectors.toList;

public class Day03 {
	
	public static void main(String[] args) {
		Day03 day = new Day03();
		day.doPuzzle1("sample");
		day.doPuzzle1("input");
		day.doPuzzle2("sample");
		day.doPuzzle2("input");
	}

	private final Logger log = getLogger(u -> u);

	private static int getPriority(Integer of) {
		return of > 94 ? of - 96 : of - 38;
	}

	private static List<Integer> toIntList(String string) {
		return string.chars().mapToObj(Integer::valueOf).collect(toList());
	}

	@TrackTime
	public void doPuzzle1(String inputFile) {
		log.info(getAllLines(() -> inputFile)
				.stream()
				.map(line -> new SimpleEntry<>(StringUtils.substring(line, 0, line.length() / 2),
				                               StringUtils.substring(line, line.length() / 2)))
				.peek(log::debug)
				.map(entry -> new SimpleEntry<>(toIntList(entry.getKey()),
				                                toIntList(entry.getValue())))
				.map(entry -> CollectionUtils.intersection(entry.getKey(), entry.getValue()))
				.map(item -> item.toArray(new Integer[0])[0])
				.peek(item -> log.debug("{}", (char)(0 + item)))
				.mapToInt(Day03::getPriority)
				.peek(log::debug)
				.sum()
		);
	}

	@TrackTime
	public void doPuzzle2(String inputFile) {
		List<String> lines = getAllLines(() -> inputFile);
		List<Integer> priorities = new ArrayList<>(lines.size() / 3 + 1);

		for (int i = 0; i < lines.size(); i += 3) {
			List<Integer> one = toIntList(lines.get(i));
			List<Integer> two = toIntList(lines.get(i + 1));
			List<Integer> tre = toIntList(lines.get(i + 2));
			priorities.add(CollectionUtils.intersection(CollectionUtils.intersection(one, two), tre).toArray(new Integer[0])[0]);
		}

		log.info(priorities.stream().mapToInt(Day03::getPriority).sum());
	}
}
