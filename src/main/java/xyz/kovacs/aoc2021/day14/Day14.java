package xyz.kovacs.aoc2021.day14;

import org.apache.logging.log4j.Level;

import java.util.Arrays;
import java.util.List;

import static xyz.kovacs.util.AocUtils.*;

public class Day14 {
	
	public static void main(String[] args) {
		doPuzzle("sample", 10); // 1588
		doPuzzle("input", 10); // ?
//		doPuzzle("sample", 40); // 2188189693529
//		doPuzzle("input", 40); // ?
	}
	
	/**
	 *
	 */
	public static void doPuzzle(String inputFile, int steps) {
		List<String> input = getAllLines(() -> inputFile);
		
		long[][] polymer = new long['Z' - 'A' + 1]['Z' - 'A' + 1];
		
		String firstLine = input.remove(0);
		input.remove(0);
		
		int firstChar = firstLine.charAt(0) - 'A';
		int lastChar = firstLine.charAt(firstLine.length() - 1) - 'A';
		for (int i = 0; i < firstLine.length() - 1; ++i) {
			polymer[firstLine.charAt(i) - 'A'][firstLine.charAt(i + 1) - 'A'] = 1;
		}
		
		getLogger(u -> u).debug("Initial digrams are:");
		getLogger(u -> u).debug("[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z]");
		logMap(polymer, getLogger(u -> u), Level.DEBUG);
		for (int step = 1; step <= steps; ++step) {
			long[][] polymerʹ = new long['Z' - 'A' + 1]['Z' - 'A' + 1];
			
			for (String rule : input) {
				String[] split = rule.split(" -> ");
				int from1 = split[0].charAt(0) - 'A';
				int from2 = split[0].charAt(1) - 'A';
				int via = split[1].charAt(0) - 'A';
				
				long amount = polymer[from1][from2];
				polymer[from1][from2] = 0;
				polymerʹ[from1][via] += amount;
				polymerʹ[via][from2] += amount;
			}
			
			polymer = polymerʹ;
			getLogger(u -> u).debug("Digrams after {} steps", step);
			logMap(polymer, getLogger(u -> u), Level.DEBUG);
			
			long max = Long.MIN_VALUE;
			long min = Long.MAX_VALUE;
			for (int i = 'A' - 'A'; i <= 'Z' - 'A'; ++i) {
				long count = Arrays.stream(polymer[i]).sum() + (i == lastChar || i == firstChar ? 1L : 0L);
				if (count == 0) {
					continue;
				}
				max = Math.max(max, count);
				min = Math.min(min, count);
			}
			
			getLogger(u -> u).info("After {} steps: Max - Min = {}", step, max - min);
		}
	}
	
}