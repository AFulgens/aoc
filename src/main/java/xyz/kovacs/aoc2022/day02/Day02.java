package xyz.kovacs.aoc2022.day02;

import org.apache.logging.log4j.Logger;

import xyz.kovacs.util.AocUtils;
import xyz.kovacs.util.TimeAspect.TrackTime;

import static xyz.kovacs.util.AocUtils.getLogger;

import java.util.function.BiFunction;

public class Day02 {
	public static void main(String[] args) {
		Day02 day = new Day02();
		day.doPuzzle("sample", puzzle1());
		day.doPuzzle("input", puzzle1());
		day.doPuzzle("sample", puzzle2());
		day.doPuzzle("input", puzzle2());
	}

	private final Logger log = getLogger(u -> u);

	public enum Enemy {
		ROCK("A"), PAPER("B"), SCISSORS("C");

		private String from;

		private Enemy(String from) {
			this.from = from;
		}

		public static Enemy getFrom(String from) {
			for (Enemy candidate : Enemy.values()) {
				if (candidate.from.equals(from)) {
					return candidate;
				}
			}
			throw new IllegalArgumentException("No idea what " + from + " is");
		}
	}

	public enum Me {
		ROCK("X", 1), PAPER("Y", 2), SCISSORS("Z", 3);

		private String from;
		private int value;

		private Me(String from, int value) {
			this.from = from;
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static Me getFrom(String from) {
			for (Me candidate : Me.values()) {
				if (candidate.from.equals(from)) {
					return candidate;
				}
			}
			throw new IllegalArgumentException("No idea what " + from + " is");
		}
	}

	public enum Outcome {
		WIN("Z", 6), DRAW("Y", 3), LOSE("X", 0);

		private String from;
		private int value;

		private Outcome(String from, int value) {
			this.from = from;
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static Outcome getFrom(String from) {
			for (Outcome candidate : Outcome.values()) {
				if (candidate.from.equals(from)) {
					return candidate;
				}
			}
			throw new IllegalArgumentException("No idea what " + from + " is");
		}
	}

	public static Outcome result(Me me, Enemy enemy) {
		return switch(me) {
			case ROCK -> {
				yield switch(enemy) {
					case ROCK -> Outcome.DRAW;
					case PAPER -> Outcome.LOSE;
					case SCISSORS -> Outcome.WIN;
					default -> throw new IllegalArgumentException("Go away");
				};
			}
			case PAPER -> {
				yield switch(enemy) {
					case ROCK -> Outcome.WIN;
					case PAPER -> Outcome.DRAW;
					case SCISSORS -> Outcome.LOSE;
					default -> throw new IllegalArgumentException("Go away");
				};
			}
			case SCISSORS -> {
				yield switch(enemy) {
					case ROCK -> Outcome.LOSE;
					case PAPER -> Outcome.WIN;
					case SCISSORS -> Outcome.DRAW;
					default -> throw new IllegalArgumentException("Go away");
				};
			}
			default -> throw new IllegalArgumentException("Go away");
		};
	}

	public static Me forOutcome(Enemy enemy, Outcome outcome) {
		return switch(enemy) {
			case ROCK -> {
				yield switch(outcome) {
					case LOSE -> Me.SCISSORS;
					case DRAW -> Me.ROCK;
					case WIN -> Me.PAPER;
					default -> throw new IllegalArgumentException("Go away");
				};
			}
			case PAPER -> {
				yield switch(outcome) {
					case LOSE -> Me.ROCK;
					case DRAW -> Me.PAPER;
					case WIN -> Me.SCISSORS;
					default -> throw new IllegalArgumentException("Go away");
				};
			}
			case SCISSORS -> {
				yield switch(outcome) {
					case LOSE -> Me.PAPER;
					case DRAW -> Me.SCISSORS;
					case WIN -> Me.ROCK;
					default -> throw new IllegalArgumentException("Go away");
				};
			}
			default -> throw new IllegalArgumentException("Go away");
		};
	}

	public static BiFunction<String, String, Integer> puzzle1() {
		return (choice1, choice2) -> result(Me.getFrom(choice2), Enemy.getFrom(choice1)).getValue() + Me.getFrom(choice2).getValue();
	}

	public static BiFunction<String, String, Integer> puzzle2() {
		return (choice1, choice2) -> forOutcome(Enemy.getFrom(choice1), Outcome.getFrom(choice2)).getValue() + Outcome.getFrom(choice2).getValue();
	}

	@TrackTime
	public void doPuzzle(String inputFile, BiFunction<String, String, Integer> calculator) {
		long sum = 0;
		for (String line : AocUtils.getAllLines(() -> inputFile)) {
			String[] choices = line.split(" ");
			log.debug("line is {}", line);
			sum += calculator.apply(choices[0], choices[1]);
			log.debug("sum is now {}", sum);
		}
		log.info("sum at end is {}", sum);
	}

}
