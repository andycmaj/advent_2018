use std::fs;
use std::env;

#[no_mangle]
pub fn get_answer() -> String {
    let args: Vec<String> = env::args().collect();

    let contents = fs::read_to_string(&args[3]).expect("Something went wrong reading the file");

    let sum: i32 = contents
        .split('\n')
        .map(|x| x.parse::<i32>().unwrap())
        .sum();

    sum.to_string()
}
