# nano-id
A tiny, secure, URL-friendly unique string ID generator for Clojure and ClojureScript.

- **Secure**. It uses cryptographically strong random APIs.
- **Fast**. It's ~35% faster than [jnanoid](https://github.com/aventrix/jnanoid), and almost as fast as UUID.
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

 1.60µs 

Evaluation count : 379296 in 6 samples of 63216 calls.
             Execution time mean : 1.597453 µs
    Execution time std-deviation : 7.981484 ns
   Execution time lower quantile : 1.586471 µs ( 2.5%)
   Execution time upper quantile : 1.605730 µs (97.5%)
                   Overhead used : 2.336439 ns

##### nano-id #####

 1.72µs 

Evaluation count : 357846 in 6 samples of 59641 calls.
             Execution time mean : 1.722310 µs
    Execution time std-deviation : 65.704837 ns
   Execution time lower quantile : 1.677106 µs ( 2.5%)
   Execution time upper quantile : 1.799135 µs (97.5%)
                   Overhead used : 2.336439 ns

##### jnanoid #####

 2.61µs 

Evaluation count : 235944 in 6 samples of 39324 calls.
             Execution time mean : 2.614275 µs
    Execution time std-deviation : 39.679415 ns
   Execution time lower quantile : 2.576874 µs ( 2.5%)
   Execution time upper quantile : 2.667085 µs (97.5%)
                   Overhead used : 2.336439 ns
```
Configuration:
- MacBook Air (mid 2013), 1.3 GHz Intel Core i5, 8GB RAM;
- Java(TM) SE Runtime Environment (build 1.8.0_251-b08);
- Clojure 1.10.1.

## Usage
### Leiningen or Boot
`[nano-id "1.0.0"]`

### Clojure CLI
`nano-id {:mvn/version "1.0.0"}`

### Default ID generator
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

### Custom ID generator
If for whatever reason the default implementation doesn't fit your project, you can build your own ID generator just passing your alphabet and ID size in `custom` function. It will give you back a new generator:
```clojure
user=> (require '[nano-id.core :refer [custom]])
nil

user=> (def my-nano-id (custom ".-" 6))
#'user/my-nano-id

user=> (my-nano-id)
"-.---."
```

Also you can provide your random bytes generator. In the example below we use this feature to encode the current time:
```clojure
(let [alphabet "-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz"
      time-gen (fn [n]
                 (->> (quot (System/currentTimeMillis) 1000)
                      (iterate #(unsigned-bit-shift-right % 6))
                      (take n)
                      reverse))
      time-id  (custom alphabet 6 time-gen)]
  (time-id))
"0TfMui"
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
