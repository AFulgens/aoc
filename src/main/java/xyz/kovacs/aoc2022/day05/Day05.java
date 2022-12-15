package xyz.kovacs.aoc2022.day05;

import org.apache.logging.log4j.Logger;

import xyz.kovacs.util.TimeAspect.TrackTime;

import static xyz.kovacs.util.AocUtils.getLogger;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static xyz.kovacs.util.AocUtils.getAllLines;

public class Day05 {
	
	public static void main(String[] args) {
		Day05 day = new Day05();
		day.doPuzzle("sample", false);
		day.doPuzzle("input", false);
		day.doPuzzle("sample", true);
		day.doPuzzle("input", true);
	}

	private final Logger log = getLogger(u -> u);

	@TrackTime
	public void doPuzzle(String inputFile, boolean moveBundle) {
		List<String> allLines = getAllLines(() -> inputFile);

		// getting the stacks
		int lineLength = allLines.get(0).length();
		List<Deque<Character>> stacks = new ArrayList<>((lineLength + 1) / 4);
		IntStream.range(0, (lineLength + 1) / 4).forEach(i -> stacks.add(new LinkedList<>()));

		int currentLine = 0;
		for (; currentLine < allLines.size(); ++currentLine) {
			String line;
			if (!(line = allLines.get(currentLine)).contains("[")) {
				currentLine += 2;
				break;
			}

			for (int column = 1; column < lineLength; column += 4) {
				char currentChar = ' ';
				if ((currentChar = line.charAt(column)) != ' ') {
					stacks.get(column / 4).addFirst(currentChar);
				}
			}
		}
		log.debug("Stacks are:");
		if (log.isDebugEnabled()) {
			stacks.stream().forEach(log::debug);
		}

		// moving stacks around
		for (; currentLine < allLines.size(); ++currentLine) {
			String line = allLines.get(currentLine);
			log.debug(line);
			String[] parameters = line.split(" ");
			int thisMany = Integer.valueOf(parameters[1]);
			int from = Integer.valueOf(parameters[3]) - 1;
			int to = Integer.valueOf(parameters[5]) - 1;

			// no, I don't want to move bundles, so we will just LIFO it twice
			Deque<Character> fuque = new LinkedList<>();
			for (int i = 0; i < thisMany; ++i) {
				fuque.addLast(stacks.get(from).removeLast());
			}
			while (!fuque.isEmpty()) {
				stacks.get(to).addLast(moveBundle ? fuque.removeLast() : fuque.removeFirst());
			}

			log.debug("Stacks are:");
			if (log.isDebugEnabled()) {
				stacks.stream().forEach(log::debug);
			}
		}
		
		// gotcha
		log.info(stacks.stream().map(Deque::getLast).map(Object::toString).collect(Collectors.joining()));
	}
}
