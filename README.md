# nano-id
A tiny, secure, URL-friendly unique string ID generator for Clojure and ClojureScript.

- **Secure**. It uses cryptographically strong random APIs.
- **Fast**. It's 20% faster than jnanoid (the java implementation).
- **Compact**. It uses a larger alphabet than UUID (A-Za-z0-9_-). So ID size was reduced from 36 to 21 symbols.
- **URL-Firendly**. It uses only URL-friendly characters. Perfect for unique identifiers in web applications.

```clojure
(require '[nano-id.core :refer [nano-id]])
(nano-id) ;; => "trxwfoC8mqB3Q8Wrdq4OQ"
```

[![Clojars Project](https://img.shields.io/clojars/v/nano-id.svg)](https://clojars.org/nano-id)
[![cljdoc badge](https://cljdoc.org/badge/nano-id/nano-id)](https://cljdoc.org/d/nano-id/nano-id/CURRENT)
[![CircleCI](https://circleci.com/gh/zelark/nano-id/tree/master.svg?style=svg)](https://circleci.com/gh/zelark/nano-id/tree/master)

## Benchmark
```
$ lein bench
##### UUID #####

 1.23µs 

Evaluation count : 501570 in 6 samples of 83595 calls.
             Execution time mean : 1.227453 µs
    Execution time std-deviation : 39.436371 ns
   Execution time lower quantile : 1.187412 µs ( 2.5%)
   Execution time upper quantile : 1.285405 µs (97.5%)
                   Overhead used : 2.337542 ns

##### nano-id (clojure) #####

 2.22µs 

Evaluation count : 276894 in 6 samples of 46149 calls.
             Execution time mean : 2.220927 µs
    Execution time std-deviation : 13.947733 ns
   Execution time lower quantile : 2.204769 µs ( 2.5%)
   Execution time upper quantile : 2.236207 µs (97.5%)
                   Overhead used : 2.337542 ns

##### jnanoid (java) #####

 2.67µs 

Evaluation count : 229920 in 6 samples of 38320 calls.
             Execution time mean : 2.666806 µs
    Execution time std-deviation : 19.558573 ns
   Execution time lower quantile : 2.646141 µs ( 2.5%)
   Execution time upper quantile : 2.689172 µs (97.5%)
                   Overhead used : 2.337542 ns
```
Configuration:
- MacBook Air (mid 2013), 1.3 GHz Intel Core i5, 8GB RAM;
- Java(TM) SE Runtime Environment (build 1.8.0_251-b08);
- Clojure 1.10.1.

## Usage
### Normal
Add to your project.clj: `[nano-id "0.11.0"]`.

The default implementation uses 64-character alphabet and generates 21-character IDs.
```clojure
user=> (require '[nano-id.core :refer [nano-id]])
nil

user=> (nano-id)
"NOBHihn110UuXbF2JiKxT"
```

If you want to reduce the ID size (and increase collision probability), you can pass the size as an argument.
```clojure
user=> (nano-id 10)
"N2g6IlJP0l"
```
Don’t forget to check the safety of your ID size via [collision probability calculator](https://zelark.github.io/nano-id-cc/).

### Custom alphabet or random number generator
Also you can provide your own alphabet as follow
```clojure
user=> (require '[nano-id.custom :refer [generate]])
nil

user=> (def my-nano-id (generate "abc"))
#'user/my-nano-id

user=> (my-nano-id 10)
"abcbabacba"
```

Also you can provide your custom random number generator, for example
```clojure
(let [alphabet "-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz"
      time-gen (fn [n]
                 (take n (iterate #(unsigned-bit-shift-right % 6)
                                  (quot (System/currentTimeMillis) 1000))))
      time-id  (generate alphabet time-gen)]
  (time-id 6))
"1RJu2O"
```
This encodes current time using a lexicographical alphabet.

## Tools
- [Nano ID Collision Calculator](https://zelark.github.io/nano-id-cc/)

## Other implementations
You can find implementations in other programming languages [here](https://github.com/ai/nanoid#other-programming-languages).

## License
Copyright © 2018-2020 Aleksandr Zhuravlev.

Code released under the [MIT License](https://github.com/zelark/nano-id/blob/master/LICENSE).

Based on the original [Nano ID](https://github.com/ai/nanoid) by [Andrey Sitnik](https://github.com/ai/).
