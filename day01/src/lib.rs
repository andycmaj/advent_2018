use std::collections::HashSet;
use std::env;
use std::fs;

#[no_mangle]
pub fn get_answer() -> String {
    let args: Vec<String> = env::args().collect();

    let contents = fs::read_to_string(&args[3]).expect("Something went wrong reading the file");
    let deltas: Vec<i32> = contents
        .split_whitespace()
        .map(|x| x.parse::<i32>().unwrap())
        .collect();

    // Part 1
    let sum: i32 = deltas.iter().sum();

    println!("part 1: {}", sum);

    let looper = deltas.into_iter().cycle();

    let mut seen = HashSet::new();
    seen.insert(0);

    let mut frequency = 0;
    for delta in looper {
        frequency += delta;
        if seen.contains(&frequency) {
            break;
        }
        seen.insert(frequency);
    }

    return frequency.to_string();
}
