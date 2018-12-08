use std::env;

extern crate libloading;

use libloading::Library;

// Shim for answer module
struct Application(Library);
impl Application {
    fn get_answer(&self) -> String {
        unsafe {
            let f = self.0.get::<fn() -> String>(b"get_answer\0").unwrap();
            f()
        }
    }
}

fn main() {
    let args: Vec<String> = env::args().collect();
    let module = &args[2];
    let lib_path = format!("../{}/target/debug/libday.dylib", module);

    let app = Application(Library::new(lib_path).unwrap_or_else(|error| panic!("{}", error)));

    println!("answer: {}", app.get_answer());
}
